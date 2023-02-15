package src.stored;

import java.time.ZonedDateTime;

public class Dragon implements Comparable<Dragon> {

    private static long instances = 0;

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long age; //Значение поля должно быть больше 0, Поле может быть null
    private Long wingspan; //Значение поля должно быть больше 0, Поле может быть null
    private float weight; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private Person killer; //Поле может быть null

    public Dragon() {
        id = instances++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public void setWingspan(Long wingspan) {
        this.wingspan = wingspan;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setKiller(Person killer) {
        this.killer = killer;
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
}