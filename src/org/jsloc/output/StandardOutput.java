/*
# Copyright (c) 2013, Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 All rights reserved.
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright

    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * The names of its contributors may not be used to endorse or promote products
      derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL Vassilios Karakoidas BE LIABLE FOR ANY
 DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
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

        fileStatistics.append("Resource Type,File Count,Total File Count\n");
        sizeStatistics.append("Resource Type,Source Lines of Code,Comments Lines of Code\n");
       
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
