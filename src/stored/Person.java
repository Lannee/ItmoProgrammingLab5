package src.stored;

public class Person implements Comparable<Person> {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.util.Date birthday; //Поле может быть null
    private float height; //Значение поля должно быть больше 0
    private String passportID; //Длина строки не должна быть больше 38, Длина строки должна быть не меньше 4, Поле не может быть null
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
}