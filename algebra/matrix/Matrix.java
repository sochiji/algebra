package matrix;

import java.io.Serializable;
import java.util.Scanner;

import exception.MatrixIllegalOperationException;
import exception.MatrixIndexOutException;
import exception.MatrixNotSquareException;
import exception.MatrixWrongSizeException;
import number.Fraction;
import util.GCD;

public final class Matrix implements Cloneable, Serializable {

    private static final long serialVersionUID = 4795541498067910217L;

    public static Matrix getE(int size) {
        return getE(size, new Fraction(1));
    }

    public static Matrix getE(int size, Fraction lambda) {
        Fraction[][] ret = new Fraction[size][size];
        for (int i = 0; i <= size - 1; i++)
            for (int j = 0; j <= size - 1; j++)
                if (i == j)
                    ret[i][j] = lambda;
                else
                    ret[i][j] = new Fraction(0);
        return new Matrix(ret);
    }

    public static Matrix getZero(int size) {
        Fraction[][] ret = new Fraction[size][size];
        Fraction z = new Fraction(0);
        for (int i = 0; i <= size - 1; i++)
            for (int j = 0; j <= size - 1; j++)
                ret[i][j] = z;
        return new Matrix(ret);
    }

    public static Matrix matrixFromInput(int r, int c) throws MatrixWrongSizeException {
        if (r == 0 || c == 0)
            throw new MatrixWrongSizeException();
        System.out.println("Input a Matrix for " + r + " rows, " + c + " column s: ");
        Fraction[][] num = new Fraction[r][c];
        for (int i = 0; i <= r - 1; i++)
            for (int j = 0; j <= c - 1; j++)
                num[i][j] = Fraction.fractionFromInput();
        return new Matrix(num);
    }

    public static Matrix matrixFromString(int r, int c, String s) {
        Scanner sc = new Scanner(s);
        Fraction[][] num = new Fraction[r][c];
        for (int i = 0; i <= r - 1; i++)
            for (int j = 0; j <= c - 1; j++)
                num[i][j] = Fraction.valueOf(sc.next());
        sc.close();
        return new Matrix(num);
    }

    private int rows = -1;

    private int cols = -1;

    private Fraction[][] num;

    public Matrix(Fraction[][] fracNum) {
        rows = fracNum.length;
        cols = fracNum[0].length;
        if (rows == 0 || cols == 0)
            throw new MatrixWrongSizeException();
        num = fracNum;
    }

    public Matrix(long[][] intNum) {
        rows = intNum.length;
        cols = intNum[0].length;
        if (rows == 0 || cols == 0)
            throw new MatrixWrongSizeException();
        num = new Fraction[rows][cols];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                num[i][j] = new Fraction(intNum[i][j]);
    }

    public Matrix add(Matrix o) throws MatrixWrongSizeException {
        if (rows == o.rows && cols == o.cols) {
            Fraction[][] oFrac = o.getValue();
            Fraction[][] retFrac = new Fraction[rows][cols];
            for (int i = 0; i <= rows - 1; i++)
                for (int j = 0; j <= cols - 1; j++)
                    retFrac[i][j] = num[i][j].add(oFrac[i][j]);
            return new Matrix(retFrac);
        } else {
            throw new MatrixWrongSizeException();
        }
    }

    public void addByColumn(int from, int to, Fraction rate) throws MatrixIndexOutException {
        if (from > cols - 1 || cols < 0 || to > cols - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= rows - 1; i++)
            num[i][to] = num[i][to].add(num[i][from].mul(rate));
    }

    public void addByRow(int from, int to, Fraction rate) throws MatrixIndexOutException {
        if (from > rows - 1 || from < 0 || to > rows - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= cols - 1; i++)
            num[to][i] = num[to][i].add(num[from][i].mul(rate));
    }

    public Matrix belowCombine(Matrix o) {
        if (cols != o.cols)
            throw new MatrixWrongSizeException();
        Fraction[][] cl = o.getValue();
        Fraction[][] ret = new Fraction[rows + o.rows][cols];
        for (int j = 0; j <= cols - 1; j++) {
            for (int i = 0; i <= rows - 1; i++)
                ret[i][j] = num[i][j];
            for (int i = 0; i <= o.rows - 1; i++)
                ret[i + rows][j] = cl[i][j];
        }
        return new Matrix(ret);
    }

    public void clear() {
        rows = -1;
        cols = -1;
        num = null;
    }

    @Override
    protected Matrix clone() {// Deep Clone Needed
        Fraction[][] ret = new Fraction[rows][cols];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                ret[i][j] = num[i][j];
        return new Matrix(ret);
    }

    public Fraction detCal() throws MatrixNotSquareException {
        if (rows != cols)
            throw new MatrixNotSquareException();
        if (rows == 1)
            return num[0][0];
        if (rows == 2)
            return num[0][0].mul(num[1][1]).sub(num[0][1].mul(num[1][0]));
        int posR = 0, posC = 0;
        Matrix tmp = clone();
        Fraction[][] cl = tmp.getValue();
        do {
            posR = posC;// Square Only
            if (cl[posR][posC].getA() == 0) {// 主对角线上有元素为0
                do {
                    posR++;
                } while (posR <= cl.length - 1 && cl[posR][posC].getA() == 0);// 寻找此列中非0元素
                if (posR > cl.length - 1)
                    return new Fraction(0);// 找不到非0元素 则此列全为0
                else
                    tmp.addByRow(posR, posC, new Fraction(1));// 将非0元素行加到0元素对角线所在行
            }
            posR = posC;// Square Only
            do {
                posR++;
                Fraction rate = cl[posR][posC].div(cl[posC][posC]);
                tmp.addByRow(posC, posR, rate.getMinus());
                // System.out.println();
                // new Matrix(cl).print();// Debug
            } while (posR < rows - 1);
            posC++;
        } while (posC < cols - 1);
        posR = 0;
        posC = 0;
        Fraction ret = new Fraction(1);
        for (int i = 0; i <= rows - 1; i++)
            ret = ret.mul(cl[i][i]);
        return ret;
    }

    public int getCols() {
        return cols;
    }

    public Matrix getColumnVector(int... index) {
        Fraction[][] ret = new Fraction[rows][index.length];
        int count = 0;
        for (int j : index) {
            j--;
            if (j < 0 || j >= cols)
                throw new MatrixIndexOutException();
            for (int i = 0; i <= rows - 1; i++)
                ret[i][count] = num[i][j];
            count++;
        }
        return new Matrix(ret);
    }

    public Matrix getInverse() {
        if (!isSquare() || detCal().getA() == 0)
            throw new MatrixIllegalOperationException("Not Inversable.");
        int[] index = new int[cols];
        for (int i = 0; i <= cols - 1; i++)
            index[i] = i + cols + 1;
        return rightCombine(getE(rows)).getRowSimplified().getColumnVector(index);
    }

    public Matrix getRowLadder() {
        int posR;
        int posC;
        int nonZeroRow = 0;
        int nonZeroCol = 0;
        Matrix ret = clone();
        Fraction[][] cl = ret.getValue();
        do {
            posR = nonZeroRow;
            posC = nonZeroCol;
            while (posR <= rows - 1 && cl[posR][posC].getA() == 0)// 寻找列中的首个非零元素
                posR++;
            if (posR <= rows - 1) {
                if (posR != nonZeroRow)// 非零行的第一元素为0
                    ret.addByRow(posR, nonZeroRow, new Fraction(1));
                posR = nonZeroRow;
                do {
                    posR++;
                    if (posR == rows)
                        break;
                    Fraction rate = cl[posR][posC].div(cl[nonZeroRow][nonZeroCol]);
                    ret.addByRow(nonZeroRow, posR, rate.getMinus());
                    // System.out.println();
                    // new Matrix(cl).print();// Debug
                } while (posR < rows - 1);
                nonZeroRow++;
            }
            nonZeroCol++;
        } while (nonZeroCol <= cols - 1);
        return ret;
    }

    public int getRows() {
        return rows;
    }

    public Matrix getRowSimplified() {
        Matrix ret;
        ret = getRowLadder();
        Fraction[][] cl = ret.getValue();
        int posR = rows - 1;
        do {
            int posC = cols;
            for (int i = 0; i <= cols - 1; i++)
                if (cl[posR][i].getA() != 0) {
                    posC = i;
                    break;
                }
            if (posC != cols) {
                Fraction rate = cl[posR][posC].getInverse();
                ret.mulByRow(posR, rate);
                for (int i = posR - 1; i >= 0; i--) {
                    rate = cl[i][posC].div(cl[posR][posC]);
                    ret.addByRow(posR, i, rate.getMinus());
                    // System.out.println(ret);// Debug
                }
            }
            posR--;
        } while (posR >= 0);
        return ret;
    }

    public Matrix getRowVector(int... index) {
        Fraction[][] ret = new Fraction[index.length][cols];
        int count = 0;
        for (int i : index) {
            i--;
            if (i < 0 || i >= rows)
                throw new MatrixIndexOutException();
            for (int j = 0; j <= cols - 1; j++)
                ret[count][j] = num[i][j];
            count++;
        }
        return new Matrix(ret);
    }

    public Fraction[][] getValue() {
        return num;
    }

    protected boolean isRowLadder() {
        if (rows == 1 || cols == 1)
            return true;
        int max = -2;
        for (int i = 0; i <= rows - 1; i++) {
            int c = -1;
            for (int j = 0; j <= cols - 1; j++) {
                if (num[i][j].getA() != 0) {
                    c = j;
                    break;
                }
                c = cols;
            }
            if (c > max)
                max = c;
            else if (c != cols)
                return false;
        }
        return true;
    }

    public boolean isSquare() {
        return rows == cols;
    }

    public Matrix matMul(Matrix o) throws MatrixWrongSizeException {
        if (cols != o.rows)
            throw new MatrixWrongSizeException();
        Fraction[][] on = o.getValue();
        Fraction[][] retn = new Fraction[rows][o.cols];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= o.cols - 1; j++) {
                retn[i][j] = new Fraction(0, 1);
                for (int k = 0; k <= cols - 1; k++) {
                    retn[i][j] = retn[i][j].add(num[i][k].mul(on[k][j]));
                }
            }
        return new Matrix(retn);
    }

    public Matrix minorDet(int a, int b) {
        Fraction[][] retn = new Fraction[rows - 1][cols - 1];
        int rowsx = 0;
        for (int i = 0; i <= rows - 1; i++) {
            if (i == a - 1)
                continue;
            int colsx = 0;
            for (int j = 0; j <= cols - 1; j++) {
                if (j == b - 1)
                    continue;
                retn[rowsx][colsx] = num[i][j];
                colsx++;
            }
            rowsx++;
        }
        return new Matrix(retn);
    }

    public void mulByColumn(int to, Fraction rate) throws MatrixIndexOutException {
        if (to > rows - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= rows - 1; i++)
            num[i][to] = num[i][to].mul(rate);
    }

    public void mulByRow(int to, Fraction rate) throws MatrixIndexOutException {
        if (to > rows - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= cols - 1; i++)
            num[to][i] = num[to][i].mul(rate);
    }

    public Matrix numMul(Fraction o) {
        Fraction[][] retn = new Fraction[rows][cols];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                retn[i][j] = num[i][j].mul(o);
        return new Matrix(retn);
    }

    public Matrix power(int n) {// Minus parameter needed
        if (!isSquare())
            throw new MatrixNotSquareException();
        Matrix ret = this;
        if (n == 1)
            return this;
        if (n == 0)
            return getE(rows);
        for (int i = 1; i <= n; i++)
            ret = ret.matMul(this);// Improve Needed
        return ret;
    }

    public void print() {
        for (int i = 0; i <= rows - 1; i++) {
            for (int j = 0; j <= cols - 1; j++) {
                System.out.print(num[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void printLinear() {
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++) {
                System.out.print(num[i][j]);
                System.out.print(" ");
            }
    }

    public long rank() {
        Matrix ret;
        ret = getRowLadder();
        Fraction[][] cl = ret.getValue();
        int posR = rows - 1;
        int emptyRows = 0;
        do {
            boolean isEmpty = true;
            for (int i = 0; i <= cols - 1; i++)
                if (cl[posR][i].getA() != 0) {
                    isEmpty = false;
                    break;
                }
            if (isEmpty)
                emptyRows++;
            posR--;
        } while (posR > 0);
        return Math.min(cols, rows - emptyRows);
    }

    public Matrix rightCombine(Matrix o) {
        if (rows != o.rows)
            throw new MatrixWrongSizeException();
        Fraction[][] cl = o.getValue();
        Fraction[][] ret = new Fraction[rows][cols + o.cols];
        for (int i = 0; i <= rows - 1; i++) {
            for (int j = 0; j <= cols - 1; j++)
                ret[i][j] = num[i][j];
            for (int j = 0; j <= o.cols - 1; j++)
                ret[i][j + rows] = cl[i][j];
        }
        return new Matrix(ret);
    }

    public void setSize(int r, int c) throws MatrixIllegalOperationException {
        if (num == null) {
            rows = r;
            cols = c;
        } else
            throw new MatrixIllegalOperationException("This matrix is not empty, please clear it first.");
    }

    public Matrix simplifyByRow() {
        Matrix ret = clone();
        Fraction[][] cl = ret.getValue();
        int r = ret.rows;
        int c = ret.cols;
        for (int i = 0; i <= r - 1; i++) {
            long maxf = 1;
            for (int j = 0; j <= c - 1; j++)
                maxf = GCD.minCommonMultiple(maxf, cl[i][j].getB());
            Fraction f = new Fraction(maxf);
            for (int j = 0; j <= c - 1; j++)
                cl[i][j] = cl[i][j].mul(f);
        }
        return ret;
    }

    public Matrix sub(Matrix o) {
        if (rows == o.rows && cols == o.cols) {
            Fraction[][] oFrac = o.getValue();
            Fraction[][] retFrac = new Fraction[rows][cols];
            for (int i = 0; i <= rows - 1; i++)
                for (int j = 0; j <= cols - 1; j++)
                    retFrac[i][j] = num[i][j].sub(oFrac[i][j]);
            return new Matrix(retFrac);
        } else {
            throw new MatrixWrongSizeException();
        }
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i <= rows - 1; i++) {
            for (int j = 0; j <= cols - 1; j++)
                ret = ret + num[i][j] + " ";
            if (i != rows - 1)
                ret = ret + "\n";
        }
        return ret;
    }

    public String toStringLinear() {
        String ret = "";
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                ret = ret + num[i][j] + " ";
        return ret;
    }

    public Matrix trans() {
        Fraction[][] retn = new Fraction[cols][rows];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                retn[j][i] = num[i][j];
        return new Matrix(retn);
    }

    public Matrix getStandard() {
        Matrix ret = getRowSimplified();
        Fraction[][] val = ret.getValue();
        int rows = ret.getRows();
        int cols = ret.getCols();
        int posR = 0;
        do {
            int posC = 0;
            while (posC < cols)
                if (val[posR][posC].getA() == 1)
                    break;
                else
                    posC++;
            posC++;
            while (posC < cols) {
                if (val[posR][posC].getA() != 0)
                    val[posR][posC] = new Fraction(0);
                posC++;
            }
            posR++;
        } while (posR < rows);
        return ret;
    }
}