package org.example;

import org.example.matrix.AdjazenzMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdjazenzTest {

    public static void main(String[] args) {
        try {

            System.out.println();
            AdjazenzMatrix matrix = new AdjazenzMatrix(AdjazenzMatrix.readCsvMatrix("/Users/antoniomolina/Spenger/4BAIF/theorie-pos/graphen/src/matrix.csv").getMatrix());
            Logic logic = new Logic(matrix);
            System.out.println("Input");
            logic.getMatrix().printMatrix();

            System.out.println("Distanzmatrix");
            AdjazenzMatrix distanzMatrix = new AdjazenzMatrix(logic.getDistanzMatrix().getMatrix());
            distanzMatrix.printMatrix();

            System.out.println("Exzentrität");
            HashMap<Integer, Integer> exzentri = logic.getExzentri();
            for (Map.Entry<Integer, Integer> entry : exzentri.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println();
            System.out.println();

            System.out.println("Wegmatrix");
            AdjazenzMatrix wegMatrix = new AdjazenzMatrix(logic.getWegMatrix().getMatrix());
            wegMatrix.printMatrix();

            System.out.println(String.format("Komponente (%d)", logic.getKomponentenAnzahl()));
            ArrayList<int[]> komponente = logic.getKomponente();
            for(int[] komponent : komponente){
                for(int i = 0; i < komponent.length; i++){
                    if(i != 0)
                        System.out.print(";");
                    System.out.print(komponent[i]);
                }
                System.out.println();
            }

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
            System.out.println(String.format("Zyklus (%d)", logic.getZyklus().size()));
            System.out.println(logic.getZyklus());

            System.out.println();
            System.out.println(String.format("Eulerlinie (%d)", logic.getEulerLinie().size()));
            System.out.println(logic.getEulerLinie());

            System.out.println();
            System.out.println(String.format("Blöcke (%d)", logic.getBloecke().size()));
            System.out.println(logic.getBloecke());

            System.out.println();
            System.out.println("Spannbäume: "+logic.getSpannbaume());
        } catch (Exception ex){
            System.out.println("Exception found: "+ex.getMessage());
        }
    }
}
