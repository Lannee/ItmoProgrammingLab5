package main.java.src.stored;

import main.java.src.annotations.*;
import main.java.src.logic.data.ValidationMode;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * One of the stored objects
 */
public class Dragon implements Comparable<Dragon>, Serializable {

    private static long instances = 0;

    @Storable
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Storable
    @Fillable
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Complex
    @Fillable
    @Storable
    private Coordinates coordinates; //Поле не может быть null
    @Storable
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Nullable
    @Fillable
    @Storable
    @Restriction(filter = ValidationMode.LOW_NUMERIC_BOUND, value = 0)
    private Long age; //Значение поля должно быть больше 0, Поле может быть null
    @Nullable
    @Fillable
    @Storable
    @Restriction(filter = ValidationMode.LOW_NUMERIC_BOUND, value = 0)
    private Long wingspan; //Значение поля должно быть больше 0, Поле может быть null
    @Fillable
    @Storable
    @Restriction(filter = ValidationMode.LOW_NUMERIC_BOUND, value = 0)
    private float weight; //Значение поля должно быть больше 0
    @Fillable
    @Storable
    private Color color; //Поле не может быть null
    @Nullable
    @Complex
    @Fillable
    @Storable
    private Person killer; //Поле может быть null

    public Dragon() {
        id = UUID.randomUUID().getLeastSignificantBits() & ~(1 << 63);
        creationDate = ZonedDateTime.now();
    }

    @Override
    public int compareTo(Dragon o) {
        int compare;
        if(age != null & o.age != null & (compare = age.compareTo(o.age)) != 0) {
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
//        return (int) (id - o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragon = (Dragon) o;
        return id == dragon.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, age, wingspan, weight, color, killer);
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate.toLocalDate() +
                ", age=" + age +
                ", wingspan=" + wingspan +
                ", weight=" + weight +
                ", color=" + color +
                ", killer=" + killer +
                '}';
    }
}