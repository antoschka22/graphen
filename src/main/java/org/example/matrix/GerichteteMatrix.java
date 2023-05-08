package org.example.matrix;

import org.example.MatrixException;

import java.util.ArrayList;
import java.util.Stack;

public class GerichteteMatrix extends Matrix{

    private int knoten = super.getKnoten();
    private int[][] transponierteMatrix;
    Matrix testMatrix = super.copyForMatrix(false);


    public GerichteteMatrix(int[][] matrix) throws MatrixException {
        super(matrix);
        this.transponierteMatrix = matrixTransponieren();
    }

    public int[][] getTransponierteMatrix() { return transponierteMatrix; }

    private int[][] matrixTransponieren(){
        int[][] matrix = super.getMatrix();
        int[][] result = new int[knoten][knoten];

        for(int row = 0; row < knoten; row++){
            for(int col = 0; col < knoten; col++){
                int value = matrix[row][col];
                result[col][row] = value;
            }
        }

        return result;
    }

    public ArrayList<ArrayList<Integer>> calcStarkeZusammenhangskomponente() throws MatrixException {
        // Aufruf von DFS(G) -> TF(v)
        // TF Reihenfolge bekommen in einem Stack
        boolean[] besuchteKnoten = new boolean[knoten];
        Stack<Integer> TFReihenfolge = new Stack<>();
        for (int i = 0; i < knoten; i++) {
            if (!besuchteKnoten[i]) {
                dfs(i, besuchteKnoten, TFReihenfolge, null, super.getMatrix(), true);
            }
        }

        // DFS von der tranponierten Matrix in der Reihenfolge von Schritt eins
        besuchteKnoten = new boolean[knoten];
        ArrayList<ArrayList<Integer>> starkeKomponente = new ArrayList<>();
        while (!TFReihenfolge.isEmpty()) {
            int knoten = TFReihenfolge.pop();
            if (!besuchteKnoten[knoten]) {
                ArrayList<Integer> komponent = new ArrayList<>();
                dfs(knoten, besuchteKnoten, TFReihenfolge, komponent, transponierteMatrix, false);
                starkeKomponente.add(komponent);
            }
        }

        return starkeKomponente;
    }

    private static void dfs(int knoten, boolean[] besuchteKnoten, Stack<Integer> TFStack, ArrayList<Integer> zusammenhangskomponente, int[][] matrix, boolean tfReihenfolge) {
        besuchteKnoten[knoten] = true;
        if (tfReihenfolge) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[knoten][i] == 1 && !besuchteKnoten[i]) {
                    dfs(i, besuchteKnoten, TFStack, zusammenhangskomponente, matrix, true);
                }
            }
            TFStack.push(knoten);
        } else {
            zusammenhangskomponente.add(knoten+1);
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[knoten][i] == 1 && !besuchteKnoten[i]) {
                    dfs(i, besuchteKnoten, null, zusammenhangskomponente, matrix, false);
                }
            }
        }
    }


}
