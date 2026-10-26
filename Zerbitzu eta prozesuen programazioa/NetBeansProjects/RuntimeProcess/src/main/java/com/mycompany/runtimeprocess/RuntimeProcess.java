/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.runtimeprocess;
import java.io.IOException;

/**
 *
 * @author aimar
 */
//Hainbat prozesu sortzea Runtime bidez, gero haietako azkena suntsitzeko

// Aztertu, ulertu eta probatu kodea eta beheko galdereei erantzunak eman eta igo moodle-era
public class RuntimeProcess {

    public static void main(String[] args) {

        if (args.length <= 0) {

            System.err.println( "Exekutatzeko programa bat behar da");

            System.exit(-1);

        }

        Runtime runtime = Runtime.getRuntime();//Runtime klaseko instantzia bat lortzen du

        try {

            Process process = runtime.exec(args);

            int retorno = process.waitFor(); 

            System.out.println(args [0] +" exekuzioak itzuli egiten du "+ retorno);

            Thread.sleep(2000); //2000 milisegundoko atzerapena

            process = runtime.exec("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");

             int retorno2 = process.waitFor();

             System.out.println("Nabigatzailearen exekuzioak itzuli egiten du " + retorno2);

             Thread.sleep(2000);

            process=runtime.exec("cmd /c start msedge http://google.es");

            int retorno3 = process.waitFor(); 

            System.out.println("Google-en exekuzioak itzuli egiten du " + retorno3);

           process.destroy();

        } catch (InterruptedException ex) {

            System.err.println("Ume-prozesuak oker amaitu zuen");

            System.exit(-1);

        } catch (IOException ex) {

            System.err.println("S/I salbuespena!!");

            System.exit(-1);

        }

    }

}

