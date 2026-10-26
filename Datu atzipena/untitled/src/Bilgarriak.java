/* Bilgarri klaseak (Wrappers): oinarrizko tipo bat objektu bihurtzea ahalbidetzen dute.
9 daude: Integer, Long, Float, Double, Short, Byte, Character, Boolean eta Void. Karaktere-kateak oinarrizko mota bihurtzeko aukera ematen duten metodoak erabil daitezke
 */

public class Bilgarriak {
public static void main(String[] args) {
System.out.println(Integer.parseInt("124"));
System.out.println(Long.parseLong("1650097"));
System.out.println(Byte.parseByte("21"));
System.out.println(Short.parseShort("45"));
System.out.println(Float.parseFloat("124.89"));
System.out.println(Double.parseDouble("1.5e8"));
// Biderketa, mota egokiarekin egin ahal izateko
System.out.println(Double.parseDouble("3.1416")*2);

}

}
