package src.stored;

import src.annotations.Complex;
import src.annotations.Fillable;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Dragon implements Comparable<Dragon>, Serializable {

    private static long instances = 0;

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Fillable
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Complex
    @Fillable
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Fillable
    private Long age; //Значение поля должно быть больше 0, Поле может быть null
    @Fillable
    private Long wingspan; //Значение поля должно быть больше 0, Поле может быть null
    @Fillable
    private float weight; //Значение поля должно быть больше 0
    @Fillable
    private Color color; //Поле не может быть null
    @Complex
    @Fillable
    private Person killer; //Поле может быть null

    public Dragon() {
        id = instances++;
        creationDate = ZonedDateTime.now();
    }

    @Override
    public int compareTo(Dragon o) {
        int compare;
        if((compare = age.compareTo(o.age)) != 0) {
            return compare;
        } else if((compare = name.compareTo(o.name)) != 0){
            return compare;
        } else if((compare = coordinates.compareTo(o.coordinates)) != 0){
            return compare;
        } else if((compare = wingspan.compareTo(o.wingspan)) != 0){
            return compare;
        } else if((compare = (int)(weight - o.weight)) != 0){
            return compare;
        } else if((compare = color.compareTo(o.color)) != 0){
            return compare;
        } else {
            return killer.compareTo(o.killer);
        }
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", age=" + age +
                ", wingspan=" + wingspan +
                ", weight=" + weight +
                ", color=" + color +
                ", killer=" + killer +
                '}';
    }
}