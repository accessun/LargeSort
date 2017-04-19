package io.github.accessun.largesort.handler;

import io.github.accessun.largesort.model.MetaInfo;
import io.github.accessun.largesort.operator.FileSpliter;

public class FileSpliterHandler extends AbstractLargeSortHandler {

    @Override
    public MetaInfo handle(MetaInfo info) {
        FileSpliter spliter = new FileSpliter();
        String file = info.getBaseDir() + info.getDataFileName();
        try {
            spliter.split(file);
            info.setSplitPrefix(spliter.getPrefix());
            info.setTimestamp(spliter.getTimestamp());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return forward(info);
    }

}
