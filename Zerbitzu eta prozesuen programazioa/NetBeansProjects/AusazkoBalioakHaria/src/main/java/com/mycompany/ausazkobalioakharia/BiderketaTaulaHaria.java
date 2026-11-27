/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ausazkobalioakharia;

/**
 *
 * @author aimar
 */
// Hari honek emandako zenbakiaren biderketa-taula inprimatzen du (kasu honetan 7arena)
public class BiderketaTaulaHaria extends Thread {

    private final int zenbakia;

    public BiderketaTaulaHaria(String izena, int zenbakia) {
        super(izena);
        this.zenbakia = zenbakia;
    }

    @Override
    public void run() {
        // 1etik 10era biderkatzen du zenbakia eta pantailatik ateratzen du
        for (int i = 1; i <= 10; i++) {
            System.out.println("[" + getName() + "] " + zenbakia + " x " + i + " = " + (zenbakia * i));
        }
        System.out.println(getName() + " haria amaitu da.");
    }
}