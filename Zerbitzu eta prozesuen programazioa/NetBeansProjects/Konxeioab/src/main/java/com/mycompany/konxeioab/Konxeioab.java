/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.konxeioab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 *
 * @author aimar
 */
public class Konxeioab {

 
  public static void main(String[] args) {
        try {
            // Lehenengo semea (Linuxeko "ls -la"-ren baliokidea)
            Process ls = new ProcessBuilder(
                    "powershell.exe", "-NoProfile", "-Command",
                    "Get-ChildItem -Force").start();

            // Bigarren semea 'd' letra guztiak 'D' bihurtzen ditu (Linuxeko "tr d D")
            // input = prozesuaren sarrera estandarra
            Process tr = new ProcessBuilder(
                    "powershell.exe", "-NoProfile", "-Command",
                    "$input | ForEach-Object { $_ -creplace 'd','D' }").start();

            // Lehenengo semearen irteera irakurtzeko streama
            BufferedReader lsIrteera = new BufferedReader(
                    new InputStreamReader(ls.getInputStream()));

            // Bigarren semearen sarrerara idazteko streama
            PrintStream trSarrera = new PrintStream(tr.getOutputStream());

            String line;
            while ((line = lsIrteera.readLine()) != null) {
                trSarrera.println(line);
                trSarrera.flush();
            }
            // Sarrera itxi, bigarren semeak EOF jaso dezan
            trSarrera.close();

            // Bigarren semearen irteera estandarra irakurri eta inprimitu
            BufferedReader trIrteera = new BufferedReader(
                    new InputStreamReader(tr.getInputStream()));
            while ((line = trIrteera.readLine()) != null) {
                System.out.println(line);
            }

            ls.waitFor();
            tr.waitFor();

        } catch (Exception e) {
            //Errorea gertatzen bada, pantailan erakutsi
            e.printStackTrace();
        }
    }
}