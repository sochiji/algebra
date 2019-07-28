package exception;

/**
 * FileException
 */
public abstract class FileException extends Exception {
    private static final long serialVersionUID = 1L;
    protected String fileName;

    public abstract void showMessage();

    FileException(String name) {
        fileName = name;
    }
}