import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Teklatuko sarrera guztia hemen dago: irakurketa eta datuen balidazioa.
 * Metodo guztiek balio zuzena sartu arte errepikatzen dute galdera.
 * "lehenetsia" parametroa null ez bada, ENTER sakatuz balio hori mantentzen da.
 */
public class Sarrera {

    // Scanner bakarra programa osorako
    private static final Scanner teklatua = new Scanner(System.in);

    public static void itxi() {
        teklatua.close();
    }

    /** Lerro oso bat irakurtzen du, balidatu gabe. */
    public static String irakurriLerroa(String mezua) {
        System.out.print(mezua);
        // trim() jarri dut aurreko eta ondoko zuriuneak kentzeko
        return teklatua.nextLine().trim();
    }

    /** Testua irakurtzen du: ezin da hutsik egon eta ezin du ";" eduki. */
    public static String irakurriTestua(String mezua, String lehenetsia) {
        while (true) {
            String lerroa = irakurriLerroa(mezua + oharra(lehenetsia));
            if (lerroa.isEmpty()) {
                // ENTER sakatu badu eta balio lehenetsia badago, hori erabiltzen da
                if (lehenetsia != null) return lehenetsia;
                System.out.println(" Errorea: ezin da hutsik utzi.");
            } else if (lerroa.contains(";")) {
                // ";" eremu-bereizlea da, ezin da testuan egon
                System.out.println(" Errorea: ezin da \";\" karakterea erabili.");
            } else {
                return lerroa;
            }
        }
    }

    public static String irakurriTestua(String mezua) {
        return irakurriTestua(mezua, null);
    }

    /** Zenbaki osoa irakurtzen du, minimoa edo handiagoa. */
    public static int irakurriEnteroa(String mezua, int minimoa, Integer lehenetsia) {
        while (true) {
            String lerroa = irakurriLerroa(mezua + oharra(lehenetsia));
            if (lerroa.isEmpty() && lehenetsia != null) return lehenetsia;
            try {
                int zenbakia = Integer.parseInt(lerroa);
                if (zenbakia >= minimoa) return zenbakia;
                System.out.println(" Errorea: " + minimoa + " edo handiagoa izan behar da.");
            } catch (NumberFormatException e) {
                // letrak edo zuriuneak sartu baditu
                System.out.println(" Errorea: zenbaki oso bat sartu behar duzu.");
            }
        }
    }

    public static int irakurriEnteroa(String mezua, int minimoa) {
        return irakurriEnteroa(mezua, minimoa, null);
    }

    /** Zenbaki erreala irakurtzen du (>= 0). Koma edo puntua onartzen dira. */
    public static double irakurriErreala(String mezua, Double lehenetsia) {
        while (true) {
            String lerroa = irakurriLerroa(mezua + oharra(lehenetsia));
            if (lerroa.isEmpty() && lehenetsia != null) return lehenetsia;
            try {
                // koma puntuarekin ordezkatzen dut parseDouble-k ondo irakurtzeko
                double erreala = Double.parseDouble(lerroa.replace(',', '.'));
                if (!Double.isFinite(erreala)) {
                    System.out.println(" Errorea: zenbaki baliogabea.");
                } else if (erreala < 0) {
                    System.out.println(" Errorea: ezin da negatiboa izan.");
                } else {
                    return erreala;
                }
            } catch (NumberFormatException e) {
                System.out.println(" Errorea: zenbaki bat sartu behar duzu (adib. 10.50).");
            }
        }
    }

    public static double irakurriErreala(String mezua) {
        return irakurriErreala(mezua, null);
    }

    /** Data irakurtzen du uuuu-hh-ee formatuan. */
    public static LocalDate irakurriData(String mezua, LocalDate lehenetsia) {
        while (true) {
            String lerroa = irakurriLerroa(mezua + oharra(lehenetsia));
            if (lerroa.isEmpty() && lehenetsia != null) return lehenetsia;
            try {
                return LocalDate.parse(lerroa);
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
            String lerroa = irakurriLerroa(mezua + oharra(lehenetsia));
            if (lerroa.isEmpty() && lehenetsia != null) return lehenetsia;
            try {
                return LocalTime.parse(lerroa);
            } catch (DateTimeParseException e) {
                System.out.println(" Errorea: ordu baliogabea. Formatua: oo:mm (adib. 21:30).");
            }
        }
    }

    public static LocalTime irakurriOrdua(String mezua) {
        return irakurriOrdua(mezua, null);
    }

    /** Fitxategi-izen baliozko bat irakurtzen du. */
    public static String irakurriFitxategiIzena(String mezua) {
        while (true) {
            String lerroa = irakurriLerroa(mezua);
            if (lerroa.isEmpty()) {
                System.out.println(" Errorea: izena ezin da hutsik egon.");
            } else if (lerroa.matches(".*[\\\\/:*?\"<>|].*")) {
                // karaktere hauek ezin dira fitxategi-izenetan erabili
                System.out.println(" Errorea: izenak ezin ditu karaktere hauek eduki: \\ / : * ? \" < > |");
            } else {
                return lerroa;
            }
        }
    }

    /** Baiezkoa (bai) edo ezezkoa (ez) eskatzen du. */
    public static boolean baieztatu(String mezua) {
        while (true) {
            // minuskulara pasatzen dut "BAI" edo "Bai" idazten badu ere onartzeko
            String lerroa = irakurriLerroa(mezua + " (Bai/Ez): ").toLowerCase();
            if (lerroa.equals("bai")) return true;
            if (lerroa.equals("ez")) return false;
            System.out.println(" Errorea: 'bai' edo 'ez' idatzi.");
        }
    }

    // balio lehenetsia agertzen da galderan (ENTER = mantendu)
    private static String oharra(Object lehenetsia) {
        return lehenetsia == null ? "" : "[" + lehenetsia + "] ";
    }
}