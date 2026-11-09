/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ausazkoa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.File;

/**
 *
 * @author aimar
 */
public class Aleatorioa {

    public static void main(String[] args) {
        try {
            // Semea abiarazi: Java beraren bidez, klase-bide berdinarekin
            String javaBidea = System.getProperty("java.home")
                    + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");

            ProcessBuilder pb = new ProcessBuilder(
                    javaBidea, "-cp", classpath,
                    "com.mycompany.ausazkoa.Ausazkoa");
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process semea = pb.start();

            // Semearen sarrerara idazteko eta irteeratik irakurtzeko streamak
            PrintStream semearenSarrera = new PrintStream(semea.getOutputStream());
            BufferedReader semearenIrteera = new BufferedReader(
                    new InputStreamReader(semea.getInputStream()));

            // Aitaren sarrera estandarra
            BufferedReader teklatua = new BufferedReader(
                    new InputStreamReader(System.in));

            System.out.println("Idatzi edozer zenbaki bat jasotzeko, "
                    + "edo 'amaiera' bukatzeko.");

            String lerroa;
            while ((lerroa = teklatua.readLine()) != null) {
                if (lerroa.trim().equalsIgnoreCase("amaiera")) {
                    break;
                }
                // Semeari zenbaki bat eskatu
                semearenSarrera.println();
                semearenSarrera.flush();

                // Semearen erantzuna irakurri eta inprimitu
                String zenbakia = semearenIrteera.readLine();
                System.out.println("Ausazko zenbakia: " + zenbakia);
            }

            // Semea amaitu sarrera itxi, semeak EOF jasotzen du eta bukatzen da
            semearenSarrera.close();
            semea.waitFor();
            System.out.println("Programa amaitu da.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}