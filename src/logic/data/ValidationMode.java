package main.java.src.logic.data;

import java.time.Instant;
import java.util.Date;
import java.util.function.BiPredicate;

/**
 * enum, representing different fields restrictions
 */
public enum ValidationMode {
    /**
     * max numeric value
     */
    TOP_NUMERIC_BOUND((u, v) -> {
        if(! (v instanceof Number && v instanceof Comparable)) throw new NumberFormatException();
        return Double.compare(u, ((Number) v).doubleValue()) >= 0;
    }),
    /**
     * min numeric value
     */
    LOW_NUMERIC_BOUND((u, v) -> {
        if(! (v instanceof Number && v instanceof Comparable)) throw new NumberFormatException();
        return Double.compare(u, ((Number) v).doubleValue()) <= 0;
    }),
    /**
     * max string length
     */
    MAX_STRING_LENGTH((u, v) -> {
        if(! (v instanceof CharSequence)) throw new NumberFormatException();
        return ((CharSequence) v).length() <= u;
    }),
    /**
     * min string length
     */
    MIN_STRING_LENGTH((u, v) -> {
        if(! (v instanceof CharSequence)) throw new NumberFormatException();
        return ((CharSequence) v).length() >= u;
    }),
    /**
     * max date value
     */
    MAX_DATE((u, v) -> {
        if(! (v instanceof Date)) throw new NumberFormatException();
        return ((Date) v).before(new Date(u.longValue()));
    }),
    /**
     * min date value
     */
    MIN_DATE((u, v) -> {
        if(! (v instanceof Date)) throw new NumberFormatException();
        return ((Date) v).after(new Date(u.longValue() - 10801000));
    }),
    /**
     * lover then current value
     */
    LOWER_THAN_CURRENT_DATE((u, v) -> {
        if(! (v instanceof Date)) throw new NumberFormatException();
        return ((Date) v).before(new Date());
    });

    BiPredicate<Double, Object> validation;

    /**
     * Validation constructor
     * @param validation BiPredicate comparing to values
     */
    ValidationMode(BiPredicate<Double, Object> validation) {
        this.validation = validation;
    }

    /**
     * @return returns BiPredicate to compare values
     */
    public BiPredicate<Double, Object> getValidation() {
        return validation;
    }
}
