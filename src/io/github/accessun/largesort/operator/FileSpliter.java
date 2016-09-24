package io.github.accessun.largesort.operator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
    
    public String split(String pathname) throws IOException, DataFormatException {
        return split(pathname, new File(pathname).getParentFile() + "");
    }
    
    public String split(String pathname, String targetDir) throws IOException, DataFormatException {
        
        FileReader fr = new FileReader(new File(pathname));
        BufferedReader reader = new BufferedReader(fr);
        
        BufferedWriter[] writerArr = new BufferedWriter[NUMBER_OF_SPLITS];
        FileWriter fw;
        BufferedWriter writer;
        for (int i = 0; i < NUMBER_OF_SPLITS; i++) {
           fw = new FileWriter(new File(targetDir + File.separator + getDestinationFile(i)));
           writer = new BufferedWriter(fw);
           writerArr[i] = writer;
        }
        
        Record record;
        String line;
        while ((line = reader.readLine()) != null) {
            record = DataMappingUtils.mapToRecord(line);
            writer = writerArr[getFileNumber(record)];
            writer.write(line + "\n");
        }
        
        for (int i = 0; i < NUMBER_OF_SPLITS; i++) {
            writerArr[i].close();
        }
        reader.close();
        
        return PREFIX_NAME;
    }
    
    private String getDestinationFile(int i) {
        return new StringBuilder(PREFIX_NAME).append(i).append(".").append(EXTENSION_NAME) + "";
    }
    
    private int getFileNumber(Record record) {
        return (record.hashCode() & 0x7fffffff) % NUMBER_OF_SPLITS;
    }
    
}
