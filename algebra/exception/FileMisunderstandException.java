package exception;

/**
 * FileMisunderstandException
 */
public class FileMisunderstandException extends FileException {
    private static final long serialVersionUID = 1L;
    String fileName;

    FileMisunderstandException(String filename) {
        super(filename);
    }

    @Override
    public void showMessage() {
        System.out.println("File " + fileName + " is beyond description.");
    }
}