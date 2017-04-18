package io.github.accessun.largesort.handler;

public abstract class AbstractLargeSortHandler implements LargeSortHandler {

    protected LargeSortHandler handler;

    @Override
    public void setSuccessor(LargeSortHandler handler) {
        this.handler = handler;
    }

}
