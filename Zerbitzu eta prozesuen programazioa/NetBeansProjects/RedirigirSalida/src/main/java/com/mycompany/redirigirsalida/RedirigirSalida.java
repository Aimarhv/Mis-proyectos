/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.redirigirsalida;

import java.io.*;



/**
 *
 * @author aimar
 */
public class RedirigirSalida {

    public static void main(String[] args) {

        try {

            // Prozesu semea sortu

            ProcessBuilder pb = new ProcessBuilder("ipconfig");

            // Prozesu semearen irteera artxibo batera birbideratzea

            pb.redirectOutput(new File("salida.txt"));

            // Prozesua hasi

            Process proceso = pb.start();

            // Prozesua amaitu arte itxaron

            int exitCode = proceso.waitFor();

            System.out.println("Prozesua kode honekin amaitu da: " + exitCode);

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();

        }

    }

}