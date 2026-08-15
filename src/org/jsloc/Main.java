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
package org.jsloc;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.jsloc.output.OutputFactory;
import org.jsloc.project.ProjectStatistics;
import org.jsloc.project.Resource;

import static java.util.stream.Collectors.joining;
import static org.jsloc.Configuration.logInfo;

/**
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 */
public class Main {

    /** How many unrecognized suffixes to name before trailing off. */
    private static final int UNKNOWN_REPORTED = 10;

    private static void help() {
        logInfo("JSLoCcount - Vassilios Karakoidas (bkarak@aueb.gr)\n");
        logInfo("usage:\n");
        logInfo("java -jar jsloccount.jar <directory>\n");

        logInfo("Supported Languages:\n");
        for (Resource resource : Resource.values()) {
            logInfo("* " + resource);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            help();
            return;
        }

        Path directory;

        try {
            directory = Path.of(args[0]);
        } catch (InvalidPathException ipe) {
            logInfo("ERROR: " + args[0] + " is not a valid path");
            return;
        }

        if (!Files.isDirectory(directory)) {
            logInfo("ERROR: " + directory.toAbsolutePath() + " is not a directory");
            return;
        }

        ProjectStatistics statistics = new ProjectStatistics(directory);

        OutputFactory.getFileOutput(statistics).produce();
        reportUnknown(statistics);
    }

    /**
     * Names the most common file types the tool did not recognize, so that the gap
     * in {@link Resource} is visible rather than buried in the "Other" bucket.
     */
    private static void reportUnknown(ProjectStatistics statistics) {
        List<Map.Entry<String, Long>> unknown = statistics.unknownSuffixes();

        if (unknown.isEmpty()) { return; }

        long files = unknown.stream().mapToLong(Map.Entry::getValue).sum();
        String top = unknown.stream()
                            .limit(UNKNOWN_REPORTED)
                            .map(entry -> entry.getKey() + " (" + entry.getValue() + ")")
                            .collect(joining(", "));

        logInfo(unknown.size() + " unrecognized file " + (unknown.size() == 1 ? "type" : "types")
                + " covering " + files + " " + (files == 1 ? "file" : "files") + ": " + top
                + (unknown.size() > UNKNOWN_REPORTED ? ", ..." : ""));
    }
}
