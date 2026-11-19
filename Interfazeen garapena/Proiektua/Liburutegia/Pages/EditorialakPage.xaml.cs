namespace Liburutegia;

public partial class EditorialakPage : ContentPage
{
    readonly DataService _d = DataService.Instance;
    Editoriala? _hautatua;

    public EditorialakPage()
    {
        InitializeComponent();
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();

        var id = _d.PendingEditoriala?.Id ?? _hautatua?.Id;
        _d.PendingEditoriala = null;

        var zerrenda = _d.Editorialak.OrderBy(e => e.Izena).ToList();
        LblKopurua.Text = $"{zerrenda.Count} editorial";
        EditorialZerrenda.ItemsSource = zerrenda;
        EditorialZerrenda.SelectedItem = zerrenda.FirstOrDefault(e => e.Id == id);
    }

    void OnEditorialaHautatu(object? sender, SelectionChangedEventArgs e)
    {
        _hautatua = e.CurrentSelection.FirstOrDefault() as Editoriala;
        Erakutsi(_hautatua);
    }

    async void Erakutsi(Editoriala? ed)
    {
        XehetasunTxartela.IsVisible = ed != null;
        HutsaTxartela.IsVisible = ed == null;
        if (ed == null)
        {
            LiburuZerrenda.ItemsSource = null;
            return;
        }

        Logoa.BackgroundColor = ed.Kolorea;
        LblInizialak.Text = ed.Inizialak;
        LblIzena.Text = ed.Izena;
        LblHerria.Text = ed.Herria;
        LblUrtea.Text = ed.FundazioUrtea.ToString();
        LblWeb.Text = ed.Webgunea;
        LiburuZerrenda.ItemsSource = _d.LiburuakEditorialeka(ed.Id).OrderBy(l => l.Izenburua).ToList();

        XehetasunTxartela.Opacity = 0;
        await XehetasunTxartela.FadeTo(1, 200, Easing.CubicOut);
    }

    async void OnLiburuaBiklik(object? sender, TappedEventArgs e)
    {
        if ((sender as BindableObject)?.BindingContext is Liburua l)
        {
            _d.PendingLiburua = l;
            await Shell.Current.GoToAsync("//LiburuakPage");
        }
    }
}
