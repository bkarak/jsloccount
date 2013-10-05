package org.jsloc.output;


import org.jsloc.project.ProjectStatistics;

public class AbstractOutputFactory {
    public static AbstractOutput getStandardOutput(ProjectStatistics ps) {
        return (new StandardOutput(ps));
    }
}
