using System.Collections.ObjectModel;

namespace I01._12_Landetxeren_erreserba
{
    /// <summary>
    /// Landetxeko gela bat: izena eta argazkia.
    /// </summary>
    public class Gela
    {
        /// <summary>Gelaren izena.</summary>
        public string Izena { get; set; } = "";

        /// <summary>Gelaren argazkiaren fitxategi-izena.</summary>
        public string Argazkia { get; set; } = "";
    }

    /// <summary>
    /// Erreserba bat: hasiera eta amaiera data (biak barne).
    /// </summary>
    /// <param name="Hasiera">Erreserbaren lehen eguna.</param>
    /// <param name="Amaiera">Erreserbaren azken eguna.</param>
    public record Tartea(DateTime Hasiera, DateTime Amaiera);

    /// <summary>
    /// Landetxeen erreserbaren orri nagusia: gelak erakusten ditu, prezioa kalkulatzen du
    /// eta erreserbak egiten ditu, data gainjartzen ez badira.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        // Prezioak eta aste bateko egun kopurua
        private const decimal ASTEKO_PREZIOA = 300m;
        private const decimal AIREPORTUA = 25m;
        private const decimal BISITA_GIDATUA = 150m;
        private const decimal UME_ZAINTZA_EGUNEKO = 40m;
        private const int ASTEAREN_EGUNAK = 7;

        // Gelen zerrenda
        private readonly List<Gela> gelak = new()
        {
            new Gela { Izena = "Gela 01", Argazkia = "gela1.jpg" },
            new Gela { Izena = "Gela 02", Argazkia = "gela2.jpg" },
            new Gela { Izena = "Gela 03", Argazkia = "gela3.jpg" },
            new Gela { Izena = "Gela 04", Argazkia = "gela4.jpg" },
            new Gela { Izena = "Gela 05", Argazkia = "gela5.jpg" },
        };

        // Gela bakoitzaren erreserbak (gelaren izena da gakoa)
        private readonly Dictionary<string, List<Tartea>> erreserbak = new();

        // Pantailako zerrendan erakusten diren erreserbak
        private readonly ObservableCollection<string> zerrenda = new();

        // Une honetan erakusten ari den gelaren posizioa
        private int unekoPosizioa = 0;

        // Denbora pasata gelaz aldatzeko tenporizadorea
        private IDispatcherTimer? timer;

        private readonly Random ausaz = new();

        // Hilabeteen izenak, erreserben testua idazteko
        private static readonly string[] hilabeteak =
        {
            "Urtarrilak", "Otsailak", "Martxoak", "Apirilak", "Maiatzak", "Ekainak",
            "Uztailak", "Abuztuak", "Irailak", "Urriak", "Azaroak", "Abenduak"
        };

        /// <summary>
        /// Eraikitzailea. Gela bakoitzarentzat erreserba zerrenda hutsa sortu,
        /// tenporizadorea prestatu eta lehenengo gela erakusten du.
        /// </summary>
        public MainPage()
        {
            InitializeComponent();

            foreach (var g in gelak) erreserbak[g.Izena] = new List<Tartea>();

            // Ezer egiten ez bada, 7 segundoro hurrengo gelara pasatzen da
            timer = Dispatcher.CreateTimer();
            timer.Interval = TimeSpan.FromSeconds(7);
            timer.Tick += Timer_Tick;

            lstErreserbak.ItemsSource = zerrenda;

            // Hasierako datak: gaur eta aste bat
            dpHasiera.Date = DateTime.Today;
            dpAmaiera.Date = DateTime.Today.AddDays(6);

            timer.Start();
            Kalkulatu();
            Erakutsi(0);
        }

        // ================= Datak =================

        // Aukeratutako hasiera eta amaiera datak (ordurik gabe)
        private DateTime Hasiera => (dpHasiera.Date ?? DateTime.Today).Date;
        private DateTime Amaiera => (dpAmaiera.Date ?? Hasiera).Date;

        /// <summary>
        /// Aukeratutako egun kopurua kalkulatzen du, bi egunak barne.
        /// </summary>
        /// <returns>Egun kopurua (inoiz ez da 0 baino txikiagoa).</returns>
        private int EgunKopurua()
        {
            // Bi egunak barne: 2tik 8ra = 7 egun
            int egunak = (Amaiera - Hasiera).Days + 1;
            return Math.Max(egunak, 0);
        }

        /// <summary>
        /// Hasiera data aldatzean, amaiera data zuzendu eta prezioa berriz kalkulatzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void DpHasiera_DateSelected(object? sender, DateChangedEventArgs e)
        {
            // Amaiera ezin da hasiera baino lehenago izan
            if (Amaiera < Hasiera) dpAmaiera.Date = Hasiera;
            Kalkulatu();
            TimerBerrabiarazi();
        }

        /// <summary>
        /// Amaiera data aldatzean, hasiera data zuzendu eta prezioa berriz kalkulatzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void DpAmaiera_DateSelected(object? sender, DateChangedEventArgs e)
        {
            if (Amaiera < Hasiera) dpHasiera.Date = Amaiera;
            Kalkulatu();
            TimerBerrabiarazi();
        }

        // ================= Prezioen kalkulua =================

        /// <summary>
        /// Egonaldiaren prezioa, gehigarriak eta guztira kalkulatu eta pantailan jartzen ditu.
        /// </summary>
        private void Kalkulatu()
        {
            int egunak = EgunKopurua();

            // Egonaldia: asteko prezioa 7 egunetan banatuta, egun kopuruaz biderkatuta
            decimal egonaldia = Math.Round(ASTEKO_PREZIOA / ASTEAREN_EGUNAK * egunak, 2);

            // Gehigarriak: markatutakoak batu (ume zaintza egunez ordaintzen da)
            decimal gehigarriak = 0;
            if (chkAireportua.IsChecked) gehigarriak += AIREPORTUA;
            if (chkBisita.IsChecked) gehigarriak += BISITA_GIDATUA;
            if (chkUme.IsChecked) gehigarriak += UME_ZAINTZA_EGUNEKO * egunak;

            txtEgonaldia.Text = egonaldia.ToString("0.##");
            txtGehigarriak.Text = gehigarriak.ToString("0.##");
            txtGuztira.Text = (egonaldia + gehigarriak).ToString("0.##");
        }

        /// <summary>
        /// Gehigarri bat markatu edo desmarkatzean, prezioa berriz kalkulatzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void Aldaketa(object? sender, CheckedChangedEventArgs e)
        {
            Kalkulatu();
            TimerBerrabiarazi();
        }

        // ================= Gelak (irudia + geziak) =================

        /// <summary>
        /// Aurreko gelara pasatzen da.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void BtnAurrekoa_Clicked(object? sender, EventArgs e)
        {
            // Lehenengoan bagaude, azkenera pasatzen da
            Erakutsi((unekoPosizioa - 1 + gelak.Count) % gelak.Count);
            TimerBerrabiarazi();
        }

        /// <summary>
        /// Hurrengo gelara pasatzen da.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void BtnHurrengoa_Clicked(object? sender, EventArgs e)
        {
            // Azkenean bagaude, lehenengora pasatzen da
            Erakutsi((unekoPosizioa + 1) % gelak.Count);
            TimerBerrabiarazi();
        }

        /// <summary>
        /// Emandako posizioko gelaren izena, argazkia eta erreserbak erakusten ditu.
        /// </summary>
        /// <param name="indizea">Erakutsi beharreko gelaren posizioa.</param>
        private void Erakutsi(int indizea)
        {
            unekoPosizioa = indizea;
            lblGela.Text = gelak[indizea].Izena;
            imgGela.Source = gelak[indizea].Argazkia;

            // ● = aukeratutako gela, ○ = besteak
            lblPuntuak.Text = string.Join(" ", gelak.Select((g, i) => i == indizea ? "●" : "○"));

            EguneratuZerrenda();
        }

        // ================= Erreserba =================

        /// <summary>
        /// Erreserba egiten du aukeratutako gelan eta datetan, tartea libre badago.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private async void BtnErreserba_Clicked(object? sender, EventArgs e)
        {
            TimerBerrabiarazi();

            var gela = gelak[unekoPosizioa];
            DateTime hasiera = Hasiera;
            DateTime amaiera = Amaiera;
            var gelaErreserbak = erreserbak[gela.Izena];

            // Bi tarte gainjartzen dira: batek bestea hasi baino lehen ez badu amaitzen
            bool okupatuta = amaiera < hasiera ||
                             gelaErreserbak.Any(t => hasiera <= t.Amaiera && t.Hasiera <= amaiera);

            if (okupatuta)
            {
                await DisplayAlertAsync("Erreserba", "Aste horretan ezin da erreserba egin", "Ados");
                return;
            }

            // Erreserba gorde eta zerrenda dataren arabera ordenatu
            gelaErreserbak.Add(new Tartea(hasiera, amaiera));
            gelaErreserbak.Sort((a, b) => a.Hasiera.CompareTo(b.Hasiera));
            EguneratuZerrenda();
            await DisplayAlertAsync("Erreserba", "Adierazitako astean egindako erreserba", "Ados");
        }

        /// <summary>
        /// Tarte bat testu bihurtzen du. Adibidez: "2024: Urriak 2 - 8"
        /// edo "2025: Urtarrilak 27 - Otsailak 2".
        /// </summary>
        /// <param name="t">Testu bihurtu beharreko tartea.</param>
        /// <returns>Tartea idatzita.</returns>
        private static string TarteaFormatua(Tartea t)
        {
            string hasiera = $"{t.Hasiera.Year}: {hilabeteak[t.Hasiera.Month - 1]} {t.Hasiera.Day}";

            // Amaieran bakarrik jartzen da aldatzen den zatia (urtea, hilabetea edo eguna)
            string amaiera;
            if (t.Hasiera.Year != t.Amaiera.Year)
                amaiera = $"{t.Amaiera.Year}: {hilabeteak[t.Amaiera.Month - 1]} {t.Amaiera.Day}";
            else if (t.Hasiera.Month != t.Amaiera.Month)
                amaiera = $"{hilabeteak[t.Amaiera.Month - 1]} {t.Amaiera.Day}";
            else
                amaiera = $"{t.Amaiera.Day}";

            return $"{hasiera} - {amaiera}";
        }

        /// <summary>
        /// Pantailako erreserba zerrenda eguneratzen du, uneko gelakoekin.
        /// </summary>
        private void EguneratuZerrenda()
        {
            zerrenda.Clear();
            foreach (var t in erreserbak[gelak[unekoPosizioa].Izena])
                zerrenda.Add(TarteaFormatua(t));
        }

        // ================= Tenporizadorea =================

        /// <summary>
        /// Tenporizadorea gelditu eta berriz hasten du, denbora zerotik kontatzeko.
        /// </summary>
        private void TimerBerrabiarazi()
        {
            timer?.Stop();
            timer?.Start();
        }

        /// <summary>
        /// Denbora pasatzean, hurrengo gelara pasatzen da automatikoki.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen tenporizadorea.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void Timer_Tick(object? sender, EventArgs e)
        {
            // 1-2-3-4-5-1...
            Erakutsi((unekoPosizioa + 1) % gelak.Count);
        }
    }
}