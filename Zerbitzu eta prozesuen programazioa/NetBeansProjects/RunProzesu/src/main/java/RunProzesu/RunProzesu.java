//ProcessBuilder erabiliz prozesu bat sortu.

//Argudio desberdinak erabili kodea probatzeko 

//1. Zer argudio erabili dituzu probatzeko? Hiru jarri.

package RunProzesu;

import java.io.IOException;

public class RunProzesu {

    public static void main(String[] args) {

        if (args.length <= 0) {

            System.err.println("Exekutatzeko programa bat behar da");

            System.exit(-1);

        }

        ProcessBuilder pb = new ProcessBuilder(args);

        try {

            int retorno;

            Process process = pb.start(); //prozesu berri bat hasten du (ume-prozesua)

            retorno = process.waitFor(); //aita prozesua zain dago azpiprozesua (ume-prozesua) amaitu arte

            //retorno-n azpiprozesuaren irteera-balioa jasotzen da

            System.out.println( args [0] +" ume-prozesuaren exekuzioak itzuli egiten du "+ retorno);

        } catch (IOException ex) {

            System.err.println("S/I salbuespena!!");

            System.exit(-1);

        } catch (InterruptedException ex) {

            System.err.println("Ume-prozesuak oker amaitu zuen");

            System.exit(-1);

        }

        System.out.println("Aita prozesua amaitu da");

    }

}

/* Prozesu berri bat sortzea JVMren azpitik exekutatzen ari den S.E.aren mende dago. Zentzu horretan, hainbat arazo gerta daitezke, hala nola:

* Ez duzu exekutagarria aurkitzen, adierazitako bidea dela eta.

* Exekuzio-baimenik ez izatea.

* Sisteman baliozko exekutagarria ez izatea eta abar.

Kasu gehienetan, salbuespen bat ezartzen da sistema zehatzaren mende, baina beti izango da IOException azpiklase bat.*/