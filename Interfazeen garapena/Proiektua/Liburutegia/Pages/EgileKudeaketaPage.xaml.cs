namespace Liburutegia;

public partial class EgileKudeaketaPage : ContentPage
{
    readonly DataService _d = DataService.Instance;
    Egilea? _editatzen;

    public EgileKudeaketaPage()
    {
        InitializeComponent();
        DpJaiotza.MaximumDate = DateTime.Today;
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();
        ZerrendaEguneratu();

        var pending = _d.PendingEditEgilea;
        _d.PendingEditEgilea = null;
        if (pending != null) Kargatu(pending); else Garbitu();
    }

    void ZerrendaEguneratu() =>
        EgileZerrenda.ItemsSource = _d.Egileak.OrderBy(e => e.Abizenak).ThenBy(e => e.Izena).ToList();

    void Garbitu()
    {
        _editatzen = null;
        EgileZerrenda.SelectedItem = null;
        LblTitulua.Text = "Egile berria";
        LblAzpititulua.Text = "Bete datuak eta sakatu Gorde.";
        EntIzena.Text = "";
        EntAbizenak.Text = "";
        EntHerrialdea.Text = "";
        DpJaiotza.Date = new DateTime(1980, 1, 1);
        BtnEzabatu.IsEnabled = false;
        Baliozkotu();
    }

    void Kargatu(Egilea e)
    {
        _editatzen = e;
        LblTitulua.Text = "Egilea editatu";
        LblAzpititulua.Text = $"{e.IzenOsoa} · {e.LiburuKopuruaTestua}";
        EntIzena.Text = e.Izena;
        EntAbizenak.Text = e.Abizenak;
        EntHerrialdea.Text = e.Herrialdea;
        DpJaiotza.Date = e.JaiotzeData;
        BtnEzabatu.IsEnabled = true;
        EgileZerrenda.SelectedItem = (EgileZerrenda.ItemsSource as IEnumerable<Egilea>)?.FirstOrDefault(x => x.Id == e.Id);
        Baliozkotu();
    }

    void OnEgileaHautatu(object? sender, SelectionChangedEventArgs e)
    {
        if (e.CurrentSelection.FirstOrDefault() is Egilea eg && eg.Id != _editatzen?.Id)
            Kargatu(eg);
    }

    void OnAldatu(object? sender, EventArgs e) => Baliozkotu();

    // Izena, abizenak eta herrialdea nahitaezkoak
    bool Baliozkotu()
    {
        bool ok = !string.IsNullOrWhiteSpace(EntIzena.Text)
                  && !string.IsNullOrWhiteSpace(EntAbizenak.Text)
                  && !string.IsNullOrWhiteSpace(EntHerrialdea.Text);
        BtnGorde.IsEnabled = ok;
        return ok;
    }

    async void OnGorde(object? sender, EventArgs e)
    {
        if (!Baliozkotu()) return;

        var eg = _editatzen ?? new Egilea();
        eg.Izena = EntIzena.Text!.Trim();
        eg.Abizenak = EntAbizenak.Text!.Trim();
        eg.Herrialdea = EntHerrialdea.Text!.Trim();
        eg.JaiotzeData = DpJaiotza.Date;

        bool berria = _editatzen == null;
        _d.Gorde(eg);

        ZerrendaEguneratu();
        Garbitu();
        await OharraErakutsi(berria ? $"{eg.IzenOsoa} sortu da." : $"{eg.IzenOsoa} eguneratu da.");
    }

    void OnBerria(object? sender, EventArgs e) => Garbitu();

    async void OnEzabatu(object? sender, EventArgs e)
    {
        if (_editatzen == null) return;

        // Liburuak erlazionatuta dituen egilea ezin da zuzenean ezabatu
        int liburuKop = _d.LiburuakEgileka(_editatzen.Id).Count();
        if (liburuKop > 0)
        {
            await DisplayAlert("Ezin da ezabatu",
                $"{_editatzen.IzenOsoa} egileak {liburuKop} liburu ditu erlazionatuta. " +
                "Ezabatu edo aldatu lehenik liburu horiek.", "Ados");
            return;
        }

        bool baietz = await DisplayAlert("Egilea ezabatu",
            $"{_editatzen.IzenOsoa} ezabatu nahi duzu?", "Ezabatu", "Utzi");
        if (!baietz) return;

        var izena = _editatzen.IzenOsoa;
        _d.Ezabatu(_editatzen);
        ZerrendaEguneratu();
        Garbitu();
        await OharraErakutsi($"{izena} ezabatu da.");
    }

    // Oharra leunki agertu eta desagertu
    int _oharraZenb;
    async Task OharraErakutsi(string mezua)
    {
        int zenb = ++_oharraZenb;
        LblOharra.Text = mezua;
        Oharra.IsVisible = true;
        Oharra.TranslationY = -6;
        await Task.WhenAll(Oharra.FadeTo(1, 200, Easing.CubicOut), Oharra.TranslateTo(0, 0, 200, Easing.CubicOut));
        await Task.Delay(2200);
        if (zenb != _oharraZenb) return;   // bitartean beste ohar bat agertu da
        await Oharra.FadeTo(0, 300);
        Oharra.IsVisible = false;
    }
}
