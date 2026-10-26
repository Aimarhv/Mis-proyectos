/* Objektu array non elementu bakoitza beste objektu array bat den */
package ArrayBidimentsionala;
public class ArrayBidimentsionala {
    public static void main(String[] args) {
        //Lehenengo dimentsioa bakarrik da nahitaezkoa

        int [][] tabla =new  int [3][];

        tabla[0]=new int [1];
        tabla[1]=new int [2];
        tabla[2]=new int [3];

        System.out.println("Errenkada kopurua: "+tabla.length);
        System.out.println("Errenkada 0. Zutabeak: "+tabla[0].length);
        System.out.println("Errenkada 1. Zutabeak: "+tabla[1].length);
        System.out.println("Errenkada 2. Zutabeak: "+tabla[2].length);

        //Posible da adierazpenaren unean array bat hasieratzea:
        //int [][] tabla ={{0}, {0,0}, {0,0,0}};

    }
}