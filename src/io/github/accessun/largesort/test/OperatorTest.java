package io.github.accessun.largesort.test;

import org.junit.Test;

import io.github.accessun.largesort.handler.DataGeneratorHandler;
import io.github.accessun.largesort.handler.FileSpliterHandler;
import io.github.accessun.largesort.handler.LargeSortHandler;
import io.github.accessun.largesort.model.MetaInfo;

public class OperatorTest {

    private MetaInfo info;

    public OperatorTest() {
        int dataAmount = 30; // how many lines of data the file is intended to hold
        MetaInfoInitializer initializer = MetaInfoInitializer.getInstance();
        info = initializer.init(dataAmount);
    }

    @Test
    public void testGenerator() {
        LargeSortHandler handler = new DataGeneratorHandler();
        handler.handle(info);
    }

    @Test
    public void testSpliter() {
        LargeSortHandler handler = new DataGeneratorHandler();
        LargeSortHandler spliterHandler = new FileSpliterHandler();
        handler.setSuccessor(spliterHandler);

        handler.handle(info);
    }

    /*
    @Test
    public void testSplitString() throws IOException, DataFormatException {
        String prefix = spliter.split(initialFile);
        System.out.println("File Prefix: " + prefix);
    }

    @Test
    public void testSort() throws IOException, DataFormatException {
        sorter.sort(sortFile, new AgeComparator());
        sorter.sort(sortFile2, new AgeComparator());
    }

    @Test
    public void testMerge() throws IOException, DataFormatException {
        merger.merge(sortFile, sortFile2, mergeFile);
    }

    @Test
    public void testMergeReduce() throws IOException, DataFormatException {
        String[] filePaths = new String[10];
        for (int i = 0; i < filePaths.length; i++) {
            filePaths[i] = baseDir + "F_SPLIT-1492498783443-" + i + ".txt";
        }
        merger.mergeReduce(filePaths);
    }
    */
}
