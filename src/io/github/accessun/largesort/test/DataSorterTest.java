package io.github.accessun.largesort.test;

import org.junit.Test;

import io.github.accessun.largesort.handler.DataGeneratorHandler;
import io.github.accessun.largesort.handler.DataSorterHandler;
import io.github.accessun.largesort.handler.FileSpliterHandler;
import io.github.accessun.largesort.handler.LargeSortHandler;

public class DataSorterTest extends OperatorTest {

    @Test
    public void testSorter() {
        LargeSortHandler handler = new DataGeneratorHandler();
        LargeSortHandler spliterHandler = new FileSpliterHandler();
        LargeSortHandler sorterHandler = new DataSorterHandler();
        handler.setSuccessor(spliterHandler);
        spliterHandler.setSuccessor(sorterHandler);

        handler.handle(info);
    }

}
