package io.github.accessun.largesort.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class HashSplitTest {

    @Test
    public void testHashSplit() {
        List<String> strs = new ArrayList<>();
        int count = 0;
        while (count++ < 20) {
            strs.add(UUID.randomUUID().toString());
        }
        strs.forEach(s -> System.out.println(s + ": " + ((s.hashCode() & 0x7fffffff) % 10)));
    }

}
