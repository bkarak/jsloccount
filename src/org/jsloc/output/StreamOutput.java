package org.jsloc.output;

import org.jsloc.project.ProjectStatistics;

import java.io.PrintStream;

/**
 * Writes the combined report to one stream as a single valid CSV document, so that a
 * piped run can be read by any CSV consumer.
 *
 * @author Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 */
public class StreamOutput extends AbstractOutput {
    private final PrintStream stream;

    public StreamOutput(ProjectStatistics ps, PrintStream stream) {
        super(ps);
        this.stream = stream;
    }

    @Override
    public void produce() {
        stream.print(combinedStatistics());
        stream.flush();
    }
}
