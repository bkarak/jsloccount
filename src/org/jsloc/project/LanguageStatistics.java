package org.jsloc.project;

import org.jsloc.resources.statistics.Statistics;

/**
 * Mutable running totals for one {@link Resource} across a whole project.
 */
final class LanguageStatistics {
    private long sourceLines;
    private long commentLines;
    private long totalLines;
    private long fileCount;

    void addFile() {
        fileCount++;
    }

    void add(Statistics statistics) {
        sourceLines += statistics.sourceLines();
        commentLines += statistics.commentLines();
        totalLines += statistics.totalLines();
    }

    long sourceLines() {
        return sourceLines;
    }

    long commentLines() {
        return commentLines;
    }

    long totalLines() {
        return totalLines;
    }

    long fileCount() {
        return fileCount;
    }
}
