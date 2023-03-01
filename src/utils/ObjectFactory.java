package main.java.src.utils;

import main.java.src.Program;
import main.java.src.annotations.*;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

/**
 * Util class for creating objects instances by their classes
 */
public class ObjectFactory {
    /**
     * Creates object with users interactive input
     * @param ClT - Class of constructing object
     * @return final object
     */
    public static <T> T createObjectInteractively(Class<T> ClT) {
        Program program = Program.getInstance();
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
            Field field = iterator.next();
            Class<?> fieldType = field.getType();
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(Complex.class)) {
                    if(field.isAnnotationPresent(Nullable.class)) {
                        program.out.print("Do you want to create " + fieldType.getSimpleName() + " object (y/n) : ");

                        switch (program.in.readLine()) {
                            case "y" -> field.set(obj, createObjectInteractively(fieldType));
                            case "n" -> field.set(obj, null);
                            default -> throw new NumberFormatException();
                        }

                    } else {
                        field.set(obj, createObjectInteractively(fieldType));
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
                        if(checkValueForRestrictions(field, enumValue))
                            field.set(obj, enumValue);
                        else
                            throw new NumberFormatException();
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
                            Object value = convertFunction.apply(line);

                            if(checkValueForRestrictions(field, value))
                                field.set(obj, value);
                            else
                                throw new NumberFormatException();
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

    private static boolean checkValueForRestrictions(Field field, Object value) {
        if(!field.isAnnotationPresent(Restriction.class)) return true;

        boolean out = true;

        Restriction restriction = field.getAnnotation(Restriction.class);
        out &= restriction.filter().getValidation().test(restriction.value(), value);

        if(field.isAnnotationPresent(ExRestriction.class)) {
            ExRestriction exRestriction = field.getAnnotation(ExRestriction.class);
            Restriction exArgument = exRestriction.restriction();
            out &= exArgument.filter().getValidation().test(exArgument.value(), value);
        }

        return out;
    }
}
