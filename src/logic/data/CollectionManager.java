package src.logic.data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CollectionManager<T> {

    private List<T> collection = new LinkedList<>();
    private final Class<?> clT;
    private final Date initialization;
    private Date modification;

    public CollectionManager(Class<?> clT) {
        this.clT = clT;
        initialization = new Date();
        modification = new Date();
    }

    public void initialize(String filePath) {

    }

    public String getInfo() {
        return "Тип хранимых данных : " + clT.getName() + "\n" +
                "Размер коллекции : " + collection.size() + "\n" +
                "Дата инициализации : " + initialization.getTime() + "\n" +
                "Дата последнего изменения : " + modification.getTime();
    }

    public List<T> getElements() {
        return collection;
    }

    public void clear() {
        collection.clear();
    }


}
