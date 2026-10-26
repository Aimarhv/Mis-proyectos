using System;
using System.Collections.Generic;
using System.Text;

namespace Ordezkariahautatzea
{
    /// <summary>
    /// Ikasle bat irudikatzen duen klasea: izena, abizena eta zerrendan
    /// erakusteko izen osoa gordetzen ditu.
    /// </summary>
    public class Ikaslea
    {
        private string _izena = "";
        private string _abizena = "";

        // ListView-ean testua zein kolorez erakutsi
        public Color testuKolore = Colors.White;

        public string Izena
        {
            get
            {
                return _izena;
            }
            set
            {
                _izena = value;
                // Izena aldatzean, erakusteko izen osoa ere eguneratu
                ErakustekoIzena = _izena + " " + _abizena;
            }
        }

        public string Abizena
        {
            get
            {
                return _abizena;
            }
            set
            {
                _abizena = value;
                // Abizena aldatzean, erakusteko izen osoa ere eguneratu
                ErakustekoIzena = _izena + " " + _abizena;
            }
        }

        // Izena eta abizena elkartuta, ListView-ean zuzenean erakusteko
        public string? ErakustekoIzena { get; set; }
    }
}