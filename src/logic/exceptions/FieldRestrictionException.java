package main.java.src.logic.exceptions;

/**
 * thrown in situations when field value does not suit specified restrictions
 */
public class FieldRestrictionException extends NumberFormatException {
    public FieldRestrictionException() {
        this("value is out of restriction bound");
    }

    public FieldRestrictionException(String s) {
        super(s);
    }
}
