package io.github.accessun.largesort.handler;

import io.github.accessun.largesort.model.MetaInfo;

public interface LargeSortHandler {

    void setSuccessor(LargeSortHandler handler);

    MetaInfo handle(MetaInfo info);

}
