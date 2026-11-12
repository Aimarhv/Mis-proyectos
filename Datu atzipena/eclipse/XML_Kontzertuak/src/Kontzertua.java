import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Kontzertu baten datuak gordetzen dituen klasea (taulako erregistro bat).
 * Fitxategiko lerro bat ("kodea;taldea;eguna;ordua;leihatila;prezioa") eta objektu baten
 * arteko bihurketak ere hemen daude.
 */
public class Kontzertua {

    private int kodea;
    private String taldea;
    private LocalDate eguna;
    private LocalTime ordua;
    private double leihatila;
    private double prezioa;

    // eraikitzaileak eremu guztiak jasotzen ditu
    public Kontzertua(int kodea, String taldea, LocalDate eguna, LocalTime ordua, double leihatila, double prezioa) {
        this.kodea = kodea;
        this.taldea = taldea;
        this.eguna = eguna;
        this.ordua = ordua;
        this.leihatila = leihatila;
        this.prezioa = prezioa;
    }

    // Getterrak
    public int getKodea() { return kodea; }
    public String getTaldea() { return taldea; }
    public LocalDate getEguna() { return eguna; }
    public LocalTime getOrdua() { return ordua; }
    public double getLeihatila() { return leihatila; }
    public double getPrezioa() { return prezioa; }

    // Setterrak
    public void setKodea(int kodea) { this.kodea = kodea; }
    public void setTaldea(String taldea) { this.taldea = taldea; }
    public void setEguna(LocalDate eguna) { this.eguna = eguna; }
    public void setOrdua(LocalTime ordua) { this.ordua = ordua; }
    public void setLeihatila(double leihatila) { this.leihatila = leihatila; }
    public void setPrezioa(double prezioa) { this.prezioa = prezioa; }

    /** Objektua fitxategiko lerro bihurtzen du (eremuak ";" bidez bereizita). */
    public String lerroraBihurtu() {
        return kodea + ";" + taldea + ";" + eguna + ";" + ordua + ";" + leihatila + ";" + prezioa;
    }

    /**
     * Fitxategiko lerro bat objektu bihurtzen du.
     * @return Kontzertua, edo null lerroa okerra bada (eremu kopurua edo formatua)
     */
    public static Kontzertua lerrotikSortu(String lerroa) {
        if (lerroa == null) {
            return null;
        }
        // -1 jarri dut amaierako eremu hutsak ere kontatzeko
        String[] atalak = lerroa.split(";", -1);
        // 6 eremu izan behar ditu, bestela okerra da
        if (atalak.length != 6) {
            return null;
        }
        try {
            return new Kontzertua(Integer.parseInt(atalak[0].trim()), atalak[1].trim(),
                    LocalDate.parse(atalak[2].trim()), LocalTime.parse(atalak[3].trim()),
                    Double.parseDouble(atalak[4].trim()), Double.parseDouble(atalak[5].trim()));
        } catch (NumberFormatException | DateTimeParseException e) {
            // zenbakia edo data gaizki badago
            return null;
        }
    }

    // pantailan erakusteko formatua
    @Override
    public String toString() {
        return String.format("%3d | %-48s | %s %s | leihatila: %6.2f | prezioa: %6.2f",
                kodea, taldea, eguna, ordua, leihatila, prezioa);
    }
}