package org.jsloc;

import org.jsloc.logger.Logger;
import org.jsloc.logger.NativeLogger;

/**
 * 
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 */
public class Configuration {
    private final static Configuration defaultInstance;
    //
    private Logger logger;
    
    static {
        defaultInstance = new Configuration();
    }
    
    public Configuration() {
        this.logger = new NativeLogger();
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public static Configuration getInstance() {
        return defaultInstance;
    }
}
