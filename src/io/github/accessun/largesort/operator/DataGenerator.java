package io.github.accessun.largesort.operator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.github.accessun.largesort.model.Record;
import io.github.accessun.largesort.util.RandomUtils;

public class DataGenerator {

    /**
     * Generate random data used to test out the sorting algorithm implemented
     * by this project.
     *
     * @param pathname
     *            the pathname of file you want to the generated data to go into
     * @param dataAmount
     *            total number of line of data record to generate
     * @throws IOException
     */
    public void generateToFile(String pathname, int dataAmount) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(pathname), StandardOpenOption.WRITE,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (int i = 0; i < dataAmount; i++) {
                writer.write(getLineRecord());
            }
        }
    }

    private String getLineRecord() {
        return new Record(RandomUtils.getRandomName(), RandomUtils.getRandomAge()) + "";
    }
}
