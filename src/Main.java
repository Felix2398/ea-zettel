import utils.ArrayUtils;
import zettel01.MinSumFinder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] arr1 = ArrayUtils.createArray(1_000, -100, 100);
        int[] arr2 = ArrayUtils.createArray(10_000, -100, 100);
        int[] arr3 = ArrayUtils.createArray(100_000, -100, 100);
        int[] arr4 = ArrayUtils.createArray(1_000_000, -100, 100);
        int[] arr5 = ArrayUtils.createArray(10_000_000, -100, 100);

        List<int[]> arrays = List.of(arr1, arr2, arr3, arr4, arr5);

        for (int[] array : arrays) {
            System.out.println("n = " + array.length);

            long start = System.currentTimeMillis();
            //MinSumFinder.minSumNaive(array);
            long end = System.currentTimeMillis();
            //System.out.println("naive = " + (end - start) + "ms");

            start = System.currentTimeMillis();
            MinSumFinder.minSumDivideConquer(array);
            end = System.currentTimeMillis();
            System.out.println("divide and conquer = " + (end - start) + "ms");

            start = System.currentTimeMillis();
            MinSumFinder.minSumDynamic(array);
            end = System.currentTimeMillis();
            System.out.println("dynamic = " + (end - start) + "ms");

            System.out.println();
        }
    }

}