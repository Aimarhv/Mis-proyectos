using SQLite;

namespace Liburutegia;

/// <summary>
/// Datuen atzipena SQLite bidez. Taulak memorian kargatzen dira (Egileak, Editorialak, Liburuak)
/// eta aldaketak datu-basean gordetzen dira Gorde(...) / Ezabatu(...) deituta.
/// </summary>
public class DataService
{
    public static readonly string[] Generoak =
    {
        "Abentura", "Biografia", "Zientzia fikzioa", "Komedia", "Drama", "Fantasia", "Historia",
        "Misterioa", "Poesia", "Eleberria", "Saiakera", "Beldurra", "Thriller"
    };

    public static DataService Instance { get; } = new();

    readonly SQLiteConnection _db;

    public List<Egilea> Egileak { get; private set; } = new();
    public List<Editoriala> Editorialak { get; private set; } = new();
    public List<Liburua> Liburuak { get; private set; } = new();

    // Pantaila batetik bestera aukeratutako elementua pasatzeko
    public Liburua? PendingLiburua { get; set; }
    public Egilea? PendingEgilea { get; set; }
    public Editoriala? PendingEditoriala { get; set; }
    public Liburua? PendingEditLiburua { get; set; }
    public Egilea? PendingEditEgilea { get; set; }

    DataService()
    {
        var bidea = Path.Combine(FileSystem.AppDataDirectory, "liburutegia.db3");
        _db = new SQLiteConnection(bidea);

        _db.CreateTable<Egilea>();
        _db.CreateTable<Editoriala>();
        _db.CreateTable<Liburua>();

        if (_db.Table<Egilea>().Count() == 0 &&
            _db.Table<Editoriala>().Count() == 0 &&
            _db.Table<Liburua>().Count() == 0)
        {
            ProbaDatuakSartu();
        }

        Egileak = _db.Table<Egilea>().ToList();
        Editorialak = _db.Table<Editoriala>().ToList();
        Liburuak = _db.Table<Liburua>().ToList();
    }

    // Datu-base hutsa denean soilik. Id-ak 1etik aurrera sortzen dira, ordena honetan.
    void ProbaDatuakSartu()
    {
        _db.InsertAll(new[]
        {
            new Egilea { Izena = "Ana", Abizenak = "Telleria", JaiotzeData = new DateTime(1982, 4, 12), Herrialdea = "Euskal Herria" },   // 1
            new Egilea { Izena = "Jon", Abizenak = "Agirre", JaiotzeData = new DateTime(1975, 9, 3), Herrialdea = "Euskal Herria" },      // 2
            new Egilea { Izena = "Miren", Abizenak = "Garcia", JaiotzeData = new DateTime(1990, 1, 25), Herrialdea = "Espainia" },        // 3
            new Egilea { Izena = "Unai", Abizenak = "Etxeberria", JaiotzeData = new DateTime(1968, 11, 8), Herrialdea = "Euskal Herria" } // 4
        });

        _db.InsertAll(new[]
        {
            new Editoriala { Izena = "Argitaletxe X", Herria = "Donostia", Webgunea = "www.argitaletxex.eus", FundazioUrtea = 1985 },            // 1
            new Editoriala { Izena = "Mendi Argitaletxea", Herria = "Iruñea", Webgunea = "www.mendiargitaletxea.eus", FundazioUrtea = 1999 },   // 2
            new Editoriala { Izena = "Itsaso Liburuak", Herria = "Bilbo", Webgunea = "www.itsasoliburuak.eus", FundazioUrtea = 2008 }           // 3
        });

        _db.InsertAll(new[]
        {
            new Liburua { Izenburua = "Misterio Zaharra", ISBN = "978-84-12345-67-8", ArgitalpenUrtea = 2021, Generoa = "Misterioa", EgileaId = 1, EditorialaId = 1, Eskuragarri = false },
            new Liburua { Izenburua = "Sekretu Gorria", ISBN = "978-84-12345-68-5", ArgitalpenUrtea = 2018, Generoa = "Thriller", EgileaId = 1, EditorialaId = 1 },
            new Liburua { Izenburua = "Hitz Isilak", ISBN = "978-84-12345-69-2", ArgitalpenUrtea = 2023, Generoa = "Drama", EgileaId = 1, EditorialaId = 2 },
            new Liburua { Izenburua = "Gure Iragana", ISBN = "978-84-22222-01-1", ArgitalpenUrtea = 2015, Generoa = "Historia", EgileaId = 2, EditorialaId = 2 },
            new Liburua { Izenburua = "Ozeanoko Isiltasuna", ISBN = "978-84-33333-02-7", ArgitalpenUrtea = 2020, Generoa = "Abentura", EgileaId = 3, EditorialaId = 3 },
            new Liburua { Izenburua = "Hiztegi Berria", ISBN = "978-84-44444-03-4", ArgitalpenUrtea = 2012, Generoa = "Saiakera", EgileaId = 4, EditorialaId = 1 }
        });
    }

    // ---------- Kontsultak ----------
    public Egilea? GetEgilea(int id) => Egileak.FirstOrDefault(e => e.Id == id);
    public Editoriala? GetEditoriala(int id) => Editorialak.FirstOrDefault(e => e.Id == id);
    public IEnumerable<Liburua> LiburuakEgileka(int egileaId) => Liburuak.Where(l => l.EgileaId == egileaId);
    public IEnumerable<Liburua> LiburuakEditorialeka(int editorialaId) => Liburuak.Where(l => l.EditorialaId == editorialaId);

    // ---------- Liburuak: alta / aldaketa / baja ----------
    public void Gorde(Liburua l)
    {
        if (l.Id == 0) { _db.Insert(l); Liburuak.Add(l); }   // Insert-ek Id-a esleitzen du
        else _db.Update(l);
    }

    public void Ezabatu(Liburua l)
    {
        _db.Delete(l);
        Liburuak.Remove(l);
    }

    // ---------- Egileak: alta / aldaketa / baja ----------
    public void Gorde(Egilea e)
    {
        if (e.Id == 0) { _db.Insert(e); Egileak.Add(e); }
        else _db.Update(e);
    }

    public void Ezabatu(Egilea e)
    {
        _db.Delete(e);
        Egileak.Remove(e);
    }
}
