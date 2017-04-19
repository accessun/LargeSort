package io.github.accessun.largesort.test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import io.github.accessun.largesort.model.MetaInfo;

public class OperatorTest {

    protected MetaInfo info;

    public OperatorTest() {
        int dataAmount = 30; // how many lines of data the file is intended to hold
        MetaInfoInitializer initializer = MetaInfoInitializer.getInstance();
        info = initializer.init(dataAmount);
    }

    /**
     * <p>
     * Delete all files and directories recursively in the base directory. The
     * base directory is specified in the configuration file. A {@code boolean}
     * parameter {@code delete} is expected to pass to this method. If the
     * {@code delete} is {@code true}, this method is instructed to delete all
     * the contents within the base directory. Otherwise, it simply print out
     * all the files and directories that can be deleted. <strong>It is highly
     * recommended that you should double check by passing {@code false} to this
     * method before actually perform the clean-up.</strong>
     *
     * @param delete
     *            whether to delete all the contents in the base directory
     */
    protected void cleanUp(boolean delete) {
        Path start = Paths.get(info.getBaseDir());
        try {
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    cleanUpOperation(file, delete);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (!Files.isSameFile(dir, start))
                        cleanUpOperation(dir, delete);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanUpOperation(Path path, boolean delete) throws IOException {
        String log = path.toString();
        if (delete) {
            log += " DELETED";
            Files.delete(path);
        }
        System.out.println(log);
    }

    /*
    @Test
    public void testSplitString() throws IOException, DataFormatException {
        String prefix = spliter.split(initialFile);
        System.out.println("File Prefix: " + prefix);
    }

    @Test
    public void testSort() throws IOException, DataFormatException {
        sorter.sort(sortFile, new AgeComparator());
        sorter.sort(sortFile2, new AgeComparator());
    }

    @Test
    public void testMerge() throws IOException, DataFormatException {
        merger.merge(sortFile, sortFile2, mergeFile);
    }

    @Test
    public void testMergeReduce() throws IOException, DataFormatException {
        String[] filePaths = new String[10];
        for (int i = 0; i < filePaths.length; i++) {
            filePaths[i] = baseDir + "F_SPLIT-1492498783443-" + i + ".txt";
        }
        merger.mergeReduce(filePaths);
    }
    */
}
