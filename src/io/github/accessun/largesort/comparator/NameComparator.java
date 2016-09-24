package io.github.accessun.largesort.comparator;

import java.util.Comparator;

import io.github.accessun.largesort.model.Record;

public class NameComparator implements Comparator<Record> {

    @Override
    public int compare(Record o1, Record o2) {
        return o1.getName().compareTo(o2.getName());
    }
    
}
