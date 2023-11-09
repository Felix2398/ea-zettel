package zettel01;


public class MinSumFinder {
    public record Result(int i, int j, int sum) {
        public static Result min(Result a, Result b, Result c) {
            if (a.sum() < b.sum() && a.sum() < c.sum()) {
                return a;
            } else if (b.sum() < c.sum()) {
                return b;
            } else {
                return c;
            }
        }

        public static Result min(Result a, Result b) {
            if (a.sum() < b.sum()) {
                return a;
            } else {
                return b;
            }
        }

        public static Result combine(Result left, Result right) {
            if (left.j() + 1 != right.i()) {
                throw new RuntimeException("cant merge " + left + " and " + right);
            }
            return new Result(left.i(), right.j(), left.sum() + right.sum());
        }
    }

    public static Result minSumNaive(int[] arr) {
        int min = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;

        for (int tempI = 0; tempI < arr.length; tempI++) {
            int sum = 0;
            for (int tempJ = tempI; tempJ < arr.length; tempJ++) {
                sum += arr[tempJ];
                if (sum < min) {
                    min = sum;
                    i = tempI;
                    j = tempJ;
                }
            }
        }

        return new Result(i, j, min);
    }

    public static Result minSumDivideConquer(int[] arr) {
        return minSumDivideConquer(arr, 0, arr.length - 1);
    }

    private static Result minSumDivideConquer(int[] arr, int left, int right) {
        if (left >= right) {
            return new Result(left, right, arr[left]);
        }

        int mid = (right + left) / 2;
        Result left_sum = calcLeftArray(arr, left, mid);
        Result right_sum = calcRightArray(arr, mid + 1, right);
        Result crossing_sum = Result.combine(left_sum, right_sum);
        Result mid_arr = Result.min(left_sum, right_sum, crossing_sum);

        Result left_array = minSumDivideConquer(arr, left, mid);
        Result right_array = minSumDivideConquer(arr, mid + 1, right);
        return Result.min(left_array, mid_arr, right_array);
    }

    public static Result calcLeftArray(int[] arr, int left, int mid) {
        int min = Integer.MAX_VALUE;
        int new_left = -1;
        int sum = 0;
        for (int i = mid; i >= left; i--) {
            sum += arr[i];
            if (sum < min) {
                new_left = i;
                min = sum;
            }
        }
        return new Result(new_left, mid, min);
    }

    public static Result calcRightArray(int[] arr, int mid, int right) {
        int min = Integer.MAX_VALUE;
        int new_right = -1;
        int sum = 0;
        for (int i = mid; i <= right; i++) {
            sum += arr[i];
            if (sum < min) {
                new_right = i;
                min = sum;
            }
        }
        return new Result(mid, new_right, min);
    }

    public static Result minSumDynamic(int[] arr) {
        Result minSum = new Result(-1, -1, Integer.MAX_VALUE);
        Result sum = new Result (0, -1, 0);

        for (int i = 0; i < arr.length; i++) {
            Result current = new Result(i, i, arr[i]);
            sum = Result.min(current, Result.combine(sum, current));
            minSum = Result.min(minSum, sum);
        }

        return minSum;
    }
}
