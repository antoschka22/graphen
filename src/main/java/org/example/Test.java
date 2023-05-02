package org.example;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        try {
            Matrix matrix = new Matrix(Matrix.readCsvMatrix("/Users/antoniomolina/Spenger/4BAIF/theorie-pos/graphen/src/matrix.csv").getMatrix());
            Logic logic = new Logic(matrix);
            logic.getMatrix().printMatrix();

            System.out.println("Distanzmatrix");
            Matrix distanzMatrix = new Matrix(logic.getDistanzMatrix().getMatrix());
            distanzMatrix.printMatrix();

            System.out.println("Exzentrität");
            int[] exzentri = logic.getExzentri();
            for(int i = 0; i < exzentri.length; i++){
                System.out.print(exzentri[i]+";");
            }
            System.out.println();
            System.out.println();

            System.out.println("Wegmatrix");
            Matrix wegMatrix = new Matrix(logic.getWegMatrix().getMatrix());
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
            System.out.println(logic.getRadius());

            System.out.println();
            System.out.println("Artikulationen");
            System.out.println(logic.getArtikulationen());

            System.out.println();
            System.out.println("Brücken");
            System.out.println(logic.getBruecken());
        } catch (Exception ex){
            System.out.println("Exception found: "+ex.getMessage());
        }
    }
}
