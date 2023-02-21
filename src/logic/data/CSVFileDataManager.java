package main.java.src.logic.data;

import main.java.src.utils.Parser;

import java.io.IOException;
import java.util.List;

public class CSVFileDataManager<T extends Comparable<? super T>> extends FileDataManager<T> {

    public CSVFileDataManager(Class clT) {
        super(clT);
    }

    @Override
    public void initialize(String file) throws IOException {
        CollectionMetadata metadata = Parser.parseCSV(file);
        super.initialization = metadata.getIncializationDate();
    }

    @Override
    public void loadToFile(List<T> collection) {

    }
}
