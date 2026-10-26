import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

/**
 * Menuko aukeren metodoak. Guztiek ArrayList<Kontzertua> jasotzen dute argumentu gisa.
 */
public class Kontzertuak {

    static final Scanner sc = new Scanner(System.in);

    // Zutabeen zabalera finkoak (taula beti lerrokatuta ateratzeko)
    static final int W_ID = 3, W_TALDEA = 50, W_DATA = 10, W_ORDUA = 5,
                     W_TAQ = 9, W_PREC = 9, W_AGO = 8;

    // ===================== 1. ZERRENDATU =====================
    
    /** Kontzertu guztiak taula batean erakusten ditu. */
    static void zerrendatu(ArrayList<Kontzertua> lista) {
        System.out.println("\n--- KONTZERTU GUZTIAK ---");
        if (lista.isEmpty()) {
            System.out.println("Ez dago erregistrorik.");
            return;
        }
        inprimatuTaula(lista);
    }

    // ===================== 2. KONTSULTATU =====================
    
    /** Id baten bidez kontzertu bat bilatu eta erakusten du. */
    static void kontsultatu(ArrayList<Kontzertua> lista) {
        int kodea = irakurriEnteroa("Bilatu nahi duzun Id kodea: ");
        Kontzertua k = bilatuIdBidez(lista, kodea);
        if (k == null) {
            System.out.println("Ez da " + kodea + " kodea duen erregistrorik aurkitu.");
        } else {
            System.out.println("\n--- ERREGISTROA AURKITUA ---");
            ArrayList<Kontzertua> bat = new ArrayList<>();
            bat.add(k);
            inprimatuTaula(bat);
        }
    }

    // ===================== 3. BI DATEN ARTEKOAK =====================
    /** Teklatuz emandako bi daten artean (biak barne) dauden kontzertuak zerrendatzen ditu. */
    static void dataArteanZerrendatu(ArrayList<Kontzertua> lista) {
        LocalDate hasiera = irakurriData("Hasierako data (dd/MM/uuuu): ");
        LocalDate amaiera;
        do {
            amaiera = irakurriData("Amaierako data (dd/MM/uuuu): ");
            if (amaiera.isBefore(hasiera)) {
                System.out.println(" Amaierako data ezin da hasierakoa baino lehenagokoa izan.");
            }
        } while (amaiera.isBefore(hasiera));

        ArrayList<Kontzertua> emaitza = new ArrayList<>();
        for (Kontzertua k : lista) {
            if (!k.getdia().isBefore(hasiera) && !k.getdia().isAfter(amaiera)) {
                emaitza.add(k);
            }
        }
        System.out.println("\n--- KONTZERTUAK " + hasiera.format(Kontzertua.D_FORMATO)
                + " ETA " + amaiera.format(Kontzertua.D_FORMATO) + " ARTEAN ---");
        if (emaitza.isEmpty()) {
            System.out.println("Ez dago kontzertu bat ere tarte horretan.");
        } else {
            inprimatuTaula(emaitza);
        }
    }

    // ===================== 4. ALTA =====================
    /** Kontzertu berri bat gehitzen du, Id errepikatu gabe. */
    static void altaEman(ArrayList<Kontzertua> lista) {
        System.out.println("\n--- ALTA ---");
        int id;
        while (true) {
            id = irakurriEnteroa("Id berria: ");
            if (id <= 0) {
                System.out.println(" Id-a positiboa izan behar da.");
            } else if (bilatuIdBidez(lista, id) != null) {
                System.out.println(" Id hori jada existitzen da. Beste bat sartu.");
            } else {
                break;
            }
        }
        String taldea = irakurriTestua("Taldea: ");
        LocalDate dia = irakurriData("Data (dd/MM/uuuu): ");
        LocalTime hora = irakurriOrdua("Ordua (HH:mm): ");
        double taquilla = irakurriDoublea("Taquilla prezioa (€): ");
        double precio = irakurriDoublea("Precio (€): ");
        boolean agotatuta = irakurriBaiEz("Sarrerak agortuta daude? (B/E): ");

        lista.add(new Kontzertua(id, taldea, dia, hora, taquilla, precio, agotatuta));
        System.out.println("Erregistroa ondo gehitu da.");
    }

    // ===================== 5. BAJA =====================
    /** Id bidez kontzertu bat ezabatzen du, berrespena eskatuta. */
    static void bajaEman(ArrayList<Kontzertua> lista) {
        System.out.println("\n--- BAJA ---");
        int kodea = irakurriEnteroa("Ezabatu nahi duzun Id kodea: ");
        Kontzertua k = bilatuIdBidez(lista, kodea);
        if (k == null) {
            System.out.println("Ez da " + kodea + " kodea duen erregistrorik aurkitu.");
            return;
        }
        System.out.println(k); // toString bidez erakutsi
        if (irakurriBaiEz("Ziur zaude ezabatu nahi duzula? (B/E): ")) {
            lista.remove(k);
            System.out.println("Erregistroa ezabatu da.");
        } else {
            System.out.println("Ezabatzea bertan behera utzi da.");
        }
    }

    // ===================== 6. ORDENATU =====================
    /** Erregistroak aukeratutako eremuaren arabera ordenatzen ditu. */
    static void ordenatu(ArrayList<Kontzertua> lista) {
        System.out.println("\n--- ORDENATU ---");
        System.out.println("  1. Dataren arabera (eta orduaren arabera)");
        System.out.println("  2. Taldearen arabera (A-Z)");
        System.out.println("  3. Prezioaren arabera (txikitik handira)");
        int aukera;
        do {
            aukera = irakurriEnteroa("Aukeratu (1-3): ");
        } while (aukera < 1 || aukera > 3);

        switch (aukera) {
            case 1 -> lista.sort(Comparator.comparing(Kontzertua::getdia)
                                           .thenComparing(Kontzertua::gethora));
            case 2 -> lista.sort(Comparator.comparing(k -> k.gettaldea().toLowerCase()));
            case 3 -> lista.sort(Comparator.comparingDouble(Kontzertua::getprecio));
        }
        System.out.println("Erregistroak ordenatu dira:");
        zerrendatu(lista);
    }

    // ===================== LAGUNTZAILEAK: bilaketa eta taula =====================
    static Kontzertua bilatuIdBidez(ArrayList<Kontzertua> lista, int id) {
        for (Kontzertua k : lista) {
            if (k.getid() == id) return k;
        }
        return null;
    }

    static void inprimatuTaula(ArrayList<Kontzertua> lista) {
        String fmt = "%-" + W_ID + "s │ %-" + W_TALDEA + "s │ %-" + W_DATA + "s │ %-" + W_ORDUA
                + "s │ %" + W_TAQ + "s │ %" + W_PREC + "s │ %-" + W_AGO + "s%n";
        System.out.printf(fmt, "Id", "Taldea", "Data", "Ordua", "Taquilla", "Precio", "Agortuta");
        System.out.println("─".repeat(W_ID) + "─┼─" + "─".repeat(W_TALDEA) + "─┼─" + "─".repeat(W_DATA)
                + "─┼─" + "─".repeat(W_ORDUA) + "─┼─" + "─".repeat(W_TAQ) + "─┼─"
                + "─".repeat(W_PREC) + "─┼─" + "─".repeat(W_AGO));
        for (Kontzertua k : lista) {
            String taldea = k.gettaldea().length() > W_TALDEA
                    ? k.gettaldea().substring(0, W_TALDEA - 1) + "…"
                    : k.gettaldea();
            System.out.printf(fmt,
                    k.getid(),
                    taldea,
                    k.getdia().format(Kontzertua.D_FORMATO),
                    k.gethora().format(Kontzertua.H_FORMATO),
                    String.format(Locale.US, "%.2f€", k.gettaquilla()),
                    String.format(Locale.US, "%.2f€", k.getprecio()),
                    k.isagotatuta() ? "Bai" : "Ez");
        }
        System.out.println("(" + lista.size() + " erregistro)");
    }

    // ===================== ERROREEN KONTROLA =====================
    static int irakurriEnteroa(String mezua) {
        while (true) {
            System.out.print(mezua);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(" Zenbaki oso bat sartu behar duzu.");
            }
        }
    }

    static double irakurriDoublea(String mezua) {
        while (true) {
            System.out.print(mezua);
            try {
                double balioa = Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
                if (balioa < 0) {
                    System.out.println(" Balioa ezin da negatiboa izan.");
                } else {
                    return balioa;
                }
            } catch (NumberFormatException e) {
                System.out.println(" Zenbaki bat sartu behar duzu (adib. 12.50).");
            }
        }
    }

    static String irakurriTestua(String mezua) {
        while (true) {
            System.out.print(mezua);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println(" Ezin da hutsik utzi.");
        }
    }

    static LocalDate irakurriData(String mezua) {
        while (true) {
            System.out.print(mezua);
            try {
                return LocalDate.parse(sc.nextLine().trim(), Kontzertua.D_FORMATO);
            } catch (DateTimeParseException e) {
                System.out.println(" Data okerra. Formatua: dd/MM/uuuu (adib. 05/02/2019).");
            }
        }
    }

    static LocalTime irakurriOrdua(String mezua) {
        while (true) {
            System.out.print(mezua);
            try {
                return LocalTime.parse(sc.nextLine().trim(), Kontzertua.H_FORMATO);
            } catch (DateTimeParseException e) {
                System.out.println(" Ordu okerra. Formatua: HH:mm (adib. 21:00).");
            }
        }
    }

    static boolean irakurriBaiEz(String mezua) {
        while (true) {
            System.out.print(mezua);
            String s = sc.nextLine().trim().toUpperCase();
            if (s.equals("B") || s.equals("BAI")) return true;
            if (s.equals("E") || s.equals("EZ")) return false;
            System.out.println(" B (bai) edo E (ez) idatzi.");
        }
    }
}