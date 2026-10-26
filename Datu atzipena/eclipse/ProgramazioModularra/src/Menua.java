import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Programa nagusia: datuak ArrayList batean gorde eta menua kudeatzen du.
 */
public class Menua {

    public static void main(String[] args) {
        // Kontzertu guztiak biltzen dituen ArrayList bakarra
        ArrayList<Kontzertua> kontzertuak = new ArrayList<>();

        kontzertuak.add(new Kontzertua(1, "DONNY BENET (post-disco, Sydney)", LocalDate.of(2019, 2, 5), LocalTime.of(21, 0), 7.00, 10.00, false));
        kontzertuak.add(new Kontzertua(2, "DAVID PALOMAR + RAFAEL RODRIGUEZ", LocalDate.of(2019, 2, 7), LocalTime.of(20, 0), 15.00, 18.00, true));
        kontzertuak.add(new Kontzertua(3, "FERNANDO ALFARO (pop, indie, rock, Albacete)", LocalDate.of(2019, 2, 8), LocalTime.of(21, 0), 10.00, 13.00, false));
        kontzertuak.add(new Kontzertua(4, "YUNG BEEF DJ SET (trap, reggaeton, dancehall)", LocalDate.of(2019, 2, 8), LocalTime.of(23, 59), 11.00, 15.00, false));
        kontzertuak.add(new Kontzertua(5, "15 YEARS OF PHONICA: ROMAN FLUGEL + SIMONE", LocalDate.of(2019, 2, 9), LocalTime.of(23, 59), 11.00, 15.00, true));
        kontzertuak.add(new Kontzertua(6, "LOS HERMANOS CUBERO (folk, americana, acordeon)", LocalDate.of(2019, 2, 9), LocalTime.of(22, 0), 8.00, 11.00, false));
        kontzertuak.add(new Kontzertua(7, "WREKMEISTER HARMONIES (post-rock, metal)", LocalDate.of(2019, 2, 11), LocalTime.of(20, 0), 10.00, 13.00, false));
        kontzertuak.add(new Kontzertua(8, "IZAR & STAR: JUANJO NAVAS (flamenco) + SON", LocalDate.of(2019, 2, 14), LocalTime.of(20, 0), 8.00, 10.00, true));
        kontzertuak.add(new Kontzertua(9, "LOS TIKI PHANTOMS (surf, r'n'r, BCN) + MONTE", LocalDate.of(2019, 2, 15), LocalTime.of(21, 0), 10.00, 13.00, false));
        kontzertuak.add(new Kontzertua(10, "GUS DAPPERTON (new wave, synth, dream-pop)", LocalDate.of(2019, 2, 16), LocalTime.of(21, 0), 10.00, 15.00, false));
        kontzertuak.add(new Kontzertua(11, "ID: GIULIANO LOMONTE (deep, tech, hypnotic)", LocalDate.of(2019, 2, 16), LocalTime.of(23, 59), 11.00, 15.00, false));
        kontzertuak.add(new Kontzertua(12, "ROBBY PEOPLES & THE CONGRESS (rock&roll)", LocalDate.of(2019, 2, 17), LocalTime.of(20, 30), 10.00, 13.00, false));
        kontzertuak.add(new Kontzertua(13, "KAMCHATKA (hard rock, stoner, blues, SWE)", LocalDate.of(2019, 2, 19), LocalTime.of(20, 30), 10.00, 13.00, true));

        executeMenu(kontzertuak);
        Kontzertuak.sc.close();
    }

    // ===================== MENUA =====================
    static void executeMenu(ArrayList<Kontzertua> kontzertuak) {
        int aukera;
        do {
            inprimatuMenua();
            aukera = Kontzertuak.irakurriEnteroa("Aukeratu bat: ");
            switch (aukera) {
                case 1 -> Kontzertuak.zerrendatu(kontzertuak);
                case 2 -> Kontzertuak.kontsultatu(kontzertuak);
                case 3 -> Kontzertuak.dataArteanZerrendatu(kontzertuak);
                case 4 -> Kontzertuak.altaEman(kontzertuak);
                case 5 -> Kontzertuak.bajaEman(kontzertuak);
                case 6 -> Kontzertuak.ordenatu(kontzertuak);
                case 0 -> System.out.println("\nPrograma itxi da. Agur!");
                default -> System.out.println("Aukera ez da baliozkoa, menuko zenbaki bat sartu. Berriro saiatu.");
            }
            System.out.println();
        } while (aukera != 0);
    }

    static void inprimatuMenua() {
        int ancho = 46;
        System.out.println("╔" + "═".repeat(ancho) + "╗");
        System.out.println("║" + centrar(" KONTZERTUEN MENUA ", ancho) + "║");
        System.out.println("╠" + "═".repeat(ancho) + "╣");
        inprimatuAukera("1", "Erregistro guztiak zerrendatu", ancho);
        inprimatuAukera("2", "Id bidez kontsultatu", ancho);
        inprimatuAukera("3", "Bi daten arteko kontzertuak", ancho);
        inprimatuAukera("4", "Alta (kontzertu berria)", ancho);
        inprimatuAukera("5", "Baja (kontzertua ezabatu)", ancho);
        inprimatuAukera("6", "Ordenatu erregistroak", ancho);
        System.out.println("╟" + "─".repeat(ancho) + "╢");
        inprimatuAukera("0", "Irten", ancho);
        System.out.println("╚" + "═".repeat(ancho) + "╝");
    }

    static void inprimatuAukera(String zenb, String testua, int ancho) {
        String linea = " " + zenb + ". " + testua;
        if (linea.length() > ancho) linea = linea.substring(0, ancho);
        System.out.println("║" + linea + " ".repeat(ancho - linea.length()) + "║");
    }

    static String centrar(String s, int ancho) {
        int espacios = Math.max(ancho - s.length(), 0);
        int izq = espacios / 2;
        return " ".repeat(izq) + s + " ".repeat(espacios - izq);
    }
}