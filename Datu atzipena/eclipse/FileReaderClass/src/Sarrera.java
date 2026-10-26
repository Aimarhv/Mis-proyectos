import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Teklatuko sarrera eta datuen balidazioa. Scanner bakarra programa osorako.
 */
public class Sarrera {

    // Scanner bakarra programa osorako
    static Scanner sc = new Scanner(System.in);

    /** Zenbaki oso bat eskatzen du; baliogabea bada, berriro eskatzen du. */
    static int leerEnteroValidado(String mezua) {
        System.out.print(mezua);
        while (!sc.hasNextInt()) {
            sc.nextLine(); // sarrera okerra garbitu
            System.out.println(" Zenbaki oso bat sartu behar duzu.");
            System.out.print(mezua);
        }
        int balioa = sc.nextInt();
        sc.nextLine(); // lerro-jauzia kendu hurrengo irakurketarako
        return balioa;
    }

    // ===================== LAGUNTZAILEAK: TEKLATUA ETA BALIDAZIOA =====================

    /** Bai/ez galdera egiten du; "b" edo "e" onartzen du. */
    static boolean baieztatu(String mezua) {
        while (true) {
            System.out.print(mezua + " (b/e): ");
            String erantzuna = sc.nextLine().trim().toLowerCase();
            if (erantzuna.equals("b") || erantzuna.equals("bai")) {
                return true;
            }
            if (erantzuna.equals("e") || erantzuna.equals("ez")) {
                return false;
            }
            System.out.println(" Idatzi 'b' (bai) edo 'e' (ez).");
        }
    }

    /**
     * Testu bat eskatzen du (ezin da hutsik egon, ez du ';' izan behar).
     * lehenetsia != null bada, hutsik utziz hori itzultzen da.
     */
    static String eskatuTestua(String mezua, String lehenetsia) {
        while (true) {
            System.out.print(mezua);
            String s = sc.nextLine().trim();
            if (s.isEmpty() && lehenetsia != null) {
                return lehenetsia;
            }
            if (s.isEmpty()) {
                System.out.println(" Eremu hau ezin da hutsik egon.");
            } else if (s.contains(";")) {
                System.out.println(" Ezin da ';' karakterea erabili.");
            } else {
                return s;
            }
        }
    }

    /** Data bat eskatzen du (uuuu-hh-ee formatuan). */
    static LocalDate eskatuData(String mezua, LocalDate lehenetsia) {
        while (true) {
            System.out.print(mezua);
            String s = sc.nextLine().trim();
            if (s.isEmpty() && lehenetsia != null) {
                return lehenetsia;
            }
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println(" Data baliogabea. Formatua: uuuu-hh-ee (adib. 2019-02-05).");
            }
        }
    }

    /** Ordu bat eskatzen du (HH:mm formatuan). */
    static LocalTime eskatuOrdua(String mezua, LocalTime lehenetsia) {
        while (true) {
            System.out.print(mezua);
            String s = sc.nextLine().trim();
            if (s.isEmpty() && lehenetsia != null) {
                return lehenetsia;
            }
            try {
                return LocalTime.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println(" Ordu baliogabea. Formatua: HH:mm (adib. 21:00).");
            }
        }
    }

    /** Zenbaki erreal ez-negatibo bat eskatzen du (koma edo puntua onartzen dira). */
    static double eskatuDoublea(String mezua, Double lehenetsia) {
        while (true) {
            System.out.print(mezua);
            String s = sc.nextLine().trim().replace(',', '.');
            if (s.isEmpty() && lehenetsia != null) {
                return lehenetsia;
            }
            try {
                double balioa = Double.parseDouble(s);
                if (balioa >= 0) {
                    return balioa;
                }
                System.out.println(" Zenbakiak ez du negatiboa izan behar.");
            } catch (NumberFormatException e) {
                System.out.println(" Zenbaki baliogabea.");
            }
        }
    }
}