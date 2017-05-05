package io.github.accessun.largesort.operator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import io.github.accessun.largesort.comparator.AgeComparator;
import io.github.accessun.largesort.exception.DataFormatException;
import io.github.accessun.largesort.model.Record;
import io.github.accessun.largesort.util.DataMappingUtils;

public class FileMerger {

    public void merge(String pathname1, String pathname2, String resultPath) throws IOException, DataFormatException {
        merge(pathname1, pathname2, resultPath, false);
    }

    /**
     * Merge two sorted files into one sorted file. Note that it is the
     * invoker's responsibility to make sure that the two files to be merged are
     * sorted. Note that this method assumes no empty lines exists at the
     * beginning and in between the lines of data of the file to merge.
     *
     * @param path1
     *            the absolute path of the first file to be merged on the file
     *            system
     * @param path2
     *            the absolute path of the second file to be merged on the file
     *            system
     * @param target
     *            the absolute path of the target file to be merged on the file
     *            system
     * @param deleteCacheFiles
     *            delete cache files (two to-be-merged files) or not
     * @throws IOException
     * @throws DataFormatException
     */
    public void merge(String path1, String path2, String target, boolean deleteCacheFiles)
            throws IOException, DataFormatException {
        if (path1 == null && path2 == null) {
            return;
        }
        if (path1 == null) {
            Files.copy(Paths.get(path2), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
            return;
        }
        if (path2 == null) {
            Files.copy(Paths.get(path1), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
            return;
        }

        Path filePath1 = Paths.get(path1);
        Path filePath2 = Paths.get(path2);
        Path targetPath = Paths.get(target);

        OpenOption[] writeOption = { StandardOpenOption.WRITE, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING };

        try (BufferedReader reader1 = Files.newBufferedReader(filePath1);
             BufferedReader reader2 = Files.newBufferedReader(filePath2);
             BufferedWriter writer = Files.newBufferedWriter(targetPath, writeOption)) {

            Comparator<Record> ageComp = new AgeComparator();

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            String nextLine;
            Record record1, record2;
            while (line1 != null || line2 != null) {
                if (line1 == null) {
                    nextLine = line2;
                    line2 = reader2.readLine();
                }
                else if (line2 == null) {
                    nextLine = line1;
                    line1 = reader1.readLine();
                }
                else {
                    record1 = DataMappingUtils.mapToRecord(line1);
                    record2 = DataMappingUtils.mapToRecord(line2);
                    if (ageComp.compare(record1, record2) < 0) {
                        nextLine = line1;
                        line1 = reader1.readLine();
                    } else {
                        nextLine = line2;
                        line2 = reader2.readLine();
                    }
                }
                writer.write(nextLine + "\n");
            }
        }
    }

    public void mergeReduce(String[] filePaths) throws IOException, DataFormatException {
        if (filePaths.length < 2)
            throw new UnsupportedOperationException("Cannot merge-reduce less than 2 files!");

        String prefix = StringUtils.getCommonPrefix(filePaths) + "MERGE_CACHE-";

        String firstMerge = prefix + 0 + ".txt";
        merge(filePaths[0], filePaths[1], firstMerge, false);

        String mergeCache = null;
        for (int i = 0; i < filePaths.length; i++) {
            String mergedFile = prefix + i + ".txt";
            merge(mergeCache, filePaths[i], mergedFile, false);
            mergeCache = mergedFile;
        }
    }
}
