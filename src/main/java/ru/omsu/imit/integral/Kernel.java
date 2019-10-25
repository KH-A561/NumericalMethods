package ru.omsu.imit.integral;

public class Kernel {
    public static double calculate(double x, double s) {
        return 1 / Math.sqrt(x + s * s);
    }
}
