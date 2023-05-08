package org.example;
import org.example.matrix.Matrix;

import java.io.*;
import java.util.ArrayList;

public class Adjazenzmatrix extends Matrix {

    int knoten = super.getKnoten();

    public Adjazenzmatrix(int[][] matrix) throws MatrixException {
        super(matrix);
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
    public Adjazenzmatrix multiplicationMatrix(Adjazenzmatrix a, Adjazenzmatrix b) throws MatrixException {
        int[][] matrixA = a.getMatrix();
        int[][] matrixB = b.getMatrix();
        int rowsA = a.getKnoten();
        int columsA = a.getKnoten();

        Adjazenzmatrix result = new Adjazenzmatrix(new int[rowsA][columsA]);

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

    /* Potenzmatrix */
    public Adjazenzmatrix potenzMatrix(int hochzahl) throws MatrixException {

        Adjazenzmatrix matrix = new Adjazenzmatrix(getMatrix());

        if(hochzahl < 0)
            throw new MatrixException("Ungültige Hochzahl");

        if(hochzahl < 2)
            return matrix;

        Adjazenzmatrix pMatrix = matrix.copyForMatrix(false);

        for(int i = 1; i < hochzahl;i++)
            pMatrix = multiplicationMatrix(pMatrix, matrix);

        return pMatrix;
    }

    // Überprüfe Matrizen sind gleich
    public boolean isMatrixEqual(Adjazenzmatrix matrix2){

        // + row weil wir nur eine Hälfte überprüfen müssen
        for(int row = 0; row < knoten; row++){
            for(int col = 0 + row; col < knoten; col++){
                if(getValue(row, col) != matrix2.getValue(row, col))
                    return false;
            }
        }

        return true;
    }

    // Kopiert matrix (distanzmatrix mit Unendlich)
    public Adjazenzmatrix copyForMatrix(boolean distancematrix) throws MatrixException {
        Adjazenzmatrix result = new Adjazenzmatrix(new int[knoten][knoten]);

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

    /* Matrix aus CSV-Datei auslesen */
    public static Adjazenzmatrix readCsvMatrix(String filename) throws IOException, MatrixException {
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
        Adjazenzmatrix matrix = new Adjazenzmatrix(new int[rowsAnzahl][columnsAnzahl]);
        br = new BufferedReader(new FileReader(filename));
        int rowId = 0;
        while ((zeile = br.readLine()) != null) {
            String[] row = zeile.split(";");
            for (int colId = 0 + rowId; colId < columnsAnzahl; colId++) {
                matrix.setValue(rowId, colId, Integer.parseInt(row[colId]));
                matrix.setValue(colId, rowId, Integer.parseInt(row[colId]));
            }
            rowId++;
        }
        br.close();

        return matrix;
    }

    /* Matrix ausgeben */
    public void printMatrix() {

        for (int  row = 0; row < knoten; row++) {
            for (int col = 0; col < knoten-1; col++)
                System.out.print(getMatrix()[row][col] + "  ");
            System.out.println(getMatrix()[row][knoten-1]);
        }
        System.out.println();
    }

    public boolean isMatrixAdjazent(int[][] matrix){
        for (int  row = 0; row < knoten; row++) {
            for (int col = 0; col < knoten; col++){
                if(matrix[row][col] != 0 && matrix[row][col] != 1)
                    return false;
            }
        }
        return true;
    }
}


