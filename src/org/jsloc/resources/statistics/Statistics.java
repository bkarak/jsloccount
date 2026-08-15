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
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.jsloc.project.Resource;

import static org.jsloc.Configuration.logError;

/**
 * Size metrics for a single text file.
 *
 * <p>A line is attributed to both counters when it carries code and a comment;
 * a block comment keeps claiming lines until its closing marker is reached.
 *
 * @param resource     the detected type of the counted file
 * @param sourceLines  lines holding code outside a comment
 * @param commentLines lines holding any part of a comment
 * @param totalLines   every line in the file, blank lines included
 *
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 */
public record Statistics(Resource resource, long sourceLines, long commentLines, long totalLines) {

    /**
     * Counts {@code file}, attributing lines according to the comment markers of
     * {@code resource}. An unreadable file is reported and counted as empty.
     */
    public static Statistics count(Path file, Resource resource) {
        List<Marker> markers = resource.commentMarkers();

        long sourceLines = 0;
        long commentLines = 0;
        long totalLines = 0;

        try (BufferedReader reader = newReader(file)) {
            // the block comment we are inside of, carried across lines; null when in code
            Marker openBlock = null;
            String raw;

            while ((raw = reader.readLine()) != null) {
                String line = raw.trim();

                // count the total lines, this includes the empty ones
                totalLines++;

                // if the line is empty, then continue to the next one; an empty
                // line inside a block comment leaves the block open
                if (line.isEmpty()) { continue; }

                // a column-one marker claims the whole raw line before it is trimmed
                if (openBlock == null && opensInColumnOne(raw, markers)) {
                    commentLines++;
                    continue;
                }

                boolean holdsCode = false;
                boolean holdsComment = false;
                int position = 0;

                while (position < line.length()) {
                    if (openBlock != null) {
                        holdsComment = true;

                        int end = line.indexOf(openBlock.end(), position);
                        if (end < 0) {
                            position = line.length();
                        } else {
                            position = end + openBlock.end().length();
                            openBlock = null;
                        }
                        continue;
                    }

                    Marker opening = null;
                    int start = -1;

                    for (Marker marker : markers) {
                        if (marker.atLineStart()) { continue; }

                        int index = line.indexOf(marker.start(), position);
                        if (index < 0) { continue; }

                        // earliest marker wins; on a tie the longer one is the more
                        // specific of an overlapping pair, such as /** against /*
                        boolean better = start < 0
                                      || index < start
                                      || (index == start && marker.start().length() > opening.start().length());

                        if (better) {
                            opening = marker;
                            start = index;
                        }
                    }

                    if (opening == null) {
                        holdsCode |= holdsText(line, position, line.length());
                        position = line.length();
                        continue;
                    }

                    holdsCode |= holdsText(line, position, start);
                    holdsComment = true;

                    if (opening.isSingleLine()) {
                        position = line.length();
                    } else {
                        openBlock = opening;
                        position = start + opening.start().length();
                    }
                }

                if (holdsCode) { sourceLines++; }
                if (holdsComment) { commentLines++; }
            }
        } catch (IOException ioe) {
            logError("Cannot read file " + ioe.getMessage());
        }

        return new Statistics(resource, sourceLines, commentLines, totalLines);
    }

    /** Whether an untrimmed line opens with one of the column-one markers. */
    private static boolean opensInColumnOne(String raw, List<Marker> markers) {
        for (Marker marker : markers) {
            if (marker.atLineStart() && raw.startsWith(marker.start())) { return true; }
        }

        return false;
    }

    /** Whether {@code line} carries anything but whitespace in {@code [from, to)}. */
    private static boolean holdsText(String line, int from, int to) {
        return !line.substring(from, to).isBlank();
    }

    /**
     * Reads as UTF-8, substituting undecodable bytes rather than failing, so that
     * a stray latin-1 or binary-ish source file cannot abort the count.
     */
    private static BufferedReader newReader(Path file) throws IOException {
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);

        return new BufferedReader(new InputStreamReader(Files.newInputStream(file), decoder));
    }
}
