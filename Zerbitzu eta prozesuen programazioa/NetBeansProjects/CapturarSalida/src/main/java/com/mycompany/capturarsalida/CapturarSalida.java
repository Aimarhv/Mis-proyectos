/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.capturarsalida;

import java.io.*;

/**
 *
 * @author aimar
 */
public class CapturarSalida {

    public static void main(String[] args) {

        try {

            // Prozesu semea sortu

            ProcessBuilder pb = new ProcessBuilder("tracert", "8.8.8.8");

            // Prozesua hasi

            Process proceso = pb.start();

            // Prozesu umearen irteera harrapatu

            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

            

     String linea;

            while ((linea = reader.readLine()) != null) {

                System.out.println(linea);

            }

            // Prozesua amaitu arte itxaron

            int exitCode = proceso.waitFor();

            System.out.println("Prozesua kode honekin amaitu da: " + exitCode);

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();

        }

    }

}
