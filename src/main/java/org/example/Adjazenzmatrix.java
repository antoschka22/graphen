package org.example;
import java.io.*;

public class Adjazenzmatrix{
    private int[][] matrix;
    private int knoten;

    public Adjazenzmatrix(int[][] matrix) throws MatrixException {
        if(matrix.length == matrix[0].length){
            this.matrix = matrix;
            this.knoten = matrix.length;
        }else
            throw new MatrixException("Ungültige Matrix. Nur Adjazenzmatrizen werden angenommen.");

    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getKnoten(){
        return knoten;
    }

    public int getValue(int row, int col){
        return matrix[row][col];
    }

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

    public void setMatrixToValue(int value){
        for (int row = 0; row < this.knoten; row++) {
            for(int col = 0; col < this.knoten; col++){
                matrix[row][col] = value;
            }
        }
    }

    public void setValueOnPosition(int row, int col, int value){
        this.matrix[row][col] = value;
        this.matrix[col][row] = value;
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
                System.out.print(matrix[row][col] + ";");
            System.out.println(matrix[row][knoten-1]);
        }
        System.out.println();
    }
}


