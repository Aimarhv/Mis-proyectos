import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

/**
 * Programa nagusia: datuak, menua eta File klasea erabiltzen duten metodoak
 * (CD, DIR, MKDIR, testuN.aad sortu eta DEL *.AAD).
 */
public class Fitxategia {

    // ===================== DATUAK =====================
    // Datu-basea: Kontzertua objektuen arraya
    static Kontzertua[] kontzertuak = {
            new Kontzertua(1, "DONNY BENET (post-disco, Sydney)", LocalDate.of(2019, 2, 5), LocalTime.of(21, 0), 7.00, 10.00),
            new Kontzertua(2, "DAVID PALOMAR + RAFAEL RODRIGUEZ", LocalDate.of(2019, 2, 7), LocalTime.of(20, 0), 15.00, 18.00),
            new Kontzertua(3, "FERNANDO ALFARO (pop, indie, rock, Albacete)", LocalDate.of(2019, 2, 8), LocalTime.of(21, 0), 10.00, 13.00),
            new Kontzertua(4, "YUNG BEEF DJ SET (trap, reggaeton, dancehall)", LocalDate.of(2019, 2, 8), LocalTime.of(23, 59), 11.00, 15.00),
            new Kontzertua(5, "15 YEARS OF PHONICA: ROMAN FLUGEL + SIMONE", LocalDate.of(2019, 2, 9), LocalTime.of(23, 59), 11.00, 15.00),
            new Kontzertua(6, "LOS HERMANOS CUBERO (folk, americana, acordeon)", LocalDate.of(2019, 2, 9), LocalTime.of(22, 0), 8.00, 11.00),
            new Kontzertua(7, "WREKMEISTER HARMONIES (post-rock, metal)", LocalDate.of(2019, 2, 11), LocalTime.of(20, 0), 10.00, 13.00),
            new Kontzertua(8, "IZAR & STAR: JUANJO NAVAS (flamenco) + SON", LocalDate.of(2019, 2, 14), LocalTime.of(20, 0), 8.00, 10.00),
            new Kontzertua(9, "LOS TIKI PHANTOMS (surf, r'n'r, BCN) + MONTE", LocalDate.of(2019, 2, 15), LocalTime.of(21, 0), 10.00, 13.00),
            new Kontzertua(10, "GUS DAPPERTON (new wave, synth, dream-pop)", LocalDate.of(2019, 2, 16), LocalTime.of(21, 0), 10.00, 15.00),
            new Kontzertua(11, "ID: GIULIANO LOMONTE (deep, tech, hypnotic)", LocalDate.of(2019, 2, 16), LocalTime.of(23, 59), 11.00, 15.00),
            new Kontzertua(12, "ROBBY PEOPLES & THE CONGRESS (rock&roll)", LocalDate.of(2019, 2, 17), LocalTime.of(20, 30), 10.00, 13.00),
            new Kontzertua(13, "KAMCHATKA (hard rock, stoner, blues, SWE)", LocalDate.of(2019, 2, 19), LocalTime.of(20, 30), 10.00, 13.00)
    };

    // Scanner bakarra programa osorako
    static Scanner sc = new Scanner(System.in);

    // ===================== MAIN ETA MENUA =====================
    public static void main(String[] args) {
        ejecutarMenu();
        sc.close();
    }

    // Menua errepikatzen du 0 aukeratu arte
    static void ejecutarMenu() {
        // CD eta MKDIR aukerek lan-direktorioa alda dezakete, horregatik itzultzen dute
        File lanDirektorioa = hasierakoDirektorioa();
        int opcion;
        do {
            imprimirMenu(lanDirektorioa);
            opcion = leerEnteroValidado("Aukeratu bat: ");
            switch (opcion) {
                case 1 -> lanDirektorioa = direktorioaAldatu(lanDirektorioa);
                case 2 -> direktorioaZerrendatu(lanDirektorioa);
                case 3 -> lanDirektorioa = direktorioaSortu(lanDirektorioa);
                case 4 -> fitxategiakSortu(lanDirektorioa, kontzertuak);
                case 5 -> aadEzabatu(lanDirektorioa);
                case 0 -> System.out.println("\nPrograma itxi da. Agur!");
                default -> System.out.println("Aukera ez da baliozkoa. Berriro saiatu.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    // Menuaren markoa eta aukerak inprimatzen ditu
    static void imprimirMenu(File lanDirektorioa) {
        String titulo = " KONTZERTUEN MENUA ";
        int ancho = 46;
        System.out.println("Lan-direktorioa: " + lanDirektorioa.getAbsolutePath());
        System.out.println("╔" + "═".repeat(ancho) + "╗");
        System.out.println("║" + centrar(titulo, ancho) + "║");
        System.out.println("╠" + "═".repeat(ancho) + "╣");
        imprimirOpcion("1", "Lan-direktorioa aldatu (CD)", ancho);
        imprimirOpcion("2", "Direktorioa zerrendatu (DIR)", ancho);
        imprimirOpcion("3", "Direktorio berria sortu (MKDIR)", ancho);
        imprimirOpcion("4", "Datu-basetik fitxategiak sortu (.aad)", ancho);
        imprimirOpcion("5", "*.aad fitxategiak ezabatu (DEL)", ancho);
        System.out.println("╟" + "─".repeat(ancho) + "╢");
        imprimirOpcion("0", "Irten", ancho);
        System.out.println("╚" + "═".repeat(ancho) + "╝");
    }

    // Menuko lerro bat inprimatzen du, markoaren zabalerara doituta
    static void imprimirOpcion(String num, String texto, int ancho) {
        String linea = " " + num + ". " + texto;
        System.out.println("║" + rellenar(linea, ancho) + "║");
    }

    // Testua zuriuneekin betetzen du zabalera zehatzera arte (edo moztu)
    static String rellenar(String s, int ancho) {
        if (s.length() >= ancho) return s.substring(0, ancho);
        return s + " ".repeat(ancho - s.length());
    }

    // Testua zentratzen du zabalera jakin batean
    static String centrar(String s, int ancho) {
        int espacios = ancho - s.length();
        int izq = espacios / 2, der = espacios - izq;
        return " ".repeat(Math.max(izq, 0)) + s + " ".repeat(Math.max(der, 0));
    }

    // Zenbaki oso bat eskatzen du; baliogabea bada, berriro eskatzen du
    static int leerEnteroValidado(String mezua) {
        System.out.print(mezua);
        boolean esEntero = sc.hasNextInt();
        while (!esEntero) {
            sc.nextLine(); // sarrera okerra garbitu
            System.out.println(" Zenbaki oso bat sartu behar duzu.");
            System.out.print(mezua);
            esEntero = sc.hasNextInt();
        }
        int balioa = sc.nextInt();
        sc.nextLine(); // lerro-jauzia kendu hurrengo irakurketarako
        return balioa;
    }

    // ===================== FILE KLASEA =====================

    /** Programa abiarazi den direktorioa itzultzen du. */
    static File hasierakoDirektorioa() {
        return new File(System.getProperty("user.dir"));
    }

    /**
     * CD: lan-direktorioa aldatzen du. Ezer idazten ez bada, egungoa mantentzen da.
     * @return direktorio berria (errorea badago, egungo berbera)
     */
    static File direktorioaAldatu(File unekoa) {
        System.out.println("\n--- LAN-DIREKTORIOA ALDATU (CD) ---");
        System.out.print("Direktorio berria: ");
        String sarrera = sc.nextLine().trim();

        if (sarrera.isEmpty()) {
            System.out.println("Direktorio berean jarraitzen dugu: " + unekoa.getAbsolutePath());
            return unekoa;
        }

        // ".." idatziz goiko direktoriora joaten da
        if (sarrera.equals("..")) {
            File gurasoa = unekoa.getParentFile();
            if (gurasoa == null) { // erroan ez dago goiko direktoriorik
                System.out.println("Errorea: ez dago goiko direktoriorik.");
                return unekoa;
            }
            System.out.println("Lan-direktorio berria: " + gurasoa.getAbsolutePath());
            return gurasoa;
        }

        // Ibilbide erlatiboa bada, lan-direktorioari lotzen zaio
        File berria = new File(sarrera);
        if (!berria.isAbsolute()) {
            berria = new File(unekoa, sarrera);
        }

        if (!berria.exists()) {
            System.out.println("Errorea: ez dago direktorio hori.");
            return unekoa;
        }
        if (!berria.isDirectory()) {
            System.out.println("Errorea: hori fitxategi bat da, ez direktorio bat.");
            return unekoa;
        }

        System.out.println("Lan-direktorio berria: " + berria.getAbsolutePath());
        return berria;
    }

    /** DIR: lan-direktorioko fitxategiak eta direktorioak tabulatuta erakusten ditu. */
    static void direktorioaZerrendatu(File dir) {
        System.out.println("\n--- DIREKTORIOAREN ZERRENDA (DIR) ---");

        File[] edukia = dir.listFiles();
        if (edukia == null) { // irakurri ezin bada null itzultzen du
            System.out.println("Errorea: ezin da direktorioa irakurri.");
            return;
        }

        SimpleDateFormat formatua = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        int fitxategiak = 0;
        int direktorioak = 0;
        long bytes = 0;

        System.out.println(" " + dir.getAbsolutePath() + " direktorioko edukia:\n");
        for (File f : edukia) {
            String data = formatua.format(new Date(f.lastModified()));
            // Zutabeak: data, <DIR> edo tamaina, izena
            if (f.isDirectory()) {
                System.out.printf("%-17s  %-7s %12s  %s%n", data, "<DIR>", "", f.getName());
                direktorioak++;
            } else {
                System.out.printf("%-17s  %-7s %,12d  %s%n", data, "", f.length(), f.getName());
                fitxategiak++;
                bytes = bytes + f.length();
            }
        }

        System.out.printf("%18d fitxategi %,14d byte%n", fitxategiak, bytes);
        System.out.printf("%18d direktorio%n", direktorioak);
    }

    /**
     * MKDIR: direktorio berri bat sortzen du lan-direktorioan eta lan-direktorio bihurtzen da.
     * @return direktorio berria (errorea badago, egungo berbera)
     */
    static File direktorioaSortu(File unekoa) {
        System.out.println("\n--- DIREKTORIOA SORTU (MKDIR) ---");
        System.out.print("Direktorio berriaren izena: ");
        String izena = sc.nextLine().trim();

        if (izena.isEmpty()) {
            System.out.println("Errorea: izena ezin da hutsik egon.");
            return unekoa;
        }

        // Ez dira karakter hauek onartzen
        if (izena.contains("\\") || izena.contains("/") || izena.contains(":")
                || izena.contains("*") || izena.contains("?") || izena.contains("\"")
                || izena.contains("<") || izena.contains(">") || izena.contains("|")) {
            System.out.println("Errorea: izenak ezin ditu karaktere hauek eduki: \\ / : * ? \" < > |");
            return unekoa;
        }

        File berria = new File(unekoa, izena);
        if (berria.exists()) {
            System.out.println("Errorea: badago fitxategi edo direktorio bat izen horrekin.");
            return unekoa;
        }

        	// True itzultzen du ondo sortu bada
        if (berria.mkdir()) { 
            System.out.println("Direktorioa ondo sortu da. Hau da orain lan-direktorioa.");
            return berria;
            // Bestela direktoria ezingo da sortu
        } else {
            System.out.println("Errorea: ezin izan da direktorioa sortu.");
            return unekoa;
        }
    }

    /**
     * Datu-basetik (arraytik) "testuN.aad" fitxategi bat sortzen du erregistro bakoitzeko.
     * N = kontzertuaren Id-a, eta barruan taldearen izena idazten da.
     */
    static void fitxategiakSortu(File dir, Kontzertua[] lista) {
        System.out.println("\n--- testuN.aad FITXATEGIAK SORTU ---");

        if (lista.length == 0) {
            System.out.println("Ez dago erregistrorik, ez dago ezer sortzeko.");
            return;
        }
        if (!dir.canWrite()) {
            System.out.println("Errorea: ezin da idatzi direktorio honetan.");
            return;
        }

        int sortuak = 0;
        int berridatziak = 0;
        for (int i = 0; i < lista.length; i++) {
            File fitxategia = new File(dir, "testu" + lista[i].getId() + ".aad");
            if (fitxategia.exists()) {
            	// lehendik bazegoen
                berridatziak++; 
            }
            try {
                FileWriter fw = new FileWriter(fitxategia);
                fw.write(lista[i].getTaldea());
                fw.close();
                sortuak++;
            } catch (IOException e) {
                System.out.println("Errorea " + fitxategia.getName() + " sortzean: " + e.getMessage());
            }
        }
        System.out.println(sortuak + " fitxategi sortu dira (" + berridatziak + " lehendik zeuden eta berridatzi dira).");
    }

    /** DEL *.AAD: lan-direktorioko .aad fitxategi guztiak ezabatzen ditu. */
    static void aadEzabatu(File dir) {
        System.out.println("\n--- *.AAD FITXATEGIAK EZABATU ---");

        File[] edukia = dir.listFiles();
        if (edukia == null) {
            System.out.println("Errorea: ezin da direktorioa irakurri.");
            return;
        }

        int ezabatuak = 0;
        for (File f : edukia) {
            if (f.isFile() && f.getName().endsWith(".aad")) {
                if (f.delete()) {
                    ezabatuak++;
                } else {
                    System.out.println("Ezin izan da ezabatu: " + f.getName());
                }
            }
        }
        System.out.println(ezabatuak + " fitxategi ezabatu dira.");
    }
}