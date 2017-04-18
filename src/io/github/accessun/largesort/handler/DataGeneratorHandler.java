package io.github.accessun.largesort.handler;

import java.io.IOException;

import io.github.accessun.largesort.model.MetaInfo;
import io.github.accessun.largesort.operator.DataGenerator;

public class DataGeneratorHandler extends AbstractLargeSortHandler {

    @Override
    public MetaInfo handle(MetaInfo info) {
        DataGenerator generator = new DataGenerator();
        String path = info.getBaseDir() + info.getDataFileName();
        int dataAmount = info.getDataAmount();

        try {
            generator.generateToFile(path, dataAmount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return info;
    }

}
