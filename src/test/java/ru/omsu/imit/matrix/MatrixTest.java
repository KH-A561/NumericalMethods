package ru.omsu.imit.matrix;

import org.junit.Test;
import ru.omsu.imit.integral.Integral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MatrixTest {

    @Test
    public void cloneTest() {
        int size = 3;
        double[][] arr = new double[size][size];
        Matrix matrix = new Matrix(arr, size);
        Matrix matrixClone = (Matrix) matrix.clone();
        assertEquals(matrix, matrixClone);
        matrixClone.size = 4;
        assertEquals(matrix.size, size);
    }

    @Test
    public void getInverse() {
        double[][] arr = {{7, 4}, {5, 3}};
        Matrix matrix = new Matrix(arr, arr.length);
        double[][] inverseArr = {{3.0, -4.0}, {-5.0, 7.0}};
        Matrix inverseMatrix = new Matrix(inverseArr, inverseArr.length);
        assertEquals(inverseMatrix, matrix.getInverse());
    }

    @Test
    public void solveMatrix() {
        double[][] arr = {{3, 1, 2, 5}, {3, 1, 0, 2}, {6, 4, 11, 11}, {-3, -2, -2, -10}};
        double[] answer = {-6, -10, -27, 1};
        double[] expected = {-3, -5, -1, 2};
        Matrix matrix = new Matrix(arr, arr.length);
        Solver.setMatrix(matrix);
        Solver.setRightPart(answer);
        double[] actual = Solver.solve(new GaussSeidelAlgorithm());
        assertArrayEquals(expected, actual, 0.001);
    }

    @Test
    public void solveIntegral() {
        Integral integral = new Integral(5, 1, 2, 1);
        System.out.println(Arrays.toString(integral.calculateGS()));
        System.out.println(Arrays.toString(integral.calculateDelta()));
    }
}