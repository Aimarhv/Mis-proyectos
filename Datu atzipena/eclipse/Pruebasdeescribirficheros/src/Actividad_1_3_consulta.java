import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Actividad_1_3_consulta {

    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);

        File fichero = new File("Pruebas" + File.separator + "AleatorioEmple.dat");

        // Declara el fichero de acceso aleatorio
        RandomAccessFile file = new RandomAccessFile(fichero, "r");

        int id, dep, posicion;
        Double salario;
        char apellido[] = new char[10], aux;

        System.out.println("Escriba el identificador del empleado:");
        id = teclado.nextInt();

        // Calculo dónde empieza el registro
        posicion = (id - 1) * 36;

        if (id <= 0 || posicion >= file.length())
            System.out.printf("ID: %d, NO EXISTE EMPLEADO...", id);
        else {
            file.seek(posicion);    // nos posicionamos
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

            System.out.printf("ID: %s, Apellido: %s, Departamento: %d, Salario: %.2f %n",
                    id, apellidos.trim(), dep, salario);
        }

        file.close();       // cerrar fichero
        teclado.close();
    }
}