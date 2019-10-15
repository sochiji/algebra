package matrix;

import java.io.Serializable;
import java.util.Scanner;

import exception.MatrixIllegalOperationException;
import exception.MatrixIndexOutException;
import exception.MatrixNotSquareException;
import exception.MatrixWrongSizeException;
import number.Fraction;
import util.GCD;

/**
 * 这个类使用二维数组来顺序存储矩阵，并定义了一系列的成员方法来生成矩阵和对矩阵进行运算。
 * <p>
 * 提供了静态方法{@link #matrixFromInput(int, int)}和{@link #matrixFromString(int, int, String)}从标准输入流或给定的字符串中解析矩阵。
 * 有些方法的返回值类型为{@link Fraction}，是{@code algebra.number.Fraction}中的类，用来存储有理数。
 * <p>
 * {@code Matrix}被{@code algebra}包中的其它类依赖。
 * 
 * @see Fraction
 * @author Sochiji
 */
public final class Matrix implements Cloneable, Serializable {

    private static final long serialVersionUID = 4795541498067910217L;

    /**
     * 生成以{@code size}为大小的单位方阵。
     * 
     * @param size 单位方阵的大小
     * @return 单位方阵
     * @see {@link #getE(int,Fraction)}
     */
    public static Matrix getE(int size) {
        return getE(size, new Fraction(1));
    }

    /**
     * 生成单位方阵的{@code lambda}倍矩阵。
     * 
     * @param size   单位方阵的大小
     * @param lambda 返回值相对于单位方阵的倍数
     * @return 单位方阵的{@code lambda}倍
     * @see {@link #getE(int)}
     */
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

    /**
     * 生成所有元皆为零元方阵
     * 
     * @param size 要生成的矩阵的大小
     * @return 一个只含零元的方阵
     * @see {@link #getZero(int, int)}
     */
    public static Matrix getZero(int size) {
        return getZero(size, size);
    }

    /**
     * 生成所有元皆为零元的矩阵
     * 
     * @param rows 要生成的矩阵的行数
     * @param cols 要生成的矩阵的列数
     * @return 一个只含零元的矩阵
     * @see #getZero(int)
     */
    public static Matrix getZero(int rows, int cols) {
        Fraction[][] ret = new Fraction[rows][cols];
        Fraction z = new Fraction(0);
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                ret[i][j] = z;
        return new Matrix(ret);

    }

    /**
     * 从标准输入流输入一个矩阵，这个矩阵由许多{@link Fraction}对象组成。
     * 依次输入每个{@linkplain Fraction}的值，中间用空格隔开。
     * 
     * @param r 要获取的矩阵的行数
     * @param c 要获取的矩阵的列数
     * @return 输入的矩阵
     * @throws MatrixWrongSizeException 当{@code r==0}或{@code c==0}
     */
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

    /**
     * 从字符串{@code s}解析出{@code r}行{@code c}列的矩阵
     * 
     * @param r 要解析的矩阵的行数
     * @param c 要解析的矩阵的列数
     * @param s 被解析的字符串
     * @return 解析出的字符串
     * @throws NumberFormatException            数字格式有误
     * @throws java.util.NoSuchElementException 字符串中的有理数个数不够
     */

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

    /**
     * 用一个{@link Fraction}类的二元数组生成一个矩阵
     * 
     * @param fracNum 矩阵的内容
     * @see {@link #Matrix(long[][])}
     * @see {@link Fraction#Fraction(long)}
     */
    public Matrix(Fraction[][] fracNum) {
        rows = fracNum.length;
        cols = fracNum[0].length;
        if (rows == 0 || cols == 0)
            throw new MatrixWrongSizeException();
        num = fracNum;
    }

    /**
     * 用一个{@code long}类型的二维数组生成一个矩阵
     * 
     * @param intNum 矩阵的内容
     * @see {@link #Matrix(Fraction[][])}
     */
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

    /**
     * 将{@code this}与{@code o }相加
     * 
     * @param o 要与{@code this}相加的矩阵
     * @return 相加后的结果
     * @throws MatrixWrongSizeException 两个矩阵不同型
     */
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

    /**
     * 把第{@code from + 1}列的{@code rate}倍加到第{@code to + 1}列上
     * 
     * @param from
     * @param to
     * @param rate
     * @throws MatrixIndexOutException
     * @see {@link #addByRow(int, int, Fraction)}
     */

    protected void addByColumn(int from, int to, Fraction rate) throws MatrixIndexOutException {
        if (from > cols - 1 || cols < 0 || to > cols - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= rows - 1; i++)
            num[i][to] = num[i][to].add(num[i][from].mul(rate));
    }

    /**
     * * 把第{@code from + 1}行的{@code rate}倍加到第{@code to + 1}行上
     * 
     * @param from
     * @param to
     * @param rate
     * @throws MatrixIndexOutException
     * @see {@link #addByColumn(int, int, Fraction)}
     */
    protected void addByRow(int from, int to, Fraction rate) throws MatrixIndexOutException {
        if (from > rows - 1 || from < 0 || to > rows - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= cols - 1; i++)
            num[to][i] = num[to][i].add(num[from][i].mul(rate));
    }

    /**
     * 将{@code o}合并到{@code this}的下面
     * 
     * @param o 要在下面合并的矩阵
     * @return 一个矩阵，合并后的结果
     * @see #rightCombine(Matrix)
     */

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

    /**
     * 清空{@code this}
     */
    protected void clear() {
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

    /**
     * 计算方阵的行列式
     * 
     * @return 一个{@code Fraction}对象，表示计算行列式的值。
     * @throws MatrixNotSquareException 当{@code this}的{@code rows}和{@code cols}不相等。
     */
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

    /**
     * 返回{@code cols}的值，即此矩阵的列数。
     * 
     * @return {@code cols}
     */
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

    /**
     * 生成逆矩阵。
     * 
     * @return {@code this}的逆矩阵
     * @throws MatrixIllegalOperationException 当矩阵不可逆。
     */
    public Matrix getInverse() {
        if (!isSquare() || detCal().getA() == 0)
            throw new MatrixIllegalOperationException("Not Inversable.");
        int[] index = new int[cols];
        for (int i = 0; i <= cols - 1; i++)
            index[i] = i + cols + 1;
        return rightCombine(getE(rows)).getRowSimplified().getColumnVector(index);
    }

    /**
     * 生成行阶梯形矩阵。
     * 
     * @return {@code this}的行阶梯形
     */
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

    /**
     * 返回{@code rows}的值，即此矩阵的行数。
     * 
     * @return {@code rows}
     */
    public int getRows() {
        return rows;
    }

    /**
     * 生成行最简形矩阵。
     * 
     * @return {@code this}的行最简形
     */
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

    /**
     * 抽取行号为index的行向量组组成的矩阵。
     * 
     * @param index 可为{@code int}或{@code int[]}类型。
     * @return 行向量组组成的矩阵。
     */
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

    /**
     * 抽取数据部分。
     * 
     * @return 存储数据的{@code Fraction[][]}
     */
    Fraction[][] getValue() {
        return num;
    }

    /**
     * 判断是否为行阶梯形。
     * 
     * @return 是否为行阶梯形
     */
    boolean isRowLadder() {
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

    /**
     * 判断是否为方阵。
     * 
     * @return 是否为方阵
     */
    public boolean isSquare() {
        return rows == cols;
    }

    /**
     * 右乘{@code o}
     * 
     * @param o 要右乘的矩阵
     * @return 计算所得矩阵
     * @throws MatrixWrongSizeException 当{@code this.cols != 。.rows}
     */
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

    /**
     * 生成{@code this}的{@code a}{@code b}余子阵
     * 
     * @param a 要消除的行号
     * @param b 要消除的列号
     * @return 余子阵
     */
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

    /**
     * 将某一列自乘{@code rate}倍
     * 
     * @param to   要自乘的列号
     * @param rate 倍数
     * @throws MatrixIndexOutException 当提供的{@code to}非法
     */
    public void mulByColumn(int to, Fraction rate) throws MatrixIndexOutException {
        if (to > rows - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= rows - 1; i++)
            num[i][to] = num[i][to].mul(rate);
    }

    /**
     * 将某一行自乘{@code rate}倍
     * 
     * @param to   要自乘的行号
     * @param rate 倍数
     * @throws MatrixIndexOutException 当提供的{@code to}非法
     */

    public void mulByRow(int to, Fraction rate) throws MatrixIndexOutException {
        if (to > rows - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= cols - 1; i++)
            num[to][i] = num[to][i].mul(rate);
    }

    /**
     * 以有理数乘矩阵
     * 
     * @param o 要相乘的有理数
     * @return 数乘的结果
     */
    public Matrix numMul(Fraction o) {
        Fraction[][] retn = new Fraction[rows][cols];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                retn[i][j] = num[i][j].mul(o);
        return new Matrix(retn);
    }

    /**
     * 求矩阵的幂
     * 
     * @param n 方幂的次数
     * @return {@code this}的{@code n}次幂
     */
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

    /**
     * 向标准输出流打印矩阵的内容
     */
    public void print() {
        for (int i = 0; i <= rows - 1; i++) {
            for (int j = 0; j <= cols - 1; j++) {
                System.out.print(num[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * 向标准输出流打印矩阵的内容到一行内。
     */
    public void printLinear() {
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++) {
                System.out.print(num[i][j]);
                System.out.print(" ");
            }
    }

    /**
     * 求矩阵的秩。
     * 
     * @return {@code this}的秩
     */
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

    /**
     * 将{@code o}合并到{@code this}的右侧
     * 
     * @param o 要合并的矩阵
     * @return 合并后的结果
     * @throws MatrixWrongSizeException 当{@code this.rows != o.rows}
     */
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

    /**
     * 设置{@code this}的大小，若{@code this}非空则会先清空此矩阵。
     * 
     * @param r 行数
     * @param c 列数
     * @see #clear()
     */
    public void setSize(int r, int c) throws MatrixIllegalOperationException {
        if (num != null)
            clear();
        rows = r;
        cols = c;
    }

    /**
     * 用初等行变换消去矩阵中元素的分母。
     * 
     * @return 处理后的矩阵
     */
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

    /**
     * 矩阵减法。
     * 
     * @param o 减法的操作数。
     * @return {@code this - o}
     * @throws MatrixWrongSizeException 当{@code this}与{@code o}不是同型矩阵。
     */
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

    /**
     * 返回矩阵的字符串形式，包含换行符。
     * <p>
     * {@inheritDoc}
     * 
     * @see #toStringLinear()
     */
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

    /**
     * 返回矩阵的字符串形式，不包含换行符。
     * 
     * @see #toString()
     */

    public String toStringLinear() {
        String ret = "";
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                ret = ret + num[i][j] + " ";
        return ret;
    }

    /**
     * 生成转置矩阵。
     * 
     * @return 转置矩阵
     */
    public Matrix trans() {
        Fraction[][] retn = new Fraction[cols][rows];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                retn[j][i] = num[i][j];
        return new Matrix(retn);
    }

    /**
     * 生辰标准型矩阵
     * 
     * @return 标准型矩阵
     */
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