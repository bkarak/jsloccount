package org.jsloc;

/**
 * Console logging for the whole tool.
 *
 * <p>Everything goes to standard error, leaving standard output free to carry report
 * data for {@code --stdout}.
 */
public final class Configuration {
    private static boolean quiet = false;

    private Configuration() {}

    /** Silences {@link #logInfo}; warnings and errors are always reported. */
    public static void setQuiet(boolean value) {
        quiet = value;
    }

    public static void logError(String msg) {
        System.err.println("[ERRO] " + msg);
    }

    public static void logInfo(String msg) {
        if (quiet) { return; }

        System.err.println("[INFO] " + msg);
    }

    public static void logWarn(String msg) {
        System.err.println("[WARN] " + msg);
    }
}
