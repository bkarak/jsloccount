package org.jsloc;

/**
 * Console logging for the whole tool.
 */
public final class Configuration {

    private Configuration() {}

    public static void logError(String msg) {
        System.out.println("[ERRO] " + msg);
    }

    public static void logInfo(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public static void logWarn(String msg) {
        System.out.println("[WARN] " + msg);
    }
}
