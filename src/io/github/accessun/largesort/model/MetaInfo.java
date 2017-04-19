package io.github.accessun.largesort.model;

public class MetaInfo {
    private int dataAmount; // mandatory
    private String baseDir; // mandatory
    private String dataFileName; // mandatory, file that contains all the data
    private String splitPrefix;
    private String mergeFileName = "F_MERGED.txt";
    private String timestamp; // mandatory after file split operation

    public MetaInfo(int dataAmount, String baseDir, String dataFileName) {
        this.dataAmount = dataAmount;
        this.baseDir = baseDir;
        this.dataFileName = dataFileName;
    }

    public int getDataAmount() {
        return dataAmount;
    }

    public void setDataAmount(int dataAmount) {
        this.dataAmount = dataAmount;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        if (!baseDir.endsWith(java.io.File.separator))
            baseDir = baseDir + java.io.File.separator;
        this.baseDir = baseDir;
    }

    public String getDataFileName() {
        return dataFileName;
    }

    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }

    public String getSplitPrefix() {
        return splitPrefix;
    }

    public void setSplitPrefix(String splitPrefix) {
        this.splitPrefix = splitPrefix;
    }

    public String getMergeFileName() {
        return mergeFileName;
    }

    public void setMergeFileName(String mergeFileName) {
        this.mergeFileName = mergeFileName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
