package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        try {
            Adjazenzmatrix matrix = new Adjazenzmatrix(Adjazenzmatrix.readCsvMatrix("/Users/antoniomolina/Spenger/4BAIF/theorie-pos/graphen/src/matrix.csv").getMatrix());
            Logic logic = new Logic(matrix);
            logic.getMatrix().printMatrix();

            System.out.println("Distanzmatrix");
            Adjazenzmatrix distanzMatrix = new Adjazenzmatrix(logic.getDistanzMatrix().getMatrix());
            distanzMatrix.printMatrix();

            System.out.println("Exzentrität");
            HashMap<Integer, Integer> exzentri = logic.getExzentri();
            for (Map.Entry<Integer, Integer> entry : exzentri.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println();
            System.out.println();

            System.out.println("Wegmatrix");
            Adjazenzmatrix wegMatrix = new Adjazenzmatrix(logic.getWegMatrix().getMatrix());
            wegMatrix.printMatrix();

            System.out.println("Komponente");
            ArrayList<int[]> komponente = logic.getKomponente();
            for(int[] komponent : komponente){
                for(int i = 0; i < komponent.length; i++){
                    System.out.print(komponent[i]+";");
                }
                System.out.println();
            }
            System.out.println("Anzahl: "+logic.getKomponentenAnzahl());

            System.out.println();
            System.out.println("Durchmesser");
            System.out.println(logic.getDurchmesser());

            System.out.println();
            System.out.println("Radius");
            System.out.println(logic.getRadius());

            System.out.println();
            System.out.println("Zentrum");
            System.out.println(logic.getZentrum());

            System.out.println();
            System.out.println("Artikulationen");
            System.out.println(logic.getArtikulationen());

            System.out.println();
            System.out.println("Brücken");
            ArrayList<int[]> bruecken = logic.getBruecken();
            for (int[] bruecke : bruecken) {
                for(int i = 0; i < bruecke.length; i++){
                    if(i % 2 == 0)
                        System.out.print(String.format("{%d, ", bruecke[i]));
                    else
                        System.out.println(String.format("%d}", bruecke[i]));
                }
            }

            System.out.println();
            System.out.println("Zyklus");
            System.out.println(logic.getZyklus());
            System.out.println(logic.getZyklus().size());

            System.out.println();
            System.out.println("Eulerlinie");
            System.out.println(logic.calcEulerLinie());
            System.out.println(logic.calcEulerLinie().size());
        } catch (Exception ex){
            System.out.println("Exception found: "+ex.getMessage());
        }
    }
}
