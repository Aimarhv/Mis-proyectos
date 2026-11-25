/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doshilosdemo1;

/**
 *
 * @author aimar
 */
// SimpleHilo klaseak Thread klasea heredatzen du, horregatik run() metodoa
// berridatzi dezaket eta bertan jarri hariak egin behar duena.
public class SimpleHilo extends Thread {

    // Eraikitzailean super() erabiltzen dut Thread klasearen eraikitzaileari
    // deitzeko eta hariari izena emateko
    public SimpleHilo(String nombre) {
        super(nombre);
    }

    // run() metodoan jartzen dut hariak egingo duen lana.
    // Kasu honetan, hariak 10 aldiz idazten du bere izena.
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i + "." + getName());
        }
        // Bukatzean, haria amaitu dela esaten duen mezua ateratzen dut
        System.out.println(getName() + " haria amaitu da");
    }
}
