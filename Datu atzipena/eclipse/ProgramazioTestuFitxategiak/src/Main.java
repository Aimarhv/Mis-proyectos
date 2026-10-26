import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;

/**
 * Programa nagusia: fitxategia aukeratu eta menua kudeatzen du.
 * Eragiketa guztiak KontzertuFitxategia klasean daude; teklatuko sarrera Sarrera klasean.
 */
public class Main {

    // ===================== DATUAK =====================
    // Hasierako taula: 1. aukerak fitxategia hauekin betetzeko baino ez da erabiltzen
    static final Kontzertua[] TAULA = {
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

    // ===================== MAIN =====================
    public static void main(String[] args) {
        try {
            // Lehenengo fitxategia aukeratzen dugu; null bada erabiltzaileak irten nahi du
            File fitxategia = aukeratuFitxategia();
            if (fitxategia == null) {
                System.out.println("\nPrograma itxi da. Agur");
            } else {
                ejecutarMenu(fitxategia);
            }
        } catch (NoSuchElementException e) {
            // Teklatuko sarrera amaitu da (adib. Ctrl+D)
            System.out.println("\nSarrera amaitu da. Programa itxi da.");
        } finally {
            // finally-ean jarri dut, errorea gertatu arren Scanner-a itxi dadin
            Sarrera.itxi();
        }
    }

    // ===================== FITXATEGIA AUKERATU =====================

    /**
     * Fitxategiaren ibilbidea eta izena eskatzen ditu.
     * - Badago: lehen lerroa GOIBURUA dela egiaztatzen da.
     * - Ez badago: erabiltzaileak baieztatzen badu, goiburuarekin sortzen da.
     * @return erabiliko den fitxategia, edo null erabiltzaileak irten nahi badu
     */
    static File aukeratuFitxategia() {
        // Begizta amaigabea: fitxategi on bat aukeratu arte edo irten nahi duen arte
        while (true) {
            System.out.println("--- FITXATEGIA AUKERATU ---");
            String ibilbidea = Sarrera.irakurriLerroa(" Direktorioaren Ibilbidea: ");
            // Ibilbidea hutsik badago, programa dagoen direktorioa erabiltzen dut
            File dir = ibilbidea.isEmpty() ? new File(System.getProperty("user.dir")) : new File(ibilbidea);

            if (!dir.isDirectory()) {
                System.out.println("Errorea: ibilbide hori ez da existitzen edo ez da direktorio bat.\n");
                continue; // berriro hasieratik
            }
            String izena = Sarrera.irakurriFitxategiIzena("Fitxategiaren izena: ");
            File f = new File(dir, izena).getAbsoluteFile();

            try {
                if (f.isDirectory()) {
                    System.out.println("Errorea: hori direktorio bat da, ez fitxategi bat.");
                } else if (f.exists()) {
                    // Existitzen bada, goiburua zuzena den begiratzen dut
                    if (KontzertuFitxategia.goiburuaZuzenaDa(f)) {
                        System.out.println("Fitxategia ondo dago: " + f.getAbsolutePath() + "\n");
                        return f;
                    }
                    System.out.println("Errorea: fitxategiaren lehen lerroa ez da \""
                            + KontzertuFitxategia.GOIBURUA + "\".");
                } else {
                    // Ez badago, sortu nahi duen galdetzen diot
                    System.out.println("Fitxategia ez dago.");
                    if (Sarrera.baieztatu("Sortu nahi duzu?")) {
                        KontzertuFitxategia.sortuHutsik(f);
                        System.out.println("Fitxategia sortu da: " + f.getAbsolutePath() + "\n");
                        return f;
                    }
                }
            } catch (IOException e) {
                System.out.println("Errorea fitxategia irakurtzean/sortzean: " + e.getMessage());
            }

            // Hona iristen bada, fitxategiak ez du balio: beste bat saiatu nahi duen galdetzen dut
            if (!Sarrera.baieztatu("Beste fitxategi bat aukeratu nahi duzu?")) {
                return null;
            }
            System.out.println();
        }
    }

    // ===================== MENUA =====================

    // Menua errepikatzen du 0 aukeratu arte
    static void ejecutarMenu(File f) {
        int opzioa;
        do {
            imprimirMenu(f);
            opzioa = Sarrera.irakurriEnteroa("Aukeratu bat: ", 0);
            try {
                // Aukera bakoitzak bere metodoa du, horrela main-a argiagoa da
            	switch (opzioa) {
                case 1:
                    betetzeko(f);
                    break;
                case 2:
                    hustu(f);
                    break;
                case 3:
                    zenbatu(f);
                    break;
                case 4:
                    bilatuKodea(f);
                    break;
                case 5:
                    bilatuHitza(f);
                    break;
                case 6:
                    gehitu(f);
                    break;
                case 7:
                    ezabatu(f);
                    break;
                case 8:
                    kopiatu(f);
                    break;
                case 9:
                    aldatu(f);
                    break;
                case 0:
                    System.out.println("\nPrograma itxi da. Agur!");
                    break;
                default:
                    System.out.println("Aukera ez da baliozkoa. Berriro saiatu.");
            }
            // KontzertuFitxategia-ko metodoek IOException jaurtitzen dute, hemen harrapatzen dugu
            } catch (FileNotFoundException e) {
                System.out.println("Errorea: ezin da fitxategia aurkitu (" + e.getMessage() + ").");
            } catch (IOException e) {
                System.out.println("Errorea sarrera/irteeran: " + e.getMessage());
            }
            System.out.println();
        } while (opzioa != 0);
    }

    // Menuaren marko-a marrazten du karaktere bereziekin
    static void imprimirMenu(File f) {
        int ancho = 46;
        System.out.println("Fitxategia: " + f.getAbsolutePath());
        System.out.println("╔" + "═".repeat(ancho) + "╗");
        System.out.println("║" + centrar(" KONTZERTUEN MENUA (testu-fitxategia) ", ancho) + "║");
        System.out.println("╠" + "═".repeat(ancho) + "╣");
        imprimirOpcion("1", "Fitxategia taulako datuekin bete", ancho);
        imprimirOpcion("2", "Fitxategia hustu", ancho);
        imprimirOpcion("3", "Erregistroak zenbatu", ancho);
        imprimirOpcion("4", "Erregistro bat bilatu (kodea)", ancho);
        imprimirOpcion("5", "Taldean hitz bat duten erregistroak", ancho);
        imprimirOpcion("6", "Erregistro bat gehitu", ancho);
        imprimirOpcion("7", "Erregistro bat ezabatu", ancho);
        imprimirOpcion("8", "Fitxategiaren kopia bat sortu", ancho);
        imprimirOpcion("9", "Erregistro baten eremuak aldatu", ancho);
        System.out.println("╟" + "─".repeat(ancho) + "╢");
        imprimirOpcion("0", "Irten", ancho);
        System.out.println("╚" + "═".repeat(ancho) + "╝");
    }

    static void imprimirOpcion(String num, String texto, int ancho) {
        String linea = " " + num + ". " + texto;
        System.out.println("║" + rellenar(linea, ancho) + "║");
    }

    // Testua zabalera zehatzera iristeko zuriuneak gehitzen ditu (luzeegia bada, mozten du)
    static String rellenar(String s, int ancho) {
        if (s.length() >= ancho) return s.substring(0, ancho);
        return s + " ".repeat(ancho - s.length());
    }

    // Testua erdian jartzen du, ezkerrean eta eskuinean zuriuneak banatuz
    static String centrar(String s, int ancho) {
        int espacios = ancho - s.length();
        int izq = Math.max(espacios / 2, 0), der = Math.max(espacios - izq, 0);
        return " ".repeat(izq) + s + " ".repeat(der);
    }

    // ===================== MENUKO AUKERAK =====================

    /** 1. Fitxategia taulako datuekin betetzen du (aurreko datuak galdu egiten dira). */
    static void betetzeko(File f) throws IOException {
        System.out.println("\n--- FITXATEGIA BETE ---");
        // Datuak galtzen direnez, lehenago baieztapena eskatzen dut
        if (!Sarrera.baieztatu("Fitxategiko datu guztiak galduko dira. Jarraitu?")) {
            System.out.println("Eragiketa bertan behera utzi da.");
            return;
        }
        KontzertuFitxategia.bete(f, TAULA);
        System.out.println(TAULA.length + " erregistro idatzi dira.");
    }

    /** 2. Fitxategia hutsik uzten du (goiburuko lerroa mantentzen da, bestela fitxategia ez litzateke baliozkoa izango). */
    static void hustu(File f) throws IOException {
        System.out.println("\n--- FITXATEGIA HUSTU ---");
        if (!Sarrera.baieztatu("Erregistro guztiak ezabatuko dira. Ziur zaude?")) {
            System.out.println("Eragiketa bertan behera utzi da.");
            return;
        }
        // sortuHutsik() erabiltzen dut, goiburua bakarrik idazten baitu
        KontzertuFitxategia.sortuHutsik(f);
        System.out.println("Fitxategia hutsik dago.");
    }

    /** 3. Erregistro-kopurua erakusten du. */
    static void zenbatu(File f) throws IOException {
        System.out.println("\n--- ERREGISTROAK ZENBATU ---");
        System.out.println("Fitxategiak " + KontzertuFitxategia.zenbatu(f) + " erregistro ditu.");
    }

    /** 4. Kodea emanda erregistro bat bilatzen eta erakusten du. */
    static void bilatuKodea(File f) throws IOException {
        System.out.println("\n--- ERREGISTRO BAT BILATU ---");
        int kodea = Sarrera.irakurriEnteroa("Kodea: ", 1);
        Kontzertua k = KontzertuFitxategia.bilatu(f, kodea);
        // bilatu() null itzultzen du ez badago
        if (k == null) {
            System.out.println("Ez dago " + kodea + " kodea duen erregistrorik.");
        } else {
            System.out.println(k);
        }
    }

    /** 5. Testu-eremuan (taldea) hitz bat duten erregistroak zerrendatzen ditu. */
    static void bilatuHitza(File f) throws IOException {
        System.out.println("\n--- HITZA DUTEN ERREGISTROAK ---");
        String hitza = Sarrera.irakurriTestua("Taldean bilatu nahi duzun hitza: ");
        // Metodoak berak erakusten ditu aurkitutakoak eta kopurua itzultzen du
        int kop = KontzertuFitxategia.zerrendatuHitzarekin(f, hitza);
        if (kop == 0) {
            System.out.println("Ez dago \"" + hitza + "\" duen erregistrorik.");
        } else {
            System.out.println(kop + " erregistro aurkitu dira.");
        }
    }

    /** 6. Erregistro berri bat gehitzen du amaieran (kodea ezin da errepikatu). */
    static void gehitu(File f) throws IOException {
        System.out.println("\n--- ERREGISTROA GEHITU ---");
        int kodea = Sarrera.irakurriEnteroa("Kodea: ", 1);
        // Kodea errepikatuta badago, ez dut gainerako datuak eskatzen
        if (KontzertuFitxategia.bilatu(f, kodea) != null) {
            System.out.println("Errorea: " + kodea + " kodea lehendik badago.");
            return;
        }
        String taldea = Sarrera.irakurriTestua("Taldea: ");
        LocalDate dia = Sarrera.irakurriData("Eguna (uuuu-hh-ee): ");
        LocalTime hora = Sarrera.irakurriOrdua("Ordua (oo:mm): ");
        double taquilla = Sarrera.irakurriErreala("Taquilla prezioa: ");
        double precio = Sarrera.irakurriErreala("Prezioa: ");

        Kontzertua berria = new Kontzertua(kodea, taldea, dia, hora, taquilla, precio);
        if (KontzertuFitxategia.gehitu(f, berria)) {
            System.out.println("Erregistroa gehitu da.");
        } else {
            System.out.println("Errorea: ezin izan da gehitu (kodea errepikatuta).");
        }
    }

    /** 7. Erregistro bat ezabatzen du (baieztapenarekin). */
    static void ezabatu(File f) throws IOException {
        System.out.println("\n--- ERREGISTROA EZABATU ---");
        int kodea = Sarrera.irakurriEnteroa("Ezabatu nahi duzun erregistroaren kodea: ", 1);
        Kontzertua k = KontzertuFitxategia.bilatu(f, kodea);
        if (k == null) {
            System.out.println("Ez dago " + kodea + " kodea duen erregistrorik.");
            return;
        }
        // Erregistroa erakusten dut, erabiltzaileak ziur dagoen ikus dezan
        System.out.println(k);
        if (!Sarrera.baieztatu("Erregistro hau ezabatu nahi duzu?")) {
            System.out.println("Eragiketa bertan behera utzi da.");
            return;
        }
        if (KontzertuFitxategia.ezabatu(f, kodea)) {
            System.out.println("Erregistroa ezabatu da.");
        }
    }

    /** 8. Fitxategiaren kopia bat sortzen du, egiaztapen guztiekin. */
    static void kopiatu(File f) throws IOException {
        System.out.println("\n--- FITXATEGIAREN KOPIA ---");
        String izena = Sarrera.irakurriLerroa("Fitxategiaren izena (edo ibilbide osoa): ");
        if (izena.isEmpty()) {
            System.out.println("Errorea: izena ezin da hutsik egon.");
            return;
        }
        // Ibilbide erlatiboa bada, jatorrizko fitxategiaren direktorioan sortzen da
        File helburua = new File(izena);
        if (!helburua.isAbsolute()) {
            helburua = new File(f.getParentFile(), izena);
        }
        helburua = helburua.getAbsoluteFile();

        // Kopiatu baino lehen egiaztapen guztiak egiten ditut, errorerik ez izateko
        if (!f.exists()) {
            System.out.println("Errorea: jatorrizko fitxategia ez da existitzen.");
        // getCanonicalPath() erabiltzen dut bi ibilbideak fitxategi bera diren jakiteko
        } else if (helburua.getCanonicalPath().equals(f.getCanonicalPath())) {
            System.out.println("Errorea: helburua eta jatorrizkoa fitxategi bera dira.");
        } else if (helburua.isDirectory()) {
            System.out.println("Errorea: helburua direktorio bat da.");
        } else if (!helburua.getParentFile().isDirectory()) {
            System.out.println("Errorea: helburuaren direktorioa ez da existitzen.");
        } else if (helburua.exists() && !Sarrera.baieztatu("Helburua lehendik badago. Gainidatzi?")) {
            System.out.println("Eragiketa bertan behera utzi da.");
        } else {
            KontzertuFitxategia.kopiatu(f, helburua);
            System.out.println("Kopia sortu da: " + helburua.getAbsolutePath());
        }
    }

    /** 9. Kodearen bidez aukeratutako erregistroaren eremuak aldatzen ditu (ENTER = mantendu). */
    static void aldatu(File f) throws IOException {
        System.out.println("\n--- ERREGISTROA ALDATU ---");
        int kodea = Sarrera.irakurriEnteroa("Aldatu nahi duzun erregistroaren kodea: ", 1);
        Kontzertua k = KontzertuFitxategia.bilatu(f, kodea);
        if (k == null) {
            System.out.println("Ez dago " + kodea + " kodea duen erregistrorik.");
            return;
        }
        System.out.println("Oraingo datuak: " + k);
        System.out.println("(ENTER sakatu eremu bat aldatu gabe uzteko)");

        // Kodea aldatzen bada, ezin da beste erregistro baten berdina izan
        // Azken parametroa lehenetsitako balioa da: ENTER sakatzen badu, kode zaharra gelditzen da
        int kodeBerria = Sarrera.irakurriEnteroa("Kodea: ", 1, k.getId());
        while (kodeBerria != kodea && KontzertuFitxategia.bilatu(f, kodeBerria) != null) {
            System.out.println(" Errorea: " + kodeBerria + " kodea lehendik badago.");
            kodeBerria = Sarrera.irakurriEnteroa("Kodea: ", 1, k.getId());
        }
        // Gainerako eremuetan ere oraingo balioa ematen diot lehenetsi gisa
        String taldea = Sarrera.irakurriTestua("Taldea: ", k.getTaldea());
        LocalDate dia = Sarrera.irakurriData("Eguna (uuuu-hh-ee): ", k.getDia());
        LocalTime hora = Sarrera.irakurriOrdua("Ordua (oo:mm): ", k.getHora());
        double taquilla = Sarrera.irakurriErreala("Taquilla prezioa: ", k.getTaquilla());
        double precio = Sarrera.irakurriErreala("Prezioa: ", k.getPrecio());

        // Datu berriekin objektu berria sortzen dut eta zaharra ordezkatzen dut fitxategian
        Kontzertua berria = new Kontzertua(kodeBerria, taldea, dia, hora, taquilla, precio);
        if (KontzertuFitxategia.aldatu(f, kodea, berria)) {
            System.out.println("Erregistroa aldatu da.");
        }
    }
}