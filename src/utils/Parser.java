package src.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class Parser {
    public static void parsCSV(String filePath) {
        try(InputStream fileInputStream = new FileInputStream(filePath);
            Reader decoder = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader lineReader = new BufferedReader(decoder)) {

        } catch (IOException e) {

        }
    }
}
