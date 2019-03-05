package ru.omsu.imit.matrix;

import ru.omsu.imit.mgen.Gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestingUtils {
    private static double alpha;
    private static double beta;
    private static double infNormA;
    private static double infNormAInverted;
    private static double obusl;
    private static final Gen g = new Gen();

    public static void setParameters(double alpha, double beta, double infNormA, double infNormAInverted, double obusl) {
        TestingUtils.alpha = alpha;
        TestingUtils.beta = beta;
        TestingUtils.infNormA = infNormA;
        TestingUtils.infNormAInverted = infNormAInverted;
        TestingUtils.obusl = obusl;
    }

    public static double[] generateExpectedResults(int size) {
        double[] expectedResults = new double[size];
        for (int i = 0; i < size; i++) {
            expectedResults[i] = Math.sin((double)i) * Math.sqrt(112.);
        }
        return expectedResults;
    }

    public static double[] multiplyMatrixVector(int size, double[][] matrix, double[] expectedResults) {
        double buf;
        double[] answer = new double[size];
        for (int i = 0; i < size; i++) {
            buf = 0.;
            for (int j = 0; j < size; j++) {
                buf += matrix[i][j] * expectedResults[j];
            }
            answer[i] = buf;
        }
        return answer;
    }

    private static double getZInfinity(double[] actualResults, double[] expectedResults) {
        double[] z = new double[actualResults.length];
        for (int i = 0; i < z.length; i++) {
            z[i] = actualResults[i] - expectedResults[i];
        }
        return Arrays.stream(z).map(Math::abs).max().getAsDouble();
    }

    private static double getRInfinity(Matrix matrix, double[] answer, double[] actualResults) {
        double buf;
        double[] r = new double[actualResults.length];
        for (int i = 0; i < r.length; i++) {
            buf = 0;
            for (int j = 0; j < r.length; j++) {
                buf += matrix.array[i][j] * actualResults[j];
            }
            r[i] = buf - answer[i];
        }
        return Arrays.stream(r).map(Math::abs).max().getAsDouble();
    }

    public static Map<String, Double> getAnalysis(Matrix matrix, double[] answer, double[] expectedResult, double[] actualResult) {
        Map<String, Double> analysis = new HashMap<>();
        double zInf = getZInfinity(actualResult, expectedResult);
        double xInf = Arrays.stream(expectedResult).map(Math::abs).max().getAsDouble();
        double zeta = zInf / xInf;
        double rInf = getRInfinity(matrix, answer, actualResult);
        double answerInf = Arrays.stream(answer).map(Math::abs).max().getAsDouble();
        double rho = rInf / answerInf;
        analysis.put("||z||", zInf);
        analysis.put("ζ", zeta);
        analysis.put("||r||", rInf);
        analysis.put("ρ", rho);
        return analysis;
    }

    public static void printFullAnalysisIntoFile(Map<String, Double> analysis) {
        File file = new File("C:\\Проекты студентов и преподавателей\\3 курс\\Khilko\\NumericalMethods\\src\\main\\java\\ru.omsu.imit.matrix\\results_files\\output.txt");
        try (BufferedWriter fout = new BufferedWriter(new FileWriter(file, true))) {
            fout.append(Double.toString(alpha)).append("\t\t");
            fout.append(Double.toString(beta)).append("\t\t");
            fout.append(Double.toString(infNormA)).append("\t\t");
            fout.append(Double.toString(infNormAInverted)).append("\t\t");
            fout.append(Double.toString(obusl)).append("\t\t");
            fout.append(Double.toString(analysis.get("||z||"))).append("\t\t");
            fout.append(Double.toString(analysis.get("ζ"))).append("\t\t");
            fout.append(Double.toString(analysis.get("||r||"))).append("\t\t");
            fout.append(Double.toString(analysis.get("ρ"))).append("\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
