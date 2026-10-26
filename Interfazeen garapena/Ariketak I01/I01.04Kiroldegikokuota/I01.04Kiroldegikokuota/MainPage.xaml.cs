namespace I01._04Kiroldegikokuota
{
    /// <summary>
    /// Kiroldegiko kuota kalkulatzeko orri nagusia: iraupena eta erabiltzailearen
    /// ezaugarriak kontuan hartuta azken prezioa kalkulatzen du.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        private const decimal PrezioOinarria = 100.00m;

        public MainPage()
        {
            InitializeComponent();
            GarbituBalioak();
        }

        /// <summary>
        /// Oinarrizko prezioari iraupenaren, kolektiboaren, desgaitasunaren eta
        /// familia ugariaren deskontuak aplikatuz azken kuota kalkulatzen du.
        /// </summary>
        private void KalkulatuKuota()
        {
            decimal prezioa = PrezioOinarria;

            // Iraupenaren araberako multiplikatzailea
            if (Iraupena.SelectedIndex == 1) prezioa *= 10;
            else if (Iraupena.SelectedIndex == 2) prezioa *= 0.5m;
            else if (Iraupena.SelectedIndex == 3) prezioa *= 0.25m;
            else if (Iraupena.SelectedIndex == 4) prezioa *= 0.1m;

            // Kolektiboaren araberako deskontua (elkarren artean baztertzaileak)
            if (Umeak.IsChecked) prezioa *= 0.7m;
            else if (Familia.IsChecked) prezioa *= 0.8m;
            else if (Jubilatuak.IsChecked) prezioa *= 0.6m;

            // Deskontu gehigarriak, aurrekoekin metatzen dira
            if (Desgaitasuna.IsChecked) prezioa *= 0.5m;
            if (FamiliaUgaria.IsChecked) prezioa *= 0.75m;

            LblPrezioa.Text = $"{prezioa:N2}€";
        }

        /// <summary>
        /// Edozein aukera (checkbox edo Picker) aldatzean kuota berriz kalkulatzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnAldaketaGertatuDa(object sender, EventArgs e)
        {
            KalkulatuKuota();
        }

        /// <summary>
        /// Kuota kalkulatzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnKalkulatuClicked(object sender, EventArgs e)
        {
            KalkulatuKuota();
        }

        /// <summary>
        /// Balio guztiak hasierako egoerara itzultzen ditu.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnGarbituClicked(object sender, EventArgs e)
        {
            GarbituBalioak();
        }

        /// <summary>
        /// Kontrolak balio lehenetsietara itzuli (Jubilatuak hautatuta, gainerakoak
        /// desmarkatuta, iraupen laburrena) eta kuota berriz kalkulatzen du.
        /// </summary>
        private void GarbituBalioak()
        {
            Jubilatuak.IsChecked = true;
            Desgaitasuna.IsChecked = false;
            FamiliaUgaria.IsChecked = false;
            Iraupena.SelectedIndex = 0;

            KalkulatuKuota();
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