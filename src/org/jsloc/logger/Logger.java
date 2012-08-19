package org.jsloc.logger;

/**
 * 
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 *
 */
public interface Logger {
    public void message(String msg);
    
    public void warning(String msg);
    
    public void error(String msg);
}
