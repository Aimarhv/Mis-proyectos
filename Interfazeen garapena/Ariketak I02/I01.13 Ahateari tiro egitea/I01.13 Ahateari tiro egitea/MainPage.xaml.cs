using System.IO;

namespace I01._13_Ahateari_tiro_egitea
{
    /// <summary>
    /// Helburu mota baten datuak (ahate arrunta, ahate urrezkoa...).
    /// </summary>
    /// <param name="Irudia">Irudiaren fitxategi-izena.</param>
    /// <param name="Abiadura">Zenbat pixel mugitzen den 30 ms-ro.</param>
    /// <param name="Puntuak">Jotzean lortzen den puntuazioa.</param>
    /// <param name="Pisua">Agertzeko probabilitate erlatiboa.</param>
    /// <param name="YMin">Altuera minimoa (eremuaren altueraren ehunekoa).</param>
    /// <param name="YMax">Altuera maximoa (eremuaren altueraren ehunekoa).</param>
    /// <param name="Segundoak">Jotzean gehitzen diren segundo gehigarriak.</param>
    /// <param name="Dardara">Gora-beherako mugimenduaren zabalera (zigzaga).</param>
    /// <param name="ZigzagAbiadura">Zigzaga zenbat azkar egiten duen (handiagoa = azkarragoa).</param>
    public record HelburuMota(string Irudia, double Abiadura, int Puntuak, int Pisua,
                              double YMin, double YMax, int Segundoak = 0,
                              double Dardara = 0, double ZigzagAbiadura = 0.12);

    /// <summary>
    /// Pantailan dagoen helburu bat (une horretako egoera).
    /// </summary>
    public class Helburua
    {
        /// <summary>Pantailan marrazten den irudia.</summary>
        public Image Irudia { get; set; } = null!;

        /// <summary>Helburuaren mota (abiadura, puntuak...).</summary>
        public HelburuMota Mota { get; set; } = null!;

        /// <summary>Posizio horizontala.</summary>
        public double X { get; set; }

        /// <summary>Posizio bertikala.</summary>
        public double Y { get; set; }

        /// <summary>Zigzagaren erdiko altuera.</summary>
        public double YOinarria { get; set; }

        /// <summary>Zigzagaren fasea (sinuaren angelua).</summary>
        public double Fasea { get; set; }
    }

    /// <summary>
    /// "Ahateari tiro egitea" jokoaren orri nagusia: ahateak sortu eta mugitzen ditu,
    /// sagua tiro-mira bihurtzen du eta tiroak, puntuazioa eta denbora kudeatzen ditu.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        // Jokoaren konstanteak: iraupena, ahateen eta tiro-miraren tamaina, gehienezko ahateak
        private const int PARTIDA_SEGUNDOAK = 30;
        private const double TAMAINA = 80;
        private const double DIANA_TAMAINA = 50;
        private const int GEHIENEZ = 6;

        // Ahate arruntak (ausaz ateratzen dira, "Pisua"-ren arabera)
        private static readonly HelburuMota[] motak =
        {
            new("ahate1.png", 8,  10, 60, 0.25, 0.60, Dardara: 20, ZigzagAbiadura: 0.28),
            new("ahate2.png", 13, 20, 40, 0.25, 0.60, Dardara: 35, ZigzagAbiadura: 0.15),
        };

        // Ahate urrezkoa: ez da ausaz ateratzen, partida bakoitzean 2 aldiz bakarrik
        private static readonly HelburuMota urrezkoa =
            new("ahate_urrezkoa.png", 17, 50, 0, 0.15, 0.50, Dardara: 40, ZigzagAbiadura: 0.12);

        // Tenporizadoreak: atzeranzko kontaketa, mugimendua eta ahateen sorrera
        private IDispatcherTimer? kronometroa, mugimendua, sortzailea;

        private readonly List<Helburua> helburuak = new();
        private readonly Random ausaz = new();

        private int geratzenDirenSegundoak = PARTIDA_SEGUNDOAK;
        private int puntuak = 0;
        private bool jokoanDago = false;

        // Partida hasi eta zein segundutan aterako den ahate urrezkoa
        private int urrezkoLehena, urrezkoBigarrena;

        // Tiroa blokeatzen duten oztopoak
        private Image[] Oztopoak => new[] { imgZuhaitza, imgHarria };

        /// <summary>
        /// Eraikitzailea. Tenporizadoreak prestatu, botoiak lotu eta saguaren
        /// gertaerak (tiro-mira mugitzea eta tiro egitea) konfiguratzen ditu.
        /// </summary>
        public MainPage()
        {
            InitializeComponent();

            kronometroa = Dispatcher.CreateTimer();
            kronometroa.Interval = TimeSpan.FromSeconds(1);
            kronometroa.Tick += Kronometroa_Tick;

            mugimendua = Dispatcher.CreateTimer();
            mugimendua.Interval = TimeSpan.FromMilliseconds(30);
            mugimendua.Tick += Mugimendua_Tick;

            sortzailea = Dispatcher.CreateTimer();
            sortzailea.Interval = TimeSpan.FromMilliseconds(1800);
            sortzailea.Tick += (s, e) => HelburuaSortu();

            btnHasi.Clicked += BtnHasi_Clicked;
            btnAmaitu.Clicked += BtnAmaitu_Clicked;

            // Sagua: tiro-mira mugitu eta ezkerreko klikarekin tiro egin
            var ptr = new PointerGestureRecognizer();

            ptr.PointerMoved += (s, e) =>
            {
                if (!jokoanDago) return;
                var p = e.GetPosition(Zelaia);
                if (p is null) return;
                if (!imgDiana.IsVisible) GureKurtsorea(true);

                // Tiro-mira saguaren erdian kokatzen da
                AbsoluteLayout.SetLayoutBounds(imgDiana,
                    new Rect(p.Value.X - DIANA_TAMAINA / 2, p.Value.Y - DIANA_TAMAINA / 2,
                             DIANA_TAMAINA, DIANA_TAMAINA));
            };

            ptr.PointerPressed += (s, e) =>
            {
                if (!jokoanDago) return;

#if WINDOWS
                // Ezkerreko botoiarekin bakarrik tiro egiten da
                var args = e.PlatformArgs?.PointerRoutedEventArgs;
                if (args != null &&
                    !args.GetCurrentPoint(null).Properties.IsLeftButtonPressed)
                    return;
#endif

                var p = e.GetPosition(Zelaia);
                if (p is null) return;
                Tiroa(p.Value);
            };

            ptr.PointerExited += (s, e) => GureKurtsorea(false);
            Zelaia.GestureRecognizers.Add(ptr);

            EguneratuDenbora();
            EguneratuPuntuak();
        }

        // ================= Botoiak =================

        /// <summary>
        /// "Hasi" botoia sakatzean, partida berri bat prestatu eta tenporizadoreak martxan jartzen ditu.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void BtnHasi_Clicked(object? sender, EventArgs e)
        {
            if (jokoanDago) return;

            puntuak = 0;
            geratzenDirenSegundoak = PARTIDA_SEGUNDOAK;
            jokoanDago = true;
            HelburuakGarbitu();
            EguneratuDenbora();
            EguneratuPuntuak();

            // Ahate urrezkoak bi agerraldi ditu, elkarren ondoan ez daudenak:
            // bata lehen erdian (3-8 s) eta bestea bigarren erdian (12-17 s)
            urrezkoLehena = ausaz.Next(3, 9);
            urrezkoBigarrena = ausaz.Next(12, 18);

            kronometroa?.Start();
            mugimendua?.Start();
            sortzailea?.Start();

            // Lehenengo ahatea berehala ateratzen da
            HelburuaSortu();
        }

        /// <summary>
        /// "Amaitu" botoia sakatzean, partida bertan behera uzten du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private async void BtnAmaitu_Clicked(object? sender, EventArgs e)
        {
            if (!jokoanDago) return;
            await JokoaAmaitu();
        }

        // ================= Atzeranzko kontaketa =================

        /// <summary>
        /// Segundoro exekutatzen da: denbora jaitsi, ahate urrezkoa noiz atera behar den
        /// begiratu eta denbora bukatzean partida amaitzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen tenporizadorea.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private async void Kronometroa_Tick(object? sender, EventArgs e)
        {
            geratzenDirenSegundoak--;
            EguneratuDenbora();

            // Partidan igaro diren segunduak; unea iristean ahate urrezkoa ateratzen da
            int igaroak = PARTIDA_SEGUNDOAK - geratzenDirenSegundoak;
            if (igaroak == urrezkoLehena || igaroak == urrezkoBigarrena)
                HelburuaSortu(urrezkoa);

            if (geratzenDirenSegundoak <= 0)
                await JokoaAmaitu();
        }

        /// <summary>
        /// Partida amaitzen du: tenporizadoreak gelditu, ahateak ezabatu eta emaitza erakusten du.
        /// </summary>
        private async Task JokoaAmaitu()
        {
            kronometroa?.Stop();
            mugimendua?.Stop();
            sortzailea?.Stop();
            jokoanDago = false;
            HelburuakGarbitu();
            GureKurtsorea(false);

            await DisplayAlertAsync("Partida amaitu da", $"Puntuak: {puntuak}", "Ados");

            // Hurrengo partidarako denbora berrezarri
            geratzenDirenSegundoak = PARTIDA_SEGUNDOAK;
            EguneratuDenbora();
        }

        // ================= Tiroa =================

        /// <summary>
        /// Tiro bat egiten du emandako puntuan: soinua jo, oztoporik dagoen begiratu
        /// eta ahaterik jotzen duen egiaztatzen du.
        /// </summary>
        /// <param name="punto">Klikaren posizioa joko-eremuan.</param>
        private void Tiroa(Point punto)
        {
            _ = TiroaJo();

            // Oztopoa tartean badago, bala ez da pasatzen (ahatea babestuta dago).
            // Inflate negatiboa: hitbox-a irudia baino pixka bat txikiagoa da
            foreach (var oz in Oztopoak)
                if (oz.Bounds.Inflate(-15, -10).Contains(punto))
                    return;

            // Klikaren puntuan ahaterik badago, puntuak gehitu eta kendu
            foreach (var h in helburuak)
            {
                var rect = new Rect(h.X, h.Y, TAMAINA, TAMAINA);
                if (!rect.Contains(punto)) continue;

                PuntuakGehitu(h.Mota.Puntuak);
                if (h.Mota.Segundoak > 0)
                {
                    geratzenDirenSegundoak += h.Mota.Segundoak;
                    EguneratuDenbora();
                }

                Zelaia.Children.Remove(h.Irudia);
                helburuak.Remove(h);

                // Tiro bat = helburu bat
                break;
            }
        }

        /// <summary>
        /// "tiro.wav" soinua jotzen du. Audioak huts egiten badu, jokoak aurrera jarraitzen du.
        /// </summary>
        private async Task TiroaJo()
        {
            try
            {
                using var stream = await FileSystem.OpenAppPackageFileAsync("tiro.wav");
                var ms = new MemoryStream();
                await stream.CopyToAsync(ms);
                ms.Position = 0;

#if WINDOWS
                var player = new Windows.Media.Playback.MediaPlayer();
                player.Source = Windows.Media.Core.MediaSource.CreateFromStream(
                    System.IO.WindowsRuntimeStreamExtensions.AsRandomAccessStream(ms), "audio/wav");

                // Soinua amaitzean memoria askatu
                player.MediaEnded += (s, e) => { player.Dispose(); ms.Dispose(); };
                player.Play();
#endif
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Audio error: {ex}");
            }
        }

        // ================= Kurtsorea =================

        /// <summary>
        /// Tiro-mira erakutsi edo ezkutatzen du, eta sistemako kurtsorea alderantziz.
        /// </summary>
        /// <param name="aktibo">True bada, tiro-mira erakusten da eta gezia ezkutatzen da.</param>
        private void GureKurtsorea(bool aktibo)
        {
            imgDiana.IsVisible = aktibo;
            SistemaKurtsoreaEzkutatu(aktibo);
        }

#if WINDOWS
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        private static extern int ShowCursor(bool bShow);

        private bool kurtsoreaEzkutuan;

        /// <summary>
        /// Windows-eko gezia ezkutatu edo erakusten du (ShowCursor-en deiak orekatuta mantentzen ditu).
        /// </summary>
        /// <param name="ezkutatu">True bada, gezia ezkutatzen da.</param>
        private void SistemaKurtsoreaEzkutatu(bool ezkutatu)
        {
            if (ezkutatu == kurtsoreaEzkutuan) return;
            kurtsoreaEzkutuan = ezkutatu;
            ShowCursor(!ezkutatu);
        }
#else
        /// <summary>
        /// Beste plataformetan ez du ezer egiten.
        /// </summary>
        /// <param name="ezkutatu">Erabiltzen ez den parametroa.</param>
        private void SistemaKurtsoreaEzkutatu(bool ezkutatu) { }
#endif

        // ================= Helburuak =================

        /// <summary>
        /// Ausazko ahate mota aukeratzen du, "Pisua"-ren arabera.
        /// </summary>
        /// <returns>Aukeratutako helburu mota.</returns>
        private HelburuMota MotaAukeratu()
        {
            int guztira = motak.Sum(m => m.Pisua);
            int r = ausaz.Next(guztira);

            // Ausazko zenbakia zein motari dagokion bilatu
            foreach (var m in motak)
            {
                if (r < m.Pisua) return m;
                r -= m.Pisua;
            }
            return motak[0];
        }

        /// <summary>
        /// Helburu berri bat sortzen du ezkerreko aldean. Parametrorik gabe ausazko ahatea
        /// sortzen da; parametroarekin, mota zehatz hori (ahate urrezkoa).
        /// </summary>
        /// <param name="behartua">Sortu beharreko mota zehatza, edo null ausazkoa bada.</param>
        private void HelburuaSortu(HelburuMota? behartua = null)
        {
            if (!jokoanDago || Zelaia.Width <= 0) return;

            // Gehiegi badaude ez da gehiago sortzen (urrezkoa izan ezik)
            if (behartua is null && helburuak.Count >= GEHIENEZ) return;

            var mota = behartua ?? MotaAukeratu();

            // Altuera ausazkoa, motaren tartearen barruan
            double y = Zelaia.Height * (mota.YMin + ausaz.NextDouble() * (mota.YMax - mota.YMin));

            // InputTransparent: klikak ez ditu irudiak hartzen, Zelaia-k jasotzen ditu
            var irudia = new Image
            {
                Source = mota.Irudia,
                Aspect = Aspect.AspectFit,
                WidthRequest = TAMAINA,
                HeightRequest = TAMAINA,
                InputTransparent = true
            };

            // X = -TAMAINA: pantailatik kanpo hasten da, ezkerretik
            var h = new Helburua { Irudia = irudia, Mota = mota, X = -TAMAINA, Y = y, YOinarria = y };

            AbsoluteLayout.SetLayoutBounds(irudia, new Rect(h.X, h.Y, TAMAINA, TAMAINA));

            // 1. posizioan: atzealdearen gainean, baina oztopoen eta tiro-miraren azpian
            Zelaia.Children.Insert(1, irudia);
            helburuak.Add(h);
        }

        /// <summary>
        /// Helburu guztiak eskuinera mugitzen ditu (30 ms-ro) zigzaga eginez,
        /// eta eskuinetik ateratzen direnak ezabatzen ditu.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen tenporizadorea.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void Mugimendua_Tick(object? sender, EventArgs e)
        {
            // Atzetik aurrera ibiltzen gara, ibiltzean elementuak kendu ahal izateko
            for (int i = helburuak.Count - 1; i >= 0; i--)
            {
                var h = helburuak[i];

                h.X += h.Mota.Abiadura;
                h.Fasea += h.Mota.ZigzagAbiadura;
                h.Y = h.YOinarria + Math.Sin(h.Fasea) * h.Mota.Dardara;

                if (h.X > Zelaia.Width)
                {
                    Zelaia.Children.Remove(h.Irudia);
                    helburuak.RemoveAt(i);
                }
                else
                {
                    AbsoluteLayout.SetLayoutBounds(h.Irudia, new Rect(h.X, h.Y, TAMAINA, TAMAINA));
                }
            }
        }

        /// <summary>
        /// Pantailako helburu guztiak ezabatzen ditu.
        /// </summary>
        private void HelburuakGarbitu()
        {
            foreach (var h in helburuak)
                Zelaia.Children.Remove(h.Irudia);
            helburuak.Clear();
        }

        // ================= Markagailua eta denbora =================

        /// <summary>
        /// Geratzen den denbora "m:ss" formatuan erakusten du.
        /// </summary>
        private void EguneratuDenbora()
        {
            lblDenbora.Text = $"{geratzenDirenSegundoak / 60}:{geratzenDirenSegundoak % 60:00}";
        }

        /// <summary>
        /// Puntuazioa eguneratzen du markagailuan.
        /// </summary>
        private void EguneratuPuntuak() => lblPuntuak.Text = $"Puntuak {puntuak}";

        /// <summary>
        /// Puntuazioari kopurua gehitzen dio (negatiboa bada, kendu). Inoiz ez da 0 azpitik jaisten.
        /// </summary>
        /// <param name="kopurua">Gehitu beharreko puntuak.</param>
        private void PuntuakGehitu(int kopurua)
        {
            puntuak = Math.Max(0, puntuak + kopurua);
            EguneratuPuntuak();
        }
    }
}