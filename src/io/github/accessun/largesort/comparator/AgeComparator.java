package io.github.accessun.largesort.comparator;

import java.util.Comparator;

import io.github.accessun.largesort.model.Record;

public class AgeComparator implements Comparator<Record> {

    @Override
    public int compare(Record o1, Record o2) {
        return o1.getAge() - o2.getAge();
    }
}
