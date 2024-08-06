package org.jsloc;


public class Configuration {
    private final static Configuration defaultInstance;

    static {
        defaultInstance = new Configuration();
    }
    
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
    
    public static Configuration getInstance() {
        return defaultInstance;
    }
}
