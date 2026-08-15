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

import java.util.List;

import org.jsloc.project.ProjectStatistics;
import org.jsloc.project.Resource;

/**
 * Turns a {@link ProjectStatistics} into the two ranked views every output format
 * reports on, and leaves the rendering to its subclasses.
 *
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 */
public abstract class AbstractOutput {
    protected final ProjectStatistics projectStatistics;
    private final List<ResourceValue> byLines;
    private final List<ResourceValue> byFiles;

    protected AbstractOutput(ProjectStatistics ps) {
        this.projectStatistics = ps;

        List<Resource> resources = ps.resources();

        this.byLines = resources.stream()
                                .filter(Resource::isText)
                                .map(resource -> new ResourceValue(resource, ps.sourceLines(resource)))
                                .sorted(ResourceValue.BY_VALUE_DESCENDING)
                                .toList();

        this.byFiles = resources.stream()
                                .map(resource -> new ResourceValue(resource, ps.fileCount(resource)))
                                .sorted(ResourceValue.BY_VALUE_DESCENDING)
                                .toList();
    }

    public List<ResourceValue> getResourcesByLoc() { return byLines; }

    public List<ResourceValue> getResourcesByFiles() { return byFiles; }

    public abstract void produce();
}
