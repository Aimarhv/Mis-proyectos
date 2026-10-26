import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Kontzertu baten datuak gordetzen dituen objektua (datu-baseko erregistro bat).
 * Testu-fitxategian erregistro bakoitza lerro bat da, eremuak ";" bidez bereizita:
 * id;taldea;dia;hora;taquilla;precio
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

    // ===================== FITXATEGIAREKIN LOTURA =====================

    /** Objektua fitxategiko lerro bihurtzen du (eremuak ";" bidez bereizita). */
    public String toLinea() {
        return id + ";" + taldea + ";" + dia + ";" + hora + ";" + taquilla + ";" + precio;
    }

    /**
     * Fitxategiko lerro bat Kontzertua objektu bihurtzen du.
     * @throws IllegalArgumentException lerroaren formatua okerra bada
     */
    public static Kontzertua fromLinea(String linea) {
        String[] p = linea.split(";", -1);
        if (p.length != 6) {
            throw new IllegalArgumentException("Eremu kopuru okerra lerroan: " + linea);
        }
        try {
            return new Kontzertua(Integer.parseInt(p[0].trim()), p[1], LocalDate.parse(p[2].trim()),
                    LocalTime.parse(p[3].trim()), Double.parseDouble(p[4].trim()),
                    Double.parseDouble(p[5].trim()));
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new IllegalArgumentException("Eremuen formatu okerra lerroan: " + linea);
        }
    }

    /** Pantailan erakusteko formatua. */
    @Override
    public String toString() {
        return String.format("%3d | %-48s | %s | %s | %6.2f | %6.2f", id, taldea, dia, hora, taquilla, precio);
    }

    // Getterrak
    public int getId() {
        return id;
    }

    public String getTaldea() {
        return taldea;
    }

    public LocalDate getDia() {
        return dia;
    }

    public LocalTime getHora() {
        return hora;
    }

    public double getTaquilla() {
        return taquilla;
    }

    public double getPrecio() {
        return precio;
    }

    // Setterrak
    public void setId(int id) {
        this.id = id;
    }

    public void setTaldea(String taldea) {
        this.taldea = taldea;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setTaquilla(double taquilla) {
        this.taquilla = taquilla;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}