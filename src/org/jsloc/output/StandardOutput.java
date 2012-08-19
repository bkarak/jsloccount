package org.jsloc.output;

import org.jsloc.project.ProjectStatistics;
import org.jsloc.resources.Resource;

/**
 * 
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 *
 */
public class StandardOutput extends AbstractOutput{
    
    public StandardOutput(ProjectStatistics ps) {
        super(ps);
    }

    @Override
    public void produce() {        
        System.out.println("Number of Files:\n");
        long totalFiles = this.projectStatistics.getTotalFileCount();
       
        for ( ResourceValue lv : sortedFiles ) {
            if (lv.getResource() == Resource.OTHER) { continue; }
            System.out.println(lv.getResource().getName() + ", " + lv.getValue() + " / " + totalFiles);
        }
        System.out.println(Resource.OTHER.getName() + ", " + this.projectStatistics.getFileCount(Resource.OTHER) + " / " + totalFiles);
        
        System.out.println("\nNumber of Lines (comments):\n");
        for ( ResourceValue lv : sortedLoC ) {
            System.out.println(lv.getResource().getName() + ", " + lv.getValue() + " (" + this.projectStatistics.getLOCOM(lv.getResource()) + ")");
        }
    }
}
