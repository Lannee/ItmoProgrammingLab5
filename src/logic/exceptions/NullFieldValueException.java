package main.java.src.logic.exceptions;

/**
 * thrown in situation when field value is null
 */
public class NullFieldValueException extends NumberFormatException {
    public NullFieldValueException() {
        this("Field value cannot be null");
    }

    public NullFieldValueException(String s) {
        super(s);
    }
}
