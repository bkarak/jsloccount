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

import org.jsloc.StringUtil;
import org.jsloc.project.ProjectStatistics;
import org.jsloc.project.Resource;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.jsloc.Configuration.logError;
import static org.jsloc.Configuration.logInfo;

/**
 * 
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 *
 */
public class FileOutput extends AbstractOutput{
    
    public FileOutput(ProjectStatistics ps) {
        super(ps);
    }

    @Override
    public void produce() {        
        long totalFiles = this.projectStatistics.getTotalFileCount();
        StringBuilder fileStatistics = new StringBuilder(), sizeStatistics = new StringBuilder();

        fileStatistics.append("Resource Type,File Count,Total File Count\n");
        sizeStatistics.append("Resource Type,Source Lines of Code,Comments Lines of Code\n");
       
        for ( ResourceValue lv : sortedFiles ) {
            if (lv.getResource() == Resource.OTHER) { continue; }

            List<String> values = Arrays.asList(lv.getResource().getName(), String.valueOf(lv.getValue()), String.valueOf(totalFiles));
            fileStatistics.append(StringUtil.join(",", values)).append("\n");
        }

        for ( ResourceValue lv : sortedLoC ) {
            List<String> values = Arrays.asList(lv.getResource().getName(),
                                                String.valueOf(lv.getValue()),
                                                String.valueOf(this.projectStatistics.getLOCOM(lv.getResource())));
            sizeStatistics.append(StringUtil.join(",", values)).append("\n");
        }

        this.saveToFile(fileStatistics, this.projectStatistics.getProjectName() + "-filestats.csv");
        this.saveToFile(sizeStatistics, this.projectStatistics.getProjectName() + "-sizestats.csv");
    }

    private void saveToFile(StringBuilder strbld, String filename) {
        try {
            FileWriter fw = new FileWriter(filename, false);
            fw.write(strbld.toString());
            fw.close();
            logInfo(filename + " created!");
        } catch (IOException ioe) {
            logError("Failed to create ... " + filename + " (" + ioe.getMessage() + ")");
        }
    }
}
