package org.jsloc.output;

/**
 * RFC 4180 field escaping.
 *
 * <p>No file type is named with a comma or a quote today, so nothing in the current
 * reports needs escaping — but a report format that only works for the values it
 * happens to hold is a trap for whoever adds the type that breaks it.
 *
 * <p>Records are separated with a line feed rather than the carriage-return pair the
 * RFC nominally requires: every reader accepts it, and it keeps the output usable in
 * a pipeline.
 *
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 */
public final class Csv {

    private Csv() {}

    /** One record, escaped and terminated. */
    public static String row(String... fields) {
        StringBuilder record = new StringBuilder();

        for (int i = 0; i < fields.length; i++) {
            if (i > 0) { record.append(','); }
            record.append(field(fields[i]));
        }

        return record.append('\n').toString();
    }

    /**
     * One record whose trailing fields are counts. Named apart from {@link #row} because
     * overloaded varargs would be ambiguous for a lone string argument.
     */
    public static String counts(String name, long... counts) {
        String[] fields = new String[counts.length + 1];
        fields[0] = name;

        for (int i = 0; i < counts.length; i++) {
            fields[i + 1] = String.valueOf(counts[i]);
        }

        return row(fields);
    }

    /** A field, quoted only when it holds a delimiter, a quote or a line break. */
    static String field(String value) {
        boolean quoted = value.indexOf(',') >= 0
                      || value.indexOf('"') >= 0
                      || value.indexOf('\n') >= 0
                      || value.indexOf('\r') >= 0;

        return quoted ? '"' + value.replace("\"", "\"\"") + '"' : value;
    }
}
