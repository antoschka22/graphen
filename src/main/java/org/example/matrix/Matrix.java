package org.example.matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Matrix {

    private final int[][] matrix;
    private final int knoten;

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
    }

    public Matrix copyForMatrix(boolean distancematrix) throws MatrixException {
        AdjazenzMatrix result = new AdjazenzMatrix(new int[knoten][knoten]);

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

    /* Matrix aus CSV-Datei auslesen */
    public static Matrix readCsvMatrix(String filename) throws IOException, MatrixException {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        // Wie groß ist die Matrix
        String zeile;
        int rowsAnzahl = 0;
        int columnsAnzahl = 0;
        while ((zeile= br.readLine()) != null) {
            rowsAnzahl++;
            String[] row = zeile.split(";");
            columnsAnzahl = row.length;
        }
        br.close();

        if(rowsAnzahl != columnsAnzahl)
            throw new MatrixException("Keine Adjazenzmatrix");

        // Werte hinzufügen
        Matrix matrix = new Matrix(new int[rowsAnzahl][columnsAnzahl]);
        br = new BufferedReader(new FileReader(filename));
        int rowValue = 0;
        while ((zeile = br.readLine()) != null) {
            String[] row = zeile.split(";");
            for (int colValue = 0; colValue < columnsAnzahl; colValue++) {
                matrix.setValue(rowValue, colValue, Integer.parseInt(row[colValue]));
            }
            rowValue++;
        }
        br.close();

        return matrix;
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
