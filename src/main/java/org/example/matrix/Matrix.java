package org.example.matrix;

import org.example.MatrixException;

public abstract class Matrix {

    private int[][] matrix;
    private int knoten;

    public Matrix(int[][] matrix) throws MatrixException {
        if(matrix.length == matrix[0].length){
            this.matrix = matrix;
            this.knoten = matrix.length;
        }else
            throw new MatrixException("Ungültige Matrix. Nur symmetrische Matrizen werden angenommen.");
    }

    public int[][] getMatrix() { return matrix; }

    public int getKnoten() { return knoten; }

    public int getValue(int row, int col){ return matrix[row][col]; }

    public int[] getRow(int row){
        int[] newRow = new int[getKnoten()];

        for(int i = 0; i < knoten; i++){
            newRow[i] = getValue(row, i);
        }

        return newRow;
    }

    public int getKnotengrad(int inputKnoten){
        int grad = 0;
        int[] row = getRow(inputKnoten);

        for(int i = 0; i < knoten; i++){
            if(row[i] == 1)
                grad++;
        }

        return grad;
    }

    public void setValue(int row, int col, int value){
        matrix[row][col] = value;
        matrix[col][row] = value;
    }

    public Matrix copyForMatrix(boolean distancematrix) throws MatrixException {
        Adjazenzmatrix result = new Adjazenzmatrix(new int[knoten][knoten]);

        for(int row = 0; row < knoten; row++){
            for(int col = 0; col < knoten; col++){
                if(getValue(row, col) == 0)
                    result.setValue(row, col, 0);
                else
                    result.setValue(row, col, 1);
                }
            }

        return result;
    }

    public void printMatrix() {

        for (int  row = 0; row < knoten; row++) {
            for (int col = 0; col < knoten-1; col++)
                System.out.print(getMatrix()[row][col] + "  ");
            System.out.println(getMatrix()[row][knoten-1]);
        }
        System.out.println();
    }
}
