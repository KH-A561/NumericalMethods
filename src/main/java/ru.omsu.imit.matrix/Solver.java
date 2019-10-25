package ru.omsu.imit.matrix;

import java.util.List;

public class Solver {
    public static Matrix matrix;
    public static double[] rightPart;
    public static LinearEquationsSolvingAlgorithm algorithm;


    public static LinearEquationsSolvingAlgorithm getAlgorithm() {
        return algorithm;
    }

    public static void setAlgorithm(LinearEquationsSolvingAlgorithm algorithm) {
        Solver.algorithm = algorithm;
    }

    public static Matrix getMatrix() {
        return matrix;
    }

    public static void setMatrix(Matrix matrix) {
        Solver.matrix = matrix;
    }

    public static double[] getRightPart() {
        return rightPart;
    }

    public static void setRightPart(double[] rightPart) {
        Solver.rightPart = rightPart;
    }

    static void swapRows(int firstRow, int secondRow) {
        double buf;
        for (int j = 0; j < matrix.size; j++) {
            buf = matrix.array[firstRow][j];
            matrix.array[firstRow][j] = matrix.array[secondRow][j];
            matrix.array[secondRow][j] = buf;
        }
        buf = rightPart[firstRow];
        rightPart[firstRow] = rightPart[secondRow];
        rightPart[secondRow] = buf;
    }

    static void renumberRows(int currentRow) {
        double maxElem = Math.abs(matrix.array[currentRow][currentRow]);
        int maxRow = currentRow;
        for (int i = currentRow; i < matrix.size; i++) {
            if (Math.abs(matrix.array[i][currentRow]) > maxElem) {
                maxElem = matrix.array[i][currentRow];
                maxRow = i;
            }
        }
        if (currentRow != maxRow) {
            swapRows(currentRow, maxRow);
        }
    }

    public static double[] solve(LinearEquationsSolvingAlgorithm algorithm) {
        setAlgorithm(algorithm);
        return algorithm.solve(matrix, rightPart);
    }

    static boolean index(List c, Object a) {
        if (a != null) {
            for (Object o : c) {
                if (o.equals(a)) return true;
            }
        } else {
            for (Object o : c) {
                if (o == null) return true;
            }
        }
        return false;
    }
}
