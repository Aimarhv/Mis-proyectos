
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
	public class EscribirFichTextoBuf {
		public static void main(String[] args) {
			try {
					BufferedWriter fichero = new BufferedWriter(new
FileWriter("C:\\temp\\DA\\Proba2\\FichTexto4.txt"));
					for (int i = 1; i < 11; i++) {
								fichero.write("Fila número: " + i); // escribe una línea
								fichero.newLine(); // escribe un salto de línea
					}
					fichero.close();
			} catch (FileNotFoundException fn) {
					System.out.println("No se encuentra el fichero");	
			} catch (IOException io) {
					System.out.println("Error de E/S");
		}
	}
}