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
package org.jsloc.resources.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import org.jsloc.project.Resource;

import static org.jsloc.Configuration.logError;

/**
 * 
 * 
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 */
public class Statistics {
    private Resource resource;

    // statistics
    private long linesOfCode;
    private long totalLinesOfCode;
    private long linesOfCommentCode;
    private long characterCount;
    private long commentCharacterCount;
    private long totalCharacterCount;

    // Private Enumeration, used to indicate the status of the parser
    private enum Status { CODE, COMMENT, BOTH, SINGLE }
    
    public Statistics(File f, Resource resource) {
        this.linesOfCode = 0;
        this.linesOfCommentCode = 0;
        this.totalLinesOfCode = 0;
        this.resource = resource;
        this.characterCount = 0;
        this.totalCharacterCount = 0;
        this.commentCharacterCount = 0;

        // start the calculation
        Marker[] markers = resource.getCommentMarkers();
        
        List<Marker> single = new ArrayList<>();
        List<Marker> complex = new ArrayList<>();
        
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
                totalLinesOfCode++;
                
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
                        linesOfCommentCode++;
                        if(status == Status.BOTH) {
                            linesOfCode++;
                        }                        
                        status = Status.CODE;
                        continue;
                    }

                    // Complex line markers
                    for ( Marker cm : complex ) {
                        int sIndex = line.indexOf(cm.getStartingMarker());
                        
                        if (sIndex < 0) { continue; }

                        if (sIndex > 0) {
                            linesOfCode++;
                        }                        
                        status = Status.COMMENT;
                        cur = cm;
                        break;
                    }
                } 
                if (status == Status.COMMENT) {
                    linesOfCommentCode++;
                    
                    int sIndex = line.indexOf(cur.getEndingMarker());
                    if(sIndex < 0) { continue; }
                    if(sIndex > 0) {
                        linesOfCode++;
                    }
                    status = Status.CODE;
                    continue;
                }
                linesOfCode++;
            }

            br.close();
        } catch (IOException ioe) {
            logError("Cannot read file " + ioe.getMessage());
        }
    }

    public long getLOC() {
        return linesOfCode;
    }

    public long getLOCOM() {
        return linesOfCommentCode;
    }

    public long getTotalLines() {
        return totalLinesOfCode;
    }

    public long getCharacterCount() { return characterCount; }

    public long getTotalCharacterCount() { return totalCharacterCount; }

    public long getCommentCharacterCount() { return commentCharacterCount; }

    public Resource getLanguage() {
        return resource;
    }
}