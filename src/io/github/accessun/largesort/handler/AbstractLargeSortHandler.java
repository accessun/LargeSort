package io.github.accessun.largesort.handler;

import io.github.accessun.largesort.model.MetaInfo;

public abstract class AbstractLargeSortHandler implements LargeSortHandler {

    protected LargeSortHandler successor;

    @Override
    public void setSuccessor(LargeSortHandler handler) {
        this.successor = handler;
    }

    /**
     * <p>
     * This method is intended to be used by the handler methods of each
     * inherited class of {@code AbstractLargeSortHandler} to forward request to
     * the its successor handler. The invoker of this method need not to be
     * concerned about whether its successor exists or not. If the invoker had
     * not successor, this method simply returns the {@code MetaInfo} object
     * passed to it.
     *
     * <p>
     * Logging is enabled by default. If you want to disable logging, see
     * {@link AbstractLargeSortHandler#forward(MetaInfo, boolean)}
     *
     * @param info
     *            object of class {@code MetaInfo} to be passed on to the
     *            successor
     * @return
     */
    protected MetaInfo forward(MetaInfo info) {
        return forward(info, true);
    }

    /**
     * <p>
     * This method is intended to be used by the handler methods of each
     * inherited class of {@code AbstractLargeSortHandler} to forward request to
     * the its successor handler. The invoker of this method need not to be
     * concerned about whether its successor exists or not. If the invoker had
     * not successor, this method simply returns the {@code MetaInfo} object
     * passed to it.
     *
     * @param info
     *            object of class {@code MetaInfo} to be passed on to the
     *            successor
     * @param enableLog
     *            whether to enable logging
     * @return
     */
    protected MetaInfo forward(MetaInfo info, boolean enableLog) {
        if (enableLog) {
            String log = this.getClass().getSimpleName() + " completed. ";
            if (successor != null) {
                log += "Next handler: " + successor.getClass().getSimpleName();
            } else {
                log += "Now terminated.";
            }
            System.out.println(log);
        }
        return successor == null ? info : successor.handle(info);
    }

}
