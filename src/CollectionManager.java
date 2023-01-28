package src;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionManager {

    private Collection<String> collection = new ArrayList<>(); // change due to variant!!!

    public CollectionManager() {}

    public void show() {
        collection.forEach(System.out::println);
    }

}
