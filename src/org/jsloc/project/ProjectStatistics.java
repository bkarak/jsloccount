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

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToLongFunction;

import org.jsloc.resources.statistics.Statistics;

import static org.jsloc.Configuration.logError;
import static org.jsloc.Configuration.logWarn;

/**
 * Size metrics for every {@link Resource} found underneath a project directory.
 *
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 */
public class ProjectStatistics {
    private final Map<Resource, LanguageStatistics> stats = new EnumMap<>(Resource.class);
    private final Map<String, Long> unknown = new HashMap<>();
    private final String projectName;

    public ProjectStatistics(Path directory) {
        Path absolute = directory.toAbsolutePath().normalize();
        Path name = absolute.getFileName();

        // getFileName() is null for a filesystem root, e.g. "/"
        this.projectName = (name == null ? absolute.toString() : name.toString()).trim();
        walk(directory);
    }

    private void walk(Path root) {
        try {
            Files.walkFileTree(root, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) throws IOException {
                    boolean skip = !directory.equals(root) && Files.isHidden(directory);
                    return skip ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                    if (!Files.isHidden(file)) {
                        count(file);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException failure) {
                    // unreadable entries and symlink loops must not abort the whole scan
                    logWarn("Skipping " + file + " (" + failure.getMessage() + ")");
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ioe) {
            logError("Cannot walk " + root + " (" + ioe.getMessage() + ")");
        }
    }

    private void count(Path file) {
        String fileName = file.getFileName().toString();
        Resource resource = Resource.detect(fileName);
        LanguageStatistics languageStatistics = stats.computeIfAbsent(resource, key -> new LanguageStatistics());

        languageStatistics.addFile();

        if (resource == Resource.OTHER) {
            unknown.merge(suffixOf(fileName), 1L, Long::sum);
            return;
        }

        if (resource.isBinary()) { return; }

        languageStatistics.add(Statistics.count(file, resource));
    }

    /** The suffix a report should blame for an unrecognized file. */
    private static String suffixOf(String fileName) {
        int dot = fileName.lastIndexOf('.');

        // a leading dot is a hidden file, not an extension, and those never reach here
        return dot <= 0 ? "(no extension)" : fileName.substring(dot);
    }

    public String projectName() {
        return projectName;
    }

    public long sourceLines(Resource resource) {
        return get(resource, LanguageStatistics::sourceLines);
    }

    public long commentLines(Resource resource) {
        return get(resource, LanguageStatistics::commentLines);
    }

    public long totalLines(Resource resource) {
        return get(resource, LanguageStatistics::totalLines);
    }

    public long fileCount(Resource resource) {
        return get(resource, LanguageStatistics::fileCount);
    }

    public long totalFileCount() {
        return stats.values().stream().mapToLong(LanguageStatistics::fileCount).sum();
    }

    /** The resources actually present in the project, in {@link Resource} declaration order. */
    public List<Resource> resources() {
        return List.copyOf(stats.keySet());
    }

    /**
     * How many files of each unrecognized suffix landed in {@link Resource#OTHER},
     * most frequent first. This is the evidence for which file type to teach the
     * tool next, so it is worth looking at after a scan of an unfamiliar project.
     */
    public List<Map.Entry<String, Long>> unknownSuffixes() {
        return unknown.entrySet().stream()
                      .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                                       .thenComparing(Map.Entry.comparingByKey()))
                      .toList();
    }

    private long get(Resource resource, ToLongFunction<LanguageStatistics> field) {
        LanguageStatistics languageStatistics = stats.get(resource);
        return languageStatistics == null ? 0 : field.applyAsLong(languageStatistics);
    }
}
