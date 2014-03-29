package org.jsloc;


public class StringUtil {
    public static String join(String delimiter, Iterable<String> strings) {
        StringBuilder strbld = new StringBuilder();
        strings.forEach((item) -> { strbld.append(item).append(delimiter); });
        return strbld.toString().substring(0, strbld.length() - 2);
    }
}
