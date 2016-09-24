package io.github.accessun.largesort.test;

import java.io.IOException;

import org.junit.Test;

import io.github.accessun.largesort.comparator.AgeComparator;
import io.github.accessun.largesort.exception.DataFormatException;
import io.github.accessun.largesort.operator.DataGenerator;
import io.github.accessun.largesort.operator.DataSorter;
import io.github.accessun.largesort.operator.FileMerger;
import io.github.accessun.largesort.operator.FileSpliter;

public class OperatorTest {

    DataGenerator generator = new DataGenerator();
    FileSpliter spliter = new FileSpliter();
    DataSorter sorter = new DataSorter();
    FileMerger merger = new FileMerger();

    int dataAmount = 2_000;
    
    String baseDir = "C:/Users/User/Desktop/largeSort/";
    String initialFile = baseDir + "data.txt";
    String sortFile = baseDir + "F_SPLIT-1473654232865-0.txt";
    String sortFile2 = baseDir + "F_SPLIT-1473654232865-1.txt";
    String mergeFile = baseDir + "F_MERGED.txt";
    
    @Test
    public void testGenerateToFile() throws IOException {
        generator.generateToFile(initialFile, dataAmount);
    }

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
            filePaths[i] = baseDir + "F_SPLIT-1473654232865-" + i + ".txt";
        }
        merger.mergeReduce(filePaths);
    }

}
