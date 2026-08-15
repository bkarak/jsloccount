package org.jsloc;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * The parsed command line.
 *
 * @param action    what the invocation asked for
 * @param directory the tree to scan, meaningful only for {@link Action#SCAN}
 * @param output    where the reports are written
 * @param name      base name for the report files, or null to use the directory's
 * @param toStdout  whether to write the reports to standard output instead of files
 * @param excluded  file and directory names to skip
 * @param hidden    whether to scan hidden files and directories
 * @param quiet     whether to suppress progress messages
 *
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 */
public record Options(Action action, Path directory, Path output, String name, boolean toStdout,
                      Set<String> excluded, boolean hidden, boolean quiet) {

    /** What the invocation asked the tool to do. */
    public enum Action { SCAN, HELP, VERSION, LIST_LANGUAGES }

    /** Rejects a command line, carrying the message the user should see. */
    public static class UsageException extends IllegalArgumentException {
        private static final long serialVersionUID = 1L;

        public UsageException(String message) { super(message); }
    }

    /**
     * Parses {@code args}. Options may be written {@code --name value} or
     * {@code --name=value}, and {@code --} ends option processing.
     *
     * @throws UsageException if an option is unknown, missing its value, or the
     *                        positional directory is absent or repeated
     */
    public static Options parse(String[] args) {
        Action action = Action.SCAN;
        Path directory = null;
        Path output = Path.of(".");
        String name = null;
        boolean toStdout = false;
        Set<String> excluded = new LinkedHashSet<>();
        boolean hidden = false;
        boolean quiet = false;
        boolean options = true;

        List<String> arguments = new ArrayList<>(List.of(args));

        while (!arguments.isEmpty()) {
            String argument = arguments.remove(0);

            if (options && argument.equals("--")) {
                options = false;
                continue;
            }

            if (!options || !argument.startsWith("-") || argument.equals("-")) {
                if (directory != null) {
                    throw new UsageException("only one directory may be given, and '" + argument + "' is a second");
                }
                directory = path(argument);
                continue;
            }

            // --name=value is the same as --name value
            String option = argument;
            String inlined = null;
            int equals = argument.indexOf('=');

            if (argument.startsWith("--") && equals > 0) {
                option = argument.substring(0, equals);
                inlined = argument.substring(equals + 1);
            }

            boolean takesValue = switch (option) {
                case "-o", "--output", "-n", "--name", "-x", "--exclude" -> true;
                default -> false;
            };

            if (inlined != null && !takesValue) {
                throw new UsageException("option '" + option + "' takes no value");
            }

            switch (option) {
                case "-h", "--help" -> action = Action.HELP;
                case "-V", "--version" -> action = Action.VERSION;
                case "--list-languages" -> action = Action.LIST_LANGUAGES;
                case "--stdout" -> toStdout = true;
                case "--include-hidden" -> hidden = true;
                case "-q", "--quiet" -> quiet = true;
                case "-o", "--output" -> output = path(value(option, inlined, arguments));
                case "-n", "--name" -> name = value(option, inlined, arguments);
                case "-x", "--exclude" -> excluded.add(value(option, inlined, arguments));
                default -> throw new UsageException("unknown option '" + option + "'");
            }
        }

        if (action == Action.SCAN && directory == null) {
            throw new UsageException(args.length == 0 ? "no directory given" : "no directory given among the arguments");
        }

        return new Options(action, directory, output, name, toStdout, Set.copyOf(excluded), hidden, quiet);
    }

    private static String value(String option, String inlined, List<String> remaining) {
        if (inlined != null) { return inlined; }

        if (remaining.isEmpty()) {
            throw new UsageException("option '" + option + "' needs a value");
        }

        return remaining.remove(0);
    }

    private static Path path(String value) {
        try {
            return Path.of(value);
        } catch (InvalidPathException ipe) {
            throw new UsageException("'" + value + "' is not a valid path");
        }
    }
}
