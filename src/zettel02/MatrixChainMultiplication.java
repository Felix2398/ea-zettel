package zettel02;

import utils.ArrayUtils;

import java.util.List;

public class MatrixChainMultiplication {

    public static void main(String[] args) {
        int[] arr1 = ArrayUtils.createArray(21, 1, 10);
        int[] arr2 = ArrayUtils.createArray(22, 1, 10);
        int[] arr3 = ArrayUtils.createArray(23, 1, 10);

        List<int[]> arrays = List.of(arr1, arr2, arr3);

        for (int[] array : arrays) {
            System.out.println("n = " + array.length);

            long start = System.currentTimeMillis();
            MatrixChainMultiplication.minMatrixCostAux(array);
            long end = System.currentTimeMillis();
            System.out.println("aux = " + (end - start) + "ms");

            start = System.currentTimeMillis();
            MatrixChainMultiplication.minMatrixCostMemo(array);
            end = System.currentTimeMillis();
            System.out.println("memo = " + (end - start) + "ms");

            System.out.println();
        }
    }

    public static int minMatrixCostAux(int[] p) {
        return minMatrixCostAux(p, 1, p.length - 1);
    }

    private static int minMatrixCostAux(int[] p, int i, int j) {
        if (i == j) {
            return 0;
        }

        int c = Integer.MAX_VALUE;

        for (int k = i; k < j; k++) {
            c = Math.min(c, p[i-1] * p[k] *p[j] + minMatrixCostAux(p, i,k) + minMatrixCostAux(p, k + 1, j));
        }

        return c;
    }

    public static int minMatrixCostMemo(int[] p) {
        int[][] memo = new int[p.length][p.length];
        return minMatrixCostMemo(p, memo, 1, p.length - 1);
    }

    public static int minMatrixCostMemo(int[] p, int[][] memo, int i, int j) {
        if (i == j) {
            return 0;
        }

        if (memo[i][j] != 0) {
            return memo[i][j];
        }

        memo[i][j] = Integer.MAX_VALUE;

        for (int k = i; k < j; k++) {
            memo[i][j] = Math.min(memo[i][j], p[i-1] * p[k] * p[j]
                    + minMatrixCostMemo(p, memo, i,k) + minMatrixCostMemo(p, memo,k + 1, j));
        }

        return memo[i][j];
    }


}
