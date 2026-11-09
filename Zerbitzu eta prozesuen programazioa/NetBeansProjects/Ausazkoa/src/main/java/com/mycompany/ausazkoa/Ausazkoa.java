/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ausazkoa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;


/**
 *
 * @author aimar
 */
public class Ausazkoa {

    public static void main(String[] args) {
        try {
            BufferedReader sarrera = new BufferedReader(
                    new InputStreamReader(System.in));
            Random ausazkoa = new Random();

            // Aitaren eskaera bakoitza = sarrerako lerro bat
            while (sarrera.readLine() != null) {
                System.out.println(ausazkoa.nextInt(11)); 
                // ezinbestekoa aitak berehala jasotzeko
                System.out.flush(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}