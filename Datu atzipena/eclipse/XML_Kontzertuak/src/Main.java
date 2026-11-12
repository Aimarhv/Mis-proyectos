import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Programa nagusia: XML fitxategia aukeratu eta menua kudeatzen du.
 * Eragiketa guztiak KontzertuXML klasean daude eta teklatuko sarrera Sarrera klasean.
 */
public class Main {

    // ===================== DATUAK =====================
    // 1. aukerak fitxategia datu hauekin betetzen du
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
            // lehenengo fitxategia aukeratu behar dugu; null bada irten nahi du
            File fitxategia = aukeratuFitxategia();
            if (fitxategia == null) {
                System.out.println("\nPrograma itxi da. Agur");
            } else {
                menuaExekutatu(fitxategia);
            }
        } catch (NoSuchElementException e) {
            // Ctrl+D sakatzen bada hemen sartzen da
            System.out.println("\nSarrera amaitu da. Programa itxi da.");
        } finally {
            // finally-an jarri dut errorea egon arren Scanner-a ixteko
            Sarrera.itxi();
        }
    }

    // ===================== FITXATEGIA AUKERATU =====================

    /**
     * Fitxategia aukeratzen du. Existitzen bada XML zuzena dela egiaztatzen du,
     * eta ez badago sortzeko aukera ematen du.
     * @return fitxategia, edo null erabiltzaileak irten nahi badu
     */
    static File aukeratuFitxategia() {
        // bukle amaigabea, fitxategi on bat lortu arte (edo irten arte)
        while (true) {
            System.out.println("--- XML FITXATEGIA AUKERATU ---");
            String ibilbidea = Sarrera.irakurriLerroa(" Direktorioaren Ibilbidea: ");
            // hutsik badago, uneko direktorioa erabiltzen dut
            File direktorioa = ibilbidea.isEmpty() ? new File(System.getProperty("user.dir")) : new File(ibilbidea);

            // direktorioa ez bada existitzen, berriro hasi
            if (!direktorioa.isDirectory()) {
                System.out.println("Errorea: ibilbide hori ez da existitzen edo ez da direktorio bat.\n");
                continue;
            }
            String izena = Sarrera.irakurriFitxategiIzena("Fitxategiaren izena: ");
            // ibilbide osoa lortzeko
            File f = new File(direktorioa, izena).getAbsoluteFile();

            try {
                if (f.isDirectory()) {
                    System.out.println("Errorea: hori direktorio bat da, ez fitxategi bat.");
                } else if (f.exists()) {
                    // existitzen bada, XML zuzena den eta erroa "Kontzertuak" den begiratzen dut
                    if (KontzertuXML.zuzenaDa(f)) {
                        System.out.println("Fitxategia ondo dago: " + f.getAbsolutePath() + "\n");
                        return f;
                    }
                    System.out.println("Errorea: XML-aren erro-elementua ez da <" + KontzertuXML.ERROA + ">.");
                } else {
                    // ez badago, sortu nahi duen galdetzen diot
                    System.out.println("Fitxategia ez dago.");
                    if (Sarrera.baieztatu("Sortu nahi duzu?")) {
                        KontzertuXML.sortuHutsik(f);
                        System.out.println("Fitxategia sortu da: " + f.getAbsolutePath() + "\n");
                        return f;
                    }
                }
            } catch (SAXException e) {
                // XML gaizki idatzita dagoenean (etiketak itxi gabe, fitxategi hutsa...)
                System.out.println("Errorea: fitxategia ez da XML baliozkoa (" + e.getMessage() + ").");
            } catch (IOException | ParserConfigurationException | TransformerException e) {
                System.out.println("Errorea fitxategia irakurtzean/sortzean: " + e.getMessage());
            }

            // hona iristen bada, ez da fitxategi onik lortu
            if (!Sarrera.baieztatu("Beste fitxategi bat aukeratu nahi duzu?")) {
                return null;
            }
            System.out.println();
        }
    }

    // ===================== MENUA =====================

    // menua errepikatzen du 0 sartu arte
    static void menuaExekutatu(File f) {
        int aukera;
        do {
            menuaInprimatu(f);
            aukera = Sarrera.irakurriEnteroa("Aukeratu bat: ", 0);
            try {
                // aukera bakoitzak bere metodoa du, horrela argiago geratzen da
                switch (aukera) {
                case 1:
                    betetzeko(f);
                    break;
                case 2:
                    zerrendatu(f);
                    break;
                case 3:
                    bilatuKodea(f);
                    break;
                case 4:
                    bilatuHitza(f);
                    break;
                case 5:
                    ezabatu(f);
                    break;
                case 0:
                    System.out.println("\nPrograma itxi da. Agur!");
                    break;
                default:
                    System.out.println("Aukera ez da baliozkoa. Berriro saiatu.");
                }
            // KontzertuXML-ko metodoek salbuespenak botatzen dituzte eta hemen harrapatzen ditut
            } catch (SAXException e) {
                System.out.println("Errorea: fitxategiaren XML-a ez da baliozkoa (" + e.getMessage() + ").");
            } catch (IOException e) {
                System.out.println("Errorea sarrera/irteeran: " + e.getMessage());
            } catch (ParserConfigurationException | TransformerException e) {
                System.out.println("Errorea XML-a prozesatzean: " + e.getMessage());
            }
            // lerro huts bat menuak ez elkartzeko
            System.out.println();
        } while (aukera != 0);
    }

    // menuaren koadroa marrazten du karaktere bereziekin
    static void menuaInprimatu(File f) {
        int zabalera = 46;
        System.out.println("Fitxategia: " + f.getAbsolutePath());
        System.out.println("╔" + "═".repeat(zabalera) + "╗");
        System.out.println("║" + erdiratu(" KONTZERTUEN MENUA (XML) ", zabalera) + "║");
        System.out.println("╠" + "═".repeat(zabalera) + "╣");
        aukeraInprimatu("1", "XML fitxategia taulako datuekin bete", zabalera);
        aukeraInprimatu("2", "Erregistro guztiak zerrendatu", zabalera);
        aukeraInprimatu("3", "Erregistro bat bilatu (kodea)", zabalera);
        aukeraInprimatu("4", "Taldean hitz bat duten erregistroak", zabalera);
        aukeraInprimatu("5", "Erregistro bat ezabatu (kodea)", zabalera);
        System.out.println("╟" + "─".repeat(zabalera) + "╢");
        aukeraInprimatu("0", "Irten", zabalera);
        System.out.println("╚" + "═".repeat(zabalera) + "╝");
    }

    // menuko lerro bat inprimatzen du
    static void aukeraInprimatu(String zenbakia, String testua, int zabalera) {
        String lerroa = " " + zenbakia + ". " + testua;
        System.out.println("║" + zuriuneakGehitu(lerroa, zabalera) + "║");
    }

    // zuriuneak gehitzen ditu zabalerara iritsi arte (luzeegia bada, mozten du)
    static String zuriuneakGehitu(String s, int zabalera) {
        if (s.length() >= zabalera) return s.substring(0, zabalera);
        return s + " ".repeat(zabalera - s.length());
    }

    // testua erdian jartzen du
    static String erdiratu(String s, int zabalera) {
        int zuriuneak = zabalera - s.length();
        // Math.max jarri dut negatiboa ez izateko
        int ezker = Math.max(zuriuneak / 2, 0), eskuin = Math.max(zuriuneak - ezker, 0);
        return " ".repeat(ezker) + s + " ".repeat(eskuin);
    }

    // ===================== MENUKO AUKERAK =====================

    /** XML fitxategia taulako datuekin betetzen du (aurreko datuak galdu egiten dira). */
    static void betetzeko(File f) throws ParserConfigurationException, TransformerException {
        System.out.println("\n--- FITXATEGIA BETE ---");
        // datuak galtzen direnez, lehenengo galdetzen dut
        if (!Sarrera.baieztatu("Fitxategiko datu guztiak galduko dira. Jarraitu?")) {
            System.out.println("Eragiketa bertan behera utzi da.");
            return;
        }
        KontzertuXML.bete(f, TAULA);
        System.out.println(TAULA.length + " erregistro idatzi dira.");
    }

    /** Erregistro guztiak zerrendatzen ditu. */
    static void zerrendatu(File f) throws IOException, SAXException, ParserConfigurationException {
        System.out.println("\n--- ERREGISTRO GUZTIAK ---");
        // zerrendatu metodoak zenbat erregistro dauden itzultzen du
        int kop = KontzertuXML.zerrendatu(f);
        if (kop == 0) {
            System.out.println("Fitxategian ez dago erregistrorik.");
        } else {
            System.out.println("\n" + kop + " erregistro.");
        }
    }

    /** Kodea eskatu eta erregistroa bilatzen du. */
    static void bilatuKodea(File f) throws IOException, SAXException, ParserConfigurationException {
        System.out.println("\n--- ERREGISTRO BAT BILATU ---");
        int kodea = Sarrera.irakurriEnteroa("Kodea: ", 1);
        Element nodoa = KontzertuXML.bilatu(f, kodea);
        // ez badago, bilatu-k null itzultzen du
        if (nodoa == null) {
            System.out.println("Ez dago " + kodea + " kodea duen erregistrorik.");
        } else {
            System.out.println(KontzertuXML.formatua(nodoa));
        }
    }

    /** Taldean hitz bat duten erregistroak zerrendatzen ditu. */
    static void bilatuHitza(File f) throws IOException, SAXException, ParserConfigurationException {
        System.out.println("\n--- HITZA DUTEN ERREGISTROAK ---");
        String hitza = Sarrera.irakurriTestua("Taldean bilatu nahi duzun hitza: ");
        int kop = KontzertuXML.zerrendatuHitzarekin(f, hitza);
        if (kop == 0) {
            System.out.println("Ez dago \"" + hitza + "\" duen erregistrorik.");
        } else {
            System.out.println("\n" + kop + " erregistro aurkitu dira.");
        }
    }

    /** Kodea emanda erregistro bat ezabatzen du (baieztapenarekin). */
    static void ezabatu(File f)
            throws IOException, SAXException, ParserConfigurationException, TransformerException {
        System.out.println("\n--- ERREGISTROA EZABATU ---");
        int kodea = Sarrera.irakurriEnteroa("Ezabatu nahi duzun erregistroaren kodea: ", 1);
        Element nodoa = KontzertuXML.bilatu(f, kodea);
        if (nodoa == null) {
            System.out.println("Ez dago " + kodea + " kodea duen erregistrorik.");
            return;
        }
        // erregistroa erakusten dut, erabiltzailea ziur dagoen ikusteko
        System.out.println(KontzertuXML.formatua(nodoa));
        if (!Sarrera.baieztatu("Erregistro hau ezabatu nahi duzu?")) {
            System.out.println("Eragiketa bertan behera utzi da.");
            return;
        }
        // ezabatu egin bada true itzultzen du
        if (KontzertuXML.ezabatu(f, kodea)) {
            System.out.println("Erregistroa ezabatu da.");
        }
    }
}