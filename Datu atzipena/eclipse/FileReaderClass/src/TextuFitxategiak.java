import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Programa nagusia: Kontzertua erregistroen testu-fitxategien menua.
 * Eragiketak FitxategiOperazioak klasean daude, sarrera Sarrera klasean.
 */
public class TextuFitxategiak {

    // Datu-basea: Kontzertua objektuen arraya (fitxategia betetzeko erabiltzen da)
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

    // ===================== MAIN ETA MENUA =====================
    public static void main(String[] args) {
        menua();
        Sarrera.sc.close();
    }

    /** Menu nagusia: fitxategia aukeratu eta aukerak errepikatzen ditu 0 aukeratu arte. */
    static void menua() {
        FitxategiOperazioak.fitxategia = FitxategiOperazioak.fitxategiaAukeratu();
        if (FitxategiOperazioak.fitxategia == null) { // erabiltzaileak irten nahi du
            System.out.println("\nProgramatik irten da.");
            return;
        }
        int aukera;
        do {
            menuaInprimatu();
            aukera = Sarrera.leerEnteroValidado("Aukeratu bat: ");
            try {
                switch (aukera) {
                    case 1 -> FitxategiOperazioak.fitxategiaBetetu(kontzertuak);
                    case 2 -> FitxategiOperazioak.fitxategiaHustu();
                    case 3 -> FitxategiOperazioak.erregistroakZenbatu();
                    case 4 -> FitxategiOperazioak.kodeaBilatu();
                    case 5 -> FitxategiOperazioak.hitzaBilatu();
                    case 6 -> FitxategiOperazioak.erregistroaGehitu();
                    case 7 -> FitxategiOperazioak.erregistroaEzabatu();
                    case 8 -> FitxategiOperazioak.kopiaSortu();
                    case 9 -> FitxategiOperazioak.erregistroaAldatu();
                    case 0 -> System.out.println("\nTestu-fitxategien menua itxi da.");
                    default -> System.out.println("Aukera ez da baliozkoa. Berriro saiatu.");
                }
            } catch (IOException e) {
                System.out.println("Errorea: sarrera/irteera arazoa fitxategiarekin (" + e.getMessage() + ").");
            }
            System.out.println();
        } while (aukera != 0);
    }

    /** Menuaren marko eta aukerak inprimatzen ditu . */
    static void menuaInprimatu() {
        int ancho = 46;
        System.out.println("Fitxategia: " + FitxategiOperazioak.fitxategia.getAbsolutePath());
        System.out.println("╔" + "═".repeat(ancho) + "╗");
        System.out.println("║" + centrar(" TESTU-FITXATEGIEN MENUA ", ancho) + "║");
        System.out.println("╠" + "═".repeat(ancho) + "╣");
        imprimirOpcion("1", "Fitxategia taulako datuekin bete", ancho);
        imprimirOpcion("2", "Fitxategia hustu", ancho);
        imprimirOpcion("3", "Erregistroak zenbatu", ancho);
        imprimirOpcion("4", "Erregistroa bilatu kodearen bidez", ancho);
        imprimirOpcion("5", "Erregistroak bilatu hitz baten bidez", ancho);
        imprimirOpcion("6", "Erregistroa gehitu", ancho);
        imprimirOpcion("7", "Erregistroa ezabatu", ancho);
        imprimirOpcion("8", "Fitxategiaren kopia sortu", ancho);
        imprimirOpcion("9", "Erregistroa aldatu", ancho);
        System.out.println("╟" + "─".repeat(ancho) + "╢");
        imprimirOpcion("0", "Irten", ancho);
        System.out.println("╚" + "═".repeat(ancho) + "╝");
    }


    // ===================== LAGUNTZAILEAK: MENUA =====================

    /** Menuko lerro bat inprimatzen du, markoaren zabalerara doituta. */
    static void imprimirOpcion(String num, String texto, int ancho) {
        String linea = " " + num + ". " + texto;
        if (linea.length() > ancho) {
            linea = linea.substring(0, ancho);
        }
        System.out.println("║" + linea + " ".repeat(ancho - linea.length()) + "║");
    }

    /** Testua zentratzen du zabalera jakin batean. */
    static String centrar(String s, int ancho) {
        int espacios = ancho - s.length();
        int izq = espacios / 2, der = espacios - izq;
        return " ".repeat(Math.max(izq, 0)) + s + " ".repeat(Math.max(der, 0));
    }
}