package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import matrix.Matrix;
import util.Container;

/**
 * MatrixManager
 */
public class MatrixManager {
    public static final String ENDNAME = ".matdat";
    private ArrayList<MatrixStorage> rec;
    private String cwd;

    public MatrixManager(String cwd, Container<String[]> s) {
        this.cwd = cwd;
        MatrixStorage.ENDNAME = ENDNAME;
        s.data = reloadAll();
    }

    public String[] getNameList() {
        int len = getSize();
        String ret[] = new String[getSize()];
        for (int i = 0; i <= len - 1; i++)
            ret[i] = rec.get(i).getName();
        return ret;
    }

    public String[] reloadAll() {
        rec = new ArrayList<MatrixStorage>();
        File currentLocation = new File(cwd);
        String[] fileNameList = currentLocation.list();
        ArrayList<String> failList = new ArrayList<String>();
        for (String x : fileNameList)
            if (x.endsWith(ENDNAME))
                try {
                    MatrixStorage gotten = MatrixStorage.readFromFile(x, cwd);
                    if (gotten != null)
                        rec.add(gotten);
                    else
                        failList.add(x);
                } catch (FileNotFoundException e) {
                    failList.add(x);
                }
        return failList.toArray(new String[failList.size()]);
    }

    public boolean saveToFile(int index) throws IOException {
        return rec.get(index).saveToFile(cwd);
    }

    public boolean saveToFile(MatrixStorage e) throws IOException {
        return e.saveToFile(cwd);
    }

    public void createNewFromInput(int r, int c, String matrixName) {
        rec.add(new MatrixStorage(Matrix.matrixFromInput(r, c), matrixName));
    }

    public boolean saveAll() throws IOException {
        boolean ret = true;
        for (MatrixStorage e : rec) {
            if (!saveToFile(e)) {
                ret = false;
                // System.out.println("Failed to save " + e.getName());
            }
        }
        return ret;
    }

    public void reload(int index) throws FileNotFoundException {
        String matrixName = rec.get(index).getName();
        MatrixStorage.readFromFile(matrixName + ENDNAME, cwd);
    }

    public Matrix getMatrix(int index) {
        return rec.get(index).getMat();
    }

    public void add(Matrix mat, String s) {
        add(new MatrixStorage(mat, s));
    }

    public void add(MatrixStorage matstorage) {
        rec.add(matstorage);
    }

    public MatrixStorage[] getList() {
        int len = rec.size();
        MatrixStorage[] ret = new MatrixStorage[len];
        rec.toArray(ret);
        return ret;
    }

    public int getSize() {
        return rec.size();
    }

    public boolean delete(int index) {
        File of = new File(cwd + "\\" + rec.get(index).getName() + ENDNAME);
        rec.remove(index);
        if (of.exists())
            return of.delete();
        return true;
    }

    public int[] checkNotSaved() {
        String[] currentLocation = new File(cwd).list();
        MatrixStorage[] tmp = rec.toArray(new MatrixStorage[rec.size()]);
        int len = tmp.length;
        for (String x : currentLocation) {
            if (x.endsWith(ENDNAME))
                for (int i = 0; i <= len - 1; i++)
                    if (tmp[i] != null && x.equals(tmp[i].getName() + ENDNAME)) {
                        tmp[i] = null;
                        break;
                    }
        }
        int[] ret = new int[len];
        int pos = 0;
        for (int i = 0; i <= len - 1; i++)
            if (tmp[i] != null) {
                ret[pos] = i;
                pos++;
            }
        return ret;
    }
}