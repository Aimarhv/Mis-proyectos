/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.comunicationbetweenprocess;

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
        // Argumentuetan jasotako komandoarekin ume-prozesua sortu eta abiarazi
        Process process = new ProcessBuilder(args).start();

        // Ume-prozesuaren irteera estandarra (stdout) irakurtzeko fluxua
        InputStream is = process.getInputStream();
        // Byte-fluxua karaktere-fluxu bihurtu
        InputStreamReader isr = new InputStreamReader(is);
        // Bufferra erabili lerroz lerro irakurtzeko
        BufferedReader br = new BufferedReader(isr);

        String line;

        // Zein komandoren emaitza den adierazi
        System.out.printf(Arrays.toString(args) + " prozesuaren emaitza:%n");

        // Ume-prozesuak idatzitako lerro guztiak pantailan erakutsi, fluxua amaitu arte (null)
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }
}