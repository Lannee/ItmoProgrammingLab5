package src.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClassFinder {
    /* REFACTOR IN FUTURE!!!!!
       Code was built to dynamical classes loading for initializing available commands in the program class
       but there was a problem to get files ".class" for jar archive (solution was found but not realized in code due to lack of me knowledge)
       You can find some examples on how people do this, research it and make your own method. Links are:
        https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
        https://www.cyberforum.ru/java-j2se/thread2752626.html
       GOOD LUCK FOR FUTURE ME))
     */
    private static final String PKG_SEPARATOR = ".";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String SOURCE_PATH = System.getProperty("user.dir");
    private static final String CLASS_FILE_SUFFIX = ".java";

    public static List<Class<?>> getClassFromPackage(String packagePath, Predicate<Class<?>> filter) {
        /*
            Method that returns a list of classes in the given package
            Uses filter to select classes that satisfy some condition
         */
        String filePath = SOURCE_PATH + FILE_SEPARATOR + "src" + FILE_SEPARATOR + packagePath.replace(PKG_SEPARATOR, FILE_SEPARATOR);
        File dir = new File(filePath);
//        if(!dir.isDirectory()) {throw new IllegalArgumentException(dir.getAbsolutePath() + " is not a directory.");}

        List<Class<?>> classes = new ArrayList<>();
        File[] files = dir.listFiles();
        if(files == null) {return classes;}

        for(File file : files) {
            String fileName = file.getName();
            if(file.isDirectory()) {
                classes.addAll(getClassFromPackage(packagePath + PKG_SEPARATOR + fileName, filter));
            } else if(fileName.endsWith(CLASS_FILE_SUFFIX)) {
                String className = fileName.substring(0, fileName.length() - CLASS_FILE_SUFFIX.length());
                try {
                    Class<?> cls = Class.forName(packagePath + PKG_SEPARATOR + className);
                    if(filter.test(cls)) {classes.add(cls);}
                } catch(ClassNotFoundException ignore) {} // such situation is impossible, as className is a name of the existing file
            }
        }
        return classes;
    }
}
