package io.github.accessun.largesort.util;

import io.github.accessun.largesort.exception.DataFormatException;
import io.github.accessun.largesort.model.Record;
import io.github.accessun.largesort.operator.DataValidator;

public class DataMappingUtils {

    private DataMappingUtils() {
    }
    
    public static Record mapToRecord(String line) throws DataFormatException {
        DataValidator validator = new DataValidator();
        if (!validator.validateLine(line)) {
            throw new DataFormatException("Map Failure! Re-check data format!");
        }
        String[] fields = line.split("\\t+");
        return new Record(fields[0], Integer.parseInt(fields[1]));
    }
}
