package utils;

import java.util.Random;

public class ArrayUtils {
    public static int[] createArray(int n, int min, int max) {
        Random random = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt((max - min) + 1) + min;
        }
        return arr;
    }
}
