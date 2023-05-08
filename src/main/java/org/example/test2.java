package org.example;

import org.example.matrix.GerichteteMatrix;

import java.util.ArrayList;
import java.util.Stack;

public class test2 {

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
            matrix2.printMatrix();

            System.out.println();
            GerichteteMatrix reverseMatrix = new GerichteteMatrix(matrix2.getTransponierteMatrix());
            reverseMatrix.printMatrix();
            System.out.println();


            System.out.println(matrix2.calcStarkeZusammenhangskomponente());


        } catch(MatrixException ex){
            System.out.println(ex.getMessage());
        }

    }
}
