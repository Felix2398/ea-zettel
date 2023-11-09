package zettel02;


public class TimberSales {
    public static int cutNaive(int[] prices, int length) {
        if (length == 0) {
            return 0;
        }

        int maxPrice = Integer.MIN_VALUE;

        for (int i = 1; i <= length; i++) {
            maxPrice = Math.max(maxPrice, prices[i - 1] + cutNaive(prices, length - i));
        }

        return maxPrice;
    }

    public static int cutDynamic(int[] prices, int length) {
        int[] arr = new int[length + 1];
        arr[0] = 0;

        for (int i = 1; i <= length; i++) {
            int max = Integer.MIN_VALUE;

            for (int j = 0; j < i; j++) {
                int value = prices[j] + arr[i - j - 1];
                max = Math.max(max, value);
            }

            arr[i] = max;
        }

        return arr[length];
    }
}
