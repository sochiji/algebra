package tester;

import matrix.Matrix;

public class Tester1 {
	public static void main(String[] args) {
		Matrix mat1 = Matrix.matrixFromInput(4, 4);
		System.out.println(mat1);
		System.out.println(mat1.getRowLadder());
	}
}
