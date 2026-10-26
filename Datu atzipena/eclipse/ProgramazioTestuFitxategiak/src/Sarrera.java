import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Teklatuko sarrera guztia hemen dago: irakurketa eta datuen balidazioa.
 * Metodo guztiek balio zuzena sartu arte errepikatzen dute galdera.
 * "lehenetsia" parametroa ez bada null, ENTER sakatuz balio hori mantentzen da
 * (erregistroak aldatzeko erabiltzen da).
 */
public class Sarrera {

    // Scanner bakarra programa osorako
    private static final Scanner sc = new Scanner(System.in);

    public static void itxi() {
        sc.close();
    }

    /** Lerro oso bat irakurtzen du, balidatu gabe. */
    public static String irakurriLerroa(String mezua) {
        System.out.print(mezua);
        return sc.nextLine().trim();
    }

    /** Testua irakurtzen du: ezin da hutsik egon eta ezin du ";" eduki (eremu-bereizlea da). */
    public static String irakurriTestua(String mezua, String lehenetsia) {
        while (true) {
            String s = irakurriLerroa(mezua + oharra(lehenetsia));
            if (s.isEmpty()) {
                if (lehenetsia != null) return lehenetsia;
                System.out.println(" Errorea: ezin da hutsik utzi.");
            } else if (s.contains(";")) {
                System.out.println(" Errorea: ezin da \";\" karakterea erabili.");
            } else {
                return s;
            }
        }
    }

    public static String irakurriTestua(String mezua) {
        return irakurriTestua(mezua, null);
    }

    /** Zenbaki osoa irakurtzen du, "min" edo handiagoa. */
    public static int irakurriEnteroa(String mezua, int min, Integer lehenetsia) {
        while (true) {
            String s = irakurriLerroa(mezua + oharra(lehenetsia));
            if (s.isEmpty() && lehenetsia != null) return lehenetsia;
            try {
                int n = Integer.parseInt(s);
                if (n >= min) return n;
                System.out.println(" Errorea: " + min + " edo handiagoa izan behar da.");
            } catch (NumberFormatException e) {
                System.out.println(" Errorea: zenbaki oso bat sartu behar duzu.");
            }
        }
    }

    public static int irakurriEnteroa(String mezua, int min) {
        return irakurriEnteroa(mezua, min, null);
    }

    /** Zenbaki erreala irakurtzen du (>= 0). Koma edo puntua onartzen dira. */
    public static double irakurriErreala(String mezua, Double lehenetsia) {
        while (true) {
            String s = irakurriLerroa(mezua + oharra(lehenetsia));
            if (s.isEmpty() && lehenetsia != null) return lehenetsia;
            try {
                double d = Double.parseDouble(s.replace(',', '.'));
                if (!Double.isFinite(d)) {
                    System.out.println(" Errorea: zenbaki baliogabea.");
                } else if (d < 0) {
                    System.out.println(" Errorea: ezin da negatiboa izan.");
                } else {
                    return d;
                }
            } catch (NumberFormatException e) {
                System.out.println(" Errorea: zenbaki bat sartu behar duzu (adib. 10.50).");
            }
        }
    }

    public static double irakurriErreala(String mezua) {
        return irakurriErreala(mezua, null);
    }

    /** Data irakurtzen du uuuu-hh-ee formatuan (data existitu behar da). */
    public static LocalDate irakurriData(String mezua, LocalDate lehenetsia) {
        while (true) {
            String s = irakurriLerroa(mezua + oharra(lehenetsia));
            if (s.isEmpty() && lehenetsia != null) return lehenetsia;
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println(" Errorea: data baliogabea. Formatua: uuuu-hh-ee (adib. 2019-02-05).");
            }
        }
    }

    public static LocalDate irakurriData(String mezua) {
        return irakurriData(mezua, null);
    }

    /** Ordua irakurtzen du oo:mm formatuan. */
    public static LocalTime irakurriOrdua(String mezua, LocalTime lehenetsia) {
        while (true) {
            String s = irakurriLerroa(mezua + oharra(lehenetsia));
            if (s.isEmpty() && lehenetsia != null) return lehenetsia;
            try {
                return LocalTime.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println(" Errorea: ordu baliogabea. Formatua: oo:mm (adib. 21:30).");
            }
        }
    }

    public static LocalTime irakurriOrdua(String mezua) {
        return irakurriOrdua(mezua, null);
    }

    /** Fitxategi-izen baliozko bat irakurtzen du (karaktere debekaturik gabe). */
    public static String irakurriFitxategiIzena(String mezua) {
        while (true) {
            String s = irakurriLerroa(mezua);
            if (s.isEmpty()) {
                System.out.println(" Errorea: izena ezin da hutsik egon.");
            } else if (s.matches(".*[\\\\/:*?\"<>|].*")) {
                System.out.println(" Errorea: izenak ezin ditu karaktere hauek eduki: \\ / : * ? \" < > |");
            } else {
                return s;
            }
        }
    }

    /** Baiezkoa (bai) edo ezezkoa (ez) eskatzen du. */
    public static boolean baieztatu(String mezua) {
        while (true) {
            String s = irakurriLerroa(mezua + " (Bai/Ez): ").toLowerCase();
            if (s.equals("bai")) return true;
            if (s.equals("ez")) return false;
            System.out.println(" Errorea: 'bai' edo 'ez' idatzi.");
        }
    }

    // Balio lehenetsia agertzen da galderan (ENTER = mantendu)
    private static String oharra(Object lehenetsia) {
        return lehenetsia == null ? "" : "[" + lehenetsia + "] ";
    }
}
