package main.java.src.logic.data;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import main.java.src.Program;
import main.java.src.annotations.Complex;
import main.java.src.annotations.Fillable;
import main.java.src.annotations.Nullable;
import main.java.src.annotations.Storable;
import main.java.src.utils.StringConverter;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.time.*;
import java.util.*;

/**
 * Specific implementation of FileDataManager as a CSV file storage
 * @param <T> - Stored type
 */
public class CSVFileDataManager<T extends Comparable<? super T>> extends FileDataManager<T> {

    public CSVFileDataManager(Class<T> clT){
        super(clT);
    }

    @Override
    public void initialize(String filePath) {
        Program program = Program.getInstance();
        Class<?> clT = program.collection.getClT();

        File csvFile = new File(filePath);

        List<String[]> csvContent;
        String[] headers;
        try(Reader isr = new InputStreamReader(
                new FileInputStream(csvFile));
            CSVReader reader = new CSVReader(isr)) {

            if(!csvFile.exists() || csvFile.isDirectory() || !filePath.endsWith(".csv")) throw new IOException("Incorrect file");
            super.file = csvFile;
            super.attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            super.modification = LocalDateTime.ofInstant(attr.lastModifiedTime().toInstant(), ZoneId.systemDefault());

            csvContent = reader.readAll();
            if(!(csvContent.size() < 2)) {
                headers = csvContent.get(0);
                csvContent = csvContent.subList(1, csvContent.size());

            } else {
                throw new FileFormatException("File is empty");
            }

            for(String[] values : csvContent) {
                try {
                    program.collection.add(program
                                    .collection
                                    .getClT()
                                    .cast(createObject(clT, headers, values)));

                } catch (ReflectiveOperationException e) {
                    program.out.print("Unable to create an object\n");
                }
            }

        } catch (FileFormatException e) {
            program.out.print(e.getMessage() + ". Do you want to rewrite this file (y/n) : ");
            if(!program.in.readLine().equals("y")) {
                System.exit(0);
            }

        } catch (IOException e) {
            program.out.print("Unable to initialize collection");
            System.exit(1);
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
                        .filter(e -> e.isAnnotationPresent(Storable.class))
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
        List<String> headers = new LinkedList<>();

        Field[] fields = getFieldsWithAnnotation(cl, Storable.class);

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

        Field[] fields = getFieldsWithAnnotation(objCl, Storable.class);

        for(Field field : fields) {
            field.setAccessible(true);
            Object fieldValue;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException ignore) {
                fieldValue = new Object();
            }

            if(field.isAnnotationPresent(Complex.class)) {
                String[] exLevel = new String[countObjectsFields(field.getType())];
                if(field.isAnnotationPresent(Nullable.class) && fieldValue == null) {
                    Arrays.fill(exLevel, "null");
                } else {
                    exLevel = getFieldsValues(fieldValue);
                }
                values.addAll(Arrays.asList(exLevel));
            } else {
                if(fieldValue == null) {
                    fieldValue = "null";
                } else {
                    if(field.getType().isInstance(new Date(0))) {
                        fieldValue = ((Date) fieldValue).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                    }
                    if(field.getType().isInstance(ZonedDateTime.now())) {
                        fieldValue = ((ZonedDateTime) fieldValue).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();
                    }
                }
                values.add(fieldValue.toString());
            }
        }

        return values.toArray(String[]::new);
    }

    private static Field[] getFieldsWithAnnotation(Class<?> cl, Class<? extends Annotation> annotation) {
        return Arrays.stream(cl
                        .getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(annotation))
                .toArray(Field[]::new);
    }

    private static <T> T createObject(Class<T> cl, String[] headers, String[] values) throws FileFormatException, ReflectiveOperationException {
        T obj = cl.getConstructor().newInstance();

        List<String[]> headersElements = Arrays.stream(headers)
                .map(e -> e.split("\\."))
                .toList();

        for(int i = 0; i < headersElements.size(); i++) {
            String[] header = headersElements.get(i);
            if(!header[0].equals(cl.getSimpleName())) throw new FileFormatException("Invalid file headers");

            Field field = cl.getDeclaredField(header[1]);
            Class<?> fieldType = field.getType();
            field.setAccessible(true);
            if(field.isAnnotationPresent(Complex.class)) {
                String reducePrefix = cl.getSimpleName() + "." + field.getName() + ".";
                String prefix = reducePrefix + fieldType.getSimpleName() + ".";

                String exHeader;
                int exLevelStart = 0;
                int exLevelEnd = 0;
                for(int j = 0; j < headers.length; j++) {
                    exHeader = headers[j];
                    if(exHeader.startsWith(prefix)) {
                        exLevelStart = j;
                        break;
                    }
                }

                for(int j = exLevelStart; j < headers.length; j++) {
                    exHeader = headers[j];
                    if(!exHeader.startsWith(prefix)) {
                        break;
                    }
                    exLevelEnd = j;
                }

                i = exLevelEnd;

                String[] exHeaders = Arrays.copyOfRange(headers, exLevelStart, exLevelEnd + 1);
                String[] exValues = Arrays.copyOfRange(values, exLevelStart, exLevelEnd + 1);

                exHeaders = Arrays.stream(exHeaders).map(e -> e.substring(reducePrefix.length())).toArray(String[]::new);
                field.set(obj, createObject(fieldType, exHeaders, exValues));
            } else {
                if(fieldType.isEnum()) {
                    Object enumValue;
                    try {
                        if(values[i].equals("null"))
                            if(field.isAnnotationPresent(Nullable.class))
                                enumValue = null;
                            else
                                throw new ReflectiveOperationException();
                        else
                            enumValue = Enum.valueOf((Class<Enum>) fieldType, values[i]);
                    } catch (IllegalArgumentException iae) {
                        throw new ReflectiveOperationException();
//                        throw new RuntimeException(iae);
                    }
                    field.set(obj, enumValue);
                } else {
                    String value = values[i];
                    if(value.equals("null")) {
                        if(!field.isAnnotationPresent(Nullable.class)) return null;
                        field.set(obj, null);
                    } else {
                        if(!StringConverter.methodForType.containsKey(fieldType)) throw new FileFormatException("Unsupported field type");
                        try {
                            field.set(obj,
                                    StringConverter.methodForType
                                            .get(field.getType())
                                            .apply(value));
                        } catch (NumberFormatException e) {
                            throw new FileFormatException("Invalid data");
                        }
                    }
                }
            }
        }

        return obj;
    }
}
