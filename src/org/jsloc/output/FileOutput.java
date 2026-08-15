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
import org.jsloc.project.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.jsloc.Configuration.logError;
import static org.jsloc.Configuration.logInfo;

/**
 * Writes the two CSV reports into the current working directory.
 *
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 */
public class FileOutput extends AbstractOutput {

    public FileOutput(ProjectStatistics ps) {
        super(ps);
    }

    @Override
    public void produce() {
        StringBuilder fileStatistics = new StringBuilder("Resource Type,File Count,Total File Count\n");

        for (ResourceValue value : getResourcesByFiles()) {
            if (value.resource() == Resource.OTHER) { continue; }

            fileStatistics.append(row(value.resource().displayName(),
                                      value.value(),
                                      projectStatistics.totalFileCount()));
        }

        // getResourcesByLoc() is text-only, so binaries stay out of the size report
        StringBuilder sizeStatistics = new StringBuilder("Resource Type,Source Lines of Code,Comments Lines of Code\n");

        for (ResourceValue value : getResourcesByLoc()) {
            sizeStatistics.append(row(value.resource().displayName(),
                                      value.value(),
                                      projectStatistics.commentLines(value.resource())));
        }

        String projectName = projectStatistics.projectName();

        saveToFile(fileStatistics, projectName + "-filestats.csv");
        saveToFile(sizeStatistics, projectName + "-sizestats.csv");
    }

    private static String row(String name, long first, long second) {
        return String.join(",", name, String.valueOf(first), String.valueOf(second)) + "\n";
    }

    private static void saveToFile(StringBuilder contents, String filename) {
        try {
            Files.writeString(Path.of(filename), contents, StandardCharsets.UTF_8);
            logInfo(filename + " created!");
        } catch (IOException ioe) {
            logError("Failed to create ... " + filename + " (" + ioe.getMessage() + ")");
        }
    }
}
