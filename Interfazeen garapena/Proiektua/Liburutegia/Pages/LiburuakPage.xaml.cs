using Microsoft.Maui.Controls.Shapes;

namespace Liburutegia;

public partial class LiburuakPage : ContentPage
{
    static readonly string[] Irizpideak = { "Denak", "Izenburua", "Egilea", "ISBN", "Generoa", "Editoriala" };

    readonly DataService _d = DataService.Instance;
    Liburua? _hautatua;
    string _irizpidea = "Denak";

    public LiburuakPage()
    {
        InitializeComponent();
        TxipakSortu();
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();

        LblAgurra.Text = DateTime.Now.Hour switch
        {
            < 12 => "Egun on",
            < 20 => "Arratsalde on",
            _ => "Gabon"
        };
        LblKopLiburuak.Text = _d.Liburuak.Count.ToString();
        LblKopEskuragarri.Text = _d.Liburuak.Count(l => l.Eskuragarri).ToString();
        LblKopEgileak.Text = _d.Egileak.Count.ToString();

        // Beste pantaila batetik datorren liburua (adib. Egileak pantailako klik bikoitza)
        var pending = _d.PendingLiburua;
        _d.PendingLiburua = null;
        if (pending != null)
        {
            _hautatua = pending;
            BilatuBarra.Text = "";
            _irizpidea = "Denak";
            TxipakMargotu();
        }
        Iragazi();
    }

    // ---------- Irizpide-txipak ----------
    static Color Kolorea(string gakoa) => (Color)Application.Current!.Resources[gakoa];

    void TxipakSortu()
    {
        foreach (var irizpidea in Irizpideak)
        {
            var txipa = new Border
            {
                StrokeShape = new RoundRectangle { CornerRadius = 999 },
                StrokeThickness = 1,
                Padding = new Thickness(14, 6),
                Content = new Label { Text = irizpidea, FontSize = 13, FontAttributes = FontAttributes.Bold },
                ClassId = irizpidea
            };
            var tap = new TapGestureRecognizer();
            tap.Tapped += (_, _) =>
            {
                _irizpidea = irizpidea;
                TxipakMargotu();
                Iragazi();
            };
            txipa.GestureRecognizers.Add(tap);
            IrizpideTxipak.Children.Add(txipa);
        }
        TxipakMargotu();
    }

    void TxipakMargotu()
    {
        foreach (var txipa in IrizpideTxipak.Children.OfType<Border>())
        {
            bool aktibo = txipa.ClassId == _irizpidea;
            txipa.BackgroundColor = aktibo ? Kolorea("Nagusia") : Kolorea("Gainazala");
            txipa.Stroke = aktibo ? Kolorea("Nagusia") : Kolorea("Ertza");
            if (txipa.Content is Label l) l.TextColor = aktibo ? Colors.White : Kolorea("Grisa");
        }
        BilatuBarra.Placeholder = _irizpidea == "Denak"
            ? "Bilatu izenburua, egilea, ISBNa..."
            : $"Bilatu ({_irizpidea})...";
    }

    // ---------- Bilaketa (testua aldatu ahala eguneratzen da) ----------
    void OnBilaketaAldatu(object? sender, TextChangedEventArgs e) => Iragazi();

    static bool Barne(string? iturria, string t) =>
        iturria?.Contains(t, StringComparison.CurrentCultureIgnoreCase) ?? false;

    static bool Bat(Liburua l, string irizpidea, string t) => irizpidea switch
    {
        "Izenburua" => Barne(l.Izenburua, t),
        "Egilea" => Barne(l.EgileIzena, t),
        "ISBN" => Barne(l.ISBN, t),
        "Generoa" => Barne(l.Generoa, t),
        "Editoriala" => Barne(l.EditorialIzena, t),
        _ => Barne(l.Izenburua, t) || Barne(l.EgileIzena, t) || Barne(l.ISBN, t)
             || Barne(l.Generoa, t) || Barne(l.EditorialIzena, t)
    };

    void Iragazi()
    {
        var t = (BilatuBarra.Text ?? "").Trim();
        var id = _hautatua?.Id;

        IEnumerable<Liburua> q = _d.Liburuak;
        if (t.Length > 0) q = q.Where(l => Bat(l, _irizpidea, t));
        var zerrenda = q.OrderBy(l => l.Izenburua).ToList();

        LblEmaitzak.Text = t.Length == 0
            ? $"{zerrenda.Count} liburu guztira"
            : $"{zerrenda.Count} emaitza «{t}» bilatzean";

        LiburuZerrenda.ItemsSource = zerrenda;
        LiburuZerrenda.SelectedItem = zerrenda.FirstOrDefault(l => l.Id == id);
    }

    // ---------- Xehetasunak ----------
    void OnLiburuaHautatu(object? sender, SelectionChangedEventArgs e)
    {
        _hautatua = e.CurrentSelection.FirstOrDefault() as Liburua;
        Erakutsi(_hautatua);
    }

    async void Erakutsi(Liburua? l)
    {
        XehetasunTxartela.IsVisible = l != null;
        HutsaTxartela.IsVisible = l == null;
        if (l == null) return;

        Azala.Background = l.AzalaBrush;
        AzalaTestua.Text = l.Izenburua;
        AzalaEgilea.Text = l.EgileIzena;
        LblIzenburua.Text = l.Izenburua;
        LblISBN.Text = l.ISBN;
        LblUrtea.Text = l.ArgitalpenUrtea.ToString();
        LblGeneroa.Text = l.Generoa;
        LblEgilea.Text = l.EgileIzena;
        LblEditoriala.Text = l.EditorialIzena;

        LblEskuragarri.Text = l.Eskuragarri ? "Eskuragarri: Bai" : "Eskuragarri: Ez (alokatuta)";
        LblEskuragarri.TextColor = l.EskuragarriKolorea;
        EskuragarriPuntua.Fill = new SolidColorBrush(l.EskuragarriKolorea);
        EskuragarriPilula.BackgroundColor = l.EskuragarriHondoa;

        // Sarrera leuna
        XehetasunTxartela.Opacity = 0;
        XehetasunTxartela.TranslationY = 8;
        await Task.WhenAll(
            XehetasunTxartela.FadeTo(1, 220, Easing.CubicOut),
            XehetasunTxartela.TranslateTo(0, 0, 220, Easing.CubicOut));
    }

    // ---------- Nabigazioa ----------
    async void OnIkusiEgilea(object? sender, EventArgs e)
    {
        if (_hautatua == null) return;
        _d.PendingEgilea = _hautatua.Egilea;
        await Shell.Current.GoToAsync("//EgileakPage");
    }

    async void OnIkusiEditoriala(object? sender, EventArgs e)
    {
        if (_hautatua == null) return;
        _d.PendingEditoriala = _hautatua.Editoriala;
        await Shell.Current.GoToAsync("//EditorialakPage");
    }

    async void OnLiburuBerria(object? sender, EventArgs e)
    {
        _d.PendingEditLiburua = null;
        await Shell.Current.GoToAsync("//LiburuKudeaketaPage");
    }

    async void OnEditatuLiburua(object? sender, EventArgs e)
    {
        if (_hautatua == null)
        {
            await DisplayAlert("Liburua aukeratu", "Aukeratu lehenik editatu nahi duzun liburua.", "Ados");
            return;
        }
        _d.PendingEditLiburua = _hautatua;
        await Shell.Current.GoToAsync("//LiburuKudeaketaPage");
    }

    async void OnEgileBerria(object? sender, EventArgs e)
    {
        _d.PendingEditEgilea = null;
        await Shell.Current.GoToAsync("//EgileKudeaketaPage");
    }
}
