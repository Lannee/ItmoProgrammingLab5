package main.java.src.logic.data;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

public abstract class FileDataManager<T extends Comparable<? super T>> implements DataManager<T> {

    private final List<T> collection = new LinkedList<>();
    private final Class<T> clT;
    protected ZonedDateTime initialization;
    protected ZonedDateTime modification;

    public FileDataManager(Class<T> clT) {
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
        return "Тип хранимых данных : " + clT.getSimpleName() + "\n" +
                "Размер коллекции : " + collection.size() + "\n" +
                "Дата инициализации : " + initialization + "\n" +
                "Дата последнего изменения : " + modification;
    }

    public abstract void initialize(String file) throws IOException;

    public abstract void loadToFile(List<T> collection);

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public void add(T element) {
        collection.add(element);
        sort();
    }

    @Override
    public void addAll(Collection<T> collection) {
        collection.addAll(collection);
        sort();
    }

//    @Override
//    public List<T> getElements() {
//        return collection;
//    }

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public T get(int id) {
        return collection.get(id);
    }

    @Override
    public void remove(Object o) {
        collection.remove(o);
    }


    @Override
    public List<T> getElements() {
        return collection;
    }

}
