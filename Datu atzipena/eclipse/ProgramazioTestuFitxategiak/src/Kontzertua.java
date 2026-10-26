import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Kontzertu baten datuak gordetzen dituen objektua (taulako erregistro bat).
 * Fitxategiko lerro bat ("id;taldea;dia;hora;taquilla;precio") eta objektu baten
 * arteko bihurketak ere hemen daude.
 */
public class Kontzertua {

    private int id;
    private String taldea;
    private LocalDate dia;
    private LocalTime hora;
    private double taquilla;
    private double precio;

    // Eraikitzailea: erregistro bateko eremu guztiak jasotzen ditu
    public Kontzertua(int id, String taldea, LocalDate dia, LocalTime hora, double taquilla, double precio) {
        this.id = id;
        this.taldea = taldea;
        this.dia = dia;
        this.hora = hora;
        this.taquilla = taquilla;
        this.precio = precio;
    }

    // Getterrak
    public int getId() { return id; }
    public String getTaldea() { return taldea; }
    public LocalDate getDia() { return dia; }
    public LocalTime getHora() { return hora; }
    public double getTaquilla() { return taquilla; }
    public double getPrecio() { return precio; }

    // Setterrak
    public void setId(int id) { this.id = id; }
    public void setTaldea(String taldea) { this.taldea = taldea; }
    public void setDia(LocalDate dia) { this.dia = dia; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public void setTaquilla(double taquilla) { this.taquilla = taquilla; }
    public void setPrecio(double precio) { this.precio = precio; }

    /** Objektua fitxategiko lerro bihurtzen du (eremuak ";" bidez bereizita). */
    public String toLine() {
        return id + ";" + taldea + ";" + dia + ";" + hora + ";" + taquilla + ";" + precio;
    }

    /**
     * Fitxategiko lerro bat objektu bihurtzen du.
     * @return Kontzertua, edo null lerroa okerra bada (eremu kopurua edo formatua)
     */
    public static Kontzertua fromLine(String lerroa) {
        if (lerroa == null) {
            return null;
        }
        String[] p = lerroa.split(";", -1);
        if (p.length != 6) {
            return null;
        }
        try {
            return new Kontzertua(Integer.parseInt(p[0].trim()), p[1].trim(),
                    LocalDate.parse(p[2].trim()), LocalTime.parse(p[3].trim()),
                    Double.parseDouble(p[4].trim()), Double.parseDouble(p[5].trim()));
        } catch (NumberFormatException | DateTimeParseException e) {
            return null;
        }
    }

    // Pantailan erakusteko formatua
    @Override
    public String toString() {
        return String.format("%3d | %-48s | %s %s | taquilla: %6.2f | precio: %6.2f",
                id, taldea, dia, hora, taquilla, precio);
    }
}
