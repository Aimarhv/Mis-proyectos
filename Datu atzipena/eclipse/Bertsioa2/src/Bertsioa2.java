import java.io.File;

public class Bertsioa2 {
    public static void main(String[] args) {
        String dir;
        if (args.length > 0) {
            dir = args[0];
        } else {
            dir = ".";
        }

        File f = new File(dir);
        if (f.exists() && f.isDirectory()) {
            System.out.println("Directorio actual: " + dir);
            File[] archivos = f.listFiles();
            
            if (archivos != null) {
                System.out.printf("Ficheros en el directorio actual: %d %n", archivos.length);
                for (File archivo : archivos) {
                    System.out.printf("Nombre: %s, es fichero?: %b, es directorio?: %b %n",
                            archivo.getName(), archivo.isFile(), archivo.isDirectory());
                }
            } else {
                System.out.println("No se pudieron listar los archivos en el directorio.");
            }
        } else {
            System.out.println("El directorio: " + dir + " no existe.");
        }
    }
}