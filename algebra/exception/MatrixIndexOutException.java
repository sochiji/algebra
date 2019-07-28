package exception;

/**
 * IndexOutException
 */
public class MatrixIndexOutException extends MatrixException {
    private static final long serialVersionUID = 1L;

    public MatrixIndexOutException(String s) {
        super(s);
    }

    public MatrixIndexOutException() {
    }
}