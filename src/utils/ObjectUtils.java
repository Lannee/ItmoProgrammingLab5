package main.java.src.utils;

import main.java.src.Client;
import main.java.src.annotations.*;
import main.java.src.logic.data.ValidationMode;
import main.java.src.logic.exceptions.FieldRestrictionException;
import main.java.src.logic.exceptions.NullFieldValueException;
import main.java.src.logic.streams.InputManager;
import main.java.src.logic.streams.OutputManager;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

/**
 * Util class for creating objects instances by their classes
 */
public class ObjectUtils {
    /**
     * Creates object with users interactive input
     * @param ClT - Class of constructing object
     * @return final object
     */
    public static <T> T createObjectInteractively(Class<T> ClT) {
        T obj = null;
        try {
            obj = ClT.getConstructor().newInstance();
        } catch (Exception ignored) { }

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
                        if(ObjectUtils.agreement(Client.in, Client.out, "Do you want to create " + fieldType.getSimpleName() + " object (y/n) : ")) {
                            field.set(obj, createObjectInteractively(fieldType));
                        } else {
                            field.set(obj, null);
                        }
                    } else {
                        field.set(obj, createObjectInteractively(fieldType));
                    }

                } else if (fieldType.isEnum()) {
                    List<?> enumConstants = Arrays.asList(fieldType.getEnumConstants());

                    Client.out.print("Enter " + field.getName() + "(");
                    Client.out.print(String.join(", ", enumConstants.stream().map(Object::toString).toArray(String[]::new)) + ")");
                    Client.out.print(field.isAnnotationPresent(Nullable.class) ? " (Null)" : "");
                    Client.out.print(" : ");

                    line = Client.in.readLine().toUpperCase();
                    if(line.equals("")) {
                        if(field.isAnnotationPresent(Nullable.class)) {
                            field.set(obj, null);
                        } else {
                            throw new NullFieldValueException();
                        }
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
                            throw new FieldRestrictionException();
                    }
                } else {
                    Client.out.print("Enter " + ClT.getSimpleName() + "'s " + field.getName());
                    if(fieldType.isInstance(new Date()))
                        Client.out.print(" (in format year-month-day)");

                    String fieldRestrictions = getFieldRestrictions(field);
                    Client.out.print(!fieldRestrictions.equals("") ? " (" + fieldRestrictions + ")" : "");
                    Client.out.print(field.isAnnotationPresent(Nullable.class) ? " (Null)" : "");
                    Client.out.print(" : ");
                    line = Client.in.readLine();

                    if(line.equals("")) {
                        if(field.isAnnotationPresent(Nullable.class)) {
                            field.set(obj, null);
                        } else {
                            throw new NullFieldValueException();
                        }
                    } else {
                        Function<String, ?> convertFunction = StringConverter.methodForType.get(fieldType);
                        if (convertFunction == null) {
                            Client.out.print("Sorry we don't know how to interpret " + ClT.getSimpleName() + "'s field " + field.getName() + " with " + fieldType.getSimpleName() + " type(\n");
                            field.set(obj, null);
                        } else {
                            Object value = convertFunction.apply(line);

                            if(checkValueForRestrictions(field, value))
                                field.set(obj, value);
                            else
                                throw new FieldRestrictionException();
                        }
                    }
                }
            } catch (NullFieldValueException nfve) {
                Client.out.print("Field value cannot be null\n");
                iterator.previous();
            } catch (FieldRestrictionException fre) {
                Client.out.print("Entered value has exceeded the allowed restrictions\n");
                iterator.previous();
            } catch (NumberFormatException nfe) {
                Client.out.print("Invalid value for field with " + fieldType.getSimpleName() + " type. Please try again\n");
                iterator.previous();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return obj;
    }

    private static boolean checkValueForRestrictions(Field field, Object value) {
        if(!field.isAnnotationPresent(Restriction.class)) return true;

        boolean out;

        Restriction restriction = field.getAnnotation(Restriction.class);
        out = restriction.filter().getValidation().test(restriction.value(), value);

        if(field.isAnnotationPresent(ExRestriction.class)) {
            ExRestriction exRestriction = field.getAnnotation(ExRestriction.class);
            Restriction exArgument = exRestriction.restriction();
            out &= exArgument.filter().getValidation().test(exArgument.value(), value);
        }

        return out;
    }

    private static String getFieldRestrictions(Field field) {
        Class<?> fieldType = field.getType();
        Double[] baseRestrictions = BaseTypesRestrictions.restrictions.get(fieldType);
        if(baseRestrictions == null) return "";

        String[] restrictions = Arrays.stream(baseRestrictions)
                .map(Object::toString)
                .toArray(String[]::new);

        if(field.getType().isInstance(new Date(0))) {
            restrictions[0] = Instant.ofEpochMilli(
                    Double.valueOf(
                            Double.parseDouble(restrictions[0])).longValue())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate().toString();
            restrictions[1] = Instant.ofEpochMilli(
                            Double.valueOf(
                                    Double.parseDouble(restrictions[1])).longValue())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate().toString();
        }

        if(field.isAnnotationPresent(Restriction.class)) {
            setRestrictions(field.getAnnotation(Restriction.class), restrictions, fieldType);
        }

        if(field.isAnnotationPresent(ExRestriction.class)) {
            setRestrictions(field.getAnnotation(ExRestriction.class).restriction(), restrictions, fieldType);
        }
        return (field.getType().isInstance("") ? "length: " : "") + String.join(", ", restrictions);
    }

    private static void setRestrictions(Restriction restriction, String[] restrictions, Class<?> fieldType) {
        switch (restriction.filter()) {
            case LOWER_THAN_CURRENT_DATE -> restrictions[1] = LocalDate.now().toString();
            case MAX_STRING_LENGTH, TOP_NUMERIC_BOUND -> restrictions[1] = String.valueOf(restriction.value());
            case LOW_NUMERIC_BOUND, MIN_STRING_LENGTH -> restrictions[0] = String.valueOf(restriction.value());
        }
    }

    public static void setFieldValue(Object o, String fieldName, Object value) throws NoSuchFieldException, IllegalArgumentException {
        Field field = o.getClass().getField(fieldName);
        field.setAccessible(true);

        try {
            field.set(o, value);
        } catch (IllegalAccessException e) { }
    }

    public static <T extends Comparable<T>> int saveCompare(T o1, T o2) {
        if(o1 == null & o2 == null)
            return 0;
        else if(o1 == null & o2 != null)
            return -1;
        else if(o1 != null & o2 == null)
            return 1;
        else
            return o1.compareTo(o2);
    }

    public static boolean agreement(InputManager in, OutputManager out, String phrase) {
        out.print(phrase);
        switch (in.readLine().toLowerCase()) {
            case "y", "yes" -> {
                return true;
            }
            case "n", "no" -> {
                return false;
            }
            default -> {
                return agreement(in, out, phrase);
            }
        }
    }
}
