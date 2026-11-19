using SQLite;

namespace Liburutegia;

/// <summary>Interfazerako kolore-laguntzaileak (azalak, avatarrak...).</summary>
static class Koloreak
{
    // Liburu-azal klasikoen tonuak: oihal-kolore lasaiak
    public static readonly Color[] Paleta =
    {
        Color.FromArgb("#2F4858"), Color.FromArgb("#7A3E48"), Color.FromArgb("#3E6B4E"),
        Color.FromArgb("#4B4A8C"), Color.FromArgb("#9A5B2E"), Color.FromArgb("#2E5E73"),
        Color.FromArgb("#6B4E71"), Color.FromArgb("#8C6D2F")
    };

    public static Color Hautatu(int id) => Paleta[Math.Abs(id) % Paleta.Length];

    public static Brush Gradientea(Color c) => new LinearGradientBrush(
        new GradientStopCollection
        {
            new GradientStop(c.WithLuminosity(Math.Min(1f, c.GetLuminosity() + 0.08f)), 0f),
            new GradientStop(c.WithLuminosity(Math.Max(0f, c.GetLuminosity() - 0.10f)), 1f)
        },
        new Point(0, 0), new Point(1, 1));

    public static string Inizialak(params string?[] hitzak) =>
        string.Concat(hitzak.Where(h => !string.IsNullOrWhiteSpace(h))
                            .Select(h => char.ToUpper(h!.Trim()[0]))
                            .Take(2));
}

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
    [Ignore] public int LiburuKopurua => DataService.Instance.LiburuakEgileka(Id).Count();
    [Ignore] public string LiburuKopuruaTestua => $"{LiburuKopurua} liburu";

    [Ignore] public string Inizialak => Koloreak.Inizialak(Izena, Abizenak);
    [Ignore] public Color Kolorea => Koloreak.Hautatu(Id + 3);
    [Ignore] public string HerrialdeaEtaJaiotza => $"{Herrialdea} · {JaiotzeData.Year}";
}

public class Editoriala
{
    [PrimaryKey, AutoIncrement]
    public int Id { get; set; }
    public string Izena { get; set; } = "";
    public string Herria { get; set; } = "";
    public string Webgunea { get; set; } = "";
    public int FundazioUrtea { get; set; }

    [Ignore] public int LiburuKopurua => DataService.Instance.LiburuakEditorialeka(Id).Count();
    [Ignore] public string LiburuKopuruaTestua => $"{LiburuKopurua} liburu";

    [Ignore] public string Inizialak => Koloreak.Inizialak(Izena.Split(' ').FirstOrDefault(), Izena.Split(' ').Skip(1).FirstOrDefault());
    [Ignore] public Color Kolorea => Koloreak.Hautatu(Id + 5);
    [Ignore] public string HerriaEtaUrtea => $"{Herria} · {FundazioUrtea}z geroztik";
}

public class Liburua
{
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
    [Ignore] public Color EskuragarriKolorea => Eskuragarri ? Color.FromArgb("#2E7D4F") : Color.FromArgb("#B5541C");
    [Ignore] public Color EskuragarriHondoa => Eskuragarri ? Color.FromArgb("#E5F2E9") : Color.FromArgb("#FBEBDF");

    [Ignore] public Color Kolorea => Koloreak.Hautatu(Id);
    [Ignore] public Brush AzalaBrush => Koloreak.Gradientea(Kolorea);
    [Ignore] public string Hizkia => string.IsNullOrWhiteSpace(Izenburua) ? "?" : Izenburua.Trim()[..1].ToUpper();
    [Ignore] public string UrteaEtaGeneroa => $"{ArgitalpenUrtea} · {Generoa}";
    [Ignore] public string EgileaEtaUrtea => $"{EgileIzena} · {ArgitalpenUrtea}";
}
