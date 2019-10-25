package ru.omsu.imit.matrix;

import java.util.Arrays;

public class GaussSeidelAlgorithm implements LinearEquationsSolvingAlgorithm {
    private int iterations;

    // Условие окончания
    private boolean converge(double[] xk, double[] xkp) {
        double norm = 0;
        for (int i = 0; i < xk.length; i++)
            norm += (xk[i] - xkp[i]) * (xk[i] - xkp[i]);
        return (Math.sqrt(norm) < 10E-5);
    }

    @Override
    public double[] solve(Matrix matrix, double[] rightPart) {
        iterations = 0;
        double[] p = new double[matrix.size];
        double[] x = new double[matrix.size];
        Arrays.fill(x, 0.f);

/*
    Ход метода, где:
    a[n][n] - Матрица коэффициентов
    x[n], p[n] - Текущее и предыдущее решения
    b[n] - Столбец правых частей
    Все перечисленные массивы вещественные и
    должны быть определены в основной программе,
    также в массив x[n] следует поместить начальное
    приближение столбца решений (например, все нули)
*/
        double var;
        do {
            iterations++;
            if (matrix.size >= 0) System.arraycopy(x, 0, p, 0, matrix.size);
            for (int i = 0; i < matrix.size; i++) {
                var = 0;
                for (int j = 0; j < i; j++)
                    var += (matrix.array[i][j] * x[j]);
                for (int j = i + 1; j < matrix.size; j++)
                    var += (matrix.array[i][j] * p[j]);
                if (matrix.array[i][i] == 0) throw new IllegalArgumentException("деление на 0");
                x[i] = (rightPart[i] - var) / matrix.array[i][i];
            }
        } while (!converge(x, p));
        return x;
    }

    public int getIterations() {
        return iterations;
    }
}
