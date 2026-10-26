
import java.io.*;
	public class EscribirFichTexto2 {
		public static void main(String[] args) throws IOException {
			File fichero = new File("C:\\temp\\DA\\Proba2\\FichTexto2.txt");
			// declarar fichero
			//crear flujo de salida
			FileWriter fic = new FileWriter(fichero);
			String prov[] = { "Albacete", "Avila", "Badajoz", "Cáceres", "Huelva", "Jaén", "Madrid",
			"Segovia", "Soria", "Toledo", "Valladolid", "Zamora" };
			for (int i = 0; i < prov.length; i++)
			/*fic.write(prov[i]);*/
			fic.write(prov[i] + "\n"); //lerro-jauziekin
			fic.close(); // cerrar fichero
		}
}