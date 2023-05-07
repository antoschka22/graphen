package org.example;

import javax.print.attribute.IntegerSyntax;
import java.util.*;

public class Logic {

    private final Adjazenzmatrix matrix;
    private final int knoten;
    private final Adjazenzmatrix distanzMatrix;
    private final HashMap<Integer, Integer> exzentri;
    private final Adjazenzmatrix wegMatrix;
    private final ArrayList<int[]> komponente;
    private final int komponentenAnzahl;
    private final int durchmesser;
    private final int radius;
    private final ArrayList<Integer> zentrum;
    private final ArrayList<Integer> artikulationen;
    private final ArrayList<int[]> bruecken;
    private final ArrayList<Integer> zyklus;
    private final ArrayList<Integer> eulerLinie;


    public Logic (Adjazenzmatrix matrix) throws MatrixException {
        this.matrix = matrix;
        this.knoten = matrix.getKnoten();
        this.distanzMatrix = new Adjazenzmatrix(calcDistanzMatrix());
        this.wegMatrix = new Adjazenzmatrix(calcWegMatrix(matrix));
        this.exzentri = calcExzentrizitaet();
        this.komponente = calcKomponente(matrix);
        this.komponentenAnzahl = this.komponente.size();
        this.durchmesser = calcDurchmesser();
        this.radius = calcRadius();
        this.zentrum = calcZentrum();
        this.artikulationen = calcArtikulationen();
        this.bruecken = calcBruecken();
        this.zyklus = calcEulerzyklus();
        this.eulerLinie = calcEulerLinie();
    }

    public Adjazenzmatrix getMatrix(){ return matrix; }

    public int getKnoten() { return knoten; }

    public Adjazenzmatrix getDistanzMatrix() { return distanzMatrix; }

    public HashMap<Integer, Integer> getExzentri() { return exzentri; }

    public Adjazenzmatrix getWegMatrix() { return wegMatrix; }

    public ArrayList<int[]> getKomponente() { return komponente; }

    public int getKomponentenAnzahl() { return komponentenAnzahl; }

    public int getDurchmesser() { return durchmesser; }

    public int getRadius() { return radius; }

    public ArrayList<Integer> getEulerLinie() { return eulerLinie; }

    public ArrayList<Integer> getZentrum() { return zentrum; }

    public ArrayList<Integer> getArtikulationen() { return artikulationen; }

    public ArrayList<int[]> getBruecken() { return bruecken; }

    public ArrayList<Integer> getZyklus() { return zyklus; }

    private int[][] calcDistanzMatrix() throws MatrixException {

        //Initialisierung
        Adjazenzmatrix distanzMatrix;
        distanzMatrix = matrix.copyForMatrix(true);
        distanzMatrix.setDiagonale(0);

        int k = 2;
        Adjazenzmatrix distanzKMinus2;
        Adjazenzmatrix distanzKMinus1 = matrix.potenzMatrix(k-1);


        Adjazenzmatrix potenzMatrix;
        do {
            potenzMatrix = matrix.potenzMatrix(k);

            for(int row = 0; row < knoten; row++){
                for(int col = 0; col < knoten; col++){
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

    private int[][] calcWegMatrix(Adjazenzmatrix inputMatrix) throws MatrixException {

        //Initialisierung
        Adjazenzmatrix wegMatrix;
        wegMatrix = inputMatrix.copyForMatrix(false);
        wegMatrix.setDiagonale(1);

        int k = 2;
        Adjazenzmatrix wegKMinus2;
        Adjazenzmatrix wegKMinus1 = inputMatrix.potenzMatrix(k-1);


        Adjazenzmatrix potenzMatrix = inputMatrix;
        do {
            potenzMatrix = potenzMatrix.potenzMatrix(k);

            for(int row = 0; row < knoten; row++){
                for(int col = 0; col < knoten; col++){
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

    private HashMap<Integer, Integer> calcExzentrizitaet(){
        HashMap<Integer, Integer> result = new HashMap<>();

        for(int row = 0; row < knoten; row++){
            int max = Arrays.stream(distanzMatrix.getRow(row)).max().getAsInt();
            result.put(row + 1, max);
        }

        return result;
    }

    private ArrayList<int[]> calcKomponente(Adjazenzmatrix inputMatrix) throws MatrixException {
        int knoten = inputMatrix.getKnoten();
        ArrayList<int[]> komponente = new ArrayList<>();
        int[][] currWegMatrix = calcWegMatrix(inputMatrix);
        Adjazenzmatrix newWegMatrix = new Adjazenzmatrix(currWegMatrix);
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
            throw new MatrixException("Ungültige Anzahl an Komponenten");

        if(komponentenAnzahl > 1)
            return Integer.MIN_VALUE;

        int dm = 0;
        for (Integer current : getExzentri().values()){
            if(current > dm)
                dm = current;
        }

        return dm;

    }

    private int calcRadius() throws MatrixException {

        if(komponentenAnzahl <= 0)
            throw new MatrixException("Ungültige Anzahl an Komponenten");

        if(komponentenAnzahl > 1)
            return Integer.MIN_VALUE;

        int rad = Integer.MAX_VALUE;
        for (Integer current : getExzentri().values()){
            if(current < rad)
                rad = current;
        }

        if(rad > durchmesser)
            throw new MatrixException("Radius darf nicht größer als der Durchmesser sein");

        return rad;
    }

    private ArrayList<Integer> calcZentrum() {
        if(komponentenAnzahl > 1)
            return new ArrayList<>();

        ArrayList<Integer> zentrum = new ArrayList();
        HashMap<Integer, Integer> exzentritaet = getExzentri();

        for (Map.Entry<Integer, Integer> current : exzentritaet.entrySet()) {
            if(current.getValue() == radius)
                zentrum.add(current.getKey());
        }

        return zentrum;
    }

    private ArrayList<Integer> calcArtikulationen() throws MatrixException {
        ArrayList<Integer> artikulationen = new ArrayList<>();
        Adjazenzmatrix testMatrix = matrix.copyForMatrix(false);

        for(int row = 0; row < knoten; row++){

            int grad = getDistanzMatrix().getKnotengrad(row);

            if(grad == 1 || grad == 0)
                continue;

            // entferne einen Knoten
            for(int i = 0; i < knoten; i++){
                testMatrix.setValue(row, i, 0);
                testMatrix.setValue(i, row, 0);
            }

            //berechne Wegmatrix neu
            int[][] testWegMatrix = calcWegMatrix(testMatrix);
            Adjazenzmatrix newWegMatrix = new Adjazenzmatrix(testWegMatrix);
            int testSize = calcKomponente(newWegMatrix).size() - 1;
            if(testSize > getKomponentenAnzahl())
                artikulationen.add(row + 1);

            testMatrix = matrix.copyForMatrix(false);
        }

        return artikulationen;
    }

    private ArrayList<int[]> calcBruecken() throws MatrixException {
        ArrayList<int[]> bruecken = new ArrayList<>();
        Adjazenzmatrix testMatrix = matrix.copyForMatrix(false);

        for(int row = 0; row < knoten; row++){
            // entferne eine Kante
            for(int col = 0 + row; col < knoten; col++){
                if(testMatrix.getValue(row, col) != 0){
                    testMatrix.setValue(row, col, 0);
                    testMatrix.setValue(col, row, 0);

                    int[][] testWegMatrix = calcWegMatrix(testMatrix);
                    Adjazenzmatrix newWegMatrix = new Adjazenzmatrix(testWegMatrix);
                    int testSize = calcKomponente(newWegMatrix).size();
                    if(testSize > getKomponentenAnzahl())
                        bruecken.add(new int[]{row + 1, col + 1});

                    testMatrix = matrix.copyForMatrix(false);
                }
            }

        }

        return bruecken;
    }

    private ArrayList<Integer> calcEulerzyklus() throws MatrixException {
        if(matrix.getUnevenKnotengrad().size() > 0)
            return new ArrayList<>();

        Adjazenzmatrix testMatrix;
        testMatrix = matrix.copyForMatrix(false);

        return eulerDFS(testMatrix, 0);
    }

    public ArrayList<Integer> calcEulerLinie() throws MatrixException {

        ArrayList<Integer> unevenKnontengrad = matrix.getUnevenKnotengrad();

        if(unevenKnontengrad.size() != 2)
            return new ArrayList<>();

        int start = unevenKnontengrad.get(0);

        Adjazenzmatrix testMatrix;
        testMatrix = matrix.copyForMatrix(false);

        return eulerDFS(testMatrix, start);
    }

    private ArrayList<Integer> eulerDFS(Adjazenzmatrix testMatrix, int startKnoten) {
        Stack<Integer> stack = new Stack<>();
        ArrayList<Integer> result = new ArrayList<>();

        stack.push(startKnoten);
        while (!stack.isEmpty()) {
            int knoten = stack.peek();

            if (testMatrix.getKnotengrad(knoten) == 0) {
                // Knoten hat keine Kante mehr
                result.add(stack.pop() + 1);
            } else {
                int[] row = testMatrix.getRow(knoten);
                for(int i = 0; i < row.length; i++){
                    if(row[i] == 1){
                        // Kante entfernen und nächster Knoten zum stack
                        stack.push(i);
                        testMatrix.setValue(knoten, i, 0);
                        testMatrix.setValue(i, knoten, 0);
                        break;
                    }
                }

            }
        }
        return result;
    }

}
