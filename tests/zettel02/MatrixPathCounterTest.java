package zettel02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatrixPathCounterTest {

    @Test
    void countNaive_2_2_1() {
        Assertions.assertEquals(2, MatrixPathCounter.countNaive(2,2, 1,0,0));
    }

    @Test
    void countNaive_3_3_2() {
        Assertions.assertEquals(6, MatrixPathCounter.countNaive(3,3, 2,0,0));
    }

    @Test
    void countDynamic_2_2_1() {
        Assertions.assertEquals(2, MatrixPathCounter.countDynamic(2,2, 1,0,0));
    }

    @Test
    void countDynamic_3_3_2() {
        Assertions.assertEquals(6, MatrixPathCounter.countDynamic(3,3, 2,0,0));
    }
}