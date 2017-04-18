package io.github.accessun.largesort.util;

import io.github.accessun.largesort.exception.DataFormatException;
import io.github.accessun.largesort.model.Record;
import io.github.accessun.largesort.operator.DataValidator;

public class DataMappingUtils {

    private DataMappingUtils() {}

    /**
     * Convert a string to a {@code Record} object. The string passed to this
     * method should be a valid line of record. Note that validation is
     * performed before the mapping operation.
     *
     * @param line
     *            a line of record in the original data file
     * @return
     * @throws DataFormatException
     */
    public static Record mapToRecord(String line) throws DataFormatException {
        return mapToRecord(line, true);
    }

    /**
     * Convert a string to a {@code Record} object. The string passed to this
     * method should be a valid line of record.
     *
     * @param line
     *            a string that corresponds to a Record
     * @param enableValidation
     *            enable validation or not
     * @return
     * @throws DataFormatException
     */
    public static Record mapToRecord(String line, boolean enableValidation) throws DataFormatException {
        DataValidator validator = new DataValidator();

        if (enableValidation && !validator.validateLine(line)) {
            throw new DataFormatException("Map Failure! Re-check data format!");
        }

        String[] fields = line.split("\\t+");
        return new Record(fields[0], Integer.parseInt(fields[1]));
    }
}
