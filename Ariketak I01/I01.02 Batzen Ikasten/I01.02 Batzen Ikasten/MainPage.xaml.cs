namespace I01._02_Batzen_Ikasten;

/// <summary>
/// Batuketak praktikatzeko orri nagusia: zenbaki ausazkoak sortu eta
/// erabiltzailearen erantzuna egiaztatzen du.
/// </summary>
public partial class MainPage : ContentPage
{
    private int batugaia1;
    private int batugaia2;

    public MainPage()
    {
        InitializeComponent();
    }

    /// <summary>
    /// Bi zenbaki ausazko berri sortzen ditu (1etik 10era) eta aurreko emaitzak garbitzen ditu.
    /// </summary>
    /// <param name="sender">Gertaera piztu duen kontrola.</param>
    /// <param name="e">Gertaeraren argumentuak.</param>
    private void OnSortuClicked(object sender, EventArgs e)
    {
        Random rnd = new Random();
        batugaia1 = (int)(rnd.NextDouble() * 10) + 1;
        batugaia2 = (int)(rnd.NextDouble() * 10) + 1;

        Batugaia1Entry.Text = batugaia1.ToString();
        Batugaia2Entry.Text = batugaia2.ToString();

        EmaitzaUsteEntry.Text = string.Empty;
        EmaitzaEntry.Text = string.Empty;
    }

    /// <summary>
    /// Erabiltzaileak sartutako erantzuna benetako emaitzarekin alderatzen du.
    /// </summary>
    /// <param name="sender">Gertaera piztu duen kontrola.</param>
    /// <param name="e">Gertaeraren argumentuak.</param>
    private void OnBalioztatuClicked(object sender, EventArgs e)
    {
        if (int.TryParse(EmaitzaUsteEntry.Text, out int usteEmaitza))
        {
            int emaitzaZuzena = batugaia1 + batugaia2;

            // Erabiltzailearen erantzuna zuzena den ala ez erakusten du
            EmaitzaEntry.Text = usteEmaitza == emaitzaZuzena
                ? "Zuzena! ✅"
                : $"Okerra ❌ ({emaitzaZuzena})";
        }
        else
        {
            // Erabiltzaileak ez du zenbaki bat sartu
            DisplayAlert("Errorea", "Sartu zenbaki bat", "Ados");
        }
    }
}