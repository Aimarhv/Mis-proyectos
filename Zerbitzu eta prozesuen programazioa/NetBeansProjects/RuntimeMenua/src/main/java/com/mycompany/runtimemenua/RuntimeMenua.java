/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.runtimemenua;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author aimar
 */
public class RuntimeMenua {

    public static Process RuntimeMenua = null;
    
    public static void main(String[]args) {
        Scanner sc = new Scanner (System.in);
        int aukera;
        
        do{
            System.out.println("\n=====MENUA=====");
            System.out.println("1.Egutegia ireki:");
            System.out.println("2.URL bat ireki:");
            System.out.println("3.Unity joku bta ireki");
            System.out.println("4.Irten");
            System.out.println("Aukeratu:");
             try {
                aukera = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException ex) {
                aukera = 0;
            }

            switch (aukera) {
                case 1:
                    egutegiaIreki();
                    break;
                case 2:
                    System.out.println("Web helbidea (adib. https://www.google.es): ");
                    String url = sc.nextLine().trim();
                    urlIreki(url);
                    break;
                case 3:
                    unityJokuaIreki();
                    break;
                case 4:
                    irten();
                    break;
                default:
                    System.out.println("Aukera okerra, saiatu berriro.");
            }
        } while (aukera != 4);

        sc.close();
    }
    
    // 1. Egutegia (Windows-eko Egutegia aplikazioa)
    private static void egutegiaIreki() {
        try {
            RuntimeMenua = Runtime.getRuntime().exec("cmd /c start outlookcal:");
            System.out.println("Egutegia irekkitzen...");
        } catch (IOException ex) {
            System.err.println("S/I salbuespena: " + ex.getMessage());
        }
    }
    
    
     // 2. URL bat irekitzea (parametroa: web helbidea)
      private static void urlIreki(String url) {
          if (url.isEmpty()) {
              System.err.println("Web helbidea beharrezkoa da");
              return;
          } 
      try {
            RuntimeMenua = Runtime.getRuntime().exec("cmd /c start msedge " + url);
            System.out.println(url + " irekitzen...");
        } catch (IOException ex) {
            System.err.println("S/I salbuespena: " + ex.getMessage());
        }
    }
      // 3. Unity joku bat (aldatu bidea zure jokoaren .exe-rengatik)
      private static void unityJokuaIreki() {
          try {
              RuntimeMenua = Runtime.getRuntime().exec(new String[]{
    "C:\\Users\\aimar\\Desktop\\DAM 2 A\u00D1O\\ITI-BMOD1\\SpaceInvadersexe\\My project.exe"});
              System.err.println("Unity jokua irekitzen...");
          } catch (IOException ex) {
              System.err.println("S/I salbuepsena: " + ex.getMessage());
          }
      }
      
      // 4. Irten: azken prozesua suntsitu
      private static void irten() {
          if(RuntimeMenua !=null && RuntimeMenua.isAlive()){
              RuntimeMenua.destroy();
               System.out.println("Azken prozesua amaitu da.");
        }
        System.out.println("Agur!");
    }
}
              
              
    
        
      
     
       