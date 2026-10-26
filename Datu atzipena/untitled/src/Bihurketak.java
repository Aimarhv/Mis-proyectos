public class Bihurketak{
/* Tamaina txikieneko datua automatikoki bihurtzen da tamaina handieneko mota. Cast operadorea behar da tipo handiago batetik txikiago baterako bihurketa egiteko.
 Sei zenbaki-motak eta char mota bateragarriak dira bihurketetarako. Boolean mota bateraezina da gainerako guztiekin
 */


public static void main(String[] args) {
      char c='a'; //2 bytes
      int i2; //4 bytes
      short s; //2 bytes
      double d; //8 bytes
      float f; //4 bytes
      long l; // 8 bytes


      i2=c;
      System.out.println("i2= "+i2);
      //s=c; No lo permite
      s= (short) c;
      System.out.println("s= "+s);
      d=c;
      System.out.println("d= "+d);
      s=678;

      double dou=123.67;
      //int dest = dou; No lo permite
      int dest= (int) dou;
      System.out.println("dest= "+dest);

      dou=3.4028237E+50;
      f=(float) dou;
      System.out.println("f= "+f);
      dou=7.5;
      l=(long)dou;
      System.out.println("l= "+l);


      int in=257;
      byte b; //1 byte
      b=(byte)c;
      System.out.println("b= "+b);
      b= (byte) in;
      System.out.println("b= "+b);

    }
}
