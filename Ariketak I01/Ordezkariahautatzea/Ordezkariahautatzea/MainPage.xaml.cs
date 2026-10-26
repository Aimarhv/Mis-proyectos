using System.Collections.ObjectModel;

namespace Ordezkariahautatzea
{
    /// <summary>
    /// Klaseko ordezkariak aukeratzeko orri nagusia: ikasleen zerrenda kudeatzen du
    /// eta hautatutako edo ausaz aukeratutako ikasleak ordezkari zerrendara pasatzen ditu.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        public ObservableCollection<Ikaslea> Ikasleak { get; set; } = new ObservableCollection<Ikaslea>();
        public ObservableCollection<Ikaslea> Ordezkariak { get; set; } = new ObservableCollection<Ikaslea>();

        public MainPage()
        {
            InitializeComponent();

            LstIkasleak.ItemsSource = Ikasleak;
            ListOrdezkariak.ItemsSource = Ordezkariak;

            ListOrdezkariak.ItemSelected += (s, e) => ActualizarEgoeraBotoiak();
        }

        /// <summary>
        /// Testu-koadroko izen-abizenak ikasle berri gisa gehitzen ditu zerrendara.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnGehituClicked(object sender, EventArgs e)
        {
            if (!string.IsNullOrWhiteSpace(TxtIkasleBerria.Text))
            {
                // Lehen hitza izena, gainerakoa (baldin badago) abizena
                var zatiak = TxtIkasleBerria.Text.Trim().Split(' ');
                string izena = zatiak[0];
                string abizena = zatiak.Length > 1 ? string.Join(" ", zatiak[1..]) : "";

                Ikasleak.Add(new Ikaslea { Izena = izena, Abizena = abizena });
                TxtIkasleBerria.Text = string.Empty;
            }
        }

        /// <summary>
        /// Ikasleen zerrendan hautatutako ikaslea ordezkari gisa gehitzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnIzendatuClicked(object sender, EventArgs e)
        {
            if (LstIkasleak.SelectedItem is Ikaslea hautatua && !Ordezkariak.Contains(hautatua))
            {
                Ordezkariak.Add(hautatua);
                ActualizarEgoeraBotoiak();
            }
        }

        /// <summary>
        /// Ikasleen zerrendatik ikasle bat ausaz aukeratu eta ordezkari gisa gehitzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnAusazClicked(object sender, EventArgs e)
        {
            if (Ikasleak.Count > 0)
            {
                var rand = new Random();
                var ausazkoa = Ikasleak[rand.Next(Ikasleak.Count)];
                if (!Ordezkariak.Contains(ausazkoa))
                {
                    Ordezkariak.Add(ausazkoa);
                    ActualizarEgoeraBotoiak();
                }
            }
        }

        /// <summary>
        /// Ordezkarien zerrendatik hautatutako ikaslea kentzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnKenduClicked(object sender, EventArgs e)
        {
            if (ListOrdezkariak.SelectedItem is Ikaslea hautatua)
            {
                Ordezkariak.Remove(hautatua);
                ActualizarEgoeraBotoiak();
            }
        }

        /// <summary>
        /// Ordezkarien zerrenda osoa garbitzen du (ikasleen zerrendari eutsiz).
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnHustuClicked(object sender, EventArgs e)
        {
            Ordezkariak.Clear();
            ActualizarEgoeraBotoiak();
        }

        /// <summary>
        /// Ikasle zerrenda eta ordezkarien zerrenda, biak garbitzen ditu.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnKenduDenakClicked(object sender, EventArgs e)
        {
            Ikasleak.Clear();
            Ordezkariak.Clear();
            ActualizarEgoeraBotoiak();
        }

        /// <summary>
        /// Hautatutako ordezkaria zerrendan gora mugitzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnGoraClicked(object sender, EventArgs e)
        {
            if (ListOrdezkariak.SelectedItem is Ikaslea hautatua)
            {
                int index = Ordezkariak.IndexOf(hautatua);
                if (index > 0)
                {
                    Ordezkariak.Move(index, index - 1);
                    ListOrdezkariak.SelectedItem = hautatua;
                }
            }
        }

        /// <summary>
        /// Hautatutako ordezkaria zerrendan behera mugitzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnBeheraClicked(object sender, EventArgs e)
        {
            if (ListOrdezkariak.SelectedItem is Ikaslea hautatua)
            {
                int index = Ordezkariak.IndexOf(hautatua);
                if (index >= 0 && index < Ordezkariak.Count - 1)
                {
                    Ordezkariak.Move(index, index + 1);
                    ListOrdezkariak.SelectedItem = hautatua;
                }
            }
        }

        /// <summary>
        /// Botoien egoera (gaituta/desgaituta) eta kolorea eguneratzen ditu
        /// ordezkarien zerrendaren egoeraren arabera.
        /// </summary>
        private void ActualizarEgoeraBotoiak()
        {
            // Ordezkarien zerrendan ikasle bat dagoen ala ez eta hautatutako ikaslea dagoen ala ez egiaztatzen du
            bool badagoOrdezkaria = Ordezkariak.Count > 0;
            bool hautatutaDago = ListOrdezkariak.SelectedItem != null;

            // Botoiak gaitzen/desgaitzen ditu eta kolorea aldatzen du
            BtnHustu.IsEnabled = badagoOrdezkaria;
            BtnHustu.BackgroundColor = badagoOrdezkaria ? Color.FromArgb("#9ACD32") : Color.FromArgb("#444");
            BtnHustu.TextColor = badagoOrdezkaria ? Colors.Black : Colors.White;

            // Hautatutako ikaslea dagoen ala ez egiaztatzen du
            BtnKendu.IsEnabled = hautatutaDago;
            BtnKendu.BackgroundColor = hautatutaDago ? Color.FromArgb("#9ACD32") : Color.FromArgb("#444");
            BtnKendu.TextColor = hautatutaDago ? Colors.Black : Colors.White;
        }
    }
}