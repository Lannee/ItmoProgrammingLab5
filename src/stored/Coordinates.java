package src.stored;

import src.commands.Command;

public class Coordinates implements Comparable<Coordinates> {
    private long x; //Максимальное значение поля: 955
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
}