package org.jsloc.output;

import org.jsloc.project.ProjectStatistics;
import org.jsloc.resources.Resource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

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
        StringBuilder fileStatistics = new StringBuilder(), sizeStatistics = new StringBuilder();
       
        for ( ResourceValue lv : sortedFiles ) {
            if (lv.getResource() == Resource.OTHER) { continue; }
            System.out.println(lv.getResource().getName() + ", " + lv.getValue() + " / " + totalFiles);
            fileStatistics.append(lv.getResource().getName()).append(",").append(lv.getValue()).append(",").append(totalFiles).append("\n");
        }
        System.out.println(Resource.OTHER.getName() + ", " + this.projectStatistics.getFileCount(Resource.OTHER) + " / " + totalFiles);
        
        System.out.println("\nNumber of Lines (comments):\n");
        for ( ResourceValue lv : sortedLoC ) {
            System.out.println(lv.getResource().getName() + ", " + lv.getValue() + " (" + this.projectStatistics.getLOCOM(lv.getResource()) + ")");
            sizeStatistics.append(lv.getResource().getName()).append(",").append(lv.getValue()).append(",").append(this.projectStatistics.getLOCOM(lv.getResource())).append("\n");
        }

        this.saveToFile(fileStatistics, this.projectStatistics.getProjectName() + "-filestats.csv");
        this.saveToFile(sizeStatistics, this.projectStatistics.getProjectName() + "-sizestats.csv");
    }

    private void saveToFile(StringBuilder strbld, String filename) {
        try {
            File f = new File(filename);
            FileWriter fw = new FileWriter(filename, false);
            fw.write(strbld.toString());
            fw.close();
            System.out.println(filename + " created!");
        } catch (IOException ioe) {
            System.out.println("Failed to create ... " + filename);
        }
    }
}
