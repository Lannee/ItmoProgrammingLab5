package main.java.src.annotations;

import main.java.src.logic.data.ValidationMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Restriction {
    ValidationMode filter();
    int value();
}
