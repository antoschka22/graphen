package org.example;

import org.example.matrix.GewichteteMatrix;

public class GewichteteMatrixTest {

    public static void main(String[] args) {
        try {
            System.out.println();
            GewichteteMatrix matrix = new GewichteteMatrix(GewichteteMatrix.readCsvMatrix("/Users/antoniomolina/Spenger/4BAIF/theorie-pos/graphen/src/matrix.csv").getMatrix());
            matrix.printMatrix();
            System.out.println();

            System.out.println("MST "+matrix.getMST().size());
            for(int[] mst : matrix.getMST()){
                for(int i = 0; i < mst.length; i++){
                    System.out.print(mst[i]+" ");
                }
                System.out.println();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
