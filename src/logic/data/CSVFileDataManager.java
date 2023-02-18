package src.logic.data;

import src.utils.Parser;

import java.io.IOException;

public class CSVFileDataManager<T extends Comparable<? super T>> extends FileDataManager<T> {

    public CSVFileDataManager(Class clT) {
        super(clT);
    }

    @Override
    public void initialize(String file) throws IOException {
        Parser.parsCSV(file);
    }
}
