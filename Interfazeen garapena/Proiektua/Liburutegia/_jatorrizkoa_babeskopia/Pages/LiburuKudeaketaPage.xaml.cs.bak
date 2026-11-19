namespace Liburutegia;

public partial class LiburuKudeaketaPage : ContentPage
{
    readonly DataService _d = DataService.Instance;
    Liburua? _editatzen;
    List<Egilea> _egileak = new();
    List<Editoriala> _editorialak = new();

    public LiburuKudeaketaPage()
    {
        InitializeComponent();
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();

        _egileak = _d.Egileak.OrderBy(e => e.Abizenak).ThenBy(e => e.Izena).ToList();
        _editorialak = _d.Editorialak.OrderBy(e => e.Izena).ToList();
        PkGeneroa.ItemsSource = DataService.Generoak;
        PkEgilea.ItemsSource = _egileak;
        PkEditoriala.ItemsSource = _editorialak;
        ZerrendaEguneratu();

        var pending = _d.PendingEditLiburua;
        _d.PendingEditLiburua = null;
        if (pending != null) Kargatu(pending); else Garbitu();
    }

    void ZerrendaEguneratu() =>
        LiburuZerrenda.ItemsSource = _d.Liburuak.OrderBy(l => l.Izenburua).ToList();

    // ---------- Formularioa ----------
    void Garbitu()
    {
        _editatzen = null;
        LiburuZerrenda.SelectedItem = null;
        LblTitulua.Text = "Liburu berria";
        EntIzenburua.Text = "";
        EntISBN.Text = "";
        EntUrtea.Text = "";
        PkGeneroa.SelectedItem = null;
        PkEgilea.SelectedItem = null;
        PkEditoriala.SelectedItem = null;
        SwEskuragarri.IsToggled = true;
        BtnEzabatu.IsEnabled = false;
        Baliozkotu();
    }

    void Kargatu(Liburua l)
    {
        _editatzen = l;
        LblTitulua.Text = "Liburua editatu";
        EntIzenburua.Text = l.Izenburua;
        EntISBN.Text = l.ISBN;
        EntUrtea.Text = l.ArgitalpenUrtea.ToString();
        PkGeneroa.SelectedItem = l.Generoa;
        PkEgilea.SelectedItem = _egileak.FirstOrDefault(e => e.Id == l.EgileaId);
        PkEditoriala.SelectedItem = _editorialak.FirstOrDefault(e => e.Id == l.EditorialaId);
        SwEskuragarri.IsToggled = l.Eskuragarri;
        BtnEzabatu.IsEnabled = true;
        LiburuZerrenda.SelectedItem = (LiburuZerrenda.ItemsSource as IEnumerable<Liburua>)?.FirstOrDefault(x => x.Id == l.Id);
        Baliozkotu();
    }

    void OnLiburuaHautatu(object? sender, SelectionChangedEventArgs e)
    {
        if (e.CurrentSelection.FirstOrDefault() is Liburua l && l.Id != _editatzen?.Id)
            Kargatu(l);
    }

    void OnAldatu(object? sender, EventArgs e) => Baliozkotu();

    // ---------- Balidazioak ----------
    bool Baliozkotu()
    {
        bool ok = true;

        if (string.IsNullOrWhiteSpace(EntIzenburua.Text)) ok = false;

        // ISBN: nahitaezkoa eta bakarra
        ErrISBN.Text = "";
        var isbn = EntISBN.Text?.Trim() ?? "";
        if (isbn.Length == 0) ok = false;
        else if (_d.Liburuak.Any(x => x.Id != _editatzen?.Id &&
                                      x.ISBN.Equals(isbn, StringComparison.OrdinalIgnoreCase)))
        {
            ErrISBN.Text = "ISBN hori datu-basean badago jada.";
            ok = false;
        }

        // Argitalpen-urtea: ezin da uneko urtea baino handiagoa izan
        ErrUrtea.Text = "";
        if (!int.TryParse(EntUrtea.Text, out var urtea))
        {
            if (!string.IsNullOrWhiteSpace(EntUrtea.Text)) ErrUrtea.Text = "Zenbaki bat sartu behar da.";
            ok = false;
        }
        else if (urtea > DateTime.Now.Year)
        {
            ErrUrtea.Text = $"Urtea ezin da {DateTime.Now.Year} baino handiagoa izan.";
            ok = false;
        }
        else if (urtea < 1)
        {
            ErrUrtea.Text = "Urte baliogabea.";
            ok = false;
        }

        if (PkGeneroa.SelectedItem == null || PkEgilea.SelectedItem == null || PkEditoriala.SelectedItem == null)
            ok = false;

        BtnGorde.IsEnabled = ok;
        return ok;
    }

    // ---------- Botoiak ----------
    async void OnGorde(object? sender, EventArgs e)
    {
        if (!Baliozkotu()) return;

        var l = _editatzen ?? new Liburua();
        l.Izenburua = EntIzenburua.Text!.Trim();
        l.ISBN = EntISBN.Text!.Trim();
        l.ArgitalpenUrtea = int.Parse(EntUrtea.Text!);
        l.Generoa = (string)PkGeneroa.SelectedItem;
        l.EgileaId = ((Egilea)PkEgilea.SelectedItem).Id;
        l.EditorialaId = ((Editoriala)PkEditoriala.SelectedItem).Id;
        l.Eskuragarri = SwEskuragarri.IsToggled;

        bool berria = _editatzen == null;
        _d.Gorde(l);

        await DisplayAlert("Gordeta", berria ? "Liburua sortu da." : "Liburua eguneratu da.", "Ados");
        ZerrendaEguneratu();
        Garbitu();
    }

    void OnBerria(object? sender, EventArgs e) => Garbitu();

    async void OnEzabatu(object? sender, EventArgs e)
    {
        if (_editatzen == null) return;

        bool baietz = await DisplayAlert("Liburua ezabatu",
            $"\"{_editatzen.Izenburua}\" ezabatu nahi duzu?", "Ezabatu", "Utzi");
        if (!baietz) return;

        _d.Ezabatu(_editatzen);
        ZerrendaEguneratu();
        Garbitu();
    }
}
