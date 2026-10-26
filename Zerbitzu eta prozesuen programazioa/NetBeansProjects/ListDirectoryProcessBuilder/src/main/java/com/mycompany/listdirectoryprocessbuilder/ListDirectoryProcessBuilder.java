/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.listdirectoryprocessbuilder;

import java.io.BufferedReader;

import java.io.InputStreamReader;
/**
 *
 * @author aimar
 */
public class ListDirectoryProcessBuilder {

    public static void main(String[] args) {

        try {

            // cmd.exe-ri "dir" komandoa exekutatzeko eskatu, args[0] direktorioarekin
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "dir " + args[0]);

            // Erroreak irteera arruntarekin batu
            builder.redirectErrorStream(true);

            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            // Irteera lerroz lerro inprimatu
            while ((line = reader.readLine()) != null) {

                System.out.println(line);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}