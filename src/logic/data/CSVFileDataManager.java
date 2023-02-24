package main.java.src.logic.data;

import au.com.bytecode.opencsv.CSVWriter;
import main.java.src.Program;
import main.java.src.annotations.Complex;
import main.java.src.annotations.Fillable;
import main.java.src.utils.Parser;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class CSVFileDataManager<T extends Comparable<? super T>> extends FileDataManager<T> {

    public CSVFileDataManager(Class<T> clT){
        super(clT);
    }

    @Override
    public void initialize(String filePath) throws IOException {
        File csvFile = new File(filePath);
        if(!csvFile.exists() || csvFile.isDirectory() || !filePath.endsWith(".csv")) throw new IOException("Incorrect file");

        super.file = csvFile;
//        super.modification = LocalDateTime.ofEpochSecond(
//                        csvFile.lastModified(),
//                        0,
//                        ZoneOffset.UTC);

        super.attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        super.modification = LocalDateTime.ofInstant(attr.lastModifiedTime().toInstant(), ZoneId.systemDefault());

        try(Reader isr = new InputStreamReader(
                new FileInputStream(csvFile))) {
            CollectionMetadata metadata = Parser.parseCSV(isr);
        }
    }

    @Override
    public void save() {
        Program program = Program.getInstance();
        Class<?> clT = program.collection.getClT();
        List<String[]> toCSV = new ArrayList<>(program.collection.size() + 1);

        toCSV.add(getHeaders(clT));
        program.collection.forEach(e -> toCSV.add(getFieldsValues(e)));

        try(CSVWriter writer = new CSVWriter(new FileWriter(super.file))) {
            writer.writeAll(toCSV);
        } catch (IOException e) {
            program.out.print("Unable to save collection into the file.\n");
        }

    }

    private static Integer countObjectsFields(Class<?> cl) {
        Integer out = 0;
        Field[] fields = Arrays.stream(cl
                        .getDeclaredFields())
                        .filter(e -> e.isAnnotationPresent(Fillable.class))
                        .toArray(Field[]::new);

        for(Field field : fields) {
            if(field.isAnnotationPresent(Complex.class)) {
                out += countObjectsFields(field.getType());
            } else {
                out++;
            }
        }

        return out;
    }

    private static String[] getHeaders(Class<?> cl) {
        int headersAmount = countObjectsFields(cl);
        List<String> headers = new LinkedList<>();

        Field[] fields = Arrays.stream(cl
                        .getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(Fillable.class))
                .toArray(Field[]::new);

        StringBuilder header = new StringBuilder();
        for(Field field : fields) {
            header.append(cl.getSimpleName()).append(".").append(field.getName());
            if(field.isAnnotationPresent(Complex.class)) {
                String[] exLevel = getHeaders(field.getType());
                for(String exHeader : exLevel) {
                    exHeader = header + "." + exHeader;
                    headers.add(exHeader);
                }
            } else {
//                headers.add(header.toString() + " " + field.getType().getName());
                headers.add(header.toString());
            }
            header.append(" ").append(field.getType());
            header.setLength(0);
        }

        return headers.toArray(String[]::new);
    }

    private static String[] getFieldsValues(Object obj) {
        Class<?> objCl = obj.getClass();
        List<String> values = new LinkedList<>();

        Field[] fields = Arrays.stream(objCl
                        .getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(Fillable.class))
                .toArray(Field[]::new);

        for(Field field : fields) {
            field.setAccessible(true);
            Object fieldValue;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException ignore) {
                fieldValue = new Object();
            }

            if(field.isAnnotationPresent(Complex.class)) {
                String[] exLevel = getFieldsValues(fieldValue);
                values.addAll(Arrays.asList(exLevel));
            } else {
                values.add(fieldValue.toString());
            }
        }

        return values.toArray(String[]::new);
    }
}
