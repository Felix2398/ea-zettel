package zettel03;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinarySearchTree {
    private Node root;

    public static void main(String[] args) {
        List<Integer> values = List.of(20, 15, 25, 14, 16, 21, 30);
        BinarySearchTree tree = new BinarySearchTree(values);
        List<Integer> inorder = tree.inorderWalkSuccessor();
        System.out.println(inorder);
        System.out.println(tree.min(30));
        System.out.println(tree.successor(16));
    }

    public BinarySearchTree(List<Integer> values) {
        values.forEach(this::add);
    }

    private static class Node {
        Integer value;
        Node parent;
        Node left;
        Node right;

        public Node(Integer value, Node parent, Node left, Node right) {
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

    public void add(Integer value) {
        if (root == null) {
            root = new Node(value, null, null, null);
        } else {
            add(value, root);
        }
    }

    private void add(Integer value, Node current) {
        if (value < current.value) {
            if (current.left == null) {
                current.left = new Node(value, current, null, null);
            } else {
                add(value, current.left);
            }
        } else {
            if (current.right == null) {
                current.right = new Node(value, current, null, null);
            } else {
                add(value, current.right);
            }
        }
    }

    public List<Integer> inorderWalkRecursive() {
        return inorderWalkRecursive(root);
    }

    private List<Integer> inorderWalkRecursive(Node current) {
        List<Integer> values = new ArrayList<>();

        if (current == null) {
            return values;
        }

        values.addAll(inorderWalkRecursive(current.left));
        values.add(current.value);
        values.addAll(inorderWalkRecursive(current.right));

        return values;
    }

    public List<Integer> inorderWalkStack() {
        List<Integer> values = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        Node current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            values.add(current.value);
            current = current.right;
        }

        return values;
    }

    public List<Integer> inorderWalkSuccessor() {
        List<Integer> values = new ArrayList<>();
        Node current = min(root);

        while (current != null) {
            values.add(current.value);
            current = successor(current);
        }

        return values;
    }



    private Node find(Integer key) {
        Node current = root;
        while (current != null) {
            if (key.equals(current.value)) {
                return current;
            } else if (key < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        throw new RuntimeException();
    }

    public Integer min(Integer k) {
        Node node = find(k);
        Node min = min(node);
        return min.value;
    }

    private Node min(Node k) {
        Node current = k;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public Integer successor(Integer k) {
        Node node = find(k);
        Node successor = successor(node);
        if (successor != null) {
            return successor.value;
        } else {
            return null;
        }
    }

    private Node successor(Node k) {
        if (k.right != null) {
            return min(k.right);
        }

        Node parent = k.parent;
        while (parent != null && k == parent.right) {
            k = parent;
            parent = parent.parent;
        }
        return parent;
    }
}
