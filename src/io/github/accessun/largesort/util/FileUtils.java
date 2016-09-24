package io.github.accessun.largesort.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    private FileUtils() {
    }
    
    public static void copyFile(String fromPath, String toPath) throws IOException {
        InputStream in = new FileInputStream(new File(fromPath));
        BufferedInputStream bis = new BufferedInputStream(in);
        OutputStream out = new FileOutputStream(new File(toPath));
        BufferedOutputStream bos = new BufferedOutputStream(out);
        
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) > 0) {
            bos.write(buffer, 0, bytesRead);
        }
        
        bos.close();
        bis.close();
    }
}
