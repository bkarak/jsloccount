package org.jsloc.resources.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import org.jsloc.Configuration;
import org.jsloc.resources.Resource;

/**
 * 
 * 
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 */
public class Statistics {
    private Resource resource;

    // statistics
    private long loc;
    private long total;
    private long locom;
    
    // Private Enumeration, used to indicate the status of the parser
    private enum Status {
        CODE,
        COMMENT,
        BOTH,
        SINGLE;
    }
    
    public Statistics(File f, Resource resource) {
        this.loc = 0;
        this.locom = 0;
        this.total = 0;
        this.resource = resource;

        // start the calculation
        Marker[] markers = resource.getCommentMarkers();
        
        List<Marker> single = new ArrayList<Marker>();
        List<Marker> complex = new ArrayList<Marker>();
        
        for ( Marker cm : markers ) {
            if(cm.isSingleCommentMarker()) {
                single.add(cm);
            } else {
                complex.add(cm);
            }
        }

        try {
            FileInputStream fis = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            Status status = Status.CODE;
            Marker cur = null;

            while (br.ready()) {
                String line = br.readLine().trim();
                int lineLen = line.length();

                // increase the total line, this includes the empty lines
                total++;
                
                // if the line is empty, then continue
                if (lineLen == 0) { continue; }                
                
                if(status == Status.CODE) {
                    // Single line markers
                    for ( Marker cm : single ) {
                        int sIndex = line.indexOf(cm.getStartingMarker());
                        if (sIndex < 0) { continue; }
                        
                        if (sIndex > 0) {
                            status = Status.BOTH;
                            break;
                        } else if (sIndex == 0) {
                            status = Status.SINGLE;
                            break;
                        }
                    }
                    
                    if(status == Status.SINGLE || status == Status.BOTH) {
                        locom++;
                        if(status == Status.BOTH) {
                            loc++;
                        }                        
                        status = Status.CODE;
                        continue;
                    }
                    // Complex line markers
                    for ( Marker cm : complex ) {
                        int sIndex = line.indexOf(cm.getStartingMarker());
                        
                        if (sIndex < 0) { continue; }

                        if (sIndex > 0) {
                            loc++;
                        }                        
                        status = Status.COMMENT;
                        cur = cm;
                        break;
                    }
                } 
                if (status == Status.COMMENT) {
                    locom++;
                    
                    int sIndex = line.indexOf(cur.getEndingMarker());
                    if(sIndex < 0) { continue; }
                    if(sIndex > 0) {
                        loc++;
                    }
                    status = Status.CODE;
                    continue;
                }
                loc++;
            }

            br.close();
        } catch (IOException ioe) {
            Configuration.getInstance().getLogger().error("ERROR: Cannot read file " + ioe.getMessage());
            return;
        }
    }

    public long getLOC() {
        return loc;
    }

    public long getLOCOM() {
        return locom;
    }

    public long getTotalLines() {
        return total;
    }

    public Resource getLanguage() {
        return resource;
    }
}