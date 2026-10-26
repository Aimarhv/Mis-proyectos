import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Kontzertu baten datuak gordetzen dituen objektua (datu-baseko erregistro bat).
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