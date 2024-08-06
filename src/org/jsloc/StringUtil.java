package org.jsloc;


public class StringUtil {
    public static String join(String delimiter, Iterable<String> strings) {
        return String.join(delimiter, strings);
    }
}
