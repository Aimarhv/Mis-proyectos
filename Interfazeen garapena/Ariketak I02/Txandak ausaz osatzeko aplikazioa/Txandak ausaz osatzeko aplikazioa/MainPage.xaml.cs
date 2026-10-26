using System.Collections.ObjectModel;
using System.Text;

namespace Txandak_ausaz_osatzeko_aplikazioa
{
    /// <summary>
    /// Orri nagusia: .txt fitxategi batetik ikasleen izenak kargatu, ausaz banan-banan atera
    /// (errepikatu gabe) eta amaieran ateratze ordena fitxategi batean gordetzen du.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        /// <summary>Oraindik atera gabe dauden izenak.</summary>
        private List<string> geratzenDirenak = new();

        /// <summary>Jada atera diren izenak, ateratze ordenan.</summary>
        private List<string> ateratakoak = new();

        /// <summary>Eskuineko zerrendan ikusten dena (CollectionView-ren ItemsSource).</summary>
        private ObservableCollection<string> zerrenda = new();

        /// <summary>Ausazko zenbakiak sortzeko.</summary>
        private Random ausazkoa = new();

        /// <summary>Fitxategian zeuden izenen kopuru osoa.</summary>
        private int guztira = 0;

        /// <summary>
        /// Eraikitzailea. Interfazea hasieratu eta zerrenda CollectionView-ra lotzen du.
        /// </summary>
        public MainPage()
        {
            InitializeComponent();

            // Zerrenda ikusgarria CollectionView-ri lotu
            Zerrenda.ItemsSource = zerrenda;
        }

        /// <summary>
        /// .txt fitxategi bat aukeratu eta bertako izenak kargatzen ditu (lerro bakoitzeko bat).
        /// </summary>
        /// <param name="sender">Gertaera piztu duen botoia.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private async void OnIrekiClicked(object sender, EventArgs e)
        {
            try
            {
                // Plataforma bakoitzerako .txt fitxategiak soilik erakusteko iragazkia
                var txtMota = new FilePickerFileType(new Dictionary<DevicePlatform, IEnumerable<string>>
                {
                    { DevicePlatform.WinUI, new[] { ".txt" } },
                    { DevicePlatform.Android, new[] { "text/plain" } },
                    { DevicePlatform.iOS, new[] { "public.plain-text" } },
                    { DevicePlatform.MacCatalyst, new[] { "public.plain-text" } }
                });

                var fitxategia = await FilePicker.Default.PickAsync(new PickOptions
                {
                    PickerTitle = "Aukeratu izenen fitxategia",
                    FileTypes = txtMota
                });

                // Erabiltzaileak utzi badu, ez da ezer egiten
                if (fitxategia == null)
                    return;

                // Lerroz lerro irakurri, lerro hutsak baztertuta
                var izenak = new List<string>();
                using (var stream = await fitxategia.OpenReadAsync())
                using (var irakurlea = new StreamReader(stream))
                {
                    string? lerroa;
                    while ((lerroa = await irakurlea.ReadLineAsync()) != null)
                    {
                        lerroa = lerroa.Trim();
                        if (lerroa != "")
                            izenak.Add(lerroa);
                    }
                }

                // Fitxategia hutsik badago, abisua eman eta amaitu
                if (izenak.Count == 0)
                {
                    await DisplayAlertAsync("Hutsik", "Fitxategia hutsik dago.", "Ados");
                    return;
                }

                // Dena hasieratu izen berriekin
                geratzenDirenak = izenak;
                ateratakoak.Clear();
                zerrenda.Clear();
                guztira = izenak.Count;

                Fitxategia.Text = fitxategia.FileName;
                Izena.Text = "-";
                IkasleZenbakia.Text = "";
                Atera.IsEnabled = true;
                Gorde.IsEnabled = false;
                EguneratuPantaila();
            }
            catch (Exception ex)
            {
                await DisplayAlertAsync("Errorea", "Ezin izan da fitxategia irakurri.\n" + ex.Message, "Ados");
            }
        }

        /// <summary>
        /// Ausaz izen bat aukeratzen du geratzen direnen artean, pantailan erakutsi eta
        /// ateratakoen zerrendan gehitzen du. Izen guztiak atera direnean, gordetzea gaitzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen botoia.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnAteraClicked(object sender, EventArgs e)
        {
            if (geratzenDirenak.Count == 0)
                return;

            // Ausazko posizio bat aukeratu eta izena kendu (horrela ez da errepikatuko)
            int posizioa = ausazkoa.Next(geratzenDirenak.Count);
            string izena = geratzenDirenak[posizioa];
            geratzenDirenak.RemoveAt(posizioa);
            ateratakoak.Add(izena);

            int zenbakia = ateratakoak.Count;

            // Izen handia eta ordena-zenbakia eguneratu
            Izena.Text = izena;
            IkasleZenbakia.Text = $"{zenbakia}. ikaslea";

            // Zerrendan goian jarri (berriena goian)
            zerrenda.Insert(0, $"{zenbakia}. {izena}");

            EguneratuPantaila();

            // Denak atera badira: botoia desaktibatu eta gordetzea gaitu
            if (geratzenDirenak.Count == 0)
            {
                Atera.IsEnabled = false;
                Gorde.IsEnabled = true;
                GeratzenDiraLbl.Text = "Denak atera dira";
            }
        }

        /// <summary>
        /// Ateratako izen guztiak poltsara itzultzen ditu eta zerrenda garbitzen du,
        /// fitxategia berriro ireki gabe.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen botoia.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnBerrabiaraziClicked(object sender, EventArgs e)
        {
            // Fitxategirik kargatu ez bada, ez dago ezer berrabiaraztekorik
            if (guztira == 0)
                return;

            // Ateratakoak berriro geratzen direnen artean sartu
            geratzenDirenak.AddRange(ateratakoak);
            ateratakoak.Clear();
            zerrenda.Clear();

            Izena.Text = "-";
            IkasleZenbakia.Text = "";
            Atera.IsEnabled = true;
            Gorde.IsEnabled = false;
            EguneratuPantaila();
        }

        /// <summary>
        /// Ateratze ordena fitxategi batean gordetzen du. Windows-en, izena eta kokalekua
        /// erabiltzaileak aukeratzen ditu "Gorde honela" elkarrizketa-koadroarekin.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen botoia.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private async void OnGordezerrendafitxateginClicked(object sender, EventArgs e)
        {
            try
            {
                // Idatziko den testua: lerro bakoitzean "1. Izena"
                var testua = new StringBuilder();
                for (int i = 0; i < ateratakoak.Count; i++)
                    testua.AppendLine($"{i + 1}. {ateratakoak[i]}");

#if WINDOWS
                // Windows-eko "Gorde honela" koadroa sortu
                var aukeratzailea = new Windows.Storage.Pickers.FileSavePicker();

                // Koadroa leiho honekin lotu (beharrezkoa WinUI-n)
                var leihoa = (Microsoft.UI.Xaml.Window)Window!.Handler!.PlatformView!;
                var leihoKudeatzailea = WinRT.Interop.WindowNative.GetWindowHandle(leihoa);
                WinRT.Interop.InitializeWithWindow.Initialize(aukeratzailea, leihoKudeatzailea);

                // Proposatutako izena eta fitxategi mota
                aukeratzailea.SuggestedFileName = "ordena";
                aukeratzailea.FileTypeChoices.Add("Testu fitxategia", new List<string> { ".txt" });

                var fitxategia = await aukeratzailea.PickSaveFileAsync();

                // Erabiltzaileak utzi badu, ez da ezer egiten
                if (fitxategia == null)
                    return;

                await Windows.Storage.FileIO.WriteTextAsync(fitxategia, testua.ToString());
                await DisplayAlertAsync("Gordeta", "Zerrenda gorde da.", "Ados");
#else
                // Beste plataformetan: aplikazioaren karpetan gorde, kokalekua aukeratu gabe
                string bidea = Path.Combine(FileSystem.Current.AppDataDirectory, "ordena.txt");
                await File.WriteAllTextAsync(bidea, testua.ToString());
                await DisplayAlertAsync("Gordeta", "Zerrenda hemen gorde da:\n" + bidea, "Ados");
#endif
            }
            catch (Exception ex)
            {
                await DisplayAlertAsync("Errorea", "Ezin izan da gorde.\n" + ex.Message, "Ados");
            }
        }

        /// <summary>
        /// Pantailako testuak, aurrerapen barra eta kontagailuak uneko egoerarekin eguneratzen ditu.
        /// </summary>
        private void EguneratuPantaila()
        {
            int atera = ateratakoak.Count;

            IzenKopurua.Text = $"{guztira} izen kargatuta";
            ProgresuaLbl.Text = $"{atera} / {guztira}";

            // Barra 0 eta 1 artean; fitxategirik ez dagoenean 0 (zero-rekin zatitzea saihesteko)
            ProgresuBarra.Progress = guztira == 0 ? 0 : (double)atera / guztira;

            GeratzenDiraLbl.Text = $"{geratzenDirenak.Count} geratzen dira";
            ZerrendaKopurua.Text = $"{atera} izen";
        }
    }
}