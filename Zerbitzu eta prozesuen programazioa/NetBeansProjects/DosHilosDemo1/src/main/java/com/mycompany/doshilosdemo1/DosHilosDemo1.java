/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.doshilosdemo1;

/**
 *
 * @author aimar
 */
// Programa honetan bi hari sortzen ditut eta biak batera exekutatzen ditut.
// Hari bakoitzak 10 aldiz idazten du bere izena eta bukatzean mezu bat ateratzen du.
public class DosHilosDemo1 {

    public static void main(String[] args) {
        // SimpleHilo klasearekin bi hari sortzen ditut: HiloA eta HiloB
        SimpleHilo hilo1 = new SimpleHilo("HiloA");
        SimpleHilo hilo2 = new SimpleHilo("HiloB");

        // start() erabiltzen dut hariak martxan jartzeko.
        // start()-ek run() metodoari deitzen dio, eta bi hariak batera hasten dira.
        hilo1.start();
        hilo2.start();
    }
}