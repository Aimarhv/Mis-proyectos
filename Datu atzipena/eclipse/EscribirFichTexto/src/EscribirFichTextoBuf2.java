import java.io.*;

public class EscribirFichTextoBuf2 {
    public static void main(String[] args) {
        try {
            // BufferedWriter-en ordez, PrintWriter erabiltzen dugu
            PrintWriter fichero = new PrintWriter(new FileWriter("C:\\temp\\DA\\Proba2\\FichTexto5.txt"));
            
            for (int i = 1; i < 11; i++) {
                // write() + newLine() egin ordez, println() erabiltzen dugu
                fichero.println("Fila número: " + i);
            }
            
            fichero.close(); // Fitxategia itxi
            System.out.println("Fitxategia ondo idatzi da.");
            
        } catch (FileNotFoundException fn) {
            System.out.println("No se encuentra el fichero");
        } catch (IOException io) {
            System.out.println("Error de E/S");
        }
    }
}