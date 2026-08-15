package org.jsloc.resources.statistics;

import java.util.Objects;

/**
 * A string literal delimiter pair. Text inside one is neither code to be searched
 * for comment markers nor a comment: {@code "http://example.com"} holds no comment,
 * however much its contents look like one.
 *
 * @param open       the text opening the literal
 * @param close      the text closing it, usually identical to {@code open}
 * @param escapable  whether a backslash escapes the following character, so that
 *                   {@code "a\"b"} is one literal rather than two
 * @param spansLines whether the literal may continue onto the following line, as a
 *                   Java text block, Python docstring or JavaScript template does
 * @param commentIfUnterminated whether an opening that never closes on the line was
 *                   in fact a comment marker, which is how Vim Script tells its two
 *                   uses of {@code "} apart
 *
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 */
public record Quote(String open, String close, boolean escapable, boolean spansLines,
                    boolean commentIfUnterminated) {

    public Quote {
        Objects.requireNonNull(open, "open");
        Objects.requireNonNull(close, "close");
    }

    public Quote(String open, String close, boolean escapable, boolean spansLines) {
        this(open, close, escapable, spansLines, false);
    }

    /** A literal on one line in which a backslash escapes the next character. */
    public static Quote escaped(String delimiter) {
        return new Quote(delimiter, delimiter, true, false);
    }

    /** A literal on one line that takes no escapes, such as a shell or SQL single quote. */
    public static Quote raw(String delimiter) {
        return new Quote(delimiter, delimiter, false, false);
    }

    /** A literal that may continue across lines, such as a Python docstring. */
    public static Quote spanning(String delimiter) {
        return new Quote(delimiter, delimiter, true, true);
    }

    /**
     * A delimiter that opens a literal when it closes on the same line and a comment
     * when it does not. Vim Script spells both with {@code "}: the quote in
     * {@code if exists("x")} is a string, the one in {@code set number  " why} is not.
     */
    public static Quote stringOrComment(String delimiter) {
        return new Quote(delimiter, delimiter, true, false, true);
    }
}
