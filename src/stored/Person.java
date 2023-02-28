package main.java.src.stored;

import main.java.src.annotations.*;
import main.java.src.logic.data.ValidationMode;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Comparable<Person>, Serializable {
    @Fillable
    @Storable
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Nullable
    @Fillable
    @Storable
    private java.util.Date birthday; //Поле может быть null
    @Fillable
    @Storable
    @Restriction(filter = ValidationMode.LOW_NUMERIC_BOUND, value = 0)
    private float height; //Значение поля должно быть больше 0
    @Fillable
    @Storable
    @Restriction(filter = ValidationMode.MIN_STRING_LENGTH, value = 4)
    @ExRestriction
    (restriction = @Restriction(filter = ValidationMode.MAX_STRING_LENGTH, value = 38))
    private String passportID; //Длина строки не должна быть больше 38, Длина строки должна быть не меньше 4, Поле не может быть null
    @Nullable
    @Fillable
    @Storable
    private Color hairColor; //Поле может быть null

    @Override
    public int compareTo(Person o) {
        int compare;
        if((compare = passportID.compareTo(o.passportID)) != 0) {
            return compare;
        } else if((compare = name.compareTo(o.name)) != 0){
            return compare;
        } else if((compare = (int)(height - o.height)) != 0){
            return compare;
        } else if((compare = birthday.compareTo(o.birthday)) != 0){
            return compare;
        } else {
            return hairColor.compareTo(o.hairColor);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Float.compare(person.height, height) == 0 && name.equals(person.name) && Objects.equals(birthday, person.birthday) && passportID.equals(person.passportID) && hairColor == person.hairColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, height, passportID, hairColor);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", height=" + height +
                ", passportID='" + passportID + '\'' +
                ", hairColor=" + hairColor +
                '}';
    }
}