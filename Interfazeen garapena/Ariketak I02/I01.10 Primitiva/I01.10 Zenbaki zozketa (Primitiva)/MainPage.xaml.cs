namespace I01._10_Zenbaki_zozketa__Primitiva_
{
    /// <summary>
    /// Zenbaki zozketaren (Primitiva) orri nagusia: jokalariak 49 zenbakitik 6 aukeratzen ditu,
    /// zozketak 6 zenbaki ateratzen ditu eta asmatutakoen arabera saria kalkulatzen da.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        // 49 zenbakien CheckBox-ak (0 posizioan 1 zenbakia, 1 posizioan 2 zenbakia...)
        List<CheckBox> checks = new();

        /// <summary>
        /// Eraikitzailea. 49 zenbakien taula eta saritutako 6 zenbakien koadroak sortzen ditu.
        /// </summary>
        public MainPage()
        {
            InitializeComponent();

            // 49 zenbaki bakoitzeko: CheckBox bat eta haren zenbakia, Border baten barruan
            for (int i = 1; i <= 49; i++)
            {
                var cb = new CheckBox { HorizontalOptions = LayoutOptions.Center };
                cb.CheckedChanged += Check_CheckedChanged;
                checks.Add(cb);

                var panel = new Border
                {
                    Padding = 4,
                    Content = new VerticalStackLayout
                    {
                        Children =
                        {
                            new Label { Text = i.ToString(), HorizontalOptions = LayoutOptions.Center },
                            cb
                        }
                    }
                };

                // 7 zutabeko taulan jartzen da: zutabea = (i-1) % 7, errenkada = (i-1) / 7
                GridZenbakia.Add(panel, (i - 1) % 7, (i - 1) / 7);
            }

            // Zozketan aterako diren 6 zenbakientzako koadro hutsak
            for (int j = 0; j < 6; j++)
                stackSaritutakoak.Add(new Entry { IsReadOnly = true, WidthRequest = 50, HorizontalTextAlignment = TextAlignment.Center });
        }

        /// <summary>
        /// Zenbaki bat markatzean edo desmarkatzean, 6 baino gehiago ez uztea egiaztatzen du
        /// eta "Zozketa" botoia 6 zehatz-mehatz daudenean bakarrik aktibatzen du.
        /// </summary>
        /// <param name="sender">Markatu den CheckBox-a.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        void Check_CheckedChanged(object sender, CheckedChangedEventArgs e)
        {
            // 6 baino gehiago badaude, azkena markatu dena desmarkatzen da
            if (checks.Count(c => c.IsChecked) > 6)
                ((CheckBox)sender).IsChecked = false;

            btnZozketa.IsEnabled = checks.Count(c => c.IsChecked) == 6;
        }

        /// <summary>
        /// Zozketa egiten du: 6 zenbaki ezberdin ateratzen ditu, asmatutakoak kontatzen ditu
        /// eta sari bat ematen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen botoia.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        void btnZozketaClicked(object sender, EventArgs e)
        {
            // HashSet-ak errepikatutako zenbakiak ez ditu onartzen, beraz 6 ezberdin izango dira
            var rnd = new Random();
            var premiados = new HashSet<int>();
            while (premiados.Count < 6)
                premiados.Add(rnd.Next(1, 50));

            // Zenbakiak txikitik handira ordenatu eta koadroetan jarri
            var ordenados = premiados.OrderBy(n => n).ToList();
            for (int j = 0; j < 6; j++)
                ((Entry)stackSaritutakoak.Children[j]).Text = ordenados[j].ToString();

            // Asmatutakoak kontatu eta zenbaki guztiak blokeatu
            int aciertos = 0;
            for (int i = 0; i < 49; i++)
            {
                if (checks[i].IsChecked && premiados.Contains(i + 1))
                    aciertos++;
                checks[i].IsEnabled = false;
            }

            // Asmatutakoen araberako saria
            string premio = aciertos switch
            {
                6 => "Bote",
                5 => "1.000 €",
                4 => "50 €",
                3 => "5 €",
                2 => "3 €",
                1 => "1 €",
                _ => "saririk gabe"
            };
            txtAsmatuak.Text = $"{aciertos} ({premio})";

            // Zozketa behin bakarrik egin daiteke; "Berria" botoia aktibatzen da
            btnZozketa.IsEnabled = false;
            btnBerria.IsEnabled = true;
        }

        /// <summary>
        /// Zozketa berri bat hasteko dena garbitzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen botoia.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        void btnBerriaClicked(object sender, EventArgs e)
        {
            // Zenbaki guztiak desmarkatu eta berriz aktibatu
            foreach (var cb in checks)
            {
                cb.IsChecked = false;
                cb.IsEnabled = true;
            }

            // Saritutako zenbakien koadroak eta asmatuak garbitu
            foreach (Entry en in stackSaritutakoak.Children)
                en.Text = "";

            txtAsmatuak.Text = "";
            btnZozketa.IsEnabled = false;
            btnBerria.IsEnabled = false;
        }

        /// <summary>
        /// "Irten" botoia sakatzean, aplikazioa ixten du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen botoia.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        void btnIrtenClicked(object sender, EventArgs e)
        {
            Application.Current.Quit();
        }
    }
}