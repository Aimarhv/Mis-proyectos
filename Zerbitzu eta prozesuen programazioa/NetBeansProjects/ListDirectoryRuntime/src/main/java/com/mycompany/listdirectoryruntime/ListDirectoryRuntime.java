package com.mycompany.listdirectoryruntime;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Argumentuan emandako direktorioaren edukia zerrendatzen du cmd.exe erabiliz.
 *
 * @author aimar
 */
public class ListDirectoryRuntime {

    public static void main(String[] args) {

        // Argumentua badago erabili, bestela C:\ lehenetsia
        String dir = (args.length > 0) ? args[0] : "C:\\";

        try {

            // "dir" komandoari argudioko direktorioa gehitu
            Process process = Runtime.getRuntime().exec("cmd.exe /c dir " + dir);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            // Lerroz lerro irakurri eta pantailan inprimatu
            while ((line = reader.readLine()) != null) {

                System.out.println(line);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}