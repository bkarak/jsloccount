package org.jsloc.logger;

/**
 * 
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 *
 */
public class NativeLogger implements Logger {

    public NativeLogger() {}
    
    @Override
    public void error(String msg) {
        System.out.println(msg);
    }

    @Override
    public void message(String msg) {
        System.out.println(msg);
    }

    @Override
    public void warning(String msg) {
        System.out.println(msg);
    }

}
