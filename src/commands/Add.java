package main.java.src.commands;

import main.java.src.Program;
import main.java.src.annotations.Complex;
import main.java.src.annotations.Fillable;
import main.java.src.annotations.Nullable;
import main.java.src.utils.StringConverter;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.util.*;
import java.util.function.Function;

public class Add implements Command {

    public final static String[] args = new String[0];

    @Override
    public void execute(Program program, String[] args) {
        Class<?> collectionCl = program.collection.getClT();
        program.collection.add(
                program.collection.getClT().cast(
                        createObject(
                                program, program.collection.getClT()
                        )));
        program.out.print(collectionCl.getSimpleName() + " was successfully created\n");
    }

    private <T> T createObject(Program program, Class<T> ClT) {
        T obj;
        try {
            obj = ClT.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Field[] fields = Arrays.stream(ClT
                        .getDeclaredFields())
                        .filter(e -> e.isAnnotationPresent(Fillable.class))
                        .toArray(Field[]::new);

        List<Field> fieldsList = Arrays.asList(fields);
        ListIterator<Field> iterator = fieldsList.listIterator();

        String line;
        while(iterator.hasNext()) {
//            program.out.print(obj + "\n");
            Field field = iterator.next();
            Class<?> fieldType = field.getType();
//            System.out.println(fieldType);
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(Complex.class)) {
                    if(field.isAnnotationPresent(Nullable.class)) {
                        program.out.print("Do you want to create " + fieldType.getSimpleName() + " object (y/n) : ");

                        switch (program.in.readLine()) {
                            case "y" -> field.set(obj, createObject(program, fieldType));
                            case "n" -> field.set(obj, null);
                            default -> throw new NumberFormatException();
                        }

                    } else {
                        field.set(obj, createObject(program, fieldType));
                    }
                } else if (fieldType.isEnum()) {
                    List<?> enumConstants = Arrays.asList(fieldType.getEnumConstants());

                    program.out.print("Enter " + field.getName() + "(");
                    program.out.print(String.join(", ", enumConstants.stream().map(Object::toString).toArray(String[]::new)));
                    program.out.print(") : ");

                    line = program.in.readLine();
                    if(field.isAnnotationPresent(Nullable.class) && line.equals("")) {
                        field.set(obj, null);
                    } else {
                        Object enumValue;
                        try {
                            enumValue = Enum.valueOf((Class<Enum>) fieldType, line);
                        } catch (IllegalArgumentException iae) {
                            throw new NumberFormatException();
                        }
                        field.set(obj, enumValue);
                    }
                } else {
                    program.out.print("Enter " + ClT.getSimpleName() + "'s " + field.getName());
                    if(fieldType.isInstance(new Date(0))) program.out.print(" (in format XXXX-XX-XX year-month-day)");
                    program.out.print(" : ");
                    line = program.in.readLine();

                    if(line.equals("")) {
                        if(field.isAnnotationPresent(Nullable.class)) {
                            field.set(obj, null);
                        } else {
                            throw new NumberFormatException();
                        }
                    } else {
                        Function<String, ?> convertFunction = StringConverter.methodForType.get(fieldType);
                        if (convertFunction == null) {
                            program.out.print("Sorry we don't know how to interpret " + ClT.getSimpleName() + "'s field " + field.getName() + " with " + fieldType.getSimpleName() + " type(\n");
                            field.set(obj, null);
                        } else {
                            field.set(obj, convertFunction
                                    .apply(line));
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                program.out.print("Invalid value for field with " + fieldType.getSimpleName() + " type. Please try again.\n");
                field = iterator.previous();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return obj;
    }

    @Override
    public String getDescription() {
        return "Add new element into collection";
    }

    @Override
    public String[] args() {
        return args;
    }
}
