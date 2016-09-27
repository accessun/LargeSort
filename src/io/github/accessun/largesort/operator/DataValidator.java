package io.github.accessun.largesort.operator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class DataValidator {

    /**
     * Validate the file that contains data waiting to be sorted. This function
     * should be called before the splitting and sorting operation the guarantee
     * that the data in the file can be correctly operated on by this program.
     * 
     * This method reads in data from the file and validates them line by line.
     * It is expected that this method takes some time to process when the file
     * is quite large.
     * 
     * @param pathname
     *            the pathname of the file to be validated
     * @return
     * @throws IOException
     */
    public boolean validate(String pathname) throws IOException {
        FileReader fr = new FileReader(new File(pathname));
        BufferedReader reader = new BufferedReader(fr);
        String line;
        boolean result = true;
        while ((line = reader.readLine()) != null) {
            if (!validateLine(line)) {
                result = false;
                break;
            }
        }
        reader.close();
        return result;
    }

    /**
     * Validate a line of text. If the line of text passed to this method
     * conforms to the specified pattern, the test is passed.
     * 
     * The pattern against which a line of text is tested: one or more
     * alphanumeric characters followed by one or more tabs followed by two
     * digits. The first digit should be no smaller than 1 and no larger 8. The
     * second one should be within 0 and 9, inclusively.
     * 
     * @param line
     * @return
     */
    public boolean validateLine(String line) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]+\\t+[1-8]{1}[0-9]{1}$");
        return p.matcher(line).matches();
    }

}
