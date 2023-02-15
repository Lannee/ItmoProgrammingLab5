package src.logic;

import src.stored.Dragon;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CollectionManager<T> {

    private List<T> collection = new LinkedList<>();

    public CollectionManager() {}

    public void initialize(String filePath) {

    }

    public List<T> getElements() {
        return collection;
    }

    public void clear() {
        collection.clear();
    }


}
