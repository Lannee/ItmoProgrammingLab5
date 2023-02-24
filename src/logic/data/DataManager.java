package main.java.src.logic.data;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface DataManager<T> {
//    List<T> getElements();
    String getInfo();
    void add(T element);
    void addAll(Collection<T> elements);
    void clear();
    T get(int id);
    void remove(Object o);

    int size();
    void initialize(String filePath) throws IOException;
    void save();

    Class<T> getClT();

    void forEach(Consumer<? super T> action);
}
