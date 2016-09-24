package io.github.accessun.largesort.operator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class DataValidator {
    
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

    public boolean validateLine(String line) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]+\\t+[1-8]{1}[0-9]{1}$");
        return p.matcher(line).matches();
    }

}
