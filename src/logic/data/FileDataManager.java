package main.java.src.logic.data;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Consumer;

/**
 * DataManager implementation for representation storage as a file
 * @param <T> - Stored type
 */
public abstract class FileDataManager<T extends Comparable<? super T>> implements DataManager<T> {

    private final List<T> collection = new LinkedList<>();
    private final Class<T> clT;
    protected File file;
    protected BasicFileAttributes attr;
    protected LocalDateTime modification;

    /**
     * File collection constructor
     * @param clT - is a type of stored elements
     */
    public FileDataManager(Class<T> clT){
        this.clT = clT;
    }

    @Override
    public Class<T> getClT() {
        return clT;
    }


    public void sort() {
        Collections.sort(collection);
    }

    @Override
    public String getInfo() {
        return "Stored data type : " + clT.getSimpleName() + "\n" +
                "Collection size : " + collection.size() + "\n" +
                "Initialization date : " + LocalDateTime.ofInstant(
                        attr.creationTime()
                                .toInstant(), ZoneId.systemDefault())
                                        .format(DateTimeFormatter.ofLocalizedDateTime(
                                                FormatStyle.MEDIUM, FormatStyle.MEDIUM)) + "\n" +

                "Last modification date : " + modification.format(
                        DateTimeFormatter.ofLocalizedDateTime(
                                FormatStyle.MEDIUM, FormatStyle.MEDIUM));
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public void add(T element) {
        collection.add(element);
        sort();
        modification = LocalDateTime.now();
    }

    @Override
    public void addAll(Collection<T> collection) {
        collection.addAll(collection);
        sort();
    }
    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public T get(int id) {
        return collection.get(id);
    }

    @Override
    public boolean remove(Object o) {
        for(T element : collection) {
            if(element.equals(o)) {
                collection.remove(o);
                return true;
            }
        }
        return false;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        collection.forEach(action);
    }

    @Override
    public List<T> getElements() {
        return new LinkedList<>(collection);
    }

    @Override
    public List<T> getElements(Comparator<? super T> sorter) {
        List<T> copy = new LinkedList<>(collection);
        copy.sort(sorter);
        return copy;
    }

}
