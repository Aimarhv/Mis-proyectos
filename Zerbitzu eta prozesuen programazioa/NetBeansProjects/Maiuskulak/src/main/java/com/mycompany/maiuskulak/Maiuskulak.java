/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.maiuskulak;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * @author aimar
 */

public class Maiuskulak {
    public static void main(String[] args) {

        String line;

        try {
            //semea Java
            //String cp = System.getProperty("java.class.path");
            //Process semea = new ProcessBuilder("java", "-cp", cp, "com.mycompany.maiuskulak.Semea").start();
            
            // semea C#
            Process semea = new ProcessBuilder("C:\\Users\\aimar\\Desktop\\Ariketak I01\\Maiuksulak\\Maiuksulak\\bin\\Debug\\net10.0\\Maiuksulak.exe").start();

            BufferedReader br = new BufferedReader(new InputStreamReader(semea.getInputStream()));
            PrintStream ps = new PrintStream(semea.getOutputStream());
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            PrintWriter txt = new PrintWriter(new FileWriter("emaitza.txt", true));

            while ((line = userInput.readLine()) != null) {
                ps.println(line);
                ps.flush(); // Datuak berehala bidaltzeko

                String response = br.readLine();
                if (response != null) {
                    System.out.println(response);
                    txt.println(line + " -> " + response);
                    txt.flush();
                }
            }

            txt.close();

            // Prozesua itxi
            semea.destroy();
            System.out.println("Prozesua itxi da.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errorea gertatu da: " + e.getMessage());
        }
    }
}