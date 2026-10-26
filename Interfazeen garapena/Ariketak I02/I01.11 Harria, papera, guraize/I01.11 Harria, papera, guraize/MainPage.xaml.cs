namespace I01._11_Harria__papera__guraize
{
    /// <summary>
    /// Harria, papera, guraizea jokoaren orri nagusia: jokalariak aukera bat egiten du,
    /// makinak ausaz aukeratzen du eta 10 puntu lortzen dituenak irabazten du.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        private readonly Random rnd = new Random();

        // Jokalariaren eta makinaren puntuak
        private int puntosJugador = 0, puntosMaquina = 0;

        // Irudiak aukeren ordenan: 0 = harria, 1 = papera, 2 = guraizea
        private readonly string[] imagenes = { "harria.png", "papera.png", "guraizea.png" };

        /// <summary>
        /// Eraikitzailea.
        /// </summary>
        public MainPage()
        {
            InitializeComponent();
        }

        // Botoi bakoitzak bere zenbakiarekin deitzen dio Jokatu metodoari
        private void OnHarriaClicked(object sender, EventArgs e) => Jokatu(0);
        private void OnPaperaClicked(object sender, EventArgs e) => Jokatu(1);
        private void OnGuraizeaClicked(object sender, EventArgs e) => Jokatu(2);

        /// <summary>
        /// Jokaldi bat jolasten du: makinak ausaz aukeratzen du, irudiak jarri,
        /// puntuak eguneratu eta norbaitek 10 lortu duen begiratzen du.
        /// </summary>
        /// <param name="eleccion">Jokalariaren aukera (0 harria, 1 papera, 2 guraizea).</param>
        private async void Jokatu(int eleccion)
        {
            // Makinaren aukera ausaz (0, 1 edo 2)
            int maquina = rnd.Next(3);

            // Bi aukeren irudiak erakutsi
            imgZure.Source = imagenes[eleccion];
            lblAukeratu.IsVisible = true;
            imgMakina.Source = imagenes[maquina];

            // Berdinketa bada, inork ez du punturik lortzen
            if (eleccion != maquina)
            {
                // Jokalariak irabazten du bere aukera makinarenaren hurrengoa bada
                // (papera > harria, guraizea > papera, harria > guraizea)
                if ((eleccion - maquina + 3) % 3 == 1) puntosJugador++;
                else puntosMaquina++;
            }

            lblZurePuntuak.Text = puntosJugador.ToString();
            lblMakinaPuntuak.Text = puntosMaquina.ToString();

            // Norbaitek 10 puntu lortzean partida amaitzen da
            if (puntosJugador == 10 || puntosMaquina == 10)
            {
                string mensaje = puntosJugador == 10 ? "Irabazi duzu!" : "Makinak irabazi du!";
                await DisplayAlert("Partida amaituta", mensaje, "Ados");

                // Partida berrirako dena hasierako egoerara itzuli
                puntosJugador = 0;
                puntosMaquina = 0;
                lblZurePuntuak.Text = "0";
                lblMakinaPuntuak.Text = "0";
                imgMakina.Source = null;
                imgZure.Source = null;
                lblAukeratu.IsVisible = false;
            }
        }

        /// <summary>
        /// "Irten" botoia sakatzean, aplikazioa ixten du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void Irten_Clicked(object sender, EventArgs e)
        {
            Application.Current.Quit();
        }
    }
}