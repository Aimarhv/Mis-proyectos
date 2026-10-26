//exit() metodoa kenduta, irakurgarritasuna hobetuta.
//(Utzi NetBeans-ek sortu duen "package ...;" lerroa)

package com.mycompany.runprozesu2;

import java.io.IOException;

public class RunProzesu2 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Exekutatzeko programa bat behar da");
            return; // exit() ordez, main metodoa amaitzen da
        }

        ProcessBuilder pb = new ProcessBuilder(args);

        try {
            Process process = pb.start(); // ume-prozesua hasten du
            int retorno = process.waitFor(); // aita-prozesua zain dago
            System.out.println(args[0] + " ume-prozesuaren exekuzioak itzuli egiten du " + retorno);
        } catch (IOException ex) {
            System.err.println("S/I salbuespena!!");
        } catch (InterruptedException ex) {
            System.err.println("Ume-prozesuak oker amaitu zuen");
        }

        System.out.println("Aita prozesua amaitu da");
    }
}