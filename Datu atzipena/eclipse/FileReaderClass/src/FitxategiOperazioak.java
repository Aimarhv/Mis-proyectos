import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Testu-fitxategiaren eragiketak. Guztiak fitxategiaren gainean zuzenean egiten dira
 * (ez da ArrayList-ik erabiltzen). Erregistro bakoitza lerro bat da, eremuak ";" bidez
 * bereizita:
 *   Kontzertuak 1.0
 *   1;DONNY BENET (post-disco, Sydney);2019-02-05;21:00;7.0;10.0
 *   ...
 */
public class FitxategiOperazioak {

    // Fitxategiaren lehen lerroa (datu mota eta bertsioa)
    static final String GOIBURUA = "Kontzertuak 1.0";

    // Lan egingo dugun fitxategia
    static File fitxategia;

    // ===================== FITXATEGIA AUKERATU =====================

    /**
     * Fitxategiaren izena eta ibilbidea eskatzen du (hutsik utziz programatik irteten da).
     * Existitzen bada, lehen lerroa egiaztatzen du (oker badago, menu bat ateratzen da);
     * ez bada existitzen, baieztapenarekin goiburuarekin sortzen du.
     * @return aukeratutako fitxategia, edo null erabiltzaileak irten nahi badu
     */
    static File fitxategiaAukeratu() {
        while (true) {
            System.out.print("Fitxategiaren izena eta ibilbidea: ");
            String sarrera = ibilbideaGarbitu(Sarrera.sc.nextLine());

            if (sarrera.isEmpty()) {
                return null;
            }
            File f = new File(sarrera);

            if (f.isDirectory()) {
                System.out.println("Errorea: hori direktorio bat da, ez fitxategi bat.");
            } else if (f.exists()) {
                if (goiburuaZuzena(f)) {
                    System.out.println("Fitxategia egiaztatu da: goiburua zuzena da.");
                    return f;
                }
                System.out.println("Errorea: fitxategiaren lehen lerroak ez du \"" + GOIBURUA + "\" jartzen.");
                // Goiburua okerra denean, erabiltzaileak erabakitzen du zer egin
                int aukera = arazoMenua();
                if (aukera == 0) {
                    return null;
                }
                if (aukera == 2 && Sarrera.baieztatu("Fitxategiko datu guztiak galduko dira. Jarraitu?")) {
                    try {
                        goiburuaIdatzi(f);
                        System.out.println("Fitxategia berridatzi da goiburu zuzenarekin.");
                        return f;
                    } catch (IOException e) {
                        System.out.println("Errorea fitxategia berridaztean: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("Fitxategia ez dago.");
                if (Sarrera.baieztatu("Goiburuarekin sortu nahi duzu?")) {
                    File gurasoa = f.getAbsoluteFile().getParentFile();
                    if (gurasoa == null || !gurasoa.isDirectory()) {
                        System.out.println("Errorea: ez dago ibilbide hori (karpeta existitu behar da).");
                        continue;
                    }
                    try {
                        goiburuaIdatzi(f);
                        System.out.println("Fitxategia sortu da.");
                        return f;
                    } catch (IOException e) {
                        System.out.println("Errorea fitxategia sortzean: " + e.getMessage());
                    }
                }
            }
        }
    }

    /** Goiburu okerreko fitxategiaren menua: 1 = beste bat, 2 = berridatzi, 0 = irten. */
    static int arazoMenua() {
        System.out.println("  1. Beste fitxategi bat aukeratu");
        System.out.println("  2. Fitxategia berridatzi goiburu zuzenarekin (datuak galduko dira)");
        System.out.println("  0. Irten");
        int aukera;
        do {
            aukera = Sarrera.leerEnteroValidado("Aukeratu bat: ");
            if (aukera < 0 || aukera > 2) {
                System.out.println(" Aukera ez da baliozkoa.");
            }
        } while (aukera < 0 || aukera > 2);
        return aukera;
    }

    /** Ibilbidea garbitzen du: zuriuneak eta inguruko komatxoak kentzen ditu ("C:\\..." bezala). */
    static String ibilbideaGarbitu(String ibilbidea) {
        String s = ibilbidea.trim();
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1).trim();
        }
        return s;
    }

    /** Fitxategiaren lehen lerroa GOIBURUA den egiaztatzen du. */
    static boolean goiburuaZuzena(File f) {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            return GOIBURUA.equals(br.readLine());
        } catch (IOException e) {
            return false;
        }
    }

    /** Fitxategia sortzen/berridazten du goiburu-lerroarekin soilik. */
    static void goiburuaIdatzi(File f) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            bw.write(GOIBURUA);
            bw.newLine();
        }
    }

    // ===================== MENUKO ERAGIKETAK =====================

    /** 1. Fitxategia taulako datuekin betetzen du (lehengo datuak galtzen dira). */
    static void fitxategiaBetetu(Kontzertua[] kontzertuak) throws IOException {
        System.out.println("\n--- FITXATEGIA BETETZEA ---");
        if (!Sarrera.baieztatu("Fitxategiko datuak galduko dira. Jarraitu?")) {
            System.out.println("Eragiketa bertan behera utzi da.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fitxategia))) {
            bw.write(GOIBURUA);
            bw.newLine();
            for (Kontzertua k : kontzertuak) {
                bw.write(k.toLinea());
                bw.newLine();
            }
        }
        System.out.println(kontzertuak.length + " erregistro idatzi dira.");
    }

    /** 2. Fitxategia hutsik uzten du (goiburua mantenduz). */
    static void fitxategiaHustu() throws IOException {
        System.out.println("\n--- FITXATEGIA HUSTU ---");
        if (!Sarrera.baieztatu("Erregistro guztiak ezabatuko dira. Jarraitu?")) {
            System.out.println("Eragiketa bertan behera utzi da.");
            return;
        }
        goiburuaIdatzi(fitxategia);
        System.out.println("Fitxategia hutsik utzi da.");
    }

    /** 3. Fitxategiko erregistro-kopurua (lerroak, goiburua kenduta). */
    static void erregistroakZenbatu() throws IOException {
        System.out.println("\n--- ERREGISTROAK ZENBATU ---");
        int kopurua = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fitxategia))) {
            br.readLine(); // goiburua saltatu
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    kopurua++;
                }
            }
        }
        System.out.println("Fitxategiak " + kopurua + " erregistro ditu.");
    }

    /** 4. Erregistro bat bilatu eta erakusten du, bere kodea emanda. */
    static void kodeaBilatu() throws IOException {
        System.out.println("\n--- ERREGISTROA BILATU (KODEA) ---");
        int kodea = Sarrera.leerEnteroValidado("Kodea: ");
        String linea = lerroaBilatu(kodea);
        if (linea == null) {
            System.out.println("Ez dago " + kodea + " kodea duen erregistrorik.");
            return;
        }
        try {
            goiburuaErakutsi();
            System.out.println(Kontzertua.fromLinea(linea));
        } catch (IllegalArgumentException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    /** 5. Testu-eremuan (taldea) hitz bat duten erregistroak zerrendatzen ditu. */
    static void hitzaBilatu() throws IOException {
        System.out.println("\n--- ERREGISTROAK BILATU (HITZA) ---");
        System.out.print("Bilatu nahi duzun hitza: ");
        String hitza = Sarrera.sc.nextLine().trim().toLowerCase();
        if (hitza.isEmpty()) {
            System.out.println("Errorea: hitza ezin da hutsik egon.");
            return;
        }

        int aurkituak = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fitxategia))) {
            br.readLine(); // goiburua saltatu
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                try {
                    Kontzertua k = Kontzertua.fromLinea(linea);
                    if (k.getTaldea().toLowerCase().contains(hitza)) {
                        if (aurkituak == 0) {
                            goiburuaErakutsi();
                        }
                        System.out.println(k);
                        aurkituak++;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Lerro okerra saltatu da: " + e.getMessage());
                }
            }
        }
        System.out.println(aurkituak + " erregistro aurkitu dira.");
    }

    /** 6. Erregistro bat gehitzen du fitxategiaren amaieran (kodea ezin da errepikatu). */
    static void erregistroaGehitu() throws IOException {
        System.out.println("\n--- ERREGISTROA GEHITU ---");
        int kodea;
        // Kodea positiboa eta ez errepikatua izan behar da
        while (true) {
            kodea = Sarrera.leerEnteroValidado("Kodea: ");
            if (kodea <= 0) {
                System.out.println(" Kodeak positiboa izan behar du.");
            } else if (lerroaBilatu(kodea) != null) {
                System.out.println(" Kode hori badago lehendik. Beste bat sartu.");
            } else {
                break;
            }
        }
        Kontzertua k = new Kontzertua(kodea,
                Sarrera.eskatuTestua("Taldea: ", null),
                Sarrera.eskatuData("Eguna (uuuu-hh-ee): ", null),
                Sarrera.eskatuOrdua("Ordua (HH:mm): ", null),
                Sarrera.eskatuDoublea("Taquilla: ", null),
                Sarrera.eskatuDoublea("Prezioa: ", null));

        // true = amaieran gehitu
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fitxategia, true))) {
            bw.write(k.toLinea());
            bw.newLine();
        }
        System.out.println("Erregistroa gehitu da.");
    }

    /** 7. Erregistro bat ezabatzen du (fitxategi osagarriaren bidez). */
    static void erregistroaEzabatu() throws IOException {
        System.out.println("\n--- ERREGISTROA EZABATU ---");
        int kodea = Sarrera.leerEnteroValidado("Ezabatu nahi duzun kodea: ");
        String linea = lerroaBilatu(kodea);
        if (linea == null) {
            System.out.println("Ez dago " + kodea + " kodea duen erregistrorik.");
            return;
        }
        System.out.println(linea);
        if (!Sarrera.baieztatu("Benetan ezabatu nahi duzu?")) {
            System.out.println("Eragiketa bertan behera utzi da.");
            return;
        }
        if (kopiatuAldatuz(kodea, null)) {
            System.out.println("Erregistroa ezabatu da.");
        }
    }

    /** 8. Fitxategiaren kopia bat sortzen du, helburuaren izena teklatuz hartuta. */
    static void kopiaSortu() throws IOException {
        System.out.println("\n--- KOPIA SORTU ---");
        System.out.print("Helburuko fitxategiaren izena eta ibilbidea: ");
        String sarrera = ibilbideaGarbitu(Sarrera.sc.nextLine());
        if (sarrera.isEmpty()) {
            System.out.println("Errorea: izena ezin da hutsik egon.");
            return;
        }
        File helburua = new File(sarrera);

        // Egiaztapenak
        if (helburua.isDirectory()) {
            System.out.println("Errorea: helburua direktorio bat da.");
            return;
        }
        if (helburua.getCanonicalFile().equals(fitxategia.getCanonicalFile())) {
            System.out.println("Errorea: helburua jatorrizko fitxategia bera da.");
            return;
        }
        File gurasoa = helburua.getAbsoluteFile().getParentFile();
        if (gurasoa == null || !gurasoa.isDirectory()) {
            System.out.println("Errorea: ez dago helburuaren ibilbidea.");
            return;
        }
        if (helburua.exists() && !Sarrera.baieztatu("Helburua badago lehendik. Berridatzi?")) {
            System.out.println("Eragiketa bertan behera utzi da.");
            return;
        }

        // Lerroz lerro kopiatu (goiburua barne)
        try (BufferedReader br = new BufferedReader(new FileReader(fitxategia));
             BufferedWriter bw = new BufferedWriter(new FileWriter(helburua))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(linea);
                bw.newLine();
            }
        }
        System.out.println("Kopia sortu da: " + helburua.getAbsolutePath());
    }

    /** 9. Erregistro baten eremuak aldatzen ditu (kodea ez da aldatzen). */
    static void erregistroaAldatu() throws IOException {
        System.out.println("\n--- ERREGISTROA ALDATU ---");
        int kodea = Sarrera.leerEnteroValidado("Aldatu nahi duzun kodea: ");
        String linea = lerroaBilatu(kodea);
        if (linea == null) {
            System.out.println("Ez dago " + kodea + " kodea duen erregistrorik.");
            return;
        }
        Kontzertua k;
        try {
            k = Kontzertua.fromLinea(linea);
        } catch (IllegalArgumentException e) {
            System.out.println("Errorea: " + e.getMessage());
            return;
        }

        System.out.println("Hutsik utzi eremu bat lehengoa mantentzeko.");
        k.setTaldea(Sarrera.eskatuTestua("Taldea [" + k.getTaldea() + "]: ", k.getTaldea()));
        k.setDia(Sarrera.eskatuData("Eguna [" + k.getDia() + "]: ", k.getDia()));
        k.setHora(Sarrera.eskatuOrdua("Ordua [" + k.getHora() + "]: ", k.getHora()));
        k.setTaquilla(Sarrera.eskatuDoublea("Taquilla [" + k.getTaquilla() + "]: ", k.getTaquilla()));
        k.setPrecio(Sarrera.eskatuDoublea("Prezioa [" + k.getPrecio() + "]: ", k.getPrecio()));

        if (kopiatuAldatuz(kodea, k.toLinea())) {
            System.out.println("Erregistroa aldatu da.");
        }
    }

    // ===================== LAGUNTZAILEAK: FITXATEGIA =====================

    /** Kodea duen lerroa itzultzen du, edo null ez badago. */
    static String lerroaBilatu(int kodea) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fitxategia))) {
            br.readLine(); // goiburua saltatu
            String linea;
            while ((linea = br.readLine()) != null) {
                if (lerroarenKodea(linea) == kodea) {
                    return linea;
                }
            }
        }
        return null;
    }

    /** Lerro baten lehen eremua (kodea) itzultzen du; baliogabea bada -1. */
    static int lerroarenKodea(String linea) {
        try {
            return Integer.parseInt(linea.split(";", 2)[0].trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Fitxategi osagarria erabiltzen du: jatorrizkoa kopiatzen du, kodea duen lerroa
     * ordezkatuz (lerroBerria) edo kenduz (lerroBerria == null). Gero jatorrizkoa
     * ezabatu eta osagarria berrizendatzen du.
     * @return true dena ondo atera bada
     */
    static boolean kopiatuAldatuz(int kodea, String lerroBerria) throws IOException {
        File osagarria = new File(fitxategia.getAbsoluteFile().getParentFile(), fitxategia.getName() + ".tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(fitxategia));
             BufferedWriter bw = new BufferedWriter(new FileWriter(osagarria))) {
            bw.write(br.readLine()); // goiburua
            bw.newLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                if (lerroarenKodea(linea) == kodea) {
                    if (lerroBerria != null) {
                        bw.write(lerroBerria);
                        bw.newLine();
                    }
                } else {
                    bw.write(linea);
                    bw.newLine();
                }
            }
        } // fluxuak hemen itxita daude, orain ezabatu/berrizendatu daiteke

        if (!fitxategia.delete()) {
            System.out.println("Errorea: ezin izan da jatorrizko fitxategia ezabatu.");
            osagarria.delete();
            return false;
        }
        if (!osagarria.renameTo(fitxategia)) {
            System.out.println("Errorea: ezin izan da fitxategi osagarria berrizendatu ("
                    + osagarria.getAbsolutePath() + " fitxategian daude datuak).");
            return false;
        }
        return true;
    }

    /** Erregistroen zutabeen goiburua erakusten du. */
    static void goiburuaErakutsi() {
        System.out.printf("%3s | %-48s | %-10s | %-5s | %6s | %6s%n", "ID", "TALDEA", "EGUNA", "ORDUA", "TAQ.", "PREZ.");
    }
}