package ru.omsu.imit.integral;

import ru.omsu.imit.matrix.GaussJordanAlgorithm;
import ru.omsu.imit.matrix.GaussSeidelAlgorithm;
import ru.omsu.imit.matrix.Matrix;
import ru.omsu.imit.matrix.Solver;

public class Integral {
    private int N;
    private int a;
    private int b;
    private double lambda;
    private GaussSeidelAlgorithm gaussSeidelAlgorithm;

    public Integral(int n, int a, int b, double lambda) {
        N = n;
        this.a = a;
        this.b = b;
        this.lambda = lambda;
    }

    public double[] calculateGJ() {
        double[][] array = new double[N][N];
        double[] answer = new double[N];
        double step = (double) (b - a) / N;
        for (int i = 1; i <= N; i++) {
            answer[i - 1] = Function.calculate(a + i * step - (step / 2.f));
            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    array[i - 1][j - 1] = 1 - lambda * step * Kernel.calculate(a + i * step - (step / 2.f), a + j * step - (step / 2.f));
                } else {
                    array[i - 1][j - 1] = - (lambda * step * Kernel.calculate(a + i * step - (step / 2.f), a + j * step - (step / 2.f)));
                }
            }
        }
        Matrix matrix = new Matrix(array, N);
        Solver.setMatrix(matrix);
        Solver.setRightPart(answer);
        return Solver.solve(new GaussJordanAlgorithm());
    }

    public double[] calculateGS() {
        gaussSeidelAlgorithm = new GaussSeidelAlgorithm();
        double[][] array = new double[N][N];
        double[] answer = new double[N];
        double step = (double) (b - a) / N;
        for (int i = 1; i <= N; i++) {
            answer[i - 1] = Function.calculate(a + i * step - (step / 2.f));
            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    array[i - 1][j - 1] = 1 - lambda * step * Kernel.calculate(a + i * step - (step / 2.f), a + j * step - (step / 2.f));
                } else {
                    array[i - 1][j - 1] = - (lambda * step * Kernel.calculate(a + i * step - (step / 2.f), a + j * step - (step / 2.f)));
                }
            }
        }
        Matrix matrix = new Matrix(array, N);
        Solver.setMatrix(matrix);
        Solver.setRightPart(answer);
        return Solver.solve(gaussSeidelAlgorithm);
    }

    public double[] calculateDelta() {
        double[] a = calculateGJ();
        double[] b = calculateGS();
        double[] c = new double[N];
        for (int i = 0; i < N; i++) {
            c[i] = Math.abs(a[i] - b[i]);
        }
        return c;
    }

    public GaussSeidelAlgorithm getGaussSeidelAlgorithm() {
        return gaussSeidelAlgorithm;
    }
}
