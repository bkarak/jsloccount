package org.jsloc.output;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsloc.project.ProjectStatistics;
import org.jsloc.resources.Resource;

public class IkariaOutput extends AbstractOutput {
    
    public IkariaOutput(ProjectStatistics ps) {
        super(ps);
    }

    public void produce() {
        if( sortedFiles.size() > 0) {
            Resource r = null;
            if (sortedFiles.size() > 2) {
                if (sortedFiles.get(0).getResource() == Resource.OTHER ) {
                    r = sortedFiles.get(1).getResource();
                } else { r = sortedFiles.get(0).getResource(); }
            } else {
                r = sortedFiles.get(0).getResource();
            }
            System.out.println(this.projectStatistics.getProjectName() + ", " + r);
        } else {
            System.out.println(this.projectStatistics.getProjectName() + " - Unknown");
        }
        
        StringBuilder strbld = new StringBuilder();
        
        strbld.append("Project Name: ").append(this.projectStatistics.getProjectName()).append("\n");
        strbld.append("\nNumber of Files:\n");
        long totalFiles = this.projectStatistics.getTotalFileCount();
       
        for ( ResourceValue lv : sortedFiles ) {
            if (lv.getResource() == Resource.OTHER) { continue; }
            strbld.append(lv.getResource().getName() + ", " + lv.getValue() + " / " + totalFiles).append("\n");
        }
        strbld.append(Resource.OTHER.getName() + ", " + this.projectStatistics.getFileCount(Resource.OTHER) + " / " + totalFiles).append("\n");
        
        strbld.append("\nNumber of Lines (comments):\n").append("\n");
        for ( ResourceValue lv : sortedLoC ) {
            strbld.append(lv.getResource().getName() + ", " + lv.getValue() + " (" + this.projectStatistics.getLOCOM(lv.getResource()) + ")\n");
        }
        
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(this.projectStatistics.getProjectName() + ".jsloccount"));
            dos.writeChars(strbld.toString());
        } catch (IOException ioe) {
            // silent resolve
        }
    }

}
