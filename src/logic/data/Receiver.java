package main.java.src.logic.data;

import main.java.src.Client;
import main.java.src.stored.Dragon;
import main.java.src.utils.ObjectUtils;
import main.java.src.utils.StringConverter;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


/**
 * Responsible for performing various actions on the collection
 */
public class Receiver {
    private final DataManager<Dragon> collection = new CSVFileDataManager<>(Dragon.class);

    public Receiver(String filePath) {
        collection.initialize(filePath);
    }

    public void interactiveAdd() {
        collection.add(
                getStoredType().cast(
                        ObjectUtils.createObjectInteractively(
                                collection.getClT()
                        )));
        Client.out.print(collection.getClT().getSimpleName() + " was successfully created\n");
    }

    public void interactiveAdd(Long id) {
        Object obj = ObjectUtils.createObjectInteractively(collection.getClT());
        try {
            if(id <= 0) throw new NumberFormatException("Incorrect argument value");
            ObjectUtils.setFieldValue(obj, "id", id);
            collection.add(
                    getStoredType().cast(obj)
            );
        } catch (NoSuchFieldException e) {
            Client.out.print("Stored type does not support this command\n");
        } catch (IllegalArgumentException e) {
            Client.out.print(e.getMessage() + "\n");
        }
    }

    public void clear() {
        Client.out.print("Are you sure you want to clear the collection (y/n) : ");
        if(Client.in.readLine().equals("y"))
            collection.clear();
    }

    public String getInfo() {
        return collection.getInfo();
    }

    public String[] getStringElements(Comparator<Dragon> sorter) {
        List<?> elements = collection.getElements(sorter);
        return elements.stream().
                map(Object::toString).
                toArray(String[]::new);
    }

    public String[] getStringElements() {
        return getStringElements(Comparator.naturalOrder());
    }

    public <T> Integer countCompareToValueByField(String fieldName, String value, Comparator<Comparable<T>> comparator) throws NumberFormatException {
        Integer counter = 0;
        try {
            Field field = collection.getClT().getDeclaredField(fieldName);
            field.setAccessible(true);
            Comparable givenValue = (Comparable) StringConverter.methodForType.get(field.getType()).apply(value);
            for(Object element : collection.getElements()) {
                try {
                    if(comparator.compare(givenValue, (Comparable)field.get(element)) < 0) counter++;
                } catch (IllegalAccessException impossible) { }
            }


        } catch (NoSuchFieldException nsfe) {
            Client.out.print("Stored type does not have " + fieldName + " field\n");
        }

        return counter;
    }

    public void saveCollection() {
        collection.save();
    }

    public Dragon getElementByFieldValue(String fieldName, Object value) throws NumberFormatException, NoSuchFieldException {

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

    public Dragon getElementByIndex(int index) {
        return collection.get(index);
    }

    public int collectionSize() {
        return collection.size();
    }

    public boolean removeFromCollection(Object o) {
        return collection.remove(o);
    }

    public boolean removeOn(Predicate<Dragon> filter) {
        boolean wasSomethingDeleted = false;
        for(Dragon element : collection.getElements()) {
            if(filter.test(element)) {
                wasSomethingDeleted = removeFromCollection(element);
            }
        }
        return wasSomethingDeleted;
    }

    public Class<Dragon> getStoredType() {
        return collection.getClT();
    }

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
