package main.java.src.logic.data;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public interface DataManager<T> {
//    List<T> getElements();
    String getInfo();
    void add(T element);
    void addAll(Collection<T> elements);
    void clear();
    T get(int id);
    boolean remove(Object o);
    int size();
    List<T> getElements();
    List<T> getElements(Comparator<? super T> sorter);
    void initialize(String filePath);
    void save();
    Class<T> getClT();
    void forEach(Consumer<? super T> action);
}
