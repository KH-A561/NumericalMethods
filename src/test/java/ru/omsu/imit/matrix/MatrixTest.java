package ru.omsu.imit.matrix;

import org.junit.Test;

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
}