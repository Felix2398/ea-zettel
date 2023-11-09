package zettel02;

public class MatrixPathCounter {

    public static int countNaive(int n, int m, int l, int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= m) {
            return 0;
        }

        if (l == 0) {
            return 1;
        }

        return countNaive(n, m, l - 1, i - 1, j)
                + countNaive(n, m, l - 1, i + 1, j)
                + countNaive(n, m, l - 1, i, j - 1)
                + countNaive(n, m, l - 1, i, j + 1);
    }

    public static int countDynamic(int n, int m, int l, int i, int j) {
        int[][][] arr = new int[l + 1][n][m];

        // set start position
        arr[0][i][j] = 1;

        for (int x = 1; x <= l; x++) {
            for (int y = 0; y < n; y++) {
                for (int z = 0; z < m; z++) {
                    int current = 0;

                    // go down
                    if (z > 0) {
                        current += arr[x-1][y][z - 1];
                    }

                    // go up
                    if (z < m - 1) {
                        current += arr[x-1][y][z + 1];
                    }

                    // go right
                    if (y > 0) {
                        current += arr[x-1][y - 1][z];
                    }

                    // go left
                    if (y < n - 1) {
                        current += arr[x-1][y + 1][z];
                    }

                    arr[x][y][z] += current;
                }
            }
        }

        // count paths
        int paths = 0;
        for (int y = 0; y < n; y++) {
            for (int z = 0; z < m; z++) {
                paths += arr[l][y][z];
            }
        }

        return paths;
    }
}
