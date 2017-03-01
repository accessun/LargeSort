package io.github.accessun.largesort.operator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.github.accessun.largesort.comparator.NameComparator;
import io.github.accessun.largesort.exception.DataFormatException;
import io.github.accessun.largesort.model.Record;
import io.github.accessun.largesort.util.DataMappingUtils;

public class DataSorter {

    /**
     * Sort the data in a file. Note that, by default, the sorting order is
     * specified by the <tt>NameComparator</tt>. And the file will be
     * overwritten by the sorted data.
     *
     * @param pathname
     *            the path of the file to be sorted
     * @throws IOException
     * @throws DataFormatException
     */
    public void sort(String pathname) throws IOException, DataFormatException {
        sort(pathname, pathname, new NameComparator());
    }

    /**
     * Sort the data in a file. Note that, by default, the file will be
     * overwritten by the sorted data.
     *
     * @param pathname
     *            the path of the file to be sorted
     * @param comp
     * @throws IOException
     * @throws DataFormatException
     */
    public void sort(String pathname, Comparator<Record> comp) throws IOException, DataFormatException {
        sort(pathname, pathname, comp);
    }

    /**
     * Sort the data in a file.
     *
     * @param pathname
     *            the path of the file to be sorted
     * @param targetPath
     *            the path of the file used to output sorted data (if the file
     *            already exists, all the data in it will be overwritten)
     * @param comp
     *            comparator used to specify the order for sorting
     * @throws IOException
     * @throws DataFormatException
     */
    public void sort(String pathname, String targetPath, Comparator<Record> comp)
            throws IOException, DataFormatException {

        List<Record> records = Files.readAllLines(Paths.get(pathname)).stream().map(line -> {
            try {
                return DataMappingUtils.mapToRecord(line);
            } catch (DataFormatException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        Collections.sort(records, comp);

        List<String> strList = records.stream().map(Record::toString).collect(Collectors.toList());

        Files.write(Paths.get(targetPath), strList, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
    }
}
