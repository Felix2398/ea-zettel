package zettel07;

import java.util.*;
import java.util.stream.Stream;

public class BPlusTree<K extends Comparable<K>, V> implements IBPlusTree<K, V> {
    protected Node root;

    protected final int minNodeKids;
    protected final int minLeafEntries;
    protected final int maxNodeKids;
    protected final int maxLeafEntries;


    public BPlusTree(int b, int c) {
        this(b, 2 * b - 1, c, 2 * c - 1);
    }

    public Node getRoot() {
        return root;
    }

    public BPlusTree(int minNodeKids, int maxNodeKids, int minLeafEntries, int maxLeafEntries) {
        if (minLeafEntries < 1 || minNodeKids < 2)
            throw new RuntimeException("invalid parameters");
        this.minNodeKids = minNodeKids;
        this.minLeafEntries = minLeafEntries;
        this.maxNodeKids = maxNodeKids;
        this.maxLeafEntries = maxLeafEntries;
    }

    @Override
    public void insert(K key, V value) {
        if (root == null) {
            root = new LeafNode(null, null, new ArrayList<K>(List.of(key)), new ArrayList<>(List.of(value)));
            return;
        }

        Stack<InternalNode> parents = new Stack<>();
        Node current = root;
        while (!current.isLeaf()) {
            InternalNode node = current.asInternalNode();
            int index = node.kids.size() - 1;
            for (int i = 0; i < node.size(); i++) {
                if (key.compareTo(node.keys.get(i)) < 0) {
                    index = i;
                    break;
                }
            }
            parents.push(node);
            current = node.kids.get(index);
        }

        current.asLeafNode().add(key, value);

        while (current.isOverflown()) {
            InternalNode parent;
            if (!parents.empty()) {
                parent = parents.pop();
            } else {
                parent = new InternalNode(null, null, new ArrayList<>(), new ArrayList<>());
                root = parent;
            }
            current.split(parent);
            current = parent;
        }
    }

    @Override
    public V search(K key) {
        Node current = root;
        while (!current.isLeaf()) {
            InternalNode node = current.asInternalNode();
            int index = node.kids.size() - 1;
            for (int i = 0; i < node.size(); i++) {
                if (key.compareTo(node.keys.get(i)) < 0) {
                    index = i;
                    break;
                }
            }
            current = node.kids.get(index);
        }

        LeafNode leaf = current.asLeafNode();

        for (int i = 0; i < leaf.size(); i++) {
            if (key.compareTo(leaf.keys.get(i)) == 0) {
                return leaf.values.get(i);
            }
        }
        return null;
    }

    @Override
    public Stream<V> search(K low, K high) {
        List<V> out = new ArrayList<>();
        Node current = root;
        while (!current.isLeaf()) {
            InternalNode node = current.asInternalNode();
            int index = node.kids.size() - 1;
            for (int i = 0; i < node.size(); i++) {
                if (low.compareTo(node.keys.get(i)) < 0) {
                    index = i;
                    break;
                }
            }
            current = node.kids.get(index);
        }

        LeafNode leaf = current.asLeafNode();

        while(true) {
            boolean shouldBreak = false;
            for (int i = 0; i < leaf.keys.size(); i++) {
                if (low.compareTo(leaf.keys.get(i)) <= 0 && leaf.keys.get(i).compareTo(high) <= 0) {
                    out.add(leaf.values.get(i));
                    shouldBreak = false;
                } else {
                    shouldBreak = true;
                }
            }

            if (shouldBreak) {
                break;
            }

            if (leaf.right != null) {
                leaf = leaf.right.asLeafNode();
            }
        }
        return out.stream();
    }

    public int getMinNodeKids() {
        return minNodeKids;
    }
    public int getMinLeafEntries() {
        return minLeafEntries;
    }
    public int getMaxNodeKids() {
        return maxNodeKids;
    }
    public int getMaxLeafEntries() {
        return maxLeafEntries;
    }

    public abstract class Node {
        protected Node left;
        protected Node right;
        protected List<K> keys;

        protected Node(Node left, Node right, List<K> keys) {
            this.left = left;
            this.right = right;
            this.keys = keys;
        }

        public abstract boolean isOverflown();
        public abstract boolean isUnderflown();

        public abstract boolean isFull();
        protected abstract void split(InternalNode parent);
        public abstract boolean isLeaf();

        public int size() {
            return keys.size();
        }
        public final InternalNode asInternalNode() {
            return (InternalNode) this;
        }

        public final LeafNode asLeafNode() {
            return (LeafNode) this;
        }

        public Node getLeft() {
            return left;
        }
        public Node getRight() {
            return right;
        }

        public List<K> getKeys() {
            return keys;
        }

        @Override
        public String toString() {
            return Objects.toString(keys);
        }
    }

    public class InternalNode extends Node {
        protected List<Node> kids;

        protected InternalNode(Node left, Node right, List<K> keys, List<Node> kids) {
            super(left, right, keys);
            this.kids = kids;
        }

        public void add(K key, Node left, Node right) {
            this.kids.remove(left);
            int index = Util.findInsertionIndex(key, this.keys);
            this.keys.add(index, key);
            this.kids.add(index, left);
            this.kids.add(index + 1, right);
        }

        @Override
        public boolean isOverflown() {
            return kids.size() > maxNodeKids;
        }
        @Override
        public boolean isUnderflown() {
            return kids.size() < minNodeKids;
        }

        @Override
        public boolean isFull() {
            return kids.size() == maxNodeKids;
        }

        @Override
        public boolean isLeaf() {
            return false;
        }

        protected void split(InternalNode parent) {
            int size = keys.size();
            int mid = size / 2;

            List<K> newKeys = new ArrayList<>(this.keys.subList(mid, size));
            List<Node> newKids = new ArrayList<>(this.kids.subList(mid + 1, size + 1));

            this.keys = new ArrayList<>(this.keys.subList(0, mid));
            this.kids = new ArrayList<>(this.kids.subList(0, mid + 1));


            K newKey = newKeys.get(0);
            newKeys.remove(newKey);
            InternalNode newNode = new InternalNode(this, this.getRight(), newKeys, newKids);

            if (this.getRight() != null) {
                this.right.left = newNode;
            }
            this.right = newNode;

            parent.add(newKey, this, newNode);
        }

        @Override
        public InternalNode getLeft() {
            return left != null ? left.asInternalNode() : null;
        }
        @Override
        public InternalNode getRight() {
            return right != null ? right.asInternalNode() : null;
        }
        public List<Node> getKids() {
            return Collections.unmodifiableList(kids);
        }
    }

    public class LeafNode extends Node {
        protected List<V> values;
        protected LeafNode(Node left, Node right, List<K> keys, List<V> values) {
            super(left, right, keys);
            this.values = values;
        }

        public void add(K key, V value) {
            int index = Util.findInsertionIndex(key, this.keys);
            this.keys.add(index, key);
            this.values.add(index, value);
        }

        @Override
        public boolean isOverflown() {
            return values.size() > maxLeafEntries;
        }

        @Override
        public boolean isUnderflown() {
            return values.size() < minLeafEntries;
        }

        @Override
        public boolean isFull() {
            return values.size() == maxLeafEntries;
        }

        @Override
        public LeafNode getLeft() {
            return left == null ? null : left.asLeafNode();
        }

        @Override
        public LeafNode getRight() {
            return right == null ? null : right.asLeafNode();
        }

        @Override
        protected void split(InternalNode parent) {
            int size = this.size();
            int mid = size / 2;

            List<K> newKeys = new ArrayList<>(this.keys.subList(mid, size));
            List<V> newValues = new ArrayList<>(this.values.subList(mid, size));
            this.keys.removeAll(newKeys);
            this.values.removeAll(newValues);



            LeafNode newNode = new LeafNode(this, this.getRight(), newKeys, newValues);

            if (this.getRight() != null) {
                this.right.left = newNode;
            }
            this.right = newNode;

            K newKey = newKeys.get(0);
            parent.add(newKey, this, newNode);
        }

        @Override
        public boolean isLeaf() {
            return true;
        }
        public List<V> getValues() {
            return Collections.unmodifiableList(values);
        }
    }

    public static class Util {
        public static <K extends Comparable<K>> int findInsertionIndex(K key, List<K> keys) {
            int index = 0;
            while (index < keys.size() && keys.get(index).compareTo(key) < 0) {
                index++;
            }
            return index;
        }
    }
}
