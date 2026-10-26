import java.io.*;
	public class LeerFichTextoBuf {
		public static void main(String[] args) {
			try {
				
					BufferedReader fichero = new BufferedReader(new
	FileReader("C:\\temp\\DA\\Proba2\\FichTexto3.txt"));
					String linea;
					while ((linea = fichero.readLine()) != null)
							System.out.println(linea);
					fichero.close();
				} catch (FileNotFoundException fn) {
						System.out.println("No se encuentra el fichero");
				} catch (IOException io) {
						System.out.println("Error de E/S ");
		}
	}
}