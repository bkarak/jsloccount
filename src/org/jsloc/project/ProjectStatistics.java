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
            for ( File f : d.listFiles() ) {
                if (f.isHidden()) { continue; }
                if (f.isDirectory()) { update(f); continue; }
                
                Resource l = Resource.detect(f.getAbsolutePath());
                LanguageStatistics ls = null;
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