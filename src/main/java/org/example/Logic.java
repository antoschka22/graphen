package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Logic {

    private Matrix matrix;
    private Matrix distanzMatrix;
    private int[] exzentri;
    private Matrix wegMatrix;
    private ArrayList<int[]> komponente;
    private int komponentenAnzahl;
    private int durchmesser;
    private int radius;
    private int zentrum;
    private int artikulationen;
    private int bruecken;


    public Logic (Matrix matrix) throws MatrixException {
        this.matrix = matrix;
        this.distanzMatrix = new Matrix(calcDistanzMatrix());
        this.wegMatrix = new Matrix(calcWegMatrix(matrix));
        this.exzentri = calcExzentrizitaet();
        this.komponente = calcKomponente(matrix);
        this.komponentenAnzahl = this.komponente.size();
        this.durchmesser = calcDurchmesser();
        this.radius = calcRadius();
        this.zentrum = calcZentrum();
        this.artikulationen = calcArtikulationen();
        this.bruecken = calcBruecken();
    }

    public Matrix getMatrix(){ return matrix; }

    public Matrix getDistanzMatrix() { return distanzMatrix; }

    public int[] getExzentri() { return exzentri; }

    public Matrix getWegMatrix() { return wegMatrix; }

    public ArrayList<int[]> getKomponente() { return komponente; }

    public int getKomponentenAnzahl() { return komponentenAnzahl; }

    public int getDurchmesser() { return durchmesser; }

    public int getRadius() { return radius; }

    public int getZentrum() { return zentrum; }

    public int getArtikulationen() { return artikulationen; }

    public int getBruecken() { return bruecken; }

    private int[][] calcDistanzMatrix() throws MatrixException {

        //Initialisierung
        int knoten = matrix.getKnoten();
        Matrix distanzMatrix;
        distanzMatrix = matrix.copyForMatrix(true);
        distanzMatrix.setDiagonale(0);

        int k = 2;
        Matrix distanzKMinus2;
        Matrix distanzKMinus1 = matrix.potenzMatrix(k-1);


        Matrix potenzMatrix = matrix;
        do {
            potenzMatrix = matrix.potenzMatrix(k);

            for(int row = 0; row < knoten; row++){
                for(int col = 0 + row; col < knoten; col++){
                    int poMatrValue = potenzMatrix.getValue(row, col);
                    int disMatrValue = distanzMatrix.getValue(row, col);

                    if(disMatrValue == Integer.MIN_VALUE && poMatrValue != 0){
                        distanzMatrix.setValue(row, col, k);
                        distanzMatrix.setValue(col, row, k);
                    }
                }
            }

            distanzKMinus2 = distanzKMinus1;
            distanzKMinus1 = potenzMatrix;
            k++;
        }while(k != matrix.getKnoten() && !distanzKMinus2.isMatrixEqual(distanzKMinus1));

        return distanzMatrix.getMatrix();
    }

    private int[][] calcWegMatrix(Matrix inputMatrix) throws MatrixException {

        //Initialisierung
        int knoten = inputMatrix.getKnoten();
        Matrix wegMatrix;
        wegMatrix = inputMatrix.copyForMatrix(false);
        wegMatrix.setDiagonale(1);

        int k = 2;
        Matrix wegKMinus2;
        Matrix wegKMinus1 = inputMatrix.potenzMatrix(k-1);


        Matrix potenzMatrix = inputMatrix;
        do {
            potenzMatrix = potenzMatrix.potenzMatrix(k);

            for(int row = 0; row < knoten; row++){
                for(int col = 0 + row; col < knoten; col++){
                    int poMatrValue = potenzMatrix.getValue(row, col);
                    int wegMatrValue = wegMatrix.getValue(row, col);

                    if(wegMatrValue == 0 && poMatrValue != 0){
                        wegMatrix.setValue(row, col, 1);
                        wegMatrix.setValue(col, row, 1);
                    }
                }
            }

            wegKMinus2 = wegKMinus1;
            wegKMinus1 = potenzMatrix;
            k++;
        }while(k != knoten && !wegKMinus2.isMatrixEqual(wegKMinus1));

        return wegMatrix.getMatrix();
    }

    private int[] calcExzentrizitaet(){
        int knoten = distanzMatrix.getKnoten();
        int[] result = new int[knoten];
        int max = 0;

        for(int row = 0; row < knoten; row++){
            max = Arrays.stream(distanzMatrix.getRow(row)).max().getAsInt();
            result[row] = max;
        }

        return result;
    }

    private ArrayList<int[]> calcKomponente(Matrix inputMatrix) throws MatrixException {
        int knoten = inputMatrix.getKnoten();
        ArrayList<int[]> komponente = new ArrayList<>();
        int[][] currWegMatrix = calcWegMatrix(inputMatrix);
        Matrix newWegMatrix = new Matrix(currWegMatrix);
        komponente.add(newWegMatrix.getRow(0));
        int newLineFound = 0;

        for(int row = 1; row < knoten; row++){
            int[] currentRow = newWegMatrix.getRow(row);
            for(int[] kompRow : komponente){
                if(!Arrays.equals(currentRow, kompRow))
                    newLineFound++;
            }
            if(newLineFound == komponente.size())
                komponente.add(currentRow);

            newLineFound = 0;
        }

        return komponente;
    }

    private int calcDurchmesser() throws MatrixException {

        if(komponentenAnzahl <= 0)
            throw new MatrixException("Ungültige Anzahl");

        if(komponentenAnzahl > 1)
            return Integer.MIN_VALUE;

        return Arrays.stream(getExzentri()).max().getAsInt();

    }

    private int calcRadius() throws MatrixException {

        if(komponentenAnzahl <= 0)
            throw new MatrixException("Ungültige Anzahl");

        if(komponentenAnzahl > 1)
            return Integer.MIN_VALUE;

        int min = Arrays.stream(getExzentri()).min().getAsInt();

        if(min > durchmesser)
            throw new MatrixException("Radius darf nicht größer als der Durchmesser sein");

        return min;
    }

    private int calcZentrum() {
        if(komponentenAnzahl > 1)
            return -1;

        int zentrum = 0;
        int[] exzentritaet = getExzentri();

        for(int i = 0; i < exzentritaet.length; i++){
            if(exzentritaet[i] == getRadius())
                zentrum++;
        }

        return zentrum;
    }

    private int calcArtikulationen() throws MatrixException {
        int knoten = getDistanzMatrix().getKnoten();
        int artikulationen = 0;
        Matrix testMatrix = matrix.copyForMatrix(false);

        for(int row = 0; row < knoten; row++){

            int grad = getDistanzMatrix().getKnotengrad(row);

            if(grad == 1 || grad == 0)
                break;

            // entferne einen Knoten
            for(int i = 0; i < knoten; i++){
                testMatrix.setValue(row, i, 0);
                testMatrix.setValue(i, row, 0);
            }

            int[][] testWegMatrix = calcWegMatrix(testMatrix);
            Matrix newWegMatrix = new Matrix(testWegMatrix);
            int testSize = calcKomponente(newWegMatrix).size() - 1;
            if(testSize > getKomponentenAnzahl())
                artikulationen++;

            testMatrix = matrix.copyForMatrix(false);
        }

        return artikulationen;
    }

    private int calcBruecken() throws MatrixException {
        int knoten = getDistanzMatrix().getKnoten();
        int bruecken = 0;
        Matrix testMatrix = matrix.copyForMatrix(false);

        for(int row = 0; row < knoten; row++){
            // entferne einen Knoten
            for(int i = 0 + row; i < knoten; i++){
                if(testMatrix.getValue(row, i) != 0){
                    testMatrix.setValue(row, i, 0);
                    testMatrix.setValue(i, row, 0);

                    int[][] testWegMatrix = calcWegMatrix(testMatrix);
                    Matrix newWegMatrix = new Matrix(testWegMatrix);
                    int testSize = calcKomponente(newWegMatrix).size();
                    if(testSize > getKomponentenAnzahl())
                        bruecken++;

                    testMatrix = matrix.copyForMatrix(false);
                }
            }

        }

        return bruecken;
    }

}
