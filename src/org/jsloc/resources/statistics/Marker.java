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

import java.util.Objects;

/**
 * A comment delimiter pair. A marker whose start and end are identical (for
 * example {@code //} or {@code #}) comments out the rest of the line.
 *
 * @param start    the text opening the comment
 * @param end      the text closing it, equal to {@code start} for a line comment
 * @param position where on the line the marker is allowed to open a comment
 * @param nests    whether a second opening inside the comment must be closed
 *                 before the comment ends, as Rust and Haskell require
 *
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 */
public record Marker(String start, String end, Marker.Position position, boolean nests) {

    /** Where on a line a marker may open a comment. */
    public enum Position {
        /** Anywhere it appears, which is the usual case. */
        ANYWHERE,
        /** Only as the very first character of the untrimmed line. */
        COLUMN_ONE,
        /** Only as the first non-blank character of the line. */
        LINE_START
    }

    public Marker {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        Objects.requireNonNull(position, "position");
    }

    public Marker(String start, String end) {
        this(start, end, Position.ANYWHERE, false);
    }

    public Marker(String marker) {
        this(marker, marker, Position.ANYWHERE, false);
    }

    /**
     * A line comment that only counts in column one of the untrimmed line, as
     * fixed-form Fortran requires of its {@code C} and {@code *} markers. Anywhere
     * else those characters are ordinary code.
     */
    public static Marker inColumnOne(String marker) {
        return new Marker(marker, marker, Position.COLUMN_ONE, false);
    }

    /**
     * A line comment that only counts where the line's text begins, indentation
     * aside. Vim Script needs this: a leading {@code "} opens a comment, while the
     * same character mid-line is usually a string delimiter.
     */
    public static Marker atLineStart(String marker) {
        return new Marker(marker, marker, Position.LINE_START, false);
    }

    /**
     * A block comment that nests, so {@code /* a /* b *&#47; c *&#47;} is one comment
     * rather than one that ended early. Most C-family languages do <em>not</em> nest;
     * Rust, Swift, Scala, Kotlin, Dart, Haskell, OCaml, F# and Julia do.
     */
    public static Marker nesting(String start, String end) {
        return new Marker(start, end, Position.ANYWHERE, true);
    }

    /** Whether this marker is restricted to the start of a line. */
    public boolean isPositional() {
        return position != Position.ANYWHERE;
    }

    public boolean isSingleLine() {
        return start.equals(end);
    }
}
