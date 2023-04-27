package main.java.src.annotations;

import main.java.src.logic.data.ValidationMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that show to object creator that field value must satisfy some restriction
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Restriction {
    /**
     * sets validationMode
     * @return validationMode
     */
    ValidationMode filter();

    /**
     * value to compare with
     * @return value to compare with
     */
    double value();
}
