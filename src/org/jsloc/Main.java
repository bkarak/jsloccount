package org.jsloc;

import java.io.File;

import org.jsloc.logger.Logger;
import org.jsloc.output.AbstractOutputFactory;
import org.jsloc.project.ProjectStatistics;
import org.jsloc.resources.Resource;

/**
 * 
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 *
 */
public class Main {
    
    private static void help() {
        Logger logger = Configuration.getInstance().getLogger();
        
        logger.message("JSLoCcount - Vassilios Karakoidas (bkarak@aueb.gr)\n");
        logger.message("usage:\n");
        logger.message("java -jar jsloccount.jar <directory>\n");
        
        logger.message("Supported Languages:\n");
        for ( Resource r : Resource.values() ) {
            logger.message("* " + r.toString());
        }        
    }

    public static void main(String[] args) {
        Logger logger = Configuration.getInstance().getLogger();
        
        if(args.length == 2) {
            if(args[0].compareToIgnoreCase("-d") == 0) {
                File f = new File(args[1]);
                if(f.exists() && f.isDirectory()) {
                    File[] files = f.listFiles();
                    if (files == null) {
                        return;
                    }

                    for ( File ff : files ) {
                        if(ff.isDirectory()) {
                            main(new String[] { ff.getAbsolutePath() });
                        }
                    }
                }

                return;
            }
        }
        
        if(args.length == 1) {        
            File f = new File(args[0]);
            if(f.exists() && f.isDirectory()) {
                ProjectStatistics ps = new ProjectStatistics(f);
                AbstractOutputFactory.getStandardOutput(ps).produce();
            } else {
                logger.error("ERROR: " + f.getAbsolutePath() + " is not a directory");
            }
            
            return;
        }
        
        help();
    }
}
