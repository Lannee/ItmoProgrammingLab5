package src.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringConverter {
    public final static Map<Class<?>, Function<String, ?>> methodForType = new HashMap<>();

    static {
        methodForType.put(float.class, Float::parseFloat);
        methodForType.put(Float.class, Float::parseFloat);
        methodForType.put(int.class, Integer::parseInt);
        methodForType.put(Integer.class, Integer::parseInt);
        methodForType.put(long.class, Long::parseLong);
        methodForType.put(Long.class, Long::parseLong);
        methodForType.put(String.class, e -> e);
        methodForType.put(Date.class, e -> {
            Pattern pattern = Pattern.compile("^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
            Matcher matcher = pattern.matcher(e);
            if(!matcher.find()) throw new NumberFormatException();
            return new Date(Integer.parseInt(matcher.group(1)) - 1900, Integer.parseInt(matcher.group(3)) - 1, Integer.parseInt(matcher.group(4)));
        });
    }
}
