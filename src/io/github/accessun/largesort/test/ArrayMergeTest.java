package io.github.accessun.largesort.test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Test;

public class ArrayMergeTest {

    Integer[] arr1 = new Integer[5];
    Integer[] arr2 = new Integer[8];
    
    @Test
    public void testMerge() {
        populate(arr1);
        populate(arr2);
        
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        
        printArr(arr1);
        printArr(arr2);
        
        Integer[] arr = new Integer[arr1.length + arr2.length];
        merge(arr1, arr2, arr);
        
        printArr(arr);
    }
    
    public void merge(Integer[] arr1, Integer[] arr2, Integer[] arr) {
        int len1 = arr1.length;
        int len2 = arr2.length;
        int len = arr.length;
        
        int p1 = 0;
        int p2 = 0;
        for (int i = 0; i < len; i++) {
            if (p1 >= len1)
                arr[i] = arr2[p2++];
            else if (p2 >= len2)
                arr[i] = arr1[p1++];
            else if (arr1[p1] <= arr2[p2])
                arr[i] = arr1[p1++];
            else
                arr[i] = arr2[p2++];
        }
    }

    public void populate(Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Random().nextInt(10);
        }
    }
    
    public void printArr(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        String listString = list.stream().map(Object::toString)
                .collect(Collectors.joining(", ", "[ ", " ]"));
        System.out.println(listString);
    }
}
