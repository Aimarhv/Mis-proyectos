
/* Errore aritmetikoaren kudeaketa, 0ren arteko zatiketa, programa ustekabean ez amaitzeko
*/

package Salbuespenak;
public class Salbuespenak {
public static void main(String[] args) {
    int a,b;
    try{
        a=0;
        b=10/a;
        System.out.println("Esto ya no se imprime");
    }catch (ArithmeticException objetoExcep){
        System.out.println("División por cero "+ objetoExcep);
    }
    System.out.println("continua el programa...");
    }
/*    try{
  *      bloque de código peligroso
  *    }
  *    catch (TipoExcepción1 exOb){
  *      gestión de excepciones pata TipoExcepción1
  *    }
  *    catch (TipoExcepción2 exOb){
  *      gestión de excepciones pata TipoExcepción2
  *    }
  *    ...
  *    finally {
  *        bloque de código que siempre se ejecuta
  *        sucedan o no las excepciones. Es opcional
  *    }
  */

}