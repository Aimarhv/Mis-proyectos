/* Javan array guztiak objektuak
*/
package Dimentsioa;
public class Dimentsioa {
    public static void main(String[] args) {
        int manzanas[];
        manzanas=new int[3];
        manzanas[0]=3;
        manzanas[1]=6;
        manzanas[2]=9;
     
        System.out.println("Compré "+ manzanas[0]+ " manzanas el lunes");
        System.out.println("Compré "+ manzanas[1]+ " manzanas el martes");
        System.out.println("Compré "+ manzanas[2]+ " manzanas el miércoles");
        System.out.println("Días de la semana "+ manzanas.length);
       
    }
}