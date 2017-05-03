package io.github.accessun.largesort.handler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import io.github.accessun.largesort.comparator.AgeComparator;
import io.github.accessun.largesort.model.MetaInfo;
import io.github.accessun.largesort.model.Record;
import io.github.accessun.largesort.operator.DataSorter;

public class DataSorterHandler extends AbstractLargeSortHandler {

    private Comparator<Record> comparator;

    public DataSorterHandler() {}

    public DataSorterHandler(Comparator<Record> comparator) {
        this.comparator = comparator;
    }

    @Override
    public MetaInfo handle(MetaInfo info) {
        List<String> targetFiles = listSplitsFiles(info);
        DataSorter sorter = new DataSorter();
        Comparator<Record> com = comparator == null ? new AgeComparator() : comparator;
        try {
            for (String target : targetFiles) {
                sorter.sort(target, com);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return forward(info);
    }

    private List<String> listSplitsFiles(MetaInfo info) {
        List<String> fileList = new ArrayList<>();
        String baseDir = info.getBaseDir();
        String prefix = info.getSplitPrefix();
        String ext = info.getExtensionName();

        for (int i = 0; i < info.getSplits(); i++)
            fileList.add(baseDir + prefix + i + "." + ext);

        return fileList;
    }

}
