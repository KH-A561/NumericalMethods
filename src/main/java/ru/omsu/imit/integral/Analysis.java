package ru.omsu.imit.integral;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Analysis {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        double[] values = {1.25, 1.5, 1.95};
        Integral integral = null;
        double[] gsAnswer;
        double[] gjAnswer;
        double[] closestValuesGS = new double[3];
        double[] closestValuesGJ = new double[3];
        List<Double> listGS;
        List<Double> listGJ;
        double error1;
        double error2;
        Format formatter = new DecimalFormat("0.######E0");
        int numberOfIterations;

        for (int i = 5; i <= 320; i *= 2) {
            stringBuilder.append("N = ");
            stringBuilder.append(i);
            stringBuilder.append(" | ");

            integral = new Integral(i, 1, 2, 1);
            gsAnswer = integral.calculateGS();
            numberOfIterations = integral.getGaussSeidelAlgorithm().getIterations();
            gjAnswer = integral.calculateGJ();

            listGS = Arrays.stream(gsAnswer).boxed().collect(Collectors.toList());
            listGJ = Arrays.stream(gjAnswer).boxed().collect(Collectors.toList());

            closestValuesGS[0] = listGS.stream()
                    .min(Comparator.comparingDouble(q -> Math.abs(q - values[0]))).get();
            closestValuesGS[1] = listGS.stream()
                    .min(Comparator.comparingDouble(q -> Math.abs(q - values[1]))).get();
            closestValuesGS[2] = listGS.stream()
                    .min(Comparator.comparingDouble(q -> Math.abs(q - values[2]))).get();

            closestValuesGJ[0] = listGJ.stream()
                    .min(Comparator.comparingDouble(q -> Math.abs(q - values[0]))).get();
            closestValuesGJ[1] = listGJ.stream()
                    .min(Comparator.comparingDouble(q -> Math.abs(q - values[1]))).get();
            closestValuesGJ[2] = listGJ.stream()
                    .min(Comparator.comparingDouble(q -> Math.abs(q - values[2]))).get();

            for (int j = 0; j < values.length; j++) {
                closestValuesGJ[j] = Math.abs(closestValuesGJ[j] - values[j]);
                closestValuesGS[j] = Math.abs(closestValuesGS[j] - values[j]);
            }

            error1 = Arrays.stream(closestValuesGS).max().getAsDouble();
            stringBuilder.append("GS.Error = ");
            stringBuilder.append(formatter.format(error1));
            stringBuilder.append(" | ");

            stringBuilder.append("Iter = ");
            stringBuilder.append(numberOfIterations);
            stringBuilder.append(" | ");

            error2 = Arrays.stream(closestValuesGJ).max().getAsDouble();
            stringBuilder.append("GJ.Error = ");
            stringBuilder.append(formatter.format(error2));
            stringBuilder.append(" | ");

            stringBuilder.append("|GS - GJ| = ");
            stringBuilder.append(formatter.format(Math.abs(error1 - error2)));
            stringBuilder.append(" | ");

            System.out.println(stringBuilder);
            stringBuilder.delete(0, stringBuilder.length());
        }
    }
}
