
import java.io.*;
	public class EscribirFichTexto {
			public static void main(String[] args) throws IOException {
				File fichero = new File("C:\\temp\\DA\\Proba2\\FichTexto.txt");
				// declarar fichero
				//crear flujo de salida
				FileWriter fic = new FileWriter(fichero);
				String cadena = "Esto es una prueba con FileWriter";
			//convierte la cadena en array de caracteres para extraerlos 1 a 1
				char[] cad = cadena.toCharArray();
				for (int i = 0; i < cad.length; i++)
						fic.write(cad[i]); // se va escribiendo un carácter
				fic.append('*'); // se añade al final un *
				fic.close(); // cerrar fichero
		}
}