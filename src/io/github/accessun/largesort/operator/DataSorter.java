package io.github.accessun.largesort.operator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        File file = new File(pathname);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        List<Record> records = new ArrayList<>();
        String line;
        Record record;
        while ((line = reader.readLine()) != null) {
            record = DataMappingUtils.mapToRecord(line);
            records.add(record);
        }
        reader.close();

        Collections.sort(records, comp);

        FileWriter fw = new FileWriter(new File(targetPath));
        BufferedWriter writer = new BufferedWriter(fw);
        for (Record r : records) {
            writer.write(r + "");
        }
        writer.close();
    }
}
