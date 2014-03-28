package org.jsloc.output;

import org.jsloc.project.Resource;

import java.util.Comparator;


public class ResourceValue implements Comparator<ResourceValue> {
    private Resource resource;
    private long value;

    // Used only for comparator
    ResourceValue() {
        this.resource = null;
        this.value = 0;
    }

    ResourceValue(Resource l, long value) {
        this.resource = l;
        this.value = value;
    }

    public Resource getResource() {
        return resource;
    }

    public long getValue() {
        return value;
    }

    @Override
    public int compare(ResourceValue lvalone, ResourceValue lvaltwo) {
        return (new Long(lvaltwo.value)).compareTo(lvalone.value);
    }
}
