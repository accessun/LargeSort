package io.github.accessun.largesort.operator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import io.github.accessun.largesort.model.Record;
import io.github.accessun.largesort.util.RandomUtils;

public class DataGenerator {

    public void generateToFile(String pathname, int dataAmount) throws IOException {
        FileWriter fw = new FileWriter(new File(pathname));
        BufferedWriter writer = new BufferedWriter(fw);
        
        for (int i = 0; i < dataAmount; i++) {
            writer.write(getLineRecord());
        }
        writer.close();
    }
    
    private String getLineRecord() {
        return new Record(RandomUtils.getRandomName(), RandomUtils.getRandomAge()) + "";
    }
}
