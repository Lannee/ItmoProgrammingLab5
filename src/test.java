package src;

import java.util.Map;

public class test {
    public static void main(String[] args) {
        Map<String, String> arv = System.getenv();

        arv.forEach((key, value) -> System.out.println(key + ": " + value));
//        System.out.println(arv.get("File"));
    }
}
