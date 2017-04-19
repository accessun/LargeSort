package io.github.accessun.largesort.test;

import org.junit.Test;

import io.github.accessun.largesort.handler.DataGeneratorHandler;
import io.github.accessun.largesort.handler.FileSpliterHandler;
import io.github.accessun.largesort.handler.LargeSortHandler;

public class FileSpliterTest extends OperatorTest {

    @Test
    public void testSpliter() {
        LargeSortHandler handler = new DataGeneratorHandler();
        LargeSortHandler spliterHandler = new FileSpliterHandler();
        handler.setSuccessor(spliterHandler);

        handler.handle(info);
    }

}
