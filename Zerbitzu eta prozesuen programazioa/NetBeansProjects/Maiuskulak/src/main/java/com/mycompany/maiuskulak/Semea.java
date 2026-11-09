/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maiuskulak;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author aimar
 */

public class Semea {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line.toUpperCase());
            System.out.flush();
        }
    }
}