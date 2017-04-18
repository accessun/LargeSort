package io.github.accessun.largesort.handler;

import java.util.Comparator;
import java.util.List;

import io.github.accessun.largesort.comparator.AgeComparator;
import io.github.accessun.largesort.model.MetaInfo;
import io.github.accessun.largesort.model.Record;
import io.github.accessun.largesort.operator.DataSorter;

public class DataSorterHandler extends AbstractLargeSortHandler {

    private Comparator<Record> comparator;

    private List<String> targetFiles;

    public DataSorterHandler(List<String> targetFiles) {
        this.targetFiles = targetFiles;
    }

    public DataSorterHandler(Comparator<Record> comparator, List<String> targetFiles) {
        this.comparator = comparator;
        this.targetFiles = targetFiles;
    }

    @Override
    public MetaInfo handle(MetaInfo info) {
        DataSorter sorter = new DataSorter();
        Comparator<Record> com = comparator == null ? new AgeComparator() : comparator;
        try {
            for (String target : targetFiles) {
                sorter.sort(target, com);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return info;
    }

}
