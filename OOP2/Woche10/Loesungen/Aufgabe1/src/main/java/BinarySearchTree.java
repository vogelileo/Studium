import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinarySearchTree<E extends Comparable<E>> implements IBinarySearchTree<E> {

    private Node<E> root;
    private int size;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    @Override
    public Node<E> root() {
        return root;
    }

    @Override
    public Node<E> left(Node<E> p) {
        return p.left;
    }

    @Override
    public Node<E> right(Node<E> p) {
        return p.right;
    }

    @Override
    public Node<E> parent(Node<E> p) throws IllegalArgumentException {
        if (p == root) {
            throw new IllegalArgumentException("Root has no parent.");
        }
        Node<E> current = root;
        while (current != null && current.left != p && current.right != p) {
            if (p.value.compareTo(current.value) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current;
    }

    @Override
    public Node<E> sibling(Node<E> p) {
        Node<E> parent = parent(p);
        if (parent == null) {
            return null; // p is the root
        }
        if (p.equals(parent.left)) {
            return parent.right;
        } else {
            return parent.left;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Node<E> getRoot() {
        return root;
    }

    @Override
    public Node<E> addNode(E value) {
        Node<E> newNode = new Node<>(value);
        if (root == null) {
            root = newNode;
        } else {
            Node<E> current = root;
            while (true) {
                if (newNode.value.compareTo(current.value) < 0) {
                    if (current.left == null) {
                        current.left = newNode;
                        break;
                    } else {
                        current = current.left;
                    }
                } else {
                    if (current.right == null) {
                        current.right = newNode;
                        break;
                    } else {
                        current = current.right;
                    }
                }
            }
        }
        size++;
        return newNode;
    }

    public List<E> inorderTraversal() {
        List<E> elements = new ArrayList<>();
        inorderTraversal(root, elements);
        return elements;
    }
    
    private void inorderTraversal(Node<E> node, List<E> elements) {
        if (node != null) {
            inorderTraversal(node.left, elements);
            elements.add(node.value);
            inorderTraversal(node.right, elements);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        Random generator = new Random();
        for (int i = 0; i < 3; i++) {
            int rando = generator.nextInt(1000);
            bst.addNode(rando);
        }
        System.out.println("Root: " + bst.root.value);
        List<Integer> elements = bst.inorderTraversal();
        for (Integer e : elements) {
            System.out.println(e);
        }
    }

    @Override
    public Iterable<Node<E>> children(Node<E> p) throws IllegalArgumentException {
        List<Node<E>> children = new ArrayList<>(2);
        if (p.left != null) {
            children.add(p.left);
        }
        if (p.right != null) {
            children.add(p.right);
        }
        return children;
    }

    @Override
    public int numChildren(Node<E> p) throws IllegalArgumentException {
        int count = 0;
        if (p.left != null) {
            count++;
        }
        if (p.right != null) {
            count++;
        }
        return count;
    }

    @Override
    public boolean isInternal(Node<E> p) throws IllegalArgumentException {
        return numChildren(p) > 0;
    }

    @Override
    public boolean isExternal(Node<E> p) throws IllegalArgumentException {
        return numChildren(p) == 0;
    }

    @Override
    public boolean isRoot(Node<E> p) throws IllegalArgumentException {
        return p == root;
    }
}