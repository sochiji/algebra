package exception;

/**
 * AlgebraException
 */
public class AlgebraException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message = "Exception detected!";

    public AlgebraException() {

    }

    public AlgebraException(String m) {
        message = m;
    }

    public void showMessage() {
        System.out.println(message);
    }

}