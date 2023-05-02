import org.example.Logic;
import org.example.Matrix;
import org.example.MatrixException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class TestMatrix {

    private Matrix matrix;

    @Test
    void instanceMatrix_shouldThrowExceptionNoAdjazenzmatrix() throws MatrixException {
        //Given
        int[][] array = {
                {1},{1, 3}
                };

        // When
        MatrixException exception = assertThrows(MatrixException.class,
                () -> matrix = new Matrix(array));

        // Then
        assertEquals("Ungültige Matrix. Nur Adjazenzmatrizen werden angenommen.", exception.getMessage());
    }

    @Test
    void calcDistanzMatrix_shouldReturnDistanzmatrix() throws MatrixException {
        int[][] matrix1 = {{0,1,1,1,0},
                            {1,0,0,1,1},
                            {1,0,0,1,0},
                            {1,1,1,0,0},
                            {0,1,0,0,0}};
        matrix = new Matrix (matrix1);

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
        matrix = new Matrix (matrix1);

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

        matrix = new Matrix(matrix2);
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

        matrix = new Matrix(matrix1);

        Logic logic = new Logic(matrix);
        int artikulationen = logic.getArtikulationen();

        assertEquals(1, artikulationen);
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

        matrix = new Matrix(matrix1);

        Logic logic = new Logic(matrix);
        int bruecken = logic.getBruecken();

        assertEquals(5, bruecken);
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


        matrix = new Matrix(matrix1);

        Logic logic = new Logic(matrix);

        assertEquals(3,logic.getRadius());
        assertEquals(2, logic.getZentrum());
        assertEquals(6, logic.getDurchmesser());

   }


}
