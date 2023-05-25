package org.example.matrix;

import java.util.*;

public class GewichteteMatrix extends Matrix{

    int knoten = getKnoten();
    ArrayList<int[]> MST;

    public GewichteteMatrix(int[][] matrix) throws MatrixException {
        super(matrix);
        MST = calcMST();
        if(!isGewichteterGraph())
            throw new MatrixException("Fehler");
    }

    public ArrayList<int[]> getMST() { return MST; }

    // Algorithmus von Prim
    public ArrayList<int[]> calcMST() {

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        ArrayList<Integer> gewichtung = new ArrayList<>();
        ArrayList<Integer> besuchteKnoten = new ArrayList<>();
        ArrayList<int[]> result = new ArrayList<>();


        while (!queue.isEmpty() && besuchteKnoten.size() != knoten) {
            int currKnoten = queue.poll();

            // Alle adjazente Knoten ein Mal speichern
            if(!besuchteKnoten.contains(currKnoten)){
                int[] currRow = super.getRow(currKnoten);
                Integer[] currRowInteger = Arrays.stream(currRow).boxed().toArray(Integer[]::new);
                gewichtung.addAll(Arrays.asList(currRowInteger));
            }
            
            // Minimum Gewicht
            int min = gewichtung.stream()
                    .filter(num -> num != 0)
                    .min(Integer::compareTo).get();

            // Minimum Gewicht Index
            int ArrayListIndex = gewichtung.indexOf(min);
            gewichtung.set(ArrayListIndex, 0);

            //Kantenindex vom Knoten
            int kanteZuKnoten = ArrayListIndex % knoten;
            int kanteVonKnoten = ArrayListIndex / knoten;

            // Schon besuchte Knoten ignorieren
            if(besuchteKnoten.contains(kanteZuKnoten)) {
                if(!besuchteKnoten.contains(currKnoten))
                    besuchteKnoten.add(currKnoten);
                queue.add(kanteZuKnoten);
                continue;
            }


            if(!besuchteKnoten.contains(currKnoten))
                besuchteKnoten.add(currKnoten);

            queue.add(kanteZuKnoten);
            result.add(new int[] {besuchteKnoten.get(kanteVonKnoten), kanteZuKnoten});

        }

        return result;
    }

    private boolean isGewichteterGraph() {
        for(int row = 0; row < knoten; row++){
            for(int col = row; col < knoten; col++){
                if(row == col && getValue(row, col) != 0)
                    return false;

                if(getValue(row, col) != getValue(col, row))
                    return false;
            }
        }
        return true ;
    }


}
