/* Oinarrizko motak baliotzat (kopiak) eta objektuak erreferentziatzat hartzen dira */

package Parametroak;
public class Parametroak {
    public static void main(String[] args) {
        int a=0;
        int v[]={0};
        System.out.println("antes: a="+a+", v[0]="+v[0]);
        valorEntero(a,7);
        valorVector(v,7);
        System.out.println("despues: a="+a+", v[0]="+v[0]);
    }

    public static void valorEntero(int a, int valor){
        //variable a pasa por valor
        a=valor;
    }
    public static void valorVector(int[] v, int valor){
        //variable v pasa por referencia
        v[0]=valor;
    }


}