package io.github.accessun.largesort.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtils {
    private static final int minAge = 12;
    private static final int maxAge = 80;

    private RandomUtils() {
    }
    
    public static String getRandomName() {
        // TODO: random name should be of a certain probability of duplicates
        return UUID.randomUUID().toString().substring(0, 5);
    }
    
    public static int getRandomAge() {
        Random random = new Random();
        return random.nextInt((maxAge - minAge) + 1) + minAge;
    }
}
