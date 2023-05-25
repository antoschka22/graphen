package org.example.matrix;

import java.util.ArrayList;

public class AdjazenzMatrix extends Matrix {

    int knoten = super.getKnoten();

    public AdjazenzMatrix(int[][] matrix) throws MatrixException {
        super(matrix);
    }

    @Override
    public void setValue(int row, int col, int value){
        super.getMatrix()[row][col] = value;
        super.getMatrix()[col][row] = value;
    }


    public ArrayList<Integer> getUnevenKnotengrad(){
         ArrayList<Integer> result = new ArrayList<>();

        for(int row = 0; row < knoten; row++){
            int[] kanten = getRow(row);
            int sum = 0;

            for(int kante = 0; kante < kanten.length; kante++){
                if(kanten[kante] == 1)
                    sum++;
            }

            if(sum % 2 != 0)
                result.add(row);

        }

        return result;
    }

    public void setDiagonale(int value){
        for(int row = 0; row < knoten; row++){
            for(int col = 0; col < knoten; col++){
                if(row == col)
                    setValue(row, col, value);
            }
        }
    }

    /* Matrizenmultiplikation */
    public AdjazenzMatrix multiplicationMatrix(AdjazenzMatrix a, AdjazenzMatrix b) throws MatrixException {
        int[][] matrixA = a.getMatrix();
        int[][] matrixB = b.getMatrix();
        int rowsA = a.getKnoten();
        int columsA = a.getKnoten();

        AdjazenzMatrix result = new AdjazenzMatrix(new int[rowsA][columsA]);

        for (int row = 0; row < rowsA; row++) {
            for (int col = 0; col < columsA; col++) {
                int sum = 0;
                for (int colB = 0; colB < columsA; colB++) {
                    sum += matrixA[row][colB] * matrixB[colB][col];
                }
                result.setValue(row, col, sum);
            }
        }
        return result;
    }

    public int[][] subtraktionMatrix(int[][] matrixA, int[][] matrixB) {
        int knoten = matrixA.length;

        int[][] result = new int[knoten][knoten];

        for (int i = 0; i < knoten; i++) {
            for (int j = 0; j < knoten; j++) {
                result[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }
        return result;
    }

    /* Laplacematrix */
    // Diagonale mit Knotengrad
    public int[][] laplaceMatrix(){
        int[][] result = new int[knoten][knoten];

        for(int i = 0; i < knoten; i++){
            for(int j = 0; j < knoten; j++){
                if(i == j)
                    result[i][j] = getKnotengrad(i);
            }
        }

        return result;
    }

    /* Potenzmatrix */
    public AdjazenzMatrix potenzMatrix(int hochzahl) throws MatrixException {

        AdjazenzMatrix matrix = new AdjazenzMatrix(getMatrix());

        if(hochzahl < 0)
            throw new MatrixException("Ungültige Hochzahl");

        if(hochzahl < 2)
            return matrix;

        AdjazenzMatrix pMatrix = matrix.copyForMatrix(false);

        for(int i = 1; i < hochzahl;i++)
            pMatrix = multiplicationMatrix(pMatrix, matrix);

        return pMatrix;
    }

    // Überprüfe Matrizen sind gleich
    public boolean isMatrixEqual(AdjazenzMatrix matrix2){

        // + row weil wir nur eine Hälfte überprüfen müssen
        for(int row = 0; row < knoten; row++){
            for(int col = row; col < knoten; col++){
                if(getValue(row, col) != matrix2.getValue(row, col))
                    return false;
            }
        }

        return true;
    }

    // Kopiert matrix (distanzmatrix mit Unendlich)
    @Override
    public AdjazenzMatrix copyForMatrix(boolean distancematrix) throws MatrixException {
        AdjazenzMatrix result = new AdjazenzMatrix(new int[knoten][knoten]);

        for(int row = 0; row < knoten; row++){
            for(int col = 0; col < knoten; col++){
                if(distancematrix){
                    if(getValue(row, col) == 0)
                        result.setValue(row, col, Integer.MIN_VALUE);
                    else
                        result.setValue(row, col, 1);
                } else {
                    if(getValue(row, col) == 0)
                        result.setValue(row, col, 0);
                    else
                        result.setValue(row, col, 1);
                }
            }
        }

        return result;
    }
}