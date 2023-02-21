package main.java.src.stored;

import main.java.src.annotations.Fillable;

import java.io.Serializable;

public class Coordinates implements Comparable<Coordinates>, Serializable {
    @Fillable
    private long x; //Максимальное значение поля: 955
    @Fillable
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