/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.verificarhiloimplicito;

/**
 * Javako edozein programa hari baten bidez exekutatzen dela erakusten du.
 * Javak Thread klasea du hariak sortzeko eta manipulatzeko.
 *
 * @author aimar
 */
public class VerificarHiloImplicito {

    public static void main(String[] args) {
        // Thread motako objektu bat deklaratzen da, eta une horretan exekutatzen ari den...
        // ...hariaren erreferentzia berreskuratzen da currentThread metodoaren bidez. izena, informazioa eta aktibo dauden harien kopurua ateratzeko.
        Thread hiloPrincipal = Thread.currentThread();

        // Hariaren izena lortzen du
        System.out.println(hiloPrincipal.getName());

        // Hariaren izena eta lehentasuna lortzen ditu kate moduan
        System.out.println(hiloPrincipal.toString());

        // Aktibo dauden hari kopurua itzultzen du
        System.out.println(hiloPrincipal.activeCount());
    }
}