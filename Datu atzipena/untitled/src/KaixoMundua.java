public class KaixoMundua {
    public static void main(String[] args) {
        boolean b;
        b=true;


        System.out.println("Kaixo Mundua, aurreneko adibidea naiz");
        System.out.println(Math.sqrt(-1));
        System.out.println(1.1e200*1.1e200);
        System.out.println(-1.1e200*1.1e200);

        /* Flotatzailea motako hiru balio berezi daude:
         infinitu positibo, infinitu negatibo eta ez zenbaki bat.
         Puntu flotatzailea eragiketa batek ordenagailuaren ahalmena (overflow) gainditzen duenean sortzen dira, edo zeroren arteko zatiketa gertatzen denean.
         */
        if (b) System.out.println("Es cierto");

    }

}