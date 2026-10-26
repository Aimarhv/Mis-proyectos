package DatuakJaso;
import java.util.*;
public class DatuakJaso {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String cadena;
        int adina;
        double altuera;

        System.out.println("Zure izena idatzi: ");
        cadena = sc.nextLine();
        System.out.println("Izena duzu:  "+cadena);
        System.out.println("Zure adina idatzi: ");
        adina = sc.nextInt();
        System.out.println("Dauzkazu: "+adina +" urte");
        System.out.println("Idatzi zure garaiera hamartarrekin: ");
        altuera = sc.nextDouble();
        System.out.println("Neurria: "+altuera+" metro");
        System.out.println("Adina + altuera= "+ (adina+altuera));
    }
}