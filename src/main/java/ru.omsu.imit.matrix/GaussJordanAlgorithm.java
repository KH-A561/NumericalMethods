package ru.omsu.imit.matrix;

public class GaussJordanAlgorithm implements LinearEquationsSolvingAlgorithm {
    public double[] solve(Matrix matrix, double[] rightPart) {
        double divider;
        for (int k = 0; k < matrix.size; k++) {
            Solver.renumberRows(k);
            if (matrix.array[k][k] == 0.) {
                throw new IllegalArgumentException("деление на 0");
            }
            divider = 1. / matrix.array[k][k];
            for (int j = k; j < matrix.size; j++) {
                matrix.array[k][j] *= divider;
            }
            rightPart[k] *= divider;
            for (int i = 0; i < k; i++) {
                divider = matrix.array[i][k] / matrix.array[k][k];
                for (int j = k; j < matrix.size; j++) {
                    matrix.array[i][j] -= matrix.array[k][j] * divider;
                }
                rightPart[i] -= rightPart[k] * divider;
            }
            for (int i = k + 1; i < matrix.size; i++) {
                divider = matrix.array[i][k] / matrix.array[k][k];
                for (int j = k; j < matrix.size; j++) {
                    matrix.array[i][j] -= matrix.array[k][j] * divider;
                }
                rightPart[i] -= rightPart[k] * divider;
            }
        }
        double[] result = rightPart;
        return result;
    }
}
