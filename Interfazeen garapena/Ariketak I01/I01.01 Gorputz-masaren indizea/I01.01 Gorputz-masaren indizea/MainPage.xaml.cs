namespace I01._01_Gorputz_masaren_indizea;

/// <summary>
/// Gorputz Masaren Indizea (GMK/IMC) kalkulatzeko orri nagusia.
/// </summary>
public partial class MainPage : ContentPage
{
    public MainPage()
    {
        InitializeComponent();
    }

    /// <summary>
    /// GMK kalkulatzen du: pisua (kg) zati altuera (m) ber bi.
    /// </summary>
    /// <param name="sender">Gertaera piztu duen kontrola.</param>
    /// <param name="e">Gertaeraren argumentuak.</param>
    private void OnKalkulatuClicked(object sender, EventArgs e)
    {
        if (double.TryParse(AltueraEntry.Text, out double altueraCm) &&
            double.TryParse(PisuaEntry.Text, out double pisuaKg) &&
            altueraCm > 0)
        {
            double altueraM = altueraCm / 100.0;
            double gmk = pisuaKg / (altueraM * altueraM);
            GmkEntry.Text = gmk.ToString("F2");
        }
        else
        {
            // Erabiltzaileak ez du altuera edo pisua sartu edo balio okerra sartu du
            DisplayAlert("Errorea", "Sartu altuera eta pisua zuzen", "Ados");
        }
    }

    /// <summary>
    /// Aplikazioa ixten du.
    /// </summary>
    /// <param name="sender">Gertaera piztu duen kontrola.</param>
    /// <param name="e">Gertaeraren argumentuak.</param>
    private void OnIrtenClicked(object sender, EventArgs e)
    {
        Application.Current?.Quit();
    }
}