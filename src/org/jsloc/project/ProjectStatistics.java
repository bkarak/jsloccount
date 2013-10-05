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
package org.jsloc.project;

import java.io.File;

import java.util.HashMap;
import java.util.Map;


import org.jsloc.resources.Resource;
import org.jsloc.resources.statistics.Statistics;

/**
 * 
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 */
public class ProjectStatistics {
    private Map<Resource, LanguageStatistics> stats;
    private String projectName;
    
    public ProjectStatistics(File d) {
        this.stats = new HashMap<Resource, LanguageStatistics>();
        this.projectName = d.getName().trim();
        update(d);
    } 
    
    private void update(File d) {
        if(d.isDirectory()) {
            File[] fileList = d.listFiles();

            if (fileList == null){
                return;
            }

            for ( File f : fileList ) {
                if (f.isHidden()) { continue; }
                if (f.isDirectory()) { update(f); continue; }
                
                Resource l = Resource.detect(f.getAbsolutePath());
                LanguageStatistics ls;

                if(stats.containsKey(l)) {
                    ls = stats.get(l);
                } else {
                    ls = new LanguageStatistics();
                    stats.put(l, ls);
                }
                
                ls.noFiles++;
                if(l.equals(Resource.OTHER)) { continue; }
                if(l.isBinary()) { continue; }
                
                Statistics st = new Statistics(f, l);
                                
                ls.loc += st.getLOC();
                ls.locom += st.getLOCOM();
                ls.totalLoc += st.getTotalLines();           
            }
        }
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public long getLOC(Resource l) {
        if(stats.containsKey(l)) {
            return stats.get(l).loc;
        }
        
        return 0;
    }
    
    public long getLOCOM(Resource l) {
        if(stats.containsKey(l)) {
            return stats.get(l).locom;
        }
        
        return 0;
    }
    
    public long getTotalLines() {
        long tloc = 0;
        
        for ( Resource l : Resource.values() ) {
            tloc += getLOC(l);
        }
        
        return tloc;
    }
    
    public long getFileCount(Resource l) {
        if(stats.containsKey(l)) {
            return stats.get(l).noFiles;
        }
        
        return 0;
    }
    
    public long getTotalFileCount() {
        long totalFiles = 0;
        
        for ( Resource l : stats.keySet() ) {
            totalFiles += getFileCount(l);
        }
        
        return totalFiles;
    }
    
    public Resource[] getResources() {
        return stats.keySet().toArray(new Resource[] {});
    }
} 

class LanguageStatistics {
    long loc, noFiles, locom, totalLoc; 
    
    public LanguageStatistics() {
        this.loc = 0;
        this.noFiles = 0;
        this.locom = 0;
        this.totalLoc = 0;
    }
}