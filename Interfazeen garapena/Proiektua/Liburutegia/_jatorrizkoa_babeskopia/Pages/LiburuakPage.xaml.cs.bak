namespace Liburutegia;

public partial class LiburuakPage : ContentPage
{
    readonly DataService _d = DataService.Instance;
    Liburua? _hautatua;

    public LiburuakPage()
    {
        InitializeComponent();
        IrizpidePicker.ItemsSource = new List<string> { "Denak", "Izenburua", "Egilea", "ISBN", "Generoa", "Editoriala" };
        IrizpidePicker.SelectedIndex = 0;
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();

        // Beste pantaila batetik datorren liburua (adib. Egileak pantailako klik bikoitza)
        var pending = _d.PendingLiburua;
        _d.PendingLiburua = null;
        if (pending != null)
        {
            _hautatua = pending;
            BilatuBarra.Text = "";
            IrizpidePicker.SelectedIndex = 0;
        }
        Iragazi();
    }

    // ---------- Bilaketa (testua aldatu ahala eguneratzen da) ----------
    void OnBilaketaAldatu(object? sender, TextChangedEventArgs e) => Iragazi();
    void OnIrizpideaAldatu(object? sender, EventArgs e) => Iragazi();

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
        var irizpidea = IrizpidePicker.SelectedItem as string ?? "Denak";
        var id = _hautatua?.Id;

        IEnumerable<Liburua> q = _d.Liburuak;
        if (t.Length > 0) q = q.Where(l => Bat(l, irizpidea, t));
        var zerrenda = q.OrderBy(l => l.Izenburua).ToList();

        LiburuZerrenda.ItemsSource = zerrenda;
        LiburuZerrenda.SelectedItem = zerrenda.FirstOrDefault(l => l.Id == id);
    }

    // ---------- Xehetasunak ----------
    void OnLiburuaHautatu(object? sender, SelectionChangedEventArgs e)
    {
        _hautatua = e.CurrentSelection.FirstOrDefault() as Liburua;
        Erakutsi(_hautatua);
    }

    void Erakutsi(Liburua? l)
    {
        XehetasunTxartela.IsVisible = l != null;
        HutsaTxartela.IsVisible = l == null;
        if (l == null) return;

        Azala.BackgroundColor = l.Kolorea;
        AzalaTestua.Text = l.Izenburua;
        LblIzenburua.Text = l.Izenburua;
        LblISBN.Text = l.ISBN;
        LblUrtea.Text = l.ArgitalpenUrtea.ToString();
        LblGeneroa.Text = l.Generoa;
        LblEgilea.Text = l.EgileIzena;
        LblEditoriala.Text = l.EditorialIzena;
        LblEskuragarri.Text = l.EskuragarriBai;
        LblEskuragarri.TextColor = l.EskuragarriKolorea;
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
