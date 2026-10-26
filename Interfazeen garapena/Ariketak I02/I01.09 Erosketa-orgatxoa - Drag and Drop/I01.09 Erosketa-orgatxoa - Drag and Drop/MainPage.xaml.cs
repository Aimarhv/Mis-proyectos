using System.Collections.ObjectModel;
using System.ComponentModel;

namespace I01._09_Erosketa_orgatxoa___Drag_and_Drop
{
    /// <summary>
    /// Erosketa orgatxoaren orri nagusia: arrastatze eta askatze (Drag and Drop) 
    /// bidez elementuak karrora gehitu, eta haien historiala (desegin) kudeatzen du.
    /// </summary>
    public partial class MainPage : ContentPage
    {
        // Karroko elementuen zerrenda ikusgai dagoena
        public ObservableCollection<ProductItem> CartItems { get; set; } = new ObservableCollection<ProductItem>();

        // Gehienez 20 ekintza gordetzeko historiala (Desegin / Ctrl+Z egiteko)
        private Stack<ActionRecord> historialAcciones = new Stack<ActionRecord>();

        // Uneko arrastatzen ari den produktuaren izena gordetzeko aldagaia
        private string productoArrastrado = string.Empty;

        // Fitxategi izena -> erakusteko izena
        private static readonly (string File, string Display)[] Frutak =
        {
            ("sandia", "Sandia"), ("sagarra", "Sagarra"), ("pera", "Pera"),
            ("naranja", "Naranja"), ("pina", "Piña"), ("meloia", "Meloia")
        };

        /// <summary>
        /// Eraikitzailea. Osagaiak hasieratu eta CollectionView-aren iturria lotzen du.
        /// </summary>
        public MainPage()
        {
            InitializeComponent();
            CartCollectionView.ItemsSource = CartItems;
        }

        /// <summary>
        /// Irudi bat arrastatzen hastean exekutatzen da, zein fruta den identifikatzeko.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen irudia.</param>
        /// <param name="e">Arrastatzearen gertaeraren argumentuak.</param>
        private void OnDragStarting(object sender, DragStartingEventArgs e)
        {
            if (sender is Image img)
            {
                string src = img.Source?.ToString() ?? string.Empty;
                foreach (var (file, display) in Frutak)
                {
                    if (src.Contains(file, StringComparison.OrdinalIgnoreCase))
                    {
                        productoArrastrado = display;
                        break;
                    }
                }
            }
        }

        /// <summary>
        /// Produktua orgatxoan askatzean exekutatzen da: zerrendan baldin badago kopurua 
        /// handitzen du, eta bestela elementu berri bat gehitzen du.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen kontrola.</param>
        /// <param name="e">Askatzearen gertaeraren argumentuak.</param>
        private void OnCartDrop(object sender, DropEventArgs e)
        {
            if (string.IsNullOrEmpty(productoArrastrado)) return;

            string nomeFruta = productoArrastrado;

            // Bilatu ea produktua jadanik zerrendan badagoen
            var existingItem = CartItems.FirstOrDefault(p => p.Name == nomeFruta);

            if (existingItem != null)
            {
                // Badago: kopurua handitu (INotifyPropertyChanged-ek etiketa eguneratzen du)
                existingItem.Quantity++;
                RegistrarHistorial(ActionType.Incrementar, existingItem);
            }
            else
            {
                // Ez dago: elementu berria sortu (1x kopurua)
                var newItem = new ProductItem { Name = nomeFruta, Quantity = 1 };
                CartItems.Add(newItem);
                RegistrarHistorial(ActionType.AñadirNuevo, newItem);
            }

            productoArrastrado = string.Empty;
        }

        /// <summary>
        /// "Itzuli" botoia sakatzean karroko zerrenda eta historiala osorik garbitzen ditu.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen botoia.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnItzuliClicked(object sender, EventArgs e)
        {
            CartItems.Clear();
            historialAcciones.Clear();
        }

        /// <summary>
        /// "Desegin" botoia sakatzean azken ekintza desegiten du (kopurua txikitu edo elementua ezabatu).
        /// </summary>
        /// <param name="sender">Gertaera piztu duen botoia.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnDeseginClicked(object sender, EventArgs e)
        {
            if (historialAcciones.Count > 0)
            {
                var ultimaAccion = historialAcciones.Pop();

                if (ultimaAccion.Type == ActionType.Incrementar)
                {
                    // Kopurua murriztu
                    ultimaAccion.TargetItem.Quantity--;
                }
                else if (ultimaAccion.Type == ActionType.AñadirNuevo)
                {
                    // Elementu berria zen, zerrendatik guztiz kendu
                    CartItems.Remove(ultimaAccion.TargetItem);
                }
            }
        }

        /// <summary>
        /// "Irten" botoia sakatzean aplikazioa ixten du plataformaren arabera.
        /// </summary>
        /// <param name="sender">Gertaera piztu duen botoia.</param>
        /// <param name="e">Gertaeraren argumentuak.</param>
        private void OnIrtenClicked(object sender, EventArgs e)
        {
#if ANDROID
            Android.OS.Process.KillProcess(Android.OS.Process.MyPid());
#elif WINDOWS || MACCATALYST
            Application.Current?.Quit();
#endif
        }

        /// <summary>
        /// Egindako ekintza historialean erregistratzen du, gehienez 20 gordez 
        /// (zaharrena ezabatuz maira iristean).
        /// </summary>
        /// <param name="type">Ekintza mota (Gehitu edo Berria).</param>
        /// <param name="item">Eraginapena jasan duen produktu-itema.</param>
        private void RegistrarHistorial(ActionType type, ProductItem item)
        {
            if (historialAcciones.Count >= 20)
            {
                var temp = historialAcciones.ToList();
                temp.RemoveAt(temp.Count - 1); // zaharrena kendu
                historialAcciones = new Stack<ActionRecord>(temp.AsEnumerable().Reverse());
            }

            historialAcciones.Push(new ActionRecord { Type = type, TargetItem = item });
        }
    }

    /// <summary>
    /// Ekintza motak zehazteko enumerazioa (berria gehitu ala kopurua handitu).
    /// </summary>
    public enum ActionType
    {
        AñadirNuevo,
        Incrementar
    }

    /// <summary>
    /// Orgatxoan dagoen produktu baten datuak eta kopurua kudeatzen dituen klasea.
    /// </summary>
    public class ProductItem : INotifyPropertyChanged
    {
        private int _quantity;

        /// <summary>Produktuaren izena.</summary>
        public string Name { get; set; } = string.Empty;

        /// <summary>Produktuaren kopurua (aldatzean interfazea eguneratzen du).</summary>
        public int Quantity
        {
            get => _quantity;
            set
            {
                _quantity = value;
                PropertyChanged?.Invoke(this, new(nameof(Quantity)));
                PropertyChanged?.Invoke(this, new(nameof(Display)));
            }
        }

        /// <summary>CollectionView-ak erakutsiko duen testua (adibidez: "2x Sandia").</summary>
        public string Display => $"{Quantity}x {Name}";

        public event PropertyChangedEventHandler? PropertyChanged;
    }

    /// <summary>
    /// Desegin ahal izateko egindako ekintza baten erregistroa.
    /// </summary>
    public class ActionRecord
    {
        /// <summary>Ekintza mota.</summary>
        public ActionType Type { get; set; }

        /// <summary>Eragindako produktua.</summary>
        public ProductItem TargetItem { get; set; } = null!;
    }
}