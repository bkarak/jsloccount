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
 * @param start       the text opening the comment
 * @param end         the text closing it, equal to {@code start} for a line comment
 * @param atLineStart whether the marker only opens a comment in column one
 * @param nests       whether a second opening inside the comment must be closed
 *                    before the comment ends, as Rust and Haskell require
 *
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 */
public record Marker(String start, String end, boolean atLineStart, boolean nests) {

    public Marker {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
    }

    public Marker(String start, String end) {
        this(start, end, false, false);
    }

    public Marker(String marker) {
        this(marker, marker, false, false);
    }

    /**
     * A line comment that only counts in column one of the untrimmed line, as
     * fixed-form Fortran requires of its {@code C} and {@code *} markers. Anywhere
     * else those characters are ordinary code.
     */
    public static Marker inColumnOne(String marker) {
        return new Marker(marker, marker, true, false);
    }

    /**
     * A block comment that nests, so {@code /* a /* b *&#47; c *&#47;} is one comment
     * rather than one that ended early. Most C-family languages do <em>not</em> nest;
     * Rust, Swift, Scala, Kotlin, Dart, Haskell, OCaml, F# and Julia do.
     */
    public static Marker nesting(String start, String end) {
        return new Marker(start, end, false, true);
    }

    public boolean isSingleLine() {
        return start.equals(end);
    }
}
