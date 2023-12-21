package zettel07;

import java.util.ArrayList;
import java.util.List;

public class OptimalBinarySearchTree {

    public static void main(String[] args) {
        String[] keys = { "A", "B", "C", "D", "E", "F", "G" };
        double[] probability = { 0.2, 0.1, 0.35, 0.1, 0.05, 0.15, 0.05 };
        Result result = findOptimalTree(keys, probability);
        System.out.println();
        result.printCostMatrix();
        System.out.println();
        result.printTree();
    }

    static Result findOptimalTree(String[] keys, double[] probability) {
        int n = keys.length;
        double[][] cost = new double[n + 1][n + 1];
        String[][] tree = new String[n + 1][n + 1];

        for (int i = 0; i < n; i++) {
            cost[i][i] = probability[i];
            tree[i][i] = keys[i];
        }

        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length + 1; i++) {
                int j = i + length - 1;
                cost[i][j] = Double.MAX_VALUE;
                double sum = sum(probability, i, j);

                for (int r = i; r <= j; r++) {

                    double costLeft = 0;
                    double costRight = 0;

                    if (r > i) {
                        costLeft = cost[i][r - 1];
                    }

                    if (r < j) {
                        costRight = cost[r + 1][j];
                    }

                    double costTotal = costLeft + costRight + sum;

                    if (costTotal < cost[i][j]) {
                        cost[i][j] = costTotal;
                        tree[i][j] = keys[r];
                    }
                }
            }
        }

        return new Result(cost, tree, keys);
    }

    static double sum(double[] freq, int i, int j) {
        double s = 0;
        for (int k = i; k <= j; k++) {
            if (k >= freq.length) {
                continue;
            }
            s += freq[k];
        }
        return s;
    }

    public record Result(double[][] cost, String[][] tree, String[] keys) {

        public void printCostMatrix() {
            int n = keys.length;
            List<String> rows = new ArrayList<>();
            String cellFormat = " %5.5s ";

            StringBuilder header = new StringBuilder();
            header.append("  |");
            for (int i = 0; i < n; i++) {
                header.append(String.format(cellFormat, keys[i])).append("|");
            }
            rows.add(header.toString());

            String verticalLine = "-".repeat(rows.get(0).length());
            rows.add(verticalLine);

            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(keys[i]).append(" |");
                for (int j = 0; j < n; j++) {
                    String cell = "" + cost[i][j];
                    if (cell.equals("0.0")) {
                        cell = "-";
                    }
                    sb.append(String.format(cellFormat, cell)).append("|");
                }
                sb.append("\n").append(verticalLine);
                rows.add(sb.toString());
            }

            rows.forEach(System.out::println);
        }

        public void printTree() {
            int n = keys.length;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (tree[i][j] == null) {
                        tree[i] [j] = "-";
                    }
                }
            }

            List<String> rows = new ArrayList<>();
            String cellFormat = " %s ";

            StringBuilder header = new StringBuilder();
            header.append("  |");
            for (int i = 0; i < n; i++) {
                header.append(String.format(cellFormat, keys[i])).append("|");
            }
            rows.add(header.toString());

            String verticalLine = "-".repeat(rows.get(0).length());
            rows.add(verticalLine);

            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(keys[i]).append(" |");
                for (int j = 0; j < n; j++) {
                    sb.append(String.format(cellFormat, tree[i][j])).append("|");
                }
                sb.append("\n").append(verticalLine);
                rows.add(sb.toString());
            }

            rows.forEach(System.out::println);
        }
    }




}
