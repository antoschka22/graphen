import org.example.Logic;
import org.example.matrix.AdjazenzMatrix;
import org.example.matrix.GewichteteMatrix;
import org.example.matrix.MatrixException;
import org.example.matrix.GerichteteMatrix;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMatrix {

    private AdjazenzMatrix matrix;

    @Test
    void instanceMatrix_shouldThrowExceptionNoAdjazenzmatrix() {
        //Given
        int[][] array = {
                {1},{1, 3}
                };

        // When
        MatrixException exception = assertThrows(MatrixException.class,
                () -> matrix = new AdjazenzMatrix(array));

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
        matrix = new AdjazenzMatrix(matrix1);

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
        matrix = new AdjazenzMatrix(matrix1);

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

        matrix = new AdjazenzMatrix(matrix2);
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

        matrix = new AdjazenzMatrix(matrix1);

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

        matrix = new AdjazenzMatrix(matrix1);

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


        matrix = new AdjazenzMatrix(matrix1);

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

        AdjazenzMatrix matrix = new AdjazenzMatrix(matrix1);

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

        matrix = new AdjazenzMatrix(matrix1);
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

        matrix = new AdjazenzMatrix(matrix1);
        Logic logic = new Logic(matrix);

        int[] linie = new int[logic.getEulerLinie().size()];

        for(int i = 0; i < logic.getEulerLinie().size(); i++)
            linie[i] = logic.getEulerLinie().get(i);

        int[] supposedResult = {2, 5, 4, 1, 3, 2, 1};

        assertEquals(7, logic.getEulerLinie().size());
        assertArrayEquals(supposedResult, linie);

    }

    @Test
    void calcBloecke_graphenWebsiteBSP() throws MatrixException {
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

        AdjazenzMatrix matrix = new AdjazenzMatrix(matrix1);

        Logic logic = new Logic(matrix);

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>(Arrays.asList(1, 2)));
        result.add(new ArrayList<>(Arrays.asList(2, 3, 4, 5)));
        result.add(new ArrayList<>(Arrays.asList(5, 6, 7)));
        result.add(new ArrayList<>(Arrays.asList(5, 8, 9)));
        result.add(new ArrayList<>(Arrays.asList(8, 10, 11)));
        result.add(new ArrayList<>(Arrays.asList(12, 13)));
        result.add(new ArrayList<>(Arrays.asList(15, 18, 19)));
        result.add(new ArrayList<>(Arrays.asList(17, 20, 21)));
        result.add(new ArrayList<>(Arrays.asList(22, 23, 24)));
        result.add(new ArrayList<>(Arrays.asList(11, 14, 15, 16, 17)));
        result.add(new ArrayList<>(Arrays.asList(7, 12)));
        result.add(new ArrayList<>(Arrays.asList(21, 22)));

        assertEquals(result, logic.getBloecke());

    }

    @Test
    void calcBloecke_BSP_6_3() throws MatrixException {

        int[][] matrix1 = {{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,1,0,0,1,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
                            {0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,0,1,1,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,1,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0},
                            {0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,0,1,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0}};


        AdjazenzMatrix matrix = new AdjazenzMatrix(matrix1);

        Logic logic = new Logic(matrix);

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>(Arrays.asList(1, 2)));
        result.add(new ArrayList<>(Arrays.asList(2, 3, 6, 8, 10, 19)));
        result.add(new ArrayList<>(Arrays.asList(4)));
        result.add(new ArrayList<>(Arrays.asList(5, 6, 7, 9)));
        result.add(new ArrayList<>(Arrays.asList(12, 13, 15)));
        result.add(new ArrayList<>(Arrays.asList(13, 14, 17, 18)));
        result.add(new ArrayList<>(Arrays.asList(11, 16)));
        result.add(new ArrayList<>(Arrays.asList(11, 20)));
        result.add(new ArrayList<>(Arrays.asList(15, 21, 22, 23)));
        result.add(new ArrayList<>(Arrays.asList(24, 25, 26)));
        result.add(new ArrayList<>(Arrays.asList(23, 24)));

        assertEquals(result, logic.getBloecke());
    }

    @Test
    void calcStarkeZusammenhangskomponente_PDF_11() throws MatrixException {
        int[][] matrix1 = {{0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0,0,0},
                {1,1,0,0,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,1,0,0,1},
                {0,1,0,0,1,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,0,0,0},
                {0,0,0,0,0,0,0,1,0,0,0},
                {0,0,0,0,0,0,0,1,0,0,0},
                {0,0,0,0,0,0,0,0,1,1,0}};

        GerichteteMatrix matrix2 = new GerichteteMatrix(matrix1);

        GerichteteMatrix reverseMatrix = new GerichteteMatrix(matrix2.getTransponierteMatrix());

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>(Arrays.asList(6, 7, 8, 9, 11, 10)));
        result.add(new ArrayList<>(List.of(5)));
        result.add(new ArrayList<>(Arrays.asList(2, 3, 4)));
        result.add(new ArrayList<>(List.of(1)));

        assertEquals(result, matrix2.calcStarkeZusammenhangskomponente());
    }

    @Test
    void calcMST_PDF_08() throws MatrixException {
        int[][] matrix1 = {{0, 31, 7, 0, 0, 0, 0, 0, 10},
                            {31, 0, 32, 1, 0, 0, 0, 0, 0},
                            {7, 32, 0, 13, 21, 0, 21, 0, 8},
                            {0, 1, 13, 0, 3, 15, 0, 5, 0},
                            {0, 0, 21, 3, 0, 0, 26, 2, 16},
                            {0, 0, 0, 15, 0, 0, 0, 32, 0},
                            {0, 0, 21, 0, 26, 0, 0, 19, 32},
                            {0, 0, 0, 5, 2, 32, 19, 0, 35},
                            {10, 0, 8, 0, 16, 0, 32, 35, 0}};

        GewichteteMatrix matrix = new GewichteteMatrix(matrix1);

        ArrayList<int[]> result = new ArrayList<>();
        result.add(new int[]{1, 3});
        result.add(new int[]{3, 4});
        result.add(new int[]{4, 7});
        result.add(new int[]{3, 2});
        result.add(new int[]{2, 0});
        result.add(new int[]{2, 8});
        result.add(new int[]{3, 5});
        result.add(new int[]{7, 6});

        for(int i = 0; i < result.size(); i++){
            assertArrayEquals(result.get(i), matrix.getMST().get(i));
        }
    }

    @Test
    void calcMST_bsp__1() throws MatrixException {
        int[][] matrix1 = {{0, 19, 0, 0, 0, 0, 0, 0, 21, 3, 0, 0, 32, 0, 0},
                            {19, 0, 2, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0},
                            {0, 2, 0, 31, 10, 0, 0, 0, 25, 0, 0, 0, 0, 0, 18},
                            {0, 0, 31, 0, 22, 0, 14, 0, 0, 0, 0, 0, 0, 24, 30},
                            {0, 0, 10, 22, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 28, 0, 23, 4, 16, 0, 0, 0},
                            {0, 0, 0, 14, 6, 0, 0, 0, 0, 0, 13, 7, 0, 0, 0},
                            {0, 0, 0, 0, 0, 28, 0, 0, 0, 27, 0, 0, 0, 0, 0},
                            {21, 20, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0, 5},
                            {3, 0, 0, 0, 0, 23, 0, 27, 0, 0, 0, 12, 17, 0, 0},
                            {0, 0, 0, 0, 0, 4, 13, 0, 0, 0, 0, 26, 0, 0, 29},
                            {0, 0, 0, 0, 0, 16, 7, 0, 0, 12, 26, 0, 0, 15, 0},
                            {32, 0, 0, 0, 0, 0, 0, 0, 11, 17, 0, 0, 0, 9, 8},
                            {0, 0, 0, 24, 0, 0, 0, 0, 0, 0, 0, 15, 9, 0, 1},
                            {0, 0, 18, 30, 0, 0, 0, 0, 5, 0, 29, 0, 8, 1, 0}};

        GewichteteMatrix matrix = new GewichteteMatrix(matrix1);

        ArrayList<int[]> result = new ArrayList<>();
        result.add(new int[]{1, 2});
        result.add(new int[]{2, 4});
        result.add(new int[]{4, 6});
        result.add(new int[]{6, 11});
        result.add(new int[]{11, 9});
        result.add(new int[]{9, 0});
        result.add(new int[]{6, 10});
        result.add(new int[]{10, 5});
        result.add(new int[]{6, 3});
        result.add(new int[]{11, 13});
        result.add(new int[]{13, 14});
        result.add(new int[]{14, 8});
        result.add(new int[]{14, 12});
        result.add(new int[]{9, 7});

        for(int i = 0; i < result.size(); i++){
            assertArrayEquals(result.get(i), matrix.getMST().get(i));
        }
    }
}
