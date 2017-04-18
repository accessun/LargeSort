package io.github.accessun.largesort.operator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.github.accessun.largesort.exception.DataFormatException;
import io.github.accessun.largesort.model.Record;
import io.github.accessun.largesort.util.DataMappingUtils;

public class FileSpliter {

    private int splits = 10;
    private String extension = "txt";
    private String prefix = "F_SPLIT-";
    private String timestamp = System.currentTimeMillis() + "";

    /**
     * Split the original data file into multiple parts based on the hash value
     * of each line of data. Note that the default number of split file is 10.
     * You can change this by modifying the private static field
     * <tt>NUMBER_OF_SPLITS</tt> in this class. The default path to store the
     * split files is the same with that of the original data file.
     *
     * @param pathname
     *            the path of the original data file to be split
     * @return
     * @throws IOException
     * @throws DataFormatException
     */
    public void split(String pathname) throws IOException, DataFormatException {
        String targetDir = Paths.get(pathname).getParent().toString();
        split(pathname, targetDir + "");
    }

    /**
     * Split the original data file into multiple files based on the hash value
     * of each line of data. Note that the default number of split file is 10.
     * You can change this by modifying the private static field
     * <tt>NUMBER_OF_SPLITS</tt> in this class.
     *
     * @param pathname
     *            the path of the original data file to be split
     * @param targetDir
     *            the path to store the split files
     * @return
     * @throws IOException
     * @throws DataFormatException
     */
    public void split(String pathname, String targetDir) throws IOException, DataFormatException {

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(pathname))) {

            /*
             * Since the number of the files is determined by a variable, we
             * cannot use try-with-resources statement. Manual resource
             * clean-ups are unavoidable.
             */
            BufferedWriter[] writerArr = new BufferedWriter[splits];

            OpenOption[] options = { StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW };

            try {
                /*
                 * Initialize writerArr. Each writer in this array corresponds
                 * to a file associated with the index of the writer in the
                 * array.
                 */
                for (int i = 0; i < splits; i++) {
                    Path path = Paths.get(targetDir, File.separator, getDestinationFile(i));
                    writerArr[i] = Files.newBufferedWriter(path, options);
                }

                for (String line = null; (line = reader.readLine()) != null;) {
                    Record record = DataMappingUtils.mapToRecord(line); // validation is enabled
                    BufferedWriter writer = writerArr[getFileNumber(record)];
                    writer.write(line + "\n");
                }

            } finally {
                // manually close all the writers
                for (int i = 0; i < splits; i++) {
                    if (writerArr[i] != null)
                        writerArr[i].close();
                }
            }
        }

    }

    private String getDestinationFile(int index) {
        return new StringBuilder(getPrefix()).append(index).append(".").append(extension) + "";
    }

    private int getFileNumber(Record record) {
        return (record.hashCode() & 0x7fffffff) % splits;
    }


    public int getSplits() {
        return splits;
    }

    public void setSplits(int splits) {
        this.splits = splits;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPrefix() {
        return prefix + timestamp + "-";
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
