package org.example;

import org.example.matrix.AdjazenzMatrix;
import org.example.matrix.MatrixException;

import java.util.*;

public class Logic {

    private final AdjazenzMatrix matrix;
    private final int knoten;
    private final AdjazenzMatrix distanzMatrix;
    private final HashMap<Integer, Integer> exzentri;
    private final AdjazenzMatrix wegMatrix;
    private final ArrayList<int[]> komponente;
    private final int komponentenAnzahl;
    private final int durchmesser;
    private final int radius;
    private final ArrayList<Integer> zentrum;
    private final ArrayList<Integer> artikulationen;
    private final ArrayList<int[]> bruecken;
    private final ArrayList<Integer> zyklus;
    private final ArrayList<Integer> eulerLinie;
    private final ArrayList<ArrayList<Integer>> bloecke;
    private int spannbaume;

    public Logic (AdjazenzMatrix matrix) throws MatrixException {
        this.matrix = matrix;
        this.knoten = matrix.getKnoten();
        this.distanzMatrix = new AdjazenzMatrix(calcDistanzMatrix());
        this.wegMatrix = new AdjazenzMatrix(calcWegMatrix(matrix));
        this.exzentri = calcExzentrizitaet();
        this.komponente = calcKomponente(matrix);
        this.komponentenAnzahl = this.komponente.size();
        this.durchmesser = calcDurchmesser();
        this.radius = calcRadius();
        this.zentrum = calcZentrum();
        this.artikulationen = calcArtikulationen(matrix);
        this.bruecken = calcBruecken(matrix);
        this.zyklus = calcEulerzyklus();
        this.eulerLinie = calcEulerLinie();
        this.bloecke = calcBloecke(matrix);
        this.spannbaume = calcSpannbaume();
    }

    public AdjazenzMatrix getMatrix(){ return matrix; }

    public AdjazenzMatrix getDistanzMatrix() { return distanzMatrix; }

    public HashMap<Integer, Integer> getExzentri() { return exzentri; }

    public AdjazenzMatrix getWegMatrix() { return wegMatrix; }

    public ArrayList<int[]> getKomponente() { return komponente; }

    public int getKomponentenAnzahl() { return komponentenAnzahl; }

    public int getDurchmesser() { return durchmesser; }

    public int getRadius() { return radius; }

    public ArrayList<ArrayList<Integer>> getBloecke() { return bloecke; }

    public ArrayList<Integer> getEulerLinie() { return eulerLinie; }

    public ArrayList<Integer> getZentrum() { return zentrum; }

    public ArrayList<Integer> getArtikulationen() { return artikulationen; }

    public ArrayList<int[]> getBruecken() { return bruecken; }

    public ArrayList<Integer> getZyklus() { return zyklus; }

    public int getSpannbaume() { return spannbaume; }

    private int[][] calcDistanzMatrix() throws MatrixException {

        //Initialisierung
        AdjazenzMatrix distanzMatrix;
        distanzMatrix = matrix.copyForMatrix(true);
        distanzMatrix.setDiagonale(0);

        int k = 2;
        AdjazenzMatrix distanzKMinus2;
        AdjazenzMatrix distanzKMinus1 = matrix.potenzMatrix(k-1);


        AdjazenzMatrix potenzMatrix;
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

    private int[][] calcWegMatrix(AdjazenzMatrix inputMatrix) throws MatrixException {

        //Initialisierung
        AdjazenzMatrix wegMatrix;
        wegMatrix = inputMatrix.copyForMatrix(false);
        wegMatrix.setDiagonale(1);

        int k = 2;
        AdjazenzMatrix wegKMinus2;
        AdjazenzMatrix wegKMinus1 = inputMatrix.potenzMatrix(k-1);


        AdjazenzMatrix potenzMatrix = inputMatrix;
        int knoten = inputMatrix.getKnoten();
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

    private ArrayList<int[]> calcKomponente(AdjazenzMatrix inputMatrix) throws MatrixException {
        int knoten = inputMatrix.getKnoten();
        ArrayList<int[]> komponente = new ArrayList<>();
        int[][] currWegMatrix = calcWegMatrix(inputMatrix);
        AdjazenzMatrix newWegMatrix = new AdjazenzMatrix(currWegMatrix);
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

        ArrayList<Integer> zentrum = new ArrayList<>();
        HashMap<Integer, Integer> exzentritaet = getExzentri();

        for (Map.Entry<Integer, Integer> current : exzentritaet.entrySet()) {
            if(current.getValue() == radius)
                zentrum.add(current.getKey());
        }

        return zentrum;
    }

    private ArrayList<Integer> calcArtikulationen(AdjazenzMatrix inputMatrix) throws MatrixException {
        ArrayList<Integer> artikulationen = new ArrayList<>();
        AdjazenzMatrix testMatrix = inputMatrix.copyForMatrix(false);

        for(int row = 0; row < inputMatrix.getKnoten(); row++){

            int grad = inputMatrix.getKnotengrad(row);

            if(grad == 1 || grad == 0)
                continue;

            if(isKnotenArtikulation(row, testMatrix))
                artikulationen.add(row + 1);

            testMatrix = inputMatrix.copyForMatrix(false);
        }

        return artikulationen;
    }

    private boolean isKnotenArtikulation(int inputKnoten, AdjazenzMatrix inputMatrix) throws MatrixException {
        int komponente = calcKomponente(inputMatrix).size();

        for(int i = 0; i < inputMatrix.getKnoten(); i++){
            inputMatrix.setValue(inputKnoten, i, 0);
            inputMatrix.setValue(i, inputKnoten, 0);
        }

        int[][] testWegMatrix = calcWegMatrix(inputMatrix);
        AdjazenzMatrix newWegMatrix = new AdjazenzMatrix(testWegMatrix);
        int testSize = calcKomponente(newWegMatrix).size() - 1;
        return testSize > komponente;
    }

    private ArrayList<int[]> calcBruecken(AdjazenzMatrix inputMatrix) throws MatrixException {
        ArrayList<int[]> bruecken = new ArrayList<>();
        AdjazenzMatrix testMatrix = inputMatrix.copyForMatrix(false);
        int knoten = inputMatrix.getKnoten();

        for(int row = 0; row < knoten; row++){
            // entferne eine Kante
            for(int col = row; col < knoten; col++){
                if(testMatrix.getValue(row, col) != 0){
                    testMatrix.setValue(row, col, 0);
                    testMatrix.setValue(col, row, 0);

                    int[][] testWegMatrix = calcWegMatrix(testMatrix);
                    AdjazenzMatrix newWegMatrix = new AdjazenzMatrix(testWegMatrix);
                    int testSize = calcKomponente(newWegMatrix).size();
                    if(testSize > getKomponentenAnzahl())
                        bruecken.add(new int[]{row + 1, col + 1});

                    testMatrix = inputMatrix.copyForMatrix(false);
                }
            }

        }

        return bruecken;
    }

    private ArrayList<Integer> calcEulerzyklus() throws MatrixException {
        if(matrix.getUnevenKnotengrad().size() > 0)
            return new ArrayList<>();

        AdjazenzMatrix testMatrix;
        testMatrix = matrix.copyForMatrix(false);

        return eulerDFS(testMatrix, 0);
    }

    private ArrayList<Integer> calcEulerLinie() throws MatrixException {

        ArrayList<Integer> unevenKnontengrad = matrix.getUnevenKnotengrad();

        if(unevenKnontengrad.size() != 2)
            return new ArrayList<>();

        int start = unevenKnontengrad.get(0);

        AdjazenzMatrix testMatrix;
        testMatrix = matrix.copyForMatrix(false);

        return eulerDFS(testMatrix, start);
    }

    private ArrayList<Integer> eulerDFS(AdjazenzMatrix testMatrix, int startKnoten) {
        Stack<Integer> dsfStack = new Stack<>();
        ArrayList<Integer> result = new ArrayList<>();

        dsfStack.push(startKnoten);
        while (!dsfStack.isEmpty()) {
            int knoten = dsfStack.peek();

            if (testMatrix.getKnotengrad(knoten) == 0) {
                // Knoten hat keine Kante mehr
                result.add(dsfStack.pop() + 1);
            } else {
                int[] row = testMatrix.getRow(knoten);
                for(int i = 0; i < row.length; i++){
                    if(row[i] == 1){
                        // Kante entfernen und nächster Knoten zum stack
                        dsfStack.push(i);
                        testMatrix.setValue(knoten, i, 0);
                        testMatrix.setValue(i, knoten, 0);
                        break;
                    }
                }

            }
        }
        return result;
    }

    private ArrayList<ArrayList<Integer>> calcBloecke(AdjazenzMatrix inputMatrix) throws MatrixException {

        int[] besuchteKnoten = new int[knoten];
        ArrayList<ArrayList<Integer>> blocks = new ArrayList<>();
        for (int i = 0; i < inputMatrix.getKnoten(); i++) {
            if (besuchteKnoten[i] == 0 && !isKnotenArtikulation(i, inputMatrix.copyForMatrix(false))) {
                ArrayList<Integer> block = new ArrayList<>();
                bloeckeBFS(i, block, besuchteKnoten, inputMatrix);
                Collections.sort(block);
                blocks.add(block);
            }
        }

        mergeBloecke(blocks, inputMatrix);

        //Array index + 1
        for(ArrayList<Integer> block: blocks){
            for(int i = 0; i < block.size(); i++) {
                int value = block.get(i) + 1;
                block.set(i, value);
            }
        }

        // Brücken aus Artikulationen Ausnahme
        for(int[] bruecke : getBruecken()){
            for(Integer arti1 : getArtikulationen()){
                for(Integer arti2 : getArtikulationen()){
                    if((arti1 == bruecke[0] && arti2 == bruecke[1])){
                        ArrayList<Integer> resultBloecke = new ArrayList<>();
                        resultBloecke.add(arti1);
                        resultBloecke.add(arti2);
                        Collections.sort(resultBloecke);
                        blocks.add(resultBloecke);
                    }
                }
            }
        }

        return blocks;
    }

    private void bloeckeBFS(int startKnoten, ArrayList<Integer> block, int[] besuchteKnoten, AdjazenzMatrix inputMatrix) throws MatrixException {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startKnoten);
        besuchteKnoten[startKnoten] = 1;

        while (!queue.isEmpty()) {
            AdjazenzMatrix testMatrix = inputMatrix.copyForMatrix(false);
            int currKnoten = queue.poll();

            if(isKnotenArtikulation(currKnoten, testMatrix)){
                besuchteKnoten[currKnoten] = 0;
                if(!block.contains(currKnoten))
                    block.add(currKnoten);
                continue;
            }


            block.add(currKnoten);
            for (int i = 0; i < testMatrix.getKnoten(); i++) {
                if (inputMatrix.getMatrix()[currKnoten][i] == 1 && besuchteKnoten[i] == 0) {
                    besuchteKnoten[i] = 1;
                    queue.add(i);
                }
            }
        }
    }

    //2 Phase. Blöcke mergen und berechnen ob eins ohne Artikulation gemerged werden kann
    private ArrayList<ArrayList<Integer>> mergeBloecke(ArrayList<ArrayList<Integer>> bloecke, AdjazenzMatrix inputMatrix) throws MatrixException {
        for(int i = 0; i < bloecke.size(); i++){
            for(int j = 1 + i; j < bloecke.size();j++){
                //zwei Blöcke ohne Duplikate
                Set<Integer> mergeBloecke = new HashSet<>(bloecke.get(i));
                mergeBloecke.addAll(bloecke.get(j));
                ArrayList<Integer> mergeBlock = new ArrayList<>(mergeBloecke);
                Collections.sort(mergeBlock);


                AdjazenzMatrix testMatrix = new AdjazenzMatrix(createMatrixFromArrayList(mergeBlock, inputMatrix, true));
                AdjazenzMatrix testWegmatrix = new AdjazenzMatrix(calcWegMatrix(testMatrix));
                ArrayList<int[]> testKomponente = calcKomponente(testWegmatrix);

                if(testKomponente.size() > 1)
                    continue;

                ArrayList<Integer> artiAnzahl = calcArtikulationen(testMatrix);

                if(artiAnzahl.size() > 0)
                    continue;


                if(!bloecke.contains(mergeBlock)){
                    bloecke.add(mergeBlock);
                    bloecke.remove(j);
                    bloecke.remove(i);
                }

            }

        }


        return bloecke;
    }

    // Erstelle aus einer ArrayList eine Adjazenzmatrix
    private int[][] createMatrixFromArrayList(ArrayList<Integer> list, AdjazenzMatrix inputMatrix, boolean block){
        int[][] result = new int[list.size()][list.size()];

        for(int i = 0; i < list.size(); i++){
            int[] currRow;
            if(block)
                 currRow = inputMatrix.getRow(list.get(i));
            else
                currRow = inputMatrix.getRow(i);
            int added = 0;

            for(int j = 0; j < currRow.length; j++){
                if(list.contains(j)){
                    result[i][added] = currRow[j];
                    added+= 1;
                }
            }

        }

        return result;

    }

    private int calcSpannbaume() {
        int result = 1;
        ArrayList<ArrayList<Integer>> bloecke = getBloecke();

        for(ArrayList<Integer> block : bloecke){
            int[][] blockMatrix = createMatrixFromArrayList(block, matrix, false);

            int[][] laplaceMatrix = matrix.laplaceMatrix();

            // L := D - A
            int[][] equals = matrix.subtraktionMatrix(laplaceMatrix, blockMatrix);

            int[][] LStar = getDeterminanteUntermatrik(equals, 0);

            result *= calcDeterminante(LStar);
        }

        return result;
    }

    private int calcDeterminante(int[][] matrix) {
        int knoten = matrix.length;
        int result = 0;

        // diagonalen Minus rechnen
        if(knoten == 2){
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        }

        for(int i = 0; i < knoten; i++){
            //Skip erste Zeile und Spalte i
            int[][] deterMatrix = getDeterminanteUntermatrik(matrix, i);

            // Schachbrettmuster
            if(i % 2 == 0)
                result += matrix[0][i] * calcDeterminante(deterMatrix);
            else
                result -= matrix[0][i] * calcDeterminante(deterMatrix);
        }

        return result;
    }

    private int[][] getDeterminanteUntermatrik(int[][] matrix, int spalte){
        int knoten = matrix.length;
        int[][] result = new int[knoten - 1][knoten - 1];

        for(int i = 1; i < knoten; i++){
            int hinzugefuegt = 0;
            for(int j = 0; j < knoten; j++){
                if(j != spalte) {
                    result[i - 1][hinzugefuegt] = matrix[i][j];
                    hinzugefuegt++;
                }
            }
        }

        return result;
    }
}
