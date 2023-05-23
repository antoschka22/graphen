package org.example;

import org.example.matrix.GewichteteMatrix;

public class test3 {

    public static void main(String[] args) {
        try {
            System.out.println();
            GewichteteMatrix matrix = new GewichteteMatrix(GewichteteMatrix.readCsvMatrix("/Users/antoniomolina/Spenger/4BAIF/theorie-pos/graphen/src/matrix.csv").getMatrix());
            matrix.printMatrix();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
