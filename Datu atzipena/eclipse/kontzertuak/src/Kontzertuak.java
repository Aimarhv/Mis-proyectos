import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Kontzertuen kudeaketa programa.
 * Datuak array paraleloetan gordetzen dira (indize bera = erregistro bera)
 * eta kontsola bidezko menu batetik kontsultak egiten dira.
 */
public class Kontzertuak {


    // ===================== DATUAK =====================
    // Erregistro bakoitzaren identifikatzailea (kodea)
    static int[] id = {1,2,3,4,5,6,7,8,9,10,11,12,13};

    // Taldearen edo artistaren izena eta estiloa
    static String[] grupo = {
            "DONNY BENET (post-disco, Sydney)",
            "DAVID PALOMAR + RAFAEL RODRIGUEZ",
            "FERNANDO ALFARO (pop, indie, rock, Albacete)",
            "YUNG BEEF DJ SET (trap, reggaeton, dancehall)",
            "15 YEARS OF PHONICA: ROMAN FLUGEL + SIMONE",
            "LOS HERMANOS CUBERO (folk, americana, acordeon)",
            "WREKMEISTER HARMONIES (post-rock, metal)",
            "IZAR & STAR: JUANJO NAVAS (flamenco) + SON",
            "LOS TIKI PHANTOMS (surf, r'n'r, BCN) + MONTE",
            "GUS DAPPERTON (new wave, synth, dream-pop)",
            "ID: GIULIANO LOMONTE (deep, tech, hypnotic)",
            "ROBBY PEOPLES & THE CONGRESS (rock&roll)",
            "KAMCHATKA (hard rock, stoner, blues, SWE)"
    };

    // Kontzertuaren data
    static LocalDate[] dia = {
            LocalDate.of(2019,2,5),
            LocalDate.of(2019,2,7),
            LocalDate.of(2019,2,8),
            LocalDate.of(2019,2,8),
            LocalDate.of(2019,2,9),
            LocalDate.of(2019,2,9),
            LocalDate.of(2019,2,11),
            LocalDate.of(2019,2,14),
            LocalDate.of(2019,2,15),
            LocalDate.of(2019,2,16),
            LocalDate.of(2019,2,16),
            LocalDate.of(2019,2,17),
            LocalDate.of(2019,2,19)
    };

    // Kontzertuaren ordua
    static LocalTime[] hora = {
            LocalTime.of(21, 0, 0),
            LocalTime.of(20, 0, 0),
            LocalTime.of(21, 0, 0),
            LocalTime.of(23, 59, 0),
            LocalTime.of(23, 59, 0),
            LocalTime.of(22, 0, 0),
            LocalTime.of(20, 0, 0),
            LocalTime.of(20, 0, 0),
            LocalTime.of(21, 0, 0),
            LocalTime.of(21, 0, 0),
            LocalTime.of(23, 59, 0),
            LocalTime.of(20, 30, 0),
            LocalTime.of(20, 30, 0)
    };

    // Leihatilako prezioa (taquilla) eta aurrez erositakoa (precio), euroetan
    static double[] taquilla = {7.00,15.00,10.00,11.00,11.00,8.00,10.00,8.00,10.00,10.00,11.00,10.00,10.00};
    static double[] precio   = {10.00,18.00,13.00,15.00,15.00,11.00,13.00,10.00,13.00,15.00,15.00,13.00,13.00};

    // Erregistro kopurua (id arrayaren luzeratik ateratzen da)
    static final int NUM_REGISTROS = id.length;
    // Datak dd/MM/yyyy formatuan erakusteko
    static final DateTimeFormatter D_FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    // Teklatutik datuak irakurtzeko Scanner bakarra, programa osoan partekatua
    static Scanner sc = new Scanner(System.in);

    // Zutabeen zabalera finkoak taula beti sartzeko
    static final int W_ID = 3, W_TALDEA = 50, W_DATA = 10, W_ORDUA = 5, W_TAQ = 9, W_PREC = 9;

    // ===================== MAIN =====================
    // Programaren sarrera-puntua: menua exekutatu eta amaitzean Scanner-a itxi
    public static void main(String[] args) {
        ejecutarMenu();
        sc.close();
    }

    // ===================== MENUA =====================
    // Menua errepikatzen du erabiltzaileak 0 aukeratu arte
    static void ejecutarMenu() {
        int opcion;
        do {
            imprimirMenu();
            opcion = leerEnteroValidado("Aukeratu bat: ");
            // Aukeratutako aukeraren arabera dagokion metodoari deitu
            switch (opcion) {
                case 1 -> Erregistrolista();
                case 2 -> Kodigobidezkontsultatu();
                case 3 -> Batezbestekozenbakizkoeremua();
                case 4 -> Baliomaximoa();
                case 5 -> Datazaharrena();
                case 6 -> ErregistrokateluzeenaasLarga();
                case 0 -> System.out.println("\nPrograma itxi da. Agur!");
                default -> System.out.println("Aukera ez da baliozkoa. Berriro saiatu.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    // Menuaren markoa eta aukerak pantailan marrazten ditu
    static void imprimirMenu() {
        String titulo = " KONTZERTUEN MENUA ";
        int ancho = 46; // Markoaren barneko zabalera
        System.out.println("╔" + "═".repeat(ancho) + "╗");
        System.out.println("║" + centrar(titulo, ancho) + "║");
        System.out.println("╠" + "═".repeat(ancho) + "╣");
        imprimirOpcion("1", "Erregistro guztiak zerrendatu", ancho);
        imprimirOpcion("2", "Kodearen bidez erregistro bat kontsultatu", ancho);
        imprimirOpcion("3", "Eremu zenbakizko baten batez-bestekoa", ancho);
        imprimirOpcion("4", "Eremu baten baliorik handiena duen erregistroa", ancho);
        imprimirOpcion("5", "Datarik zaharrena duen erregistroa", ancho);
        imprimirOpcion("6", "Kate luzeena duen erregistroa (Taldea)", ancho);
        System.out.println("╟" + "─".repeat(ancho) + "╢");
        imprimirOpcion("0", "Irten", ancho);
        System.out.println("╚" + "═".repeat(ancho) + "╝");
    }

    // Menuko lerro bat inprimatzen du (zenbakia + testua), zabalerara doituta
    static void imprimirOpcion(String num, String texto, int ancho) {
        String linea = " " + num + ". " + texto;
        System.out.println("║" + rellenar(linea, ancho) + "║");
    }

    // Testua ezkerrean lerrokatzen du zabalera zehatzera arte (zuriuneekin betez edo moztuz)
    static String rellenar(String s, int ancho) {
        if (s.length() >= ancho) return s.substring(0, ancho);
        return s + " ".repeat(ancho - s.length());
    }

    // Testua zentratzen du zabalera jakin batean (zuriuneak bi aldeetan banatuz)
    static String centrar(String s, int ancho) {
        int espacios = ancho - s.length();
        int izq = espacios / 2, der = espacios - izq;
        return " ".repeat(Math.max(izq,0)) + s + " ".repeat(Math.max(der,0));
    }

    // ===================== 1. Erregistro guztiak zerrendatu =====================
    // Erregistro guztiak taula batean erakusten ditu
    static void Erregistrolista() {
        System.out.println("\n--- ERREGISTRO GUZTIAK ---");
        imprimirCabecera();
        for (int i = 0; i < NUM_REGISTROS; i++) {
            Erregistroainprimitu(i);
        }
        imprimirPieTabla();
    }

    // Taularen goiburua eta bereizle-lerroa inprimatzen ditu
    static void imprimirCabecera() {
        System.out.println(
                pad("Id", W_ID) + " │ " +
                        pad("Taldea", W_TALDEA) + " │ " +
                        pad("Data", W_DATA) + " │ " +
                        pad("Ordua", W_ORDUA) + " │ " +
                        padIzq("Taquilla", W_TAQ) + " │ " +
                        padIzq("Precio", W_PREC));
        System.out.println("─".repeat(W_ID) + "─┼─" + "─".repeat(W_TALDEA) + "─┼─" +
                "─".repeat(W_DATA) + "─┼─" + "─".repeat(W_ORDUA) + "─┼─" +
                "─".repeat(W_TAQ) + "─┼─" + "─".repeat(W_PREC));
    }

    // Taularen amaieran erregistro kopurua erakusten du
    static void imprimirPieTabla() {
        System.out.println("(" + NUM_REGISTROS + " erregistro guztira)");
    }

    // i indizeko erregistroa taulako errenkada gisa inprimatzen du
    static void Erregistroainprimitu(int i) {
        // Taldearen izena luzeegia bada, moztu eta "…" gehitu
        String taldeaMostrar = grupo[i].length() > W_TALDEA
                ? grupo[i].substring(0, W_TALDEA - 1) + "…"
                : grupo[i];

        System.out.printf("%s │ %s │ %s │ %s │ %s%.2f€ │ %s%.2f€%n",
                pad(String.valueOf(id[i]), W_ID),
                pad(taldeaMostrar, W_TALDEA),
                dia[i].format(D_FORMATO),
                hora[i],
                padIzqDinero(W_TAQ), taquilla[i],
                padIzqDinero(W_PREC), precio[i]);
    }

    static String padIzqDinero(int ancho) {
        // Utzi tartea 2 hamartar dituzten zenbatekoak eskuinera lerrokatuta egon daitezen
        return " ".repeat(Math.max(0, ancho - 6));
    }

    // Testua ezkerrean lerrokatzen du: zuriuneekin betetzen du edo moztu egiten du
    static String pad(String s, int ancho) {
        if (s.length() >= ancho) return s.substring(0, ancho);
        return s + " ".repeat(ancho - s.length());
    }

    // Testua eskuinean lerrokatzen du: ezkerrean zuriuneak gehitzen ditu edo moztu egiten du
    static String padIzq(String s, int ancho) {
        if (s.length() >= ancho) return s.substring(0, ancho);
        return " ".repeat(ancho - s.length()) + s;
    }

    // ===================== 2. Kodearen bidez kontsultatu =====================
    // Erabiltzaileak sartutako Id-a bilatzen du eta erregistroa erakusten du
    static void Kodigobidezkontsultatu() {
        int kodea = leerEnteroValidado("Bilatu nahi duzun Id kodea: ");
        int indizea = bilatuIndizeaId(kodea);
        if (indizea == -1) {
            System.out.println("Ez da " + kodea + " kodea duen erregistrorik aurkitu.");
        } else {
            System.out.println("--- ERREGISTROA AURKITUA ---");
            imprimirCabecera();
            Erregistroainprimitu(indizea);
        }
    }

    // Bilaketa lineala: kodea duen erregistroaren indizea itzultzen du, edo -1 aurkitzen ez bada
    static int bilatuIndizeaId(int kodea) {
        for (int i = 0; i < NUM_REGISTROS; i++) {
            if (id[i] == kodea) return i;
        }
        return -1;
    }

    // ===================== 3. Batez-bestekoa =====================
    // Erabiltzaileak aukeratutako eremu zenbakizkoaren (taquilla/precio) batez-bestekoa erakusten du
    static void Batezbestekozenbakizkoeremua() {
        int aukera = aukeratuEremuZenbakizkoa();
        double[] eremua = (aukera == 1) ? taquilla : precio;
        String izena = (aukera == 1) ? "Taquilla" : "Precio";

        double batazbestekoa = media(eremua);
        System.out.printf("%s eremuaren batez-bestekoa: %.2f€%n", izena, batazbestekoa);
    }

    // Array bateko balioen batez-bestekoa kalkulatzen du (batura / elementu kopurua)
    static double media(double[] eremua) {
        double batura = 0;
        for (int i = 0; i < eremua.length; i++) {
            batura += eremua[i];
        }
        return batura / eremua.length;
    }

    // Eremu zenbakizko bat aukeratzeko menu txikia: 1 = Taquilla, 2 = Precio
    // Baliozko aukera sartu arte errepikatzen da
    static int aukeratuEremuZenbakizkoa() {
        System.out.println("Zein eremu erabili nahi duzu?");
        System.out.println("  1. Taquilla");
        System.out.println("  2. Precio");
        int aukera;
        do {
            aukera = leerEnteroValidado("Aukeratu (1-2): ");
        } while (aukera != 1 && aukera != 2);
        return aukera;
    }

    // ===================== 4. Baliorik handiena =====================
    // Aukeratutako eremuan balio handiena duen erregistroa erakusten du
    static void Baliomaximoa() {
        int aukera = aukeratuEremuZenbakizkoa();
        double[] eremua = (aukera == 1) ? taquilla : precio;
        String izena = (aukera == 1) ? "Taquilla" : "Precio";

        int indizeMax = indizeMaximoa(eremua);
        System.out.println("Balio handiena (" + izena + ") duen erregistroa:");
        imprimirCabecera();
        Erregistroainprimitu(indizeMax);
    }

    // Balio handiena duen posizioaren indizea itzultzen du
    // (balio berdinak badaude, lehenengoa itzultzen da)
    static int indizeMaximoa(double[] eremua) {
        int indizeMax = 0;
        for (int i = 1; i < eremua.length; i++) {
            if (eremua[i] > eremua[indizeMax]) {
                indizeMax = i;
            }
        }
        return indizeMax;
    }

    // ===================== 5. Datarik zaharrena =====================
    // Data zaharrena duen erregistroa erakusten du
    static void Datazaharrena() {
        int indizeMin = indizeDatazaharrena();
        System.out.println("Datarik zaharrena duen erregistroa:");
        imprimirCabecera();
        Erregistroainprimitu(indizeMin);
    }

    // Data goiztiarrena (zaharrena) duen posizioaren indizea itzultzen du
    static int indizeDatazaharrena() {
        int indizeMin = 0;
        for (int i = 1; i < NUM_REGISTROS; i++) {
            if (dia[i].isBefore(dia[indizeMin])) {
                indizeMin = i;
            }
        }
        return indizeMin;
    }

    // ===================== 6. Kate luzeena (Taldea) =====================
    // "Taldea" eremuan karaktere gehien dituen erregistroa erakusten du
    static void ErregistrokateluzeenaasLarga() {
        int indizeMax = indizeKateluzeena();
        System.out.println("'Taldea' eremuan kate luzeena duen erregistroa:");
        imprimirCabecera();
        Erregistroainprimitu(indizeMax);
    }

    // Izen luzeena duen taldearen indizea itzultzen du (length() erabiliz)
    static int indizeKateluzeena() {
        int indizeMax = 0;
        for (int i = 1; i < NUM_REGISTROS; i++) {
            if (grupo[i].length() > grupo[indizeMax].length()) {
                indizeMax = i;
            }
        }
        return indizeMax;
    }

    // ===================== ERROREEN KONTROLA =====================
    // Zenbaki oso bat irakurtzen du teklatutik; baliogabea sartzen bada, errore-mezua
    // erakutsi eta berriro eskatzen du baliozko zenbakia sartu arte
    static int leerEnteroValidado(String mezua) {
        System.out.print(mezua);
        boolean esEntero = sc.hasNextInt();
        while (!esEntero) {
            sc.nextLine(); // Baliogabeko sarrera garbitu
            System.out.println(" Zenbaki oso bat sartu behar duzu.");
            System.out.print(mezua);
            esEntero = sc.hasNextInt();
        }
        int balioa = sc.nextInt();
        sc.nextLine(); // Lerro-jauziaren hondarra kendu hurrengo irakurketarako
        return balioa;
    }
}