import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LeerFichAleatorio {

    public static void main(String[] args) throws IOException {
        File fichero = new File("Pruebas" + File.separator + "AleatorioEmple.dat");

        // Declara el fichero de acceso aleatorio
        RandomAccessFile file = new RandomAccessFile(fichero, "r");

        int id, dep, posicion;
        Double salario;
        char apellido[] = new char[10], aux;

        posicion = 0;   // para situarnos al principio

        for (;;) {      // recorro el fichero
            file.seek(posicion);    // nos posicionamos en posición
            id = file.readInt();    // obtengo id de empleado

            // Recorro uno a uno los caracteres del apellido
            for (int i = 0; i < apellido.length; i++) {
                aux = file.readChar();
                apellido[i] = aux;  // los voy guardando en el array
            }

            // Convierto a String el array
            String apellidos = new String(apellido);

            dep = file.readInt();           // obtengo dep
            salario = file.readDouble();    // obtengo salario

            if (id > 0)
                System.out.printf("ID: %s, Apellido: %s, Departamento: %d, Salario: %.2f %n",
                        id, apellidos.trim(), dep, salario);

            // Me posiciono para el siguiente empleado, cada empleado ocupa 36 bytes
            posicion = posicion + 36;

            // Si he recorrido todos los bytes salgo del for
            if (file.getFilePointer() == file.length())
                break;
        }   // fin bucle for

        file.close();   // cerrar fichero
    }
}