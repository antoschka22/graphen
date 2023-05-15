package org.example.matrix;

import org.example.MatrixException;

public class GewichteteMatrix extends Matrix{

    int knoten = getKnoten();

    public GewichteteMatrix(int[][] matrix) throws MatrixException {
        super(matrix);
    }


}
