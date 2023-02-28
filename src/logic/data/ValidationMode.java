package main.java.src.logic.data;

import java.util.function.BiPredicate;

public enum ValidationMode {
    TOP_NUMERIC_BOUND((u, v) -> {
        if(! (v instanceof Number && v instanceof Comparable)) throw new NumberFormatException();
        return Double.compare(u.doubleValue(), ((Number) v).doubleValue()) > 0;
    }),
    LOW_NUMERIC_BOUND((u, v) -> {
        if(! (v instanceof Number && v instanceof Comparable)) throw new NumberFormatException();
        return Double.compare(u.doubleValue(), ((Number) v).doubleValue()) < 0;
    }),
    MAX_STRING_LENGTH((u, v) -> {
        if(! (v instanceof CharSequence)) throw new NumberFormatException();
        return ((CharSequence) v).length() <= u;
    }),
    MIN_STRING_LENGTH((u, v) -> {
        if(! (v instanceof CharSequence)) throw new NumberFormatException();
        return ((CharSequence) v).length() >= u;
    });

    BiPredicate<Integer, Object> validation;

    ValidationMode(BiPredicate<Integer, Object> validation) {
        this.validation = validation;
    }

    public BiPredicate<Integer, Object> getValidation() {
        return validation;
    }
}
