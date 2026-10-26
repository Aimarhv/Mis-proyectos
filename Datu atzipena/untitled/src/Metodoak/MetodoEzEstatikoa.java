/* Metodo EZ estatikoa erabiltzea. Objektu bat sortu behar da hori eskatzeko */

package Metodoak;
public class MetodoEzEstatikoa {
    public static void main(String[] args) {
        String semana[]={"lunes", "martes", "miercoles", "jueves", "viernes","sabado", "domingo"};
        String hoy="domingo";
        MetodoEzEstatikoa mez=new MetodoEzEstatikoa();
        String siguiente=mez.diaSiguiente(semana,hoy);
        System.out.println("El día siguiente a "+hoy+ " es: "+siguiente);
    }

    public String diaSiguiente(String[] semana, String dia){
        int posicion=0, siguiente;
        for (int i=0; i<semana.length;i++)
            if (semana[i].equals(dia))
                posicion=i;
        if (posicion ==semana.length-1)
            siguiente =0;
        else
            siguiente=posicion +1;
        return semana[siguiente];
    }

}