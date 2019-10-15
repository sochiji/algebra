package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

import matrix.Matrix;

/**
 * 这个类（有名矩阵）把无名矩阵({@link Matrix})和矩阵名封装到一起，并定义了一系列成员方法提供了对成员数据的访问、修改方式，以及对磁盘文件的读写方式。
 * <p>
 * 这个类被矩阵管理({@link MatrixStorage})依赖。
 */
public class MatrixStorage implements Serializable {
    private static final long serialVersionUID = -4177037293939973239L;
    private String matrixName;
    private Matrix mat;

    static String ENDNAME;

    /**
     * 构造方法，将提供的无名矩阵和矩阵名封装为一个有名矩阵对象。
     * 
     * @param m 无名矩阵
     * @param s 矩阵名
     */
    public MatrixStorage(Matrix m, String s) {
        mat = m;
        matrixName = s;
    }

    /**
     * 这个静态方法用于以给定的文件名和路径创建一个有名矩阵对象。
     * <p>
     * 文件名是包含扩展名的。
     * 
     * @param fileName 磁盘上的文件名
     * @param cwd      路径
     * @return 一个有名矩阵对象
     * @throws FileNotFoundException 当找不到指定的文件时
     */
    public static MatrixStorage readFromFile(String fileName, String cwd) throws FileNotFoundException {
        File inFile = null;
        inFile = new File(cwd + "\\" + fileName);
        Scanner sc;
        int pos = fileName.lastIndexOf('.');
        String matrixName = fileName.substring(0, pos);
        String s = "";
        sc = new Scanner(inFile);
        try {
            int r = sc.nextInt();
            int c = sc.nextInt();
            while (sc.hasNextLine())
                s += sc.nextLine() + " ";
            sc.close();
            return new MatrixStorage(Matrix.matrixFromString(r, c, s), matrixName);
        } catch (RuntimeException e) {
            sc.close();
            return null;
        }
    }

    /**
     * 保存有名矩阵到指定路径。
     * 
     * @param cwd 指定路径
     * @return 是否成功
     * @throws IOException 若不成功
     */
    public boolean saveToFile(String cwd) throws IOException {
        PrintWriter pw;
        File outFile = new File(cwd + "\\" + matrixName + MatrixManager.ENDNAME);
        if (!outFile.exists())
            outFile.createNewFile();
        if (!outFile.canWrite()) {
            // System.out.println("Cannot write file " + matrixName +MatrixManager.ENDNAME);
            return false;
        } else {
            pw = new PrintWriter(outFile);
            pw.println(mat.getRows() + " " + mat.getCols());
            pw.println(getMat());
            pw.close();
            return true;
        }
    }

    /**
     * 获取矩阵名(不含保存为文件的扩展名)。
     * 
     * @return 矩阵名
     */
    public String getName() {
        return matrixName;
    }

    /**
     * 返回{@code this}的无名矩阵({@link Matrix})对象。
     * 
     * @return 一个{@link Matrix}对象
     */
    public Matrix getMat() {
        return mat;
    }

    /**
     * 从给定的路径删除矩阵文件。
     * 
     * @param cwd 路径
     * @return 是否成功
     */
    public boolean deleteFromFile(String cwd) {
        boolean ret = true;
        File outFile = new File(cwd + "\\" + matrixName + MatrixManager.ENDNAME);
        if (outFile.exists()) {
            if (!outFile.delete())
                ret = false;
        }
        return ret;
    }
}