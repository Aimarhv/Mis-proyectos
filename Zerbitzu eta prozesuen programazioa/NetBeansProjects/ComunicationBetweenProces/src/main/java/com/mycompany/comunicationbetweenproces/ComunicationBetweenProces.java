/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.comunicationbetweenproces;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.util.Arrays;

/**
 *
 * @author aimar
 */
public class ComunicationBetweenProcess {

    public static void main(String[] args) throws IOException {

        // Runtime erabiliz prozesua exekutatu
        Process process = Runtime.getRuntime().exec(args);

        // Prozesuaren irteera lortu
        InputStream is = process.getInputStream();

        // Byte-ak karaktere bihurtu
        InputStreamReader isr = new InputStreamReader(is);

        // Lerroz lerro irakurtzeko
        BufferedReader br = new BufferedReader(isr);

        // Lerro bakoitza gordetzeko aldagaia
        String line;

        // Exekutatutako komandoa erakutsi
        System.out.printf(Arrays.toString(args)
                + " prozesuaren emaitza:%n");

        // Emaitzak lerroz lerro irakurri eta erakutsi
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        // Irakurgailua itxi
        br.close();
    }

}