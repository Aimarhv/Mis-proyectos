using Microsoft.Maui.Controls;
using System.Collections.Generic;
using System.Linq;

namespace I01._03_Opariak_lista
{
    /// <summary>
    /// Opari zerrenda bat erakutsi eta erabiltzaileak gehienez bi opari
    /// aukeratzeko aukera ematen duen orri nagusia.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        /// <summary>
        /// Opari baten informazioa: izena eta irudiaren bidea.
        /// </summary>
        public class Oparia
        {
            public string Name { get; set; } = string.Empty;
            public string IconoPath { get; set; } = string.Empty;
        }

        // Erabiltzaileak aukeratutako lehen eta bigarren oparia (gehienez bi)
        private Oparia? lehenOparia = null;
        private Oparia? bigarrenOparia = null;

        public MainPage()
        {
            InitializeComponent();

            // Aukeratu daitezkeen opari finkoak CollectionView-ari lotzen zaizkio
            OpariakCollectionView.ItemsSource = new List<Oparia>
            {
                new Oparia { Name = "Erlojua", IconoPath = "erlojua.jpg" },
                new Oparia { Name = "Aurikularrak", IconoPath = "aurikularrak.jpg" },
                new Oparia { Name = "Bozgorailuak", IconoPath = "bozgorailuak.jpg" },
                new Oparia { Name = "Portatila", IconoPath = "portatila.jpg" },
                new Oparia { Name = "Patinetea", IconoPath = "patinetea.jpg" }
            };
        }

        /// <summary>
        /// Hautatutako opariaren arabera eskuineko xehetasunak (irudia, izenburua,
        /// azpititulua) eguneratzen ditu.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Hautaketaren argumentuak.</param>
        private void OpariakCollectionView_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            // Hautatutako opariaren izenaren arabera, dagozkion irudia eta testuak jartzen dira
            if (e.CurrentSelection.FirstOrDefault() is Oparia selectedOparia)
            {

                if (selectedOparia.Name == "Erlojua")
                {
                    ImagenOparia.Source = "erlojua.jpg";
                    LblTituloOpari.Text = "Erlojua";
                    LblSubtituloOpari.Text = "Smartwatch Pro X";
                }
                else if (selectedOparia.Name == "Aurikularrak")
                {
                    ImagenOparia.Source = "aurikularrak.jpg";
                    LblTituloOpari.Text = "Aurikularrak";
                    LblSubtituloOpari.Text = "Senhisser Boost X12";
                }
                else if (selectedOparia.Name == "Bozgorailuak")
                {
                    ImagenOparia.Source = "bozgorailuak.jpg";
                    LblTituloOpari.Text = "Bozgorailuak";
                    LblSubtituloOpari.Text = "Bluetooth Bass 360";
                }
                else if (selectedOparia.Name == "Portatila")
                {
                    ImagenOparia.Source = "portatila.jpg";
                    LblTituloOpari.Text = "Portatila";
                    LblSubtituloOpari.Text = "Asus ZenBook 14 OLED";
                }
                else if (selectedOparia.Name == "Patinetea")
                {
                    ImagenOparia.Source = "patinetea.jpg";
                    LblTituloOpari.Text = "Patinetea";
                    LblSubtituloOpari.Text = "Xiaomi Electric Scooter 4";
                }
            }
        }

        /// <summary>
        /// Hautatutako oparia lehen edo bigarren opari gisa gordetzen du
        /// (gehienez bi aukeratu daitezke).
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private async void OnAukeratuClicked(object sender, EventArgs e)
        {
            var seleccionado = OpariakCollectionView.SelectedItem as Oparia;

            // Oparirik hautatu ez bada, oharra eman eta irten
            if (seleccionado == null)
            {
                // Erabiltzaileak ez du oparirik hautatu, ohar bat erakutsi
                await this.DisplayAlertAsync("Oharra", "Mesedez, aukeratu opari bat gutxienez.", "Ados");
                return;
            }

            // Oparia jadanik aukeratuta badago (lehen edo bigarren gisa), oharra eman eta irten
            if (seleccionado == lehenOparia || seleccionado == bigarrenOparia)
            {
                // Erabiltzaileak opari hori jadanik aukeratu du, ohar bat erakutsi
                await this.DisplayAlertAsync("Oharra", "Opari hori jadanik aukeratuta dago.", "Ados");
                return;
            }

            // Lehen tokia hutsik badago, hor gorde; bestela bigarren tokian; biak beteta badaude, errorea
            if (lehenOparia == null)
            {
                lehenOparia = seleccionado;
                LblLehenOparia.Text = lehenOparia.Name;
            }
            else if (bigarrenOparia == null)
            {
                bigarrenOparia = seleccionado;
                LblBigarrenOparia.Text = bigarrenOparia.Name;
            }
            else
            {
                // Bi opari aukeratuak daude, errore bat erakutsi
                await this.DisplayAlertAsync("Errorea", "Gehienez bi opari hauta ditzakezu.", "Ados");
            }
        }

        /// <summary>
        /// Aukeratutako opari guztiak garbitzen ditu.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private async void OnEzabatuClicked(object sender, EventArgs e)
        {
            // Aukeratutako opariak eta pantailako testuak garbitu
            lehenOparia = null;
            bigarrenOparia = null;
            LblLehenOparia.Text = string.Empty;
            LblBigarrenOparia.Text = string.Empty;

            OpariakCollectionView.SelectedItem = null;

            // Erabiltzaileari oharra erakutsi
            await this.DisplayAlertAsync("Ezabatuta", "Berriro hauta dezakezu.", "Ados");
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
}