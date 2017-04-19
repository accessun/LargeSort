package io.github.accessun.largesort.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.github.accessun.largesort.model.MetaInfo;

public class MetaInfoInitializer {

    private static final MetaInfoInitializer instance = new MetaInfoInitializer();

    private MetaInfoInitializer() {}

    public static final MetaInfoInitializer getInstance() {
        return instance;
    }

    public MetaInfo init(int dataAmount) {
        String baseDir = null;
        String dataFileName = null; // file that contains all the data

        try (InputStream is = MetaInfoInitializer.class.getClassLoader().getResourceAsStream("largesort.properties")) {
            Properties prop = new Properties();
            prop.load(is);

            baseDir = prop.getProperty("largesort.baseDir");
            dataFileName = prop.getProperty("largesort.dataFileName");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new MetaInfo(dataAmount, baseDir, dataFileName);
    }
}
