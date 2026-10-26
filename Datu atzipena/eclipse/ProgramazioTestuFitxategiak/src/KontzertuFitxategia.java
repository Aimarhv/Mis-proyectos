import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Testu-fitxategiaren gaineko eragiketa guztiak.
 * Eragiketa guztiak fitxategiaren gainean zuzenean egiten dira (ez da ArrayList-ik erabiltzen).
 *
 * Fitxategiaren formatua:
 *   1. lerroa: GOIBURUA ("Kontzertuak 1.0")
 *   Gainerakoak: erregistro bat lerroko, eremuak ";" bidez bereizita
 *                id;taldea;dia;hora;taquilla;precio
 *
 * Metodoek IOException jaurtitzen dute; deitzen duenak (Main) kudeatzen ditu erroreak.
 */
public class KontzertuFitxategia {

    public static final String GOIBURUA = "Kontzertuak 1.0";

    /** Fitxategiaren lehen lerroa GOIBURUA den egiaztatzen du. */
    public static boolean goiburuaZuzenaDa(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String lehenLerroa = br.readLine();
        br.close();
        return GOIBURUA.equals(lehenLerroa);
    }

    /** Fitxategia sortzen du (edo hustu egiten du), goiburuko lerroa bakarrik utziz. */
    public static void sortuHutsik(File f) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(GOIBURUA);
        bw.newLine();
        bw.close();
    }

    /** Fitxategia taulako datuekin betetzen du. Aurreko edukia galdu egiten da. */
    public static void bete(File f, Kontzertua[] taula) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(GOIBURUA);
        bw.newLine();
        for (Kontzertua k : taula) {
            bw.write(k.toLine()); // erregistroa lerro bakarrean
            bw.newLine();
        }
        bw.close();
    }

    /** Erregistro-kopurua (goiburua eta lerro hutsak kontatu gabe). */
    public static int zenbatu(File f) throws IOException {
        int kop = 0;
        BufferedReader br = new BufferedReader(new FileReader(f));
        br.readLine(); // goiburua saltatu
        String lerroa;
        while ((lerroa = br.readLine()) != null) {
            if (!lerroa.trim().isEmpty()) {
                kop++;
            }
        }
        br.close();
        return kop;
    }

    /** Kodea emanda erregistroa bilatzen du. Ez badago, null itzultzen du. */
    public static Kontzertua bilatu(File f, int kodea) throws IOException {
        Kontzertua aurkitua = null;
        BufferedReader br = new BufferedReader(new FileReader(f));
        br.readLine(); // goiburua saltatu
        String lerroa;
        // aurkitu arte irakurtzen dugu (aurkitua == null den bitartean)
        while (aurkitua == null && (lerroa = br.readLine()) != null) {
            Kontzertua k = Kontzertua.fromLine(lerroa);
            if (k != null && k.getId() == kodea) {
                aurkitua = k;
            }
        }
        br.close();
        return aurkitua;
    }

    /** Taldearen izenean hitza duten erregistroak erakusten ditu (maiuskulak/minuskulak aintzat hartu gabe). */
    public static int zerrendatuHitzarekin(File f, String hitza) throws IOException {
        int kop = 0;
        BufferedReader br = new BufferedReader(new FileReader(f));
        br.readLine(); // goiburua saltatu
        String lerroa;
        while ((lerroa = br.readLine()) != null) {
            Kontzertua k = Kontzertua.fromLine(lerroa);
            // toLowerCase(): maiuskulak/minuskulak berdin tratatzeko
            if (k != null && k.getTaldea().toLowerCase().contains(hitza.toLowerCase())) {
                System.out.println(k);
                kop++;
            }
        }
        br.close();
        return kop;
    }

    /** Erregistro bat fitxategiaren amaieran gehitzen du. Kodea errepikatuta badago, ez du ezer egiten. */
    public static boolean gehitu(File f, Kontzertua berria) throws IOException {
        if (bilatu(f, berria.getId()) != null) {
            return false; // kodea errepikatuta
        }
        // true: amaieran gehitu (fitxategia ez da ezabatzen)
        BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
        bw.write(berria.toLine());
        bw.newLine();
        bw.close();
        return true;
    }

    /** Kodea duen erregistroa ezabatzen du. @return false kodea ez badago */
    public static boolean ezabatu(File f, int kodea) throws IOException {
        return ordezkatu(f, kodea, null);
    }

    /** Kodea duen erregistroa beste batekin ordezkatzen du. @return false kodea ez badago */
    public static boolean aldatu(File f, int kodea, Kontzertua berria) throws IOException {
        return ordezkatu(f, kodea, berria);
    }

    /**
     * Prozedura: aukeratutako erregistroa izan ezik (edo "berria"-rekin ordezkatuta),
     * fitxategi osoa fitxategi laguntzaile batean kopiatzen da. Gero jatorrizkoa
     * ezabatu eta laguntzailea jatorrizkoaren izenarekin berrizendatzen da.
     * berria == null bada, erregistroa ezabatu egiten da.
     */
    private static boolean ordezkatu(File f, int kodea, Kontzertua berria) throws IOException {
        File aux = new File(f.getAbsoluteFile().getParentFile(), f.getName() + ".aux");
        boolean aurkitua = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            BufferedWriter bw = new BufferedWriter(new FileWriter(aux));

            // goiburua auxiliarrera kopiatu
            String goiburua = br.readLine();
            bw.write(goiburua != null ? goiburua : GOIBURUA);
            bw.newLine();

            String lerroa;
            while ((lerroa = br.readLine()) != null) {
                Kontzertua k = Kontzertua.fromLine(lerroa);
                if (!aurkitua && k != null && k.getId() == kodea) {
                    aurkitua = true;
                    // berria != null bada ordezkatu; null bada ez da ezer idazten (ezabatu)
                    if (berria != null) {
                        bw.write(berria.toLine());
                        bw.newLine();
                    }
                } else if (!lerroa.trim().isEmpty()) {
                    bw.write(lerroa); // gainerako lerroak berdin kopiatu
                    bw.newLine();
                }
            }
            // fitxategiak itxi behar dira jatorrizkoa ezabatu aurretik
            br.close();
            bw.close();
        } catch (IOException e) {
            // errorea: laguntzailea garbitu eta jatorrizkoa utzi dagoen bezala
            aux.delete();
            throw e;
        }

        if (!aurkitua) {
            aux.delete();
            return false;
        }
        if (!f.delete()) {
            aux.delete();
            throw new IOException("Ezin da jatorrizko fitxategia ezabatu.");
        }
        if (!aux.renameTo(f)) {
            throw new IOException("Ezin da berrizendatu. Datuak hemen daude: " + aux.getAbsolutePath());
        }
        return true;
    }

    /** Fitxategiaren kopia bat sortzen du (goiburua barne). */
    public static void kopiatu(File jatorria, File helburua) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(jatorria));
        BufferedWriter bw = new BufferedWriter(new FileWriter(helburua));
        String lerroa;
        while ((lerroa = br.readLine()) != null) {
            bw.write(lerroa);
            bw.newLine();
        }
        br.close();
        bw.close();
    }
}