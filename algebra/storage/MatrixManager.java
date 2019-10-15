package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import matrix.Matrix;
import util.Container;

/**
 * 这个类（矩阵管理）是{@link MatrixStorage}类的数据集合的管理器类，内含{@code ArrayList<MatrixStorage>}类型的字段以存储{@link MatrixStorage}类的数据集合，并定义了一系列成员方法提供了对成员数据的访问、修改方式。
 * <p>
 * 一个对象与一个数据存储路径挂钩，负责管理该路径下所有的以.matdat为扩展名的矩阵数据文件。
 * <p>
 * 这个类依赖{@link Matrix}和{@link MatrixStorage}及其依赖的类。
 * 
 * @see Matrix 矩阵类
 * @see MatrixStorage 封装了矩阵名的矩阵类
 */
public class MatrixManager {
    public static String ENDNAME = ".matdat";
    private ArrayList<MatrixStorage> rec;
    private String cwd;

    /**
     * 创建MatrixStorage类，接管指定的工作路径，读取该路径下全部的矩阵数据文件，并将读取异常的一组文件名写入容器。
     * 
     * @param cwd 工作路径，建议使用相对路径。
     * @param s   用以存储读取异常的文件名数组的容器。
     */
    public MatrixManager(String cwd, Container<String[]> s) {
        this.cwd = cwd;
        MatrixStorage.ENDNAME = ENDNAME;
        s.data = reloadAll();
    }

    /**
     * 创建MatrixStorage类，接管指定的工作路径，读取该路径下全部的矩阵数据文件，并将读取异常的一组文件名写入容器。
     * 
     * @param cwd     工作路径，建议使用相对路径。
     * @param s       用以存储读取异常的文件名数组的容器。
     * @param endname 指定矩阵数据文件的扩展名，包含点号
     */
    public MatrixManager(String cwd, Container<String[]> s, String endname) {
        ENDNAME = endname;
        this.cwd = cwd;
        MatrixStorage.ENDNAME = ENDNAME;
        s.data = reloadAll();
    }

    /**
     * 将存储的全部{@link MatrixStorage}对象的矩阵名{@code matrixName}列表返回。
     * 
     * @return 矩阵名列表
     */
    public String[] getNameList() {
        int len = getSize();
        String ret[] = new String[getSize()];
        for (int i = 0; i <= len - 1; i++)
            ret[i] = rec.get(i).getName();
        return ret;
    }

    /**
     * 从文件存储目录重新加载所有矩阵文件，此操作会丢失内存中没有保存到磁盘上的矩阵数据，返回读取失败的矩阵名列表。
     * 
     * @return 读取失败的矩阵名列表。
     */
    public String[] reloadAll() {
        rec = new ArrayList<MatrixStorage>();
        File currentLocation = new File(cwd);
        if (!currentLocation.isDirectory())
            currentLocation.mkdir();
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

    /**
     * 尝试将索引为{@code index}的矩阵保存到磁盘，返回是否成功。
     * 
     * @param index 索引
     * @return 是否成功
     * @throws IOException 若不成功
     */
    public boolean saveToFile(int index) throws IOException {
        return rec.get(index).saveToFile(cwd);
    }

    /**
     * 指定一个{@link MatrixStorage}对象存入磁盘，返回是否成功。
     * <p>
     * 此操作不会将{@code e}存入数据字段
     * 
     * @param e 要保存的矩阵
     * @return 是否成功
     * @throws IOException 若不成功
     */
    public boolean saveToFile(MatrixStorage e) throws IOException {
        return e.saveToFile(cwd);
    }

    /**
     * 从标准输入流输入指定大小的矩阵，并以给定的矩阵名保存到数据字段。
     * 
     * @param r          行数
     * @param c          列数
     * @param matrixName 矩阵名
     */
    public void createNewFromInput(int r, int c, String matrixName) {
        rec.add(new MatrixStorage(Matrix.matrixFromInput(r, c), matrixName));
    }

    /**
     * 将数据字段的矩阵全部存入磁盘，返回是否成功。
     * 
     * @return 是否成功。
     * @throws IOException 若不成功
     */
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

    /**
     * 从磁盘重新加载索引为{@code index}的同名矩阵
     * 
     * @param index 索引
     * @throws FileNotFoundException 找不到文件
     */
    public void reload(int index) throws FileNotFoundException {
        String matrixName = rec.get(index).getName();
        MatrixStorage.readFromFile(matrixName + ENDNAME, cwd);
    }

    /**
     * 返回索引为{@code index}的矩阵({@link Matrix}类型)
     * 
     * @param index 索引
     * @return 一个矩阵，不含矩阵名(即{@link Matrix}类型)
     */
    public Matrix getMatrix(int index) {
        return rec.get(index).getMat();
    }

    /**
     * 向数据字段的末尾加入一个给定矩阵名的新矩阵({@link Matrix}类)
     * 
     * @param mat 矩阵，{@link Matrix}类
     * @param s   矩阵名
     */
    public void add(Matrix mat, String s) {
        add(new MatrixStorage(mat, s));
    }

    /**
     * 向数据字段的末尾加入一个新矩阵({@link MatrixStorage}类)
     * 
     * @param mat 矩阵，{@link MatrixStorage}类
     */
    public void add(MatrixStorage matstorage) {
        rec.add(matstorage);
    }

    /**
     * 返回数据字段中{@link MatrixStorage}列表
     * 
     * @return 矩阵列表
     */
    public MatrixStorage[] getList() {
        int len = rec.size();
        MatrixStorage[] ret = new MatrixStorage[len];
        rec.toArray(ret);
        return ret;
    }

    /**
     * 返回已经存储的矩阵的数量
     * 
     * @return 已经存储的矩阵的数量
     */
    public int getSize() {
        return rec.size();
    }

    /**
     * 从数据字段删除索引为{@code index}的矩阵
     * 
     * @param index 要删除的矩阵的索引
     * @return 是否成功
     */
    public boolean delete(int index) {
        File of = new File(cwd + "\\" + rec.get(index).getName() + ENDNAME);
        rec.remove(index);
        if (of.exists())
            return of.delete();
        return true;
    }

    /**
     * 检查数据字段中是否有磁盘上不存在其对应文件的矩阵
     * <p>
     * 不会检查内容是否相同
     * 
     * @return 磁盘上不存在其文件的矩阵的索引的列表
     */
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

    /**
     * 返回索引为{@code index}的矩阵({@link MatrixStogare}类型)
     * 
     * @param index 索引
     * @return 一个矩阵，包含矩阵名(即{@link Matrix}类型)
     */
    public MatrixStorage get(int index) {
        return rec.get(index);
    }

    /**
     * 以给定的矩阵替换数据字段中指定索引{@code index}的内容
     * 
     * @param index 索引
     * @param mat   替换成的矩阵，包含矩阵名(即{@link MatrixStorage}类型)
     * @throws IOException
     */
    public void changeMatrix(int index, MatrixStorage mat) throws IOException {
        rec.get(index).deleteFromFile(cwd);
        rec.set(index, mat);
        saveToFile(index);
    }

    /**
     * 从磁盘和数据字段删除索引为{@code index}的矩阵
     * 
     * @param index 索引
     */
    public void deleteFromFile(int index) {
        rec.get(index).deleteFromFile(cwd);
        rec.remove(index);
    }
}