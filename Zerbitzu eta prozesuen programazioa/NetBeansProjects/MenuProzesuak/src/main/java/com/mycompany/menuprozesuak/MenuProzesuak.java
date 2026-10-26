package com.mycompany.menuprozesuak;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author aimar
 */
public class MenuProzesuak {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int aukera = 0;

        do {
            System.out.println("\n=====MENUA=====");
            System.out.println("1.Irudi bat ireki (Paint):");
            System.out.println("2.Fitxategi bat ireki:");
            System.out.println("3.Javako exekutagarri bat exekutatu (.jar)");
            System.out.println("4.Irten");
            System.out.println("Aukeratu:");

            try {
                aukera = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException ex) {
                aukera = 0;
            }

            switch (aukera) {
                case 1:
                    System.out.println("Irudiaren bidea: ");
                    String irudia = irakurriBidea(sc);
                    if (existitzenDa(irudia)) {
                        exekutatu("mspaint", irudia);
                    }
                    break;
                case 2:
                    System.out.println("Fitxategiaren bidea: ");
                    String fitxategia = irakurriBidea(sc);
                    if (existitzenDa(fitxategia)) {
                        exekutatu("notepad", fitxategia);
                    }
                    break;
                case 3:
                    System.out.println("Javako exekutagarriaren bidea (.jar):");
                    String jar = irakurriBidea(sc);
                    if (existitzenDa(jar)) {
                        exekutatu("java", "-jar", jar);
                    }
                    break;
                case 4:
                    System.out.println("Agur, programa itxi da!");
                    break;
                default:
                    System.out.println("Aukera okerra, saiatu berriro.");
            }
        } while (aukera != 4);

        sc.close();
    }

    // Fitxategia existitzen den egiaztatzen du
    public static boolean existitzenDa(String bidea) {
        File f = new File(bidea);
        if (!f.exists() || !f.isFile()) {
            System.err.println("Errorea: ez da existitzen fitxategi hau: " + bidea);
            return false;
        }
        return true;
    }

    // ume-prozesua sortu eta bere amaiera itxaron
    private static void exekutatu(String... komandoa) {
        ProcessBuilder pb = new ProcessBuilder(komandoa);
       
        pb.inheritIO();

        try {
            Process process = pb.start();
            int retorno = process.waitFor();
            System.out.println(komandoa[0] + " ume-prozesuaren exekuzioak itzuli egiten du " + retorno);
        } catch (IOException ex) {
            System.err.println("S/I salbuespena! Ezin izan da exekutagarria abiarazi: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.err.println("Ume-prozesua oker amaitu da");
        }
    }

   
    private static String irakurriBidea(Scanner sc) {
        return sc.nextLine().trim().replace("\"", "");
    }
}