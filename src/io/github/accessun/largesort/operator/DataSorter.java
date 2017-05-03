package io.github.accessun.largesort.operator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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
     * Sort the data in a file. In the following three cases, this method does
     * not perform sorting on the file:
     * <ul>
     * <li>File is empty</li>
     * <li>File contains no more than one line of record</li>
     * <li>The first line of the file is an empty line</li>
     * </ul>
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

        List<String> lines = Files.readAllLines(Paths.get(pathname));
        if (lines == null || lines.size() <= 1 || lines.get(0).trim().isEmpty())
            return;

        List<Record> records = new ArrayList<>();
        for (String line : lines)
            records.add(DataMappingUtils.mapToRecord(line));

        Collections.sort(records, comp);

        List<String> strList = records.stream().map(Record::toString).collect(Collectors.toList());

        // disallow file overwrite
        Files.write(Paths.get(targetPath), strList, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
    }
}
