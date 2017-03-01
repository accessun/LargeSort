package io.github.accessun.largesort.operator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public void merge(String pathname1, String pathname2, String resultPath, boolean deleteCacheFiles) throws IOException, DataFormatException {
        if (pathname1 == null && pathname2 == null)
            return;
        if (pathname1 == null) {
            Files.copy(Paths.get(pathname2), Paths.get(resultPath), StandardCopyOption.REPLACE_EXISTING);
            return;
        }
        if (pathname2 == null) {
            Files.copy(Paths.get(pathname1), Paths.get(resultPath), StandardCopyOption.REPLACE_EXISTING);
            return;
        }

        File file1 = new File(pathname1);
        File file2 = new File(pathname2);

        if (!file1.exists() || !file2.exists() || file1.length() == 0 || file2.length() == 0) {
            throw new IOException("Files to merge not exist or empty!");
        }

        FileReader fr1 = new FileReader(file1);
        FileReader fr2 = new FileReader(file2);
        BufferedReader reader1 = new BufferedReader(fr1);
        BufferedReader reader2 = new BufferedReader(fr2);

        FileWriter fw = new FileWriter(new File(resultPath));
        BufferedWriter writer = new BufferedWriter(fw);

        String line1 = reader1.readLine();
        String line2 = reader2.readLine();

        Record record1 = DataMappingUtils.mapToRecord(line1);
        Record record2 = DataMappingUtils.mapToRecord(line2);
        Comparator<Record> ageComp = new AgeComparator();

        while (true) {
            if (line1 == null && line2 != null) {
                while (line2 != null) {
                    writer.write(line2 + "\n");
                    line2 = reader2.readLine();
                }
                break;
            } else if (line2 == null && line1 != null) {
                while (line1 != null) {
                    writer.write(line1 + "\n");
                    line1 = reader1.readLine();
                }
                break;
            } else if (ageComp.compare(record1, record2) <= 0) {
                writer.write(record1 + "");
                line1 = reader1.readLine();
                if (line1 != null)
                    record1 = DataMappingUtils.mapToRecord(line1);
            } else {
                writer.write(record2 + "");
                line2 = reader2.readLine();
                if (line2 != null)
                    record2 = DataMappingUtils.mapToRecord(line2);
            }

        }

        writer.close();
        reader2.close();
        reader1.close();

        if (deleteCacheFiles) {
            file1.delete();
            file2.delete();
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
