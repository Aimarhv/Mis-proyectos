namespace Liburutegia;

public partial class EgileakPage : ContentPage
{
    readonly DataService _d = DataService.Instance;
    Egilea? _hautatua;

    public EgileakPage()
    {
        InitializeComponent();
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();

        var id = _d.PendingEgilea?.Id ?? _hautatua?.Id;
        _d.PendingEgilea = null;

        var zerrenda = _d.Egileak.OrderBy(e => e.Abizenak).ThenBy(e => e.Izena).ToList();
        EgileZerrenda.ItemsSource = zerrenda;
        EgileZerrenda.SelectedItem = zerrenda.FirstOrDefault(e => e.Id == id);
    }

    void OnEgileaHautatu(object? sender, SelectionChangedEventArgs e)
    {
        _hautatua = e.CurrentSelection.FirstOrDefault() as Egilea;
        Erakutsi(_hautatua);
    }

    void Erakutsi(Egilea? e)
    {
        XehetasunTxartela.IsVisible = e != null;
        HutsaTxartela.IsVisible = e == null;
        if (e == null)
        {
            BibliografiaZerrenda.ItemsSource = null;
            return;
        }

        LblIzena.Text = e.Izena;
        LblAbizenak.Text = e.Abizenak;
        LblJaiotza.Text = e.JaiotzeDataTestua;
        LblHerrialdea.Text = e.Herrialdea;
        BibliografiaZerrenda.ItemsSource = _d.LiburuakEgileka(e.Id).OrderBy(l => l.ArgitalpenUrtea).ToList();
    }

    // Bibliografiako liburu batean klik bikoitza -> Liburuak pantaila, liburua aukeratuta
    async void OnLiburuaBiklik(object? sender, TappedEventArgs e)
    {
        if ((sender as BindableObject)?.BindingContext is Liburua l)
        {
            _d.PendingLiburua = l;
            await Shell.Current.GoToAsync("//LiburuakPage");
        }
    }
}
