using SQLite;

namespace Liburutegia;

public class Egilea
{
    [PrimaryKey, AutoIncrement]
    public int Id { get; set; }
    public string Izena { get; set; } = "";
    public string Abizenak { get; set; } = "";
    public DateTime JaiotzeData { get; set; }
    public string Herrialdea { get; set; } = "";

    [Ignore] public string IzenOsoa => $"{Izena} {Abizenak}";
    [Ignore] public string JaiotzeDataTestua => JaiotzeData.ToString("yyyy-MM-dd");
    [Ignore] public string LiburuKopuruaTestua => $"{DataService.Instance.LiburuakEgileka(Id).Count()} liburu";
}

public class Editoriala
{
    [PrimaryKey, AutoIncrement]
    public int Id { get; set; }
    public string Izena { get; set; } = "";
    public string Herria { get; set; } = "";
    public string Webgunea { get; set; } = "";
    public int FundazioUrtea { get; set; }

    [Ignore] public string LiburuKopuruaTestua => $"{DataService.Instance.LiburuakEditorialeka(Id).Count()} liburu";
}

public class Liburua
{
    static readonly Color[] Paleta =
    {
        Color.FromArgb("#1F4E5F"), Color.FromArgb("#5B2A3C"), Color.FromArgb("#2F5D3A"),
        Color.FromArgb("#3B3B7A"), Color.FromArgb("#7A4B1E"), Color.FromArgb("#274060")
    };

    [PrimaryKey, AutoIncrement]
    public int Id { get; set; }
    public string Izenburua { get; set; } = "";
    public string ISBN { get; set; } = "";
    public int ArgitalpenUrtea { get; set; }
    public string Generoa { get; set; } = "";
    public int EgileaId { get; set; }
    public int EditorialaId { get; set; }
    public bool Eskuragarri { get; set; } = true;

    [Ignore] public Egilea? Egilea => DataService.Instance.GetEgilea(EgileaId);
    [Ignore] public Editoriala? Editoriala => DataService.Instance.GetEditoriala(EditorialaId);
    [Ignore] public string EgileIzena => Egilea?.IzenOsoa ?? "";
    [Ignore] public string EditorialIzena => Editoriala?.Izena ?? "";

    [Ignore] public string EskuragarriBai => Eskuragarri ? "Bai" : "Ez";
    [Ignore] public string EskuragarriTestua => Eskuragarri ? "Eskuragarri" : "Alokatuta";
    [Ignore] public Color EskuragarriKolorea => Eskuragarri ? Color.FromArgb("#2E7D32") : Color.FromArgb("#B5541C");
    [Ignore] public Color Kolorea => Paleta[Math.Abs(Id) % Paleta.Length];
    [Ignore] public string UrteaEtaGeneroa => $"{ArgitalpenUrtea} · {Generoa}";
}
