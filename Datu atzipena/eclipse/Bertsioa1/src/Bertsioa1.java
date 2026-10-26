import java.io.File;

public class Bertsioa1 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java Actividad1_1 <directorio>");
            System.exit(1);
        }

        String dir = args[0]; // Obtener el directorio del argumento
        File f = new File(dir);

        if (!f.isDirectory()) {
            System.out.println("El directorio especificado no existe o no es válido.");
            System.exit(1);
        }

        File[] listaficheros = f.listFiles();
        System.out.printf("Ficheros en el directorio %s:%n", dir);

        if (listaficheros != null) {
            for (File f2 : listaficheros) {
                System.out.printf("Nombre: %s, ¿es fichero?: %b, ¿es directorio?: %b%n",
                        f2.getName(), f2.isFile(), f2.isDirectory());
            }
        }
    }
}