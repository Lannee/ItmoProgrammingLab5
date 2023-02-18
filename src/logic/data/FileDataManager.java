package src.logic.data;


import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class FileDataManager<T extends Comparable<? super T>> implements DataManager<T> {

    private List<T> collection = new LinkedList<>();
    private final Class<?> clT;
    private final ZonedDateTime initialization;
    private ZonedDateTime modification;

    public FileDataManager(Class<?> clT) {
        this.clT = clT;
        initialization = ZonedDateTime.now();
        modification = ZonedDateTime.now();
    }


    public void sort() {
        Collections.sort(collection);
    }

    @Override
    public String getInfo() {
        return "Тип хранимых данных : " + clT.getName() + "\n" +
                "Размер коллекции : " + collection.size() + "\n" +
                "Дата инициализации : " + initialization + "\n" +
                "Дата последнего изменения : " + modification;
    }

    public abstract void initialize(String file) throws IOException;

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

    @Override
    public List<T> getElements() {
        return collection;
    }

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public T get(int id) {
        return null;
    }

    @Override
    public void remove(T eleement) {

    }


}
