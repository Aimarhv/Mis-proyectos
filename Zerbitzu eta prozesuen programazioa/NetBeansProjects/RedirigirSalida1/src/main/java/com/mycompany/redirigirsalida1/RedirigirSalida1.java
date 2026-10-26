/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.redirigirsalida1;

import java.io.*;

/**
 *
 * @author aimar
 */
public class RedirigirSalida1 {

    public static void main(String[] args) {

        try {

            // Prozesu semea sortu

            ProcessBuilder pb = new ProcessBuilder("zure komandoa hemen");

            // Prozesu semearen irteera artxibo batera birbideratzea

            pb.redirectOutput(new File("salida.txt"));

            // Prozesua hasi

            Process proceso = pb.start();

            // Prozesua amaitu arte itxaron

            int exitCode = proceso.waitFor();
            
             // Amaierako kodea erakutsi
             
            System.out.println("Prozesua kode honekin amaitu da: " + exitCode);
            
            // Errorea gertatzen bada, errorearen informazioa erakutsi

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();

        }

    }

}
