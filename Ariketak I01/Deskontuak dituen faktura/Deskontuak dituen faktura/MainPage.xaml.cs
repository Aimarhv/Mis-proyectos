using System.Runtime.CompilerServices;

namespace Deskontuak_dituen_faktura
{
    /// <summary>
    /// Faktura baten kalkulua egiten duen orri nagusia: kantitatea eta prezio
    /// unitarioa sartuta, deskontua eta BEZa aplikatuz azken zenbatekoa lortzen du.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        int count = 0;

        /// <summary>
        /// Eraikitzailea. BEZ eremua %21eko balio lehenetsiarekin hasieratzen du.
        /// </summary>
        public MainPage()
        {
            InitializeComponent();
            BEZEntry.Text = "21";
        }

        /// <summary>
        /// Subtotala, deskontua (erositako kantitatearen arabera), BEZa eta
        /// azken zenbateko ordaingarria kalkulatzen ditu.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnKalkulatuClicked(object? sender, EventArgs e)
        {
            try
            {
                if (int.TryParse(KantitateaText.Text, out int kantitatea) &&
                    decimal.TryParse(Prezioa.Text, out decimal prezioa))
                {
                    decimal subtotala = kantitatea * prezioa;
                    Denera.Text = subtotala.ToString("0.00") + " €";

                    // Zenbat eta gehiago erosi, deskontu handiagoa
                    decimal deskontuaPortzentaia = 0;
                    if (kantitatea >= 1000) deskontuaPortzentaia = 10;
                    else if (kantitatea >= 100) deskontuaPortzentaia = 5;
                    else if (kantitatea >= 10) deskontuaPortzentaia = 2;

                    DeskontuaEntry.Text = deskontuaPortzentaia.ToString("0.00") + " %";

                    decimal deskontuaDenera = subtotala * (deskontuaPortzentaia / 100);
                    Deskontuadenera.Text = deskontuaDenera.ToString("0.00") + " €";

                    decimal baseImponiblea = subtotala - deskontuaDenera;

                    // BEZ lehenetsia %21 da, baina erabiltzaileak beste balio bat sar dezake
                    decimal bezPortzentaia = 21;
                    if (decimal.TryParse(BEZEntry.Text, out decimal bezLortuta))
                        bezPortzentaia = bezLortuta;

                    decimal bezDenera = baseImponiblea * (bezPortzentaia / 100);
                    BEZdenera.Text = bezDenera.ToString("0.00") + " €";

                    decimal ordaintzekoDenera = baseImponiblea + bezDenera;
                    OrdaintzekoDenera.Text = ordaintzekoDenera.ToString("0.00") + " €";
                }
                else
                {
                    // Erabiltzaileak ez du zenbakiak sartu
                    DisplayAlert("Errorea", "Sartu zenbakiak ondo.", "Ados");
                }
            }
            catch (Exception ex)
            {
                // Beste errore bat gertatzen bada, ohar bat erakusten du
                DisplayAlert("Errorea", "Errore bat gertatu da: " + ex.Message, "Ados");
            }
        }

        /// <summary>
        /// Aplikazioa ixten du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnIrtenClicked(object sender, EventArgs e)
        {
            Application.Current.Quit();
        }
    }
}