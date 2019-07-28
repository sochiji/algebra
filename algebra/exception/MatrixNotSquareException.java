package exception;

/**
 * MatrixNotSquareException
 */
public class MatrixNotSquareException extends MatrixWrongSizeException {
    private static final long serialVersionUID = 1L;

    public MatrixNotSquareException(String s) {
        super(s);
    }

    public MatrixNotSquareException() {

    }

}