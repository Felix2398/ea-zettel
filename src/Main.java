import zettel01.MinSumFinder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] arr1 = createArray(1_000, -100, 100);
        int[] arr2 = createArray(10_000, -100, 100);
        int[] arr3 = createArray(100_000, -100, 100);

        List<int[]> arrays = List.of(arr1, arr2, arr3);

        for (int[] array : arrays) {
            System.out.println("n = " + array.length);

            long start = System.currentTimeMillis();
            MinSumFinder.minSumNaive(array);
            long end = System.currentTimeMillis();
            System.out.println("naive = " + (end - start) + "ms");

            start = System.currentTimeMillis();
            MinSumFinder.minSumDivideConquer(array);
            end = System.currentTimeMillis();
            System.out.println("divide and conquer = " + (end - start) + "ms");
            System.out.println();
        }
    }

    public static int[] createArray(int n, int min, int max) {
        Random random = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt((max - min) + 1) + min;
        }
        return arr;
    }
}