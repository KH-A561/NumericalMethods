package ru.omsu.imit.matrix;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class Matrix implements Cloneable {
    public static final int SCALE = 5;
    public double[][] array;
    public int size;

    public Matrix(double[][] array, int size) {
        this.array = array;
        this.size = size;
    }

    public Matrix getInverse() {
        return new Matrix(MatrixUtils.inverse(new Array2DRowRealMatrix(this.array)).getData(), this.size);
    }

    public double getDeterminant() {
        return new LUDecomposition(new Array2DRowRealMatrix(this.array)).getDeterminant();
    }

    public Object clone() {
        double[][] result = new double[size][];
        for (int r = 0; r < size; r++) {
            result[r] = array[r].clone();
        }
        int s = size;
        return new Matrix(result, s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix)) return false;
        Matrix matrix = (Matrix) o;
        if (size == matrix.size) {
            for (int i = 0; i < array.length; i++) {
                double[] doubles = array[i];
                for (int j = 0; j < array[i].length; j++) {
                    if (BigDecimal.valueOf(array[i][j]).setScale(SCALE, BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.valueOf(matrix.array[i][j]).setScale(SCALE, BigDecimal.ROUND_HALF_UP)) != 0) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }
}
