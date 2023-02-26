package main.java.src.stored;

import main.java.src.annotations.Fillable;
import main.java.src.annotations.Nullable;
import main.java.src.annotations.Storable;

import java.io.Serializable;

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
    private float height; //Значение поля должно быть больше 0
    @Fillable
    @Storable
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