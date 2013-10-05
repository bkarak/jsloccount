package org.jsloc.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.jsloc.project.ProjectStatistics;
import org.jsloc.resources.Resource;

/**
 *
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 *
 */
public abstract class AbstractOutput {
    protected ProjectStatistics projectStatistics;
    protected ArrayList<ResourceValue> sortedLoC;
    protected ArrayList<ResourceValue> sortedFiles;
     
    protected AbstractOutput(ProjectStatistics ps) {
        this.projectStatistics = ps;
        Resource[] resources = ps.getResources();
        
        // sort by loc 
        sortedLoC = new ArrayList<ResourceValue>();
        sortedFiles = new ArrayList<ResourceValue>();
        for ( Resource r : resources ) {
            if(r.isText()) {
                sortedLoC.add(new ResourceValue(r, ps.getLOC(r)));
            }
            sortedFiles.add(new ResourceValue(r, ps.getFileCount(r)));
        }
        Collections.sort(sortedLoC, new ResourceValue());
        Collections.sort(sortedFiles, new ResourceValue());
    }
    
    public abstract void produce();
}

class ResourceValue implements Comparator<ResourceValue> {
    private Resource resource;
    private long value;
    
    // Used only for comparator
    ResourceValue() {
        this.resource = null;
        this.value = 0;
    }
    
    ResourceValue(Resource l, long value) {
        this.resource = l;
        this.value = value;
    }
    
    public Resource getResource() {
        return resource;
    }
    
    public long getValue() {
        return value;
    }

    @Override
    public int compare(ResourceValue lvalone, ResourceValue lvaltwo) {
        return (new Long(lvaltwo.value)).compareTo(lvalone.value);
    }
}