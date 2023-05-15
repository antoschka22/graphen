import org.example.Logic;
import org.example.matrix.Adjazenzmatrix;
import org.example.MatrixException;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestMatrix {

    private Adjazenzmatrix matrix;

    @Test
    void instanceMatrix_shouldThrowExceptionNoAdjazenzmatrix() {
        //Given
        int[][] array = {
                {1},{1, 3}
                };

        // When
        MatrixException exception = assertThrows(MatrixException.class,
                () -> matrix = new Adjazenzmatrix(array));

        // Then
        assertEquals("Ungültige Matrix. Nur symmetrische Matrizen werden angenommen.", exception.getMessage());
    }

    @Test
    void calcDistanzMatrix_shouldReturnDistanzmatrix() throws MatrixException {
        int[][] matrix1 = {{0,1,1,1,0},
                            {1,0,0,1,1},
                            {1,0,0,1,0},
                            {1,1,1,0,0},
                            {0,1,0,0,0}};
        matrix = new Adjazenzmatrix (matrix1);

        Logic distanzmatrix = new Logic(matrix);

        int[][] result = {{0,1,1,1,2},
                            {1,0,2,1,1},
                            {1,2,0,1,3},
                            {1,1,1,0,2},
                            {2,1,3,2,0}};

        assertArrayEquals(result, distanzmatrix.getDistanzMatrix().getMatrix());
    }

    @Test
    void calcWegMatrix_shouldReturnWegmatrix() throws MatrixException {
        int[][] matrix1 = {{0,1,1,1,0},
                            {1,0,0,1,1},
                            {1,0,0,1,0},
                            {1,1,1,0,0},
                            {0,1,0,0,0}};
        matrix = new Adjazenzmatrix (matrix1);

        Logic wegmatrix = new Logic(matrix);

        int[][] result = {{1,1,1,1,1},
                            {1,1,1,1,1},
                            {1,1,1,1,1},
                            {1,1,1,1,1},
                            {1,1,1,1,1}};

        assertArrayEquals(result, wegmatrix.getWegMatrix().getMatrix());

        int[][] matrix2 = {{0,0,1,1,0},
                {0,0,0,0,1},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {0,1,0,0,0}};

        matrix = new Adjazenzmatrix(matrix2);
        Logic wegmatrix2 = new Logic(matrix);

        int[][] result2 = {{1,0,1,1,0},
                {0,1,0,0,1},
                {1,0,1,1,0},
                {1,0,1,1,0},
                {0,1,0,0,1}};

        assertArrayEquals(result2, wegmatrix2.getWegMatrix().getMatrix());
    }

    @Test
    void calcArtikulation_PDF_06_Seite_6() throws MatrixException {
        int[][] matrix1 = {{0,1,1,0,1,0,0,0},
                            {1,0,1,1,0,0,0,0},
                            {1,1,0,1,1,0,0,0},
                            {0,1,1,0,1,0,0,0},
                            {1,0,1,1,0,1,1,0},
                            {0,0,0,0,1,0,1,1},
                            {0,0,0,0,1,1,0,1},
                            {0,0,0,0,0,1,1,0}};

        matrix = new Adjazenzmatrix(matrix1);

        Logic logic = new Logic(matrix);
        ArrayList<Integer> artikulationen = logic.getArtikulationen();

        assertEquals(1, artikulationen.size());
    }

    @Test
    void calcBruecken_PDF_06_Seite_11() throws MatrixException {
        int [][] matrix1 = {{0,1,0,0,0,0,0,0,0,0,0,0,0},
                            {1,0,1,1,0,0,0,0,0,0,0,0,0},
                            {0,1,0,0,1,0,0,0,0,0,0,0,0},
                            {0,1,0,0,1,0,0,0,0,0,0,0,0},
                            {0,0,1,1,0,1,0,0,0,0,0,0,0},
                            {0,0,0,0,1,0,1,1,0,0,0,0,0},
                            {0,0,0,0,0,1,0,1,0,0,0,0,0},
                            {0,0,0,0,0,1,1,0,1,1,0,0,0},
                            {0,0,0,0,0,0,0,1,0,0,0,0,0},
                            {0,0,0,0,0,0,0,1,0,0,1,1,0},
                            {0,0,0,0,0,0,0,0,0,1,0,1,0},
                            {0,0,0,0,0,0,0,0,0,1,1,0,1},
                            {0,0,0,0,0,0,0,0,0,0,0,1,0}};

        matrix = new Adjazenzmatrix(matrix1);

        Logic logic = new Logic(matrix);
        ArrayList<int[]> bruecken = logic.getBruecken();

        assertEquals(5, bruecken.size());
    }

   @Test
   void calcZentrum_calcRadius_calcDurchmesser_BSP_5_1() throws MatrixException {
        int[][] matrix1 = {{0,1,0,0,0,1,0,1,1,1,0,0,0,0,0,0,0,0,1,0,0},
                            {1,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
                            {0,1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0},
                            {0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {1,1,0,1,1,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0},
                            {1,0,1,0,0,0,0,0,0,1,0,1,0,0,1,0,0,0,1,0,0},
                            {1,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
                            {1,0,0,0,0,1,0,1,1,0,0,1,0,0,0,0,0,0,0,1,0},
                            {0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0},
                            {0,0,0,0,0,0,0,1,0,1,0,0,1,0,1,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,0,1,0,0,1,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,0},
                            {0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,1},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,1,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1},
                            {1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0}};


        matrix = new Adjazenzmatrix(matrix1);

        Logic logic = new Logic(matrix);

        assertEquals(3,logic.getRadius());
        assertEquals(2, logic.getZentrum().size());
        assertEquals(6, logic.getDurchmesser());

   }

   @Test
    void calcEverything_graphenWebsiteBSP() throws MatrixException {
        int[][] matrix1 = {{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                            {1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,1,1,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,0,1,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1,1,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,1,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0}};

        Adjazenzmatrix matrix = new Adjazenzmatrix(matrix1);

        Logic logic = new Logic(matrix);

        assertEquals(1, logic.getKomponentenAnzahl());
        assertEquals(9, logic.getDurchmesser());
        assertEquals(5, logic.getRadius());
        assertEquals(3, logic.getZentrum().size());
        assertEquals(10, logic.getArtikulationen().size());
        assertEquals(4, logic.getBruecken().size());
    }

    @Test
    void calcEulerZyklus_PDF_04_Seite_12() throws MatrixException {
        int[][] matrix1 = {{0,1,1,0,0,0,0,0,0},
                            {1,0,0,1,0,0,0,0,0},
                            {1,0,0,1,1,0,1,0,0},
                            {0,1,1,0,1,1,0,0,0},
                            {0,0,1,1,0,0,1,1,0},
                            {0,0,0,1,0,0,0,1,0},
                            {0,0,1,0,1,0,0,1,1},
                            {0,0,0,0,1,1,1,0,1},
                            {0,0,0,0,0,0,1,1,0}};

        matrix = new Adjazenzmatrix(matrix1);
        Logic logic = new Logic(matrix);
        int[] zyklus = new int[logic.getZyklus().size()];

        for(int i = 0; i < logic.getZyklus().size(); i++)
            zyklus[i] = logic.getZyklus().get(i);

        int[] supposedResult = {1, 3, 7, 9, 8, 7, 5, 8, 6, 4, 5, 3, 4, 2, 1};

        assertEquals(15, logic.getZyklus().size());
        assertArrayEquals(supposedResult, zyklus);
    }

    @Test
    void calcEulerLinie_shoulReturnLinie() throws MatrixException {
        int[][] matrix1 = {{0,1,1,1,0},
                            {1,0,1,0,1},
                            {1,1,0,0,0},
                            {1,0,0,0,1},
                            {0,1,0,1,0}};

        matrix = new Adjazenzmatrix(matrix1);
        Logic logic = new Logic(matrix);

        int[] linie = new int[logic.getEulerLinie().size()];

        for(int i = 0; i < logic.getEulerLinie().size(); i++)
            linie[i] = logic.getEulerLinie().get(i);

        int[] supposedResult = {2, 5, 4, 1, 3, 2, 1};

        assertEquals(7, logic.getEulerLinie().size());
        assertArrayEquals(supposedResult, linie);

    }

}
