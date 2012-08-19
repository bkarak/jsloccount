package org.jsloc.resources.statistics;

/**
 * 
 * 
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 *
 */
public class Marker {
    private String startingMarker;
    private String endingMarker;
    private boolean singleCommentMarker;
    
    public Marker(String scm, String ecm) {
        this.startingMarker = scm;
        this.endingMarker = ecm;
        this.singleCommentMarker = false;
    }
    
    public Marker(String cm) {
        this(cm, cm);
        this.singleCommentMarker = true;
    }
    
    public String getStartingMarker() {
        return startingMarker;
    }
    
    public String getEndingMarker() {
        return endingMarker;
    }
    
    public boolean isSingleCommentMarker() {
        return singleCommentMarker;
    }
}
