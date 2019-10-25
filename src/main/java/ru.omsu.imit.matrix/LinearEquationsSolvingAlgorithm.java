package ru.omsu.imit.matrix;

public interface LinearEquationsSolvingAlgorithm {
    double[] solve(Matrix matrix, double[] rightPart);
}
