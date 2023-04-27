package main.java.src.logic.data;

import main.java.src.Client;
import main.java.src.logic.exceptions.CannotCreateObjectException;
import main.java.src.stored.Dragon;
import main.java.src.utils.Formatter;
import main.java.src.utils.ObjectUtils;
import main.java.src.utils.StringConverter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;


/**
 * Responsible for performing various actions on the collection
 */
public class Receiver {
    private final DataManager<Dragon> collection = new CSVFileDataManager<>(Dragon.class);

    /**
     * Receiver constructor
     * @param filePath get path to file to initialize collection with
     */
    public Receiver(String filePath) {
        collection.initialize(filePath);
    }

    /**
     * realization of add command
     * doing interactive add into collection
     */
    public void interactiveAdd() {
        try {
            collection.add(
                    getStoredType().cast(
                            ObjectUtils.createObjectInteractively(
                                    collection.getClT()
                            )));
            Client.out.print(collection.getClT().getSimpleName() + " was successfully created\n");
        } catch (CannotCreateObjectException e) {
            Client.out.print("Unable to create object: " + e.getMessage() + "\n");
        }
    }

    /**
     * doing element interactive add into collection with setting its id
     */
    public void interactiveAdd(Long id) {
        try {
            Object obj = ObjectUtils.createObjectInteractively(collection.getClT());
            if(id <= 0) throw new NumberFormatException("Incorrect argument value");
            ObjectUtils.setFieldValue(obj, "id", id);
            collection.add(
                    getStoredType().cast(obj)
            );
        } catch (NoSuchFieldException e) {
            Client.out.print("Stored type does not support this command\n");
        } catch (IllegalArgumentException | CannotCreateObjectException e) {
            Client.out.print("Unable to create object: " + e.getMessage() + "\n");
        }
    }

    /**
     * clears the collection
     */
    public void clear() {
        if(ObjectUtils.agreement(Client.in, Client.out, "Are you sure you want to clear the collection (y/n) : ", false))
            collection.clear();
    }

    /**
     * @return returns information about commands
     */
    public String getInfo() {
        return collection.getInfo();
    }

    /**
     * @param sorter elements sorter
     * @return returns collection in table view
     */
    public String getFormattedCollection(Comparator<Dragon> sorter) {
        return Formatter.format(collection.getElements(sorter), collection.getClT());
    }

    /**
     * @return returns collection in table view
     */
    public String getFormattedCollection() {
        return getFormattedCollection(Comparator.reverseOrder());
    }

    /**
     * @param fieldName name of field to count
     * @param value value to compare with
     * @param comparator comparator doing comparing field value and the given value
     * @return returns amount of object
     * @param <T> represents stored type
     * @throws NumberFormatException thrown if given value doesn't specify field restrictions
     */
    public <T> Integer countCompareToValueByField(String fieldName, String value, Comparator<Comparable<T>> comparator) throws NumberFormatException {
        Integer counter = 0;
        try {
            Field field = collection.getClT().getDeclaredField(fieldName);
            field.setAccessible(true);
            Comparable givenValue = (Comparable) StringConverter.methodForType.get(field.getType()).apply(value);
            if(!ObjectUtils.checkValueForRestrictions(field, givenValue)) {
                throw new NumberFormatException();
            }
            for(Object element : collection.getElements()) {
                try {
                    if(comparator.compare(givenValue, (Comparable)field.get(element)) > 0) counter++;
                } catch (IllegalAccessException impossible) { }
            }


        } catch (NoSuchFieldException nsfe) {
            Client.out.print("Stored type does not have " + fieldName + " field\n");
        }

        return counter;
    }

    /**
     * saves the collection
     */
    public void saveCollection() {
        collection.save();
    }

    /**
     * @param fieldName name of filed
     * @param value value of the field
     * @return object with specified value or null
     * @throws NoSuchFieldException thrown if stored type does not have this field
     */
    public Dragon getElementByFieldValue(String fieldName, Object value) throws NoSuchFieldException {

        Field idField;
        idField = collection.getClT().getDeclaredField(fieldName);
        idField.setAccessible(true);

        for(Dragon e : collection.getElements()) {
            try {
                if(idField.get(e).equals(value)) {
                    return e;
                }
            } catch (IllegalAccessException ex) {}
        }

        return null;
    }

    /**
     * @param index index of element
     * @return found object or null
     */
    public Dragon getElementByIndex(int index) {
        return collection.get(index);
    }

    /**
     * @param o object to remove
     * @return returns true if object was removed, else returns false
     */
    public boolean removeFromCollection(Object o) {
        return collection.remove(o);
    }

    /**
     * @param filter condition to remove on
     * @param showRemoved if true then it shows everything that was removed
     * @return returns true if object was removed, else returns false
     */
    public boolean removeOn(Predicate<Dragon> filter, boolean showRemoved) {
        if(collection.size() == 0) {
            Client.out.print("Cannot remove since the collection is empty\n");
            return false;
        }

        List<Dragon> removed = new LinkedList<>();
        for(Dragon element : collection.getElements()) {
            if(filter.test(element)) {
                removed.add(element);
                removeFromCollection(element);
            }
        }

        if(showRemoved) {
            Client.out.print(Formatter.format(removed, collection.getClT()) + "\n");
        }

        return !removed.isEmpty();
    }

    /**
     * @index index of element
     * @param showRemoved if true then it shows everything that was removed
     * @return returns true if object was removed, else returns false
     */
    public boolean removeByIndex(int index, boolean showRemoved) {
        if(collection.size() == 0) {
            Client.out.print("Cannot remove since the collection is empty\n");
            return false;
        }

        if(index > collection.size()) {
            Client.out.print("Cannot remove from collection: index is out of bound\n");
            return false;
        }

        Object obj = getElementByIndex(index);
        return removeOn(e -> e == obj, showRemoved);
    }

    /**
     * @return returns stored class
     */
    public Class<Dragon> getStoredType() {
        return collection.getClT();
    }

    /**
     * @param fieldName name of field
     * @return map where first element is field value and the second one is the amount of objects with similar field value
     * @throws NoSuchFieldException thrown if stored type does not have this field
     */
    public Map<Object, Integer> groupByField(String fieldName) throws NoSuchFieldException {
        Map<Object, Integer> groups = new HashMap<>();
        Field field = collection.getClT().getDeclaredField(fieldName);
        field.setAccessible(true);
        for(Object element : collection.getElements()) {
            try {
                Object key = field.get(element);
                if(groups.containsKey(key)) {
                    Integer value = groups.get(key);
                    groups.put(key, ++value);
                } else {
                    groups.put(key, 1);
                }
            } catch (IllegalAccessException impossible) { }
        }
        return groups;
    }
}
