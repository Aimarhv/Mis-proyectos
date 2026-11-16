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
        EditorialZerrenda.ItemsSource = zerrenda;
        EditorialZerrenda.SelectedItem = zerrenda.FirstOrDefault(e => e.Id == id);
    }

    void OnEditorialaHautatu(object? sender, SelectionChangedEventArgs e)
    {
        _hautatua = e.CurrentSelection.FirstOrDefault() as Editoriala;
        Erakutsi(_hautatua);
    }

    void Erakutsi(Editoriala? ed)
    {
        XehetasunTxartela.IsVisible = ed != null;
        HutsaTxartela.IsVisible = ed == null;
        if (ed == null)
        {
            LiburuZerrenda.ItemsSource = null;
            return;
        }

        LblIzena.Text = ed.Izena;
        LblHerria.Text = ed.Herria;
        LblUrtea.Text = ed.FundazioUrtea.ToString();
        LblWeb.Text = ed.Webgunea;
        LiburuZerrenda.ItemsSource = _d.LiburuakEditorialeka(ed.Id).OrderBy(l => l.Izenburua).ToList();
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
