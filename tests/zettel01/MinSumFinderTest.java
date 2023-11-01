package zettel01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MinSumFinderTest {
    int[] testArr1 = {3, -2, 1, -4, 2, -1, 1, 2, 1};

    @Test
    void minSumNaive_exampleFromExercise() {
        MinSumFinder.Result expected = new MinSumFinder.Result(1, 3, -5);
        MinSumFinder.Result actual = MinSumFinder.minSumNaive(testArr1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void minSumDivideConquer_exampleFromExercise() {
        MinSumFinder.Result expected = new MinSumFinder.Result(1, 3, -5);
        MinSumFinder.Result actual = MinSumFinder.minSumDivideConquer(testArr1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void minSumDivideConquer_oneElement() {
        int[] arr = {1};
        MinSumFinder.Result expected = new MinSumFinder.Result(0, 0, 1);
        MinSumFinder.Result actual = MinSumFinder.minSumDivideConquer(arr);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void minSumDivideConquer_twoElements() {
        int[] arr = {3, -2};
        MinSumFinder.Result expected = new MinSumFinder.Result(1, 1, -2);
        MinSumFinder.Result actual = MinSumFinder.minSumDivideConquer(arr);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void minSumDivideConquer_crossingMidPoint() {
        int[] arr = {6, 2, 2, 8, -6, -1, 4, 3, 0, -1};
        MinSumFinder.Result expected = new MinSumFinder.Result(4, 5, -7);
        MinSumFinder.Result actual = MinSumFinder.minSumDivideConquer(arr);
        Assertions.assertEquals(expected, actual);
    }
}