package org.example.matrix;

public class GewichteteMatrix extends Matrix{

    int knoten = getKnoten();

    public GewichteteMatrix(int[][] matrix) throws MatrixException {
        super(matrix);
        if(!isGewichteterGraph())
            throw new MatrixException("Fehler");
    }

    // Algorithmus von Prim
    public GewichteteMatrix calcMST(){



        return null;
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
