using Microsoft.Maui.Controls.Shapes;

namespace Hiru_marran
{
    /// <summary>
    /// "Hiru marran" jokoaren orri nagusia: bi jokalarien txandak kudeatzen ditu,
    /// irabazlea egiaztatu eta markagailua eguneratzen du.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        private static readonly Color CellBg = Color.FromArgb("#E9D6AE");
        private static readonly Color XColor = Color.FromArgb("#E53935");
        private static readonly Color OColor = Color.FromArgb("#2B9BD6");

        // null = laukia hutsik
        private readonly string?[,] taula = new string?[3, 3];
        private Grid[,] ikonoak = null!;

        private bool aldaketaX = true;
        private int irabaziX = 0;
        private int irabaziO = 0;
        private bool jokoaAmaituta = false;

        /// <summary>
        /// Eraikitzailea. 9 Grid-ak matrize batean antolatu eta hasierako ikonoak margotzen ditu.
        /// </summary>
        public MainPage()
        {
            InitializeComponent();

            // XAMLeko 9 Grid-ak errenkada/zutabe bidez sartzen dira matrize batean
            ikonoak = new Grid[3, 3]
            {
                { Ikono00, Ikono01, Ikono02 },
                { Ikono10, Ikono11, Ikono12 },
                { Ikono20, Ikono21, Ikono22 }
            };

            // Hasierako txanda-ikonoa eta markagailuko ikono txikiak margotzen dira
            TxandaIkurraKutxa.Children.Add(SortuXIkonoa(30));
            XikonoTxikia.Children.Add(SortuXIkonoa(22));
            OikonoTxikia.Children.Add(SortuOIkonoa(22));
        }

        /// <summary>
        /// X ikurraren badge gorria sortzen du.
        /// </summary>
        /// <param name="neurria">Ikonoaren neurria.</param>
        /// <returns>X ikurra irudikatzen duen View bat.</returns>
        private View SortuXIkonoa(double neurria)
        {
            // Kuadro gorri biribildua, erdian X letra zuria duena
            return new Border
            {
                WidthRequest = neurria,
                HeightRequest = neurria,
                BackgroundColor = XColor,
                Stroke = Colors.Transparent,
                StrokeShape = new RoundRectangle { CornerRadius = neurria * 0.28 },
                Content = new Label
                {
                    Text = "\u2715",
                    TextColor = Colors.White,
                    FontAttributes = FontAttributes.Bold,
                    FontSize = neurria * 0.6,
                    HorizontalOptions = LayoutOptions.Center,
                    VerticalOptions = LayoutOptions.Center,
                    HorizontalTextAlignment = TextAlignment.Center,
                    VerticalTextAlignment = TextAlignment.Center
                }
            };
        }

        /// <summary>
        /// O ikurraren eraztun urdina sortzen du (kanpoko zirkulua + barruko zuloa).
        /// </summary>
        /// <param name="neurria">Ikonoaren neurria.</param>
        /// <returns>O ikurra irudikatzen duen View bat.</returns>
        private View SortuOIkonoa(double neurria)
        {
            // Kanpoko zirkulu urdina
            var kanpokoa = new Border
            {
                WidthRequest = neurria,
                HeightRequest = neurria,
                BackgroundColor = OColor,
                Stroke = Colors.Transparent,
                StrokeShape = new RoundRectangle { CornerRadius = neurria / 2 }
            };

            // Barruko zirkulu txikiagoa, atzeko planoaren kolorearekin, "eraztun" itxura emateko
            var barrukoa = new Border
            {
                WidthRequest = neurria * 0.55,
                HeightRequest = neurria * 0.55,
                BackgroundColor = CellBg,
                Stroke = Colors.Transparent,
                StrokeShape = new RoundRectangle { CornerRadius = neurria * 0.275 },
                HorizontalOptions = LayoutOptions.Center,
                VerticalOptions = LayoutOptions.Center
            };

            // Bi zirkuluak elkarren gainean jartzen dira Grid batean
            var multzoa = new Grid();
            multzoa.Children.Add(kanpokoa);
            multzoa.Children.Add(barrukoa);
            return multzoa;
        }

        /// <summary>
        /// Laukiari sakatzean, uneko jokalariaren ikurra jarri, txanda aldatu eta
        /// irabazlea dagoen egiaztatzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Sakatutako laukiaren kokapena dakarten argumentuak.</param>
        private async void Laukia_Sakatuta(object? sender, TappedEventArgs e)
        {
            if (jokoaAmaituta) return;
            if (e.Parameter is not string kokapena || kokapena.Length != 2) return;

            // Kokapena "01" bezalako string batean dator: lehen karakterea errenkada, bigarrena zutabea
            int errenkada = kokapena[0] - '0';
            int zutabea = kokapena[1] - '0';

            if (!string.IsNullOrEmpty(taula[errenkada, zutabea])) return;

            // Uneko txandaren ikurra jarri, bai datuetan bai pantailan
            string ikurra = aldaketaX ? "X" : "O";
            taula[errenkada, zutabea] = ikurra;

            var kontainerra = ikonoak[errenkada, zutabea];
            kontainerra.Children.Clear();
            kontainerra.Children.Add(ikurra == "X" ? SortuXIkonoa(50) : SortuOIkonoa(50));

            // Txanda aldatu eta goiko adierazlea eguneratu
            aldaketaX = !aldaketaX;
            TxandaIkurraKutxa.Children.Clear();
            TxandaIkurraKutxa.Children.Add(aldaketaX ? SortuXIkonoa(30) : SortuOIkonoa(30));

            await Irabazlea_EgiaztatuAsync();
        }

        /// <summary>
        /// Irabazle bat (edo berdinketa) dagoen egiaztatzen du 8 konbinazio posibleak
        /// aztertuz, eta hala bada, partida berria hasteko galdetzen du.
        /// </summary>
        private async Task Irabazlea_EgiaztatuAsync()
        {
            // Errenkadak, zutabeak eta bi diagonalak: bikote bakoitzak (errenkada, zutabe) lauki bat adierazten du
            int[][] konbinazioak = new int[][]
            {
                new int[]{0,0,0,1,0,2},
                new int[]{1,0,1,1,1,2},
                new int[]{2,0,2,1,2,2},
                new int[]{0,0,1,0,2,0},
                new int[]{0,1,1,1,2,1},
                new int[]{0,2,1,2,2,2},
                new int[]{0,0,1,1,2,2},
                new int[]{0,2,1,1,2,0}
            };

            // Konbinazio bakoitzean hiru laukiek ikur berbera duten begiratzen da
            foreach (var k in konbinazioak)
            {
                string? a = taula[k[0], k[1]];
                string? b = taula[k[2], k[3]];
                string? c = taula[k[4], k[5]];

                if (!string.IsNullOrEmpty(a) && a == b && b == c)
                {
                    // Irabazlea aurkitu da: markagailua eguneratu eta partida amaitu
                    jokoaAmaituta = true;
                    TxandaTestua.Text = $"{a} jokalariak irabazi du!";

                    if (a == "X")
                    {
                        irabaziX++;
                        MarkagailuaX.Text = irabaziX.ToString();
                    }
                    else
                    {
                        irabaziO++;
                        MarkagailuaO.Text = irabaziO.ToString();
                    }

                    // Beste partida bat jolastu nahi duen galdetu, eta bai bada taula garbitu
                    bool berriroJolastu = await DisplayAlert(
                        $"{a} jokalariak irabazi du",
                        "Beste partida bat jolastu nahi duzu?",
                        "Bai", "Ez");

                    if (berriroJolastu)
                        Taula_Garbitu();

                    return;
                }
            }

            // Irabazlerik ez badago, lauki hutsik dagoen begiratu berdinketa den jakiteko
            bool dagoHutsik = false;
            foreach (var laukia in taula)
            {
                if (string.IsNullOrEmpty(laukia))
                {
                    dagoHutsik = true;
                    break;
                }
            }

            if (!dagoHutsik)
            {
                // Lauki guztiak beteta eta irabazlerik ez: berdinketa
                jokoaAmaituta = true;
                TxandaTestua.Text = "Berdinketa!";

                bool berriroJolastu = await DisplayAlert(
                    "Berdinketa!",
                    "Beste partida bat jolastu nahi duzu?",
                    "Bai", "Ez");

                if (berriroJolastu)
                    Taula_Garbitu();
            }
        }

        /// <summary>
        /// Taula garbitu eta markagailua zerora jartzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnBerrirohasiClicked(object? sender, EventArgs e)
        {
            Taula_Garbitu();

            // Markagailua bakarrik hemen jartzen da zerora
            irabaziX = 0;
            irabaziO = 0;
            MarkagailuaX.Text = "0";
            MarkagailuaO.Text = "0";
        }

        /// <summary>
        /// Partida berri bat hasteko taula garbitzen du, Emaitzak markagailua ukitu gabe.
        /// </summary>
        private void Taula_Garbitu()
        {
            // Datu-matrizea eta ikono grafikoak lauki guztietan garbitu
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    taula[i, j] = null;
                    ikonoak[i, j].Children.Clear();
                }
            }

            // Egoera X-en txandara itzuli eta jokoa berriz aktibo jarri
            aldaketaX = true;
            jokoaAmaituta = false;
            TxandaTestua.Text = "jokalariaren txanda";

            TxandaIkurraKutxa.Children.Clear();
            TxandaIkurraKutxa.Children.Add(SortuXIkonoa(30));
        }
    }
}