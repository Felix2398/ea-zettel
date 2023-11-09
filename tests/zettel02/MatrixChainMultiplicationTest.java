package zettel02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatrixChainMultiplicationTest {

    int[] arr1 = {40, 20, 30, 10, 30};
    int res1 = 26000;
    int[] arr2 = {1,2,3,4,3};
    int res2 = 30;
    int[] arr3 = {10,20,30};
    int res3 = 6000;

    @Test
    void minMatrixCost_arr1() {
        Assertions.assertEquals(res1, MatrixChainMultiplication.minMatrixCostAux(arr1));
    }

    @Test
    void minMatrixCost_arr2() {
        Assertions.assertEquals(res2, MatrixChainMultiplication.minMatrixCostAux(arr2));
    }

    @Test
    void minMatrixCost_arr3() {
        Assertions.assertEquals(res3, MatrixChainMultiplication.minMatrixCostAux(arr3));
    }

    @Test
    void minMatrixCostMemo_arr1() {
        Assertions.assertEquals(res1, MatrixChainMultiplication.minMatrixCostMemo(arr1));
    }

    @Test
    void minMatrixCostMemo_arr2() {
        Assertions.assertEquals(res2, MatrixChainMultiplication.minMatrixCostMemo(arr2));
    }

    @Test
    void minMatrixCostMemo_arr3() {
        Assertions.assertEquals(res3, MatrixChainMultiplication.minMatrixCostMemo(arr3));
    }
}