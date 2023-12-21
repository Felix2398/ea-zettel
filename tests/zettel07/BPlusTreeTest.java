package zettel07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BPlusTreeTest {
    public static void main(String[] args) {
        Random random = new Random();
        random.setSeed(1234);

        BPlusTree<Integer, Integer> tree = new BPlusTree<>(10_000,10_000);
        List<Integer> values = new ArrayList<>();

        System.out.println("Create tree");
        for (int i = 0; i < 100_000; i++) {
            Integer value = random.nextInt(0,10_000_000);
            values.add(value);
            tree.insert(value, value);
        }

        Collections.sort(values);

        System.out.println();
        System.out.println("Search for elements in tree");
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(0, values.size());
            Integer key = values.get(index);
            Integer value = tree.search(key);
            System.out.printf("Key=%d, Value=%d\n", key, value);
        }

        System.out.println();
        System.out.println("Search for elements not in tree");
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(0, values.size());
            Integer key = values.get(index) * -1;
            Integer value = tree.search(key);
            System.out.printf("Key=%d, Value=%d\n", key, value);
        }

        System.out.println();
        System.out.println("Search existing interval");
        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(0, values.size() - 10);
            Integer low = values.get(index);
            Integer high = values.get(index + 10);
            List<Integer> out = tree.search(low, high).toList();
            System.out.printf("Low=%d, High=%d, Interval=%s\n", low, high, out);
        }

        System.out.println();
        System.out.println("Search not existing interval");
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(0, values.size() - 10);
            Integer low = values.get(index);
            Integer high = values.get(index + 10) * -1;
            List<Integer> out = tree.search(low, high).toList();
            System.out.printf("Low=%d, High=%d, Interval=%s\n", low, high, out);
        }

    }
}
