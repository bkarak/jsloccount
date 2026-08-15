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

import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jsloc.output.OutputFactory;
import org.jsloc.project.ProjectStatistics;
import org.jsloc.project.Resource;

import static java.util.stream.Collectors.joining;
import static org.jsloc.Configuration.logError;
import static org.jsloc.Configuration.logInfo;

/**
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 */
public class Main {

    /** How many unrecognized suffixes to name before trailing off. */
    private static final int UNKNOWN_REPORTED = 10;

    private static final int OK = 0;
    private static final int FAILED = 1;
    private static final int MISUSED = 2;

    public static void main(String[] args) {
        System.exit(run(args));
    }

    static int run(String[] args) {
        Options options;

        try {
            options = Options.parse(args);
        } catch (Options.UsageException ue) {
            logError(ue.getMessage());
            help(System.err);
            return MISUSED;
        }

        Configuration.setQuiet(options.quiet());

        switch (options.action()) {
            case HELP -> { help(System.out); return OK; }
            case VERSION -> { System.out.println("jsloccount " + version()); return OK; }
            case LIST_LANGUAGES -> { listLanguages(); return OK; }
            case SCAN -> { /* below */ }
        }

        if (!Files.isDirectory(options.directory())) {
            logError(options.directory().toAbsolutePath() + " is not a directory");
            return FAILED;
        }

        ProjectStatistics statistics =
                new ProjectStatistics(options.directory(), options.excluded(), options.hidden());

        String name = options.name() == null ? statistics.projectName() : options.name();

        if (options.toStdout()) {
            OutputFactory.getStreamOutput(statistics, System.out).produce();
        } else {
            OutputFactory.getFileOutput(statistics, options.output(), name).produce();
        }

        reportUnknown(statistics);
        return OK;
    }

    private static void help(PrintStream out) {
        out.println("""
            jsloccount - calculate size metrics for the source in a directory tree

            usage: java -jar jsloccount.jar [options] <directory>

            options:
              -o, --output <dir>    write the reports into <dir> (default: the working directory)
              -n, --name <name>     base name for the report files (default: the scanned directory)
                  --stdout          write one combined report to standard output instead of files
              -x, --exclude <name>  skip files and directories called <name>; repeatable
                  --include-hidden  scan hidden files and directories too
              -q, --quiet           suppress progress messages
                  --list-languages  list every recognized file type and exit
              -h, --help            show this help and exit
              -V, --version         show the version and exit

            Reports are written as <name>-filestats.csv and <name>-sizestats.csv.
            Progress messages go to standard error, so --stdout can be piped.""");
    }

    private static void listLanguages() {
        for (Resource.Category category : Resource.Category.values()) {
            List<Resource> members = Arrays.stream(Resource.values())
                                           .filter(resource -> resource != Resource.OTHER)
                                           .filter(resource -> resource.category() == category)
                                           .toList();

            if (members.isEmpty()) { continue; }

            System.out.println(category.title());
            members.forEach(resource -> System.out.println("  " + resource));
            System.out.println();
        }
    }

    private static String version() {
        String declared = Main.class.getPackage().getImplementationVersion();
        return declared == null ? "(development build)" : declared;
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
