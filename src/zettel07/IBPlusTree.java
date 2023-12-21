package zettel07;

import java.util.stream.Stream;

public interface IBPlusTree<K extends Comparable<K>, V> {

    void insert(K key, V value);
    V search(K key);
    Stream<V> search(K keyLow, K keyHigh);
}
