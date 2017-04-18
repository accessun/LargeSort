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

    private static final int NUMBER_OF_SPLITS = 10;
    private static final String EXTENSION_NAME = "txt";
    private static final String PREFIX_NAME;

    static {
        PREFIX_NAME = "F_SPLIT-" + System.currentTimeMillis() + "-";
    }

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
    public String split(String pathname) throws IOException, DataFormatException {
        String targetDir = Paths.get(pathname).getParent().toString();
        return split(pathname, targetDir + "");
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
    public String split(String pathname, String targetDir) throws IOException, DataFormatException {

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(pathname))) {

            /*
             * Since the number of the files is determined by a variable, we
             * cannot use try-with-resources statement. Manual resource
             * clean-ups are unavoidable.
             */
            BufferedWriter[] writerArr = new BufferedWriter[NUMBER_OF_SPLITS];

            OpenOption[] options = { StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW };

            try {
                /*
                 * Initialize writerArr. Each writer in this array corresponds
                 * to a file associated with the index of the writer in the
                 * array.
                 */
                for (int i = 0; i < NUMBER_OF_SPLITS; i++) {
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
                for (int i = 0; i < NUMBER_OF_SPLITS; i++) {
                    if (writerArr[i] != null)
                        writerArr[i].close();
                }
            }
        }

        return PREFIX_NAME;
    }

    private String getDestinationFile(int index) {
        return new StringBuilder(PREFIX_NAME).append(index).append(".").append(EXTENSION_NAME) + "";
    }

    private int getFileNumber(Record record) {
        return (record.hashCode() & 0x7fffffff) % NUMBER_OF_SPLITS;
    }

}
