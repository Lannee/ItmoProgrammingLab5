package src.logic.data;

import java.util.Collection;
import java.util.List;

public interface DataManager<T> {
    List<T> getElements();
    String getInfo();
    void add(T element);
    void addAll(Collection<T> elements);
    void clear();
    T get(int id);
    void remove(T eleement);
}
