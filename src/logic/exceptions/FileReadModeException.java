package main.java.src.logic.exceptions;

/**
 * thrown in situations when file cannot be read
 */
public class FileReadModeException extends Exception {

    public FileReadModeException() {
        this("Cannot read the file");
    }

    public FileReadModeException(String message) {
        super(message);
    }

    public FileReadModeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileReadModeException(Throwable cause) {
        super(cause);
    }
}
