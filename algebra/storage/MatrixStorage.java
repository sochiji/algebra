package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

import matrix.Matrix;

/**
 * MatrixStorage
 */
public class MatrixStorage implements Serializable {
    private static final long serialVersionUID = -4177037293939973239L;
    private String matrixName;
    private Matrix mat;

    static String ENDNAME;

    public MatrixStorage(Matrix m, String s) {
        mat = m;
        matrixName = s;
    }

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

    public boolean saveToFile(String cwd) throws FileNotFoundException, IOException {
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
     * @return the name
     */
    public String getName() {
        return matrixName;
    }

    /**
     * @return the mat
     */
    public Matrix getMat() {
        return mat;
    }
}