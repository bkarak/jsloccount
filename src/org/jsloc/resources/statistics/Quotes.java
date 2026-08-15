package org.jsloc.resources.statistics;

import java.util.List;

/**
 * The string-literal styles shared by families of languages, so that a {@code Resource}
 * names its family rather than repeating delimiters.
 *
 * <p>Two exclusions are deliberate. Rust omits the single quote because {@code 'a} is a
 * lifetime, not a literal, and treating it as one would swallow the rest of the line.
 * Visual Basic omits it too, because there {@code '} opens a comment.
 *
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 */
public final class Quotes {

    private Quotes() {}

    /** No string awareness: every character is code or comment. */
    public static final List<Quote> NONE = List.of();

    /** C and its descendants: backslash-escaped double and single quotes. */
    public static final List<Quote> C_FAMILY = List.of(Quote.escaped("\""), Quote.escaped("'"));

    /** C family plus Java's text block. */
    public static final List<Quote> JAVA = List.of(Quote.spanning("\"\"\""), Quote.escaped("\""), Quote.escaped("'"));

    /** C family plus the JavaScript template literal. */
    public static final List<Quote> JAVASCRIPT = List.of(Quote.spanning("`"), Quote.escaped("\""), Quote.escaped("'"));

    /** Python's triple-quoted forms ahead of its ordinary ones. */
    public static final List<Quote> PYTHON = List.of(Quote.spanning("\"\"\""), Quote.spanning("'''"),
                                                     Quote.escaped("\""), Quote.escaped("'"));

    /** Shells: double quotes escape, single quotes are literal throughout. */
    public static final List<Quote> SHELL = List.of(Quote.escaped("\""), Quote.raw("'"));

    /** Markup attributes and SQL literals, where a backslash is an ordinary character. */
    public static final List<Quote> MARKUP = List.of(Quote.raw("\""), Quote.raw("'"));

    /** Only the double quote escapes; the single quote means something else in the language. */
    public static final List<Quote> DOUBLE_ESCAPED = List.of(Quote.escaped("\""));

    /** Only the double quote, taking no escapes. */
    public static final List<Quote> DOUBLE_RAW = List.of(Quote.raw("\""));

    /** Only the single quote, taking no escapes, as in Pascal. */
    public static final List<Quote> SINGLE_RAW = List.of(Quote.raw("'"));

    /**
     * Lua's ordinary quotes plus its long-bracket strings, whose delimiters carry a
     * level: {@code [[ ]]}, {@code [=[ ]=]} and so on, the level letting the content
     * hold any shorter closing sequence. Levels beyond three are not recognized, as
     * nothing short of deliberately nested generated code reaches them.
     */
    public static final List<Quote> LUA = List.of(new Quote("[[", "]]", false, true),
                                                  new Quote("[=[", "]=]", false, true),
                                                  new Quote("[==[", "]==]", false, true),
                                                  new Quote("[===[", "]===]", false, true),
                                                  Quote.escaped("\""), Quote.escaped("'"));
}
