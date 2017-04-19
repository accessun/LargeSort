package io.github.accessun.largesort.test;

import org.junit.Test;

import io.github.accessun.largesort.handler.DataGeneratorHandler;
import io.github.accessun.largesort.handler.LargeSortHandler;

public class DataGeneratorTest extends OperatorTest {

    @Test
    public void testGenerator() {
        LargeSortHandler handler = new DataGeneratorHandler();
        handler.handle(info);
    }

}
