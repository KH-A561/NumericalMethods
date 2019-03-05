package ru.omsu.imit.matrix;

public class Solver {
    public static Matrix matrix;
    public static double[] answer;

    public static Matrix getMatrix() {
        return matrix;
    }

    public static void setMatrix(Matrix matrix) {
        Solver.matrix = matrix;
    }

    public static double[] getAnswer() {
        return answer;
    }

    public static void setRightPart(double[] rightPart) {
        Solver.answer = rightPart;
    }

    public static void generateRandomAnswer() {
        if (matrix == null || matrix.size == 0) {
            throw new IllegalArgumentException("empty matrix");
        }
        answer = new double[matrix.size];
        for (int i = 0; i < matrix.size; i++) {
            answer[i] = Math.random() * 10.;
        }
    }

    private static void swapRows(int firstRow, int secondRow) {
        double buf;
        for (int j = 0; j < matrix.size; j++) {
            buf = matrix.array[firstRow][j];
            matrix.array[firstRow][j] = matrix.array[secondRow][j];
            matrix.array[secondRow][j] = buf;
        }
        buf = answer[firstRow];
        answer[firstRow] = answer[secondRow];
        answer[secondRow] = buf;
    }

    private static void renumberRows(int currentRow) {
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

    private static void computeOperations(int currentRow) {
        double divider;
        divider = 1. / matrix.array[currentRow][currentRow];
        for (int j = currentRow; j < matrix.size; j++) {
            matrix.array[currentRow][j] *= divider;
        }
        answer[currentRow] *= divider;
        for (int i = 0; i < currentRow; i++) {
            divider = matrix.array[i][currentRow] / matrix.array[currentRow][currentRow];
            for (int j = currentRow; j < matrix.size; j++) {
                matrix.array[i][j] -= matrix.array[currentRow][j] * divider;
            }
            answer[i] -= answer[currentRow] * divider;
        }
        for (int i = currentRow + 1; i < matrix.size; i++) {
            divider = matrix.array[i][currentRow] / matrix.array[currentRow][currentRow];
            for (int j = currentRow; j < matrix.size; j++) {
                matrix.array[i][j] -= matrix.array[currentRow][j] * divider;
            }
            answer[i] -= answer[currentRow] * divider;
        }
    }

    public static double[] solve() {
//        Matrix matrixCopy = (Matrix) Solver.getMatrix().clone();
//        double[] answerCopy = answer.clone();
        for (int i = 0; i < matrix.size; i++) {
            renumberRows(i);
            computeOperations(i);
        }
//        Solver.matrix = matrixCopy;
        double[] result = answer;
//        answer = answerCopy;
        return result;
    }
}
