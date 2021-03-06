package ru.omsu.imit.mgen;

import org.junit.Test;
import ru.omsu.imit.matrix.GaussSeidelAlgorithm;
import ru.omsu.imit.matrix.Matrix;
import ru.omsu.imit.matrix.Solver;
import ru.omsu.imit.matrix.TestingUtils;

import java.util.Map;

public class GenTest
{

	private int N = 10;
	private double ALPHA = 1.0;
	private double BETA  = 10.;

	@Test
	public void GenTest()
	{
		int n = N;
		double alpha = ALPHA;
		double beta  = BETA;

		double[][] a = new double[n][n];
		for (int i = 0; i < n; i++)	a[i] = new double[n];

		double[][] a_inv = new double[n][n];
		for (int i = 0; i < n; i++)	a_inv[i] = new double[n];

		Gen g = new Gen();
        do {
            	g.mygen ( a, a_inv, n, alpha, beta, 1, 2, 0, 1 ); // симметричная
//            g.mygen(a, a_inv, n, alpha, beta, 1, 2, 1, 1); //проостой структуры
//            	g.mygen ( a, a_inv, n, alpha, beta, 0, 0, 2, 1 ); //жорданова клетка

//		g.print_matr(a,n);
//		g.print_matr(a_inv,n);

            Solver.setMatrix(new Matrix(a, n));
            Matrix matrixCopy = (Matrix) Solver.getMatrix().clone();

            double norm = g.matr_inf_norm(a, n);
            double norm_inv = g.matr_inf_norm(a_inv, n);
            double obusl = norm * norm_inv;
            double[] expectedResults = TestingUtils.generateExpectedResults(n);
			Solver.setRightPart(TestingUtils.multiplyMatrixVector(Solver.getMatrix().size, Solver.getMatrix().array, expectedResults));
            double[] rightPartCopy = Solver.rightPart.clone();
			double[] actualResults = Solver.solve(new GaussSeidelAlgorithm());
//			System.out.println(Arrays.toString(expectedResults));
//			System.out.println(Arrays.toString(actualResults));
            Map<String, Double> analysis = TestingUtils.getAnalysis(matrixCopy, rightPartCopy, expectedResults, actualResults);
            TestingUtils.setParameters(alpha, beta, norm, norm_inv, obusl);
            TestingUtils.printFullAnalysisIntoFile(analysis);
            beta *= 10;
        } while (beta < 10E+15);
	}

}
