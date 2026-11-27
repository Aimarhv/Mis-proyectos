/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ausazkobalioakharia;
import java.util.Random;


/**
 *
 * @author aimar
 */
// Hari honek 1etik 100era bitarteko 100 ausazko zenbaki sortu eta inprimatzen ditu
public class AusazkoBalioakHaria extends Thread {

    public AusazkoBalioakHaria(String izena) {
        super(izena);
    }

    @Override
    public void run() {
        Random ausazkoa = new Random();
        // 100 aldiz errepikatzen da 1 eta 100 arteko zenbakiak ateratzeko
        for (int i = 1; i <= 100; i++) {
            int balioa = ausazkoa.nextInt(100) + 1;
            System.out.println("[" + getName() + "] " + i + ". balioa: " + balioa);
        }
        System.out.println(getName() + " haria amaitu da.");
    }
}
