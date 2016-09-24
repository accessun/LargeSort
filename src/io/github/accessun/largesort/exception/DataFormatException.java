package io.github.accessun.largesort.exception;

public class DataFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    public DataFormatException() {
        super();
    }

    public DataFormatException(String message) {
        super(message);
    }

    public DataFormatException(Throwable cause) {
        super(cause);
    }

    public DataFormatException(String message, Throwable cause) {
        super(message, cause);
    }

}
