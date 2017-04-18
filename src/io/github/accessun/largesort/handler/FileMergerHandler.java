package io.github.accessun.largesort.handler;

import java.util.List;

import io.github.accessun.largesort.model.MetaInfo;
import io.github.accessun.largesort.operator.FileMerger;

public class FileMergerHandler extends AbstractLargeSortHandler {

    private List<String> targetFiles;

    @Override
    public MetaInfo handle(MetaInfo info) {
        FileMerger merger = new FileMerger();

        String[] filePaths = new String[targetFiles.size()];
        for (int i = 0; i < targetFiles.size(); i++)
            filePaths[i] = targetFiles.get(i);

        try {
            merger.mergeReduce(filePaths);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return info;
    }

}
