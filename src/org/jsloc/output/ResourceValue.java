package org.jsloc.output;

import org.jsloc.project.Resource;

import java.util.Comparator;

/**
 * One measured number (lines or files) attributed to a {@link Resource}.
 */
public record ResourceValue(Resource resource, long value) {

    /** Largest first; ties keep the order they were collected in. */
    static final Comparator<ResourceValue> BY_VALUE_DESCENDING =
            Comparator.comparingLong(ResourceValue::value).reversed();
}
