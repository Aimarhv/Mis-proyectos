/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ausazkobalioakharia;

/**
 *
 * @author aimar
 */
// Bi hariak sortu eta batera martxan jartzen ditu (exekutatzen den bakoitzean ordena alda daiteke)
public class BiHariPrograma {

    public static void main(String[] args) {
        AusazkoBalioakHaria ausazkoHaria = new AusazkoBalioakHaria("AusazkoHaria");
        BiderketaTaulaHaria taulaHaria = new BiderketaTaulaHaria("TaulaHaria", 7);

        // start()-ek hariak abiarazten ditu eta bakoitzaren run() exekutatzen da
        ausazkoHaria.start();
        taulaHaria.start();
    }
}