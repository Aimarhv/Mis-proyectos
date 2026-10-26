import java.io.File;

public class Actividad_1_1 {
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
            String[] archivos = f.list();
            System.out.printf("Ficheros en el directorio actual: %d %n", 
                              archivos.length);
            for (int i = 0; i < archivos.length; i++) {
                File f2 = new File(f, archivos[i]);
                System.out.printf("Nombre: %s, es fichero?: %b, es directorio?: %b %n", 
                                  archivos[i], f2.isFile(), f2.isDirectory());
            }
        } else {
            System.out.println("El directorio: " + dir + " no existe");
        }
    }
}
