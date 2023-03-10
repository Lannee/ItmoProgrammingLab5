package main.java.src.stored;

import main.java.src.annotations.Fillable;
import main.java.src.annotations.Restriction;
import main.java.src.annotations.Storable;
import main.java.src.logic.data.ValidationMode;

import java.io.Serializable;

/**
 * One of the stored objects
 */
public class Coordinates implements Comparable<Coordinates>, Serializable {
    @Fillable
    @Storable
    @Restriction(filter = ValidationMode.TOP_NUMERIC_BOUND, value = 955)
    private long x; //Максимальное значение поля: 955
    @Fillable
    @Storable
    @Restriction(filter = ValidationMode.TOP_NUMERIC_BOUND, value = 207)
    private Integer y; //Максимальное значение поля: 207, Поле не может быть null


    @Override
    public int compareTo(Coordinates o) {
        int compare;
        if((compare = (int)(x - o.x)) != 0) {
            return compare;
        } else {
            return y.compareTo(o.y);
        }
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}