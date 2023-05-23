package org.example;

import org.example.matrix.GerichteteMatrix;
import org.example.matrix.MatrixException;

public class GerichtetTest {

    public static void main(String[] args) {
        try {
            int[][] matrix1 = {{0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,1,0,0,0,0,0,0,0},
                    {1,1,0,0,0,0,0,0,0,0,0},
                    {0,0,1,0,0,0,0,0,0,0,0},
                    {0,0,0,1,0,0,0,0,0,0,0},
                    {1,0,0,0,0,0,0,1,0,0,1},
                    {0,1,0,0,1,1,0,0,0,0,0},
                    {0,0,0,0,0,0,1,0,0,0,0},
                    {0,0,0,0,0,0,0,1,0,0,0},
                    {0,0,0,0,0,0,0,1,0,0,0},
                    {0,0,0,0,0,0,0,0,1,1,0}};

            GerichteteMatrix matrix2 = new GerichteteMatrix(matrix1);
            System.out.println("Input");
            matrix2.printMatrix();

            System.out.println();
            System.out.println("Reverse Matrix");
            GerichteteMatrix reverseMatrix = new GerichteteMatrix(matrix2.getTransponierteMatrix());
            reverseMatrix.printMatrix();

            System.out.println();
            System.out.println(String.format("Starke Zusammenhangskomponente (%d)", matrix2.calcStarkeZusammenhangskomponente().size()));
            System.out.println(matrix2.calcStarkeZusammenhangskomponente());


        } catch(MatrixException ex){
            System.out.println(ex.getMessage());
        }

    }
}
