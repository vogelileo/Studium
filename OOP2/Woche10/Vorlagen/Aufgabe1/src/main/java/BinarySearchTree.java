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
        // TODO Looks for the Parent of the Node 'p'
        if (size <= 1) {
            throw new IllegalArgumentException("tree empty or there are no parents");
        }

        Node<E> lastParent = root;
        while (lastParent != null) {


        if (p.value.compareTo(lastParent.value) < 0) {
            if (p.value == lastParent.left.value) {
                return lastParent;
            }
            lastParent = lastParent.left;
        }
        if (p.value.compareTo(lastParent.value) > 0) {
            if (p.value == lastParent.right.value) {
                return lastParent;
            }
            lastParent = lastParent.right;
        }
        }

        throw new IllegalArgumentException("Node not in tree");

    }

    @Override
    public Node<E> sibling(Node<E> p) {
        try{
          Node<E> localParent = parent(p);
          if(localParent.right == p){
              return localParent.left;
          }
          else{
              return localParent.right;
          }

        } catch (IllegalArgumentException e) {
           return null;
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
      Node<E> p = new Node<E>(value);

        if(root == null){
            root = p;
            size++;
        }
        Node<E> currentNode = root;

        while(true){
            if (p.value.compareTo(currentNode.value) < 0) {
                if(currentNode.left == null){
                    currentNode.left = p;
                    size++;
                    return p;

                }
                currentNode = currentNode.left;

            }
            else if (p.value.compareTo(currentNode.value) > 0) {
                if(currentNode.right == null){
                    currentNode.right = p;
                    size++;
                    return p;
                }
                currentNode = currentNode.right;
            }
            else{
                break;
            }


        }
        return currentNode;
    }

    @Override
    public Iterable<Node<E>> children(Node<E> p) throws IllegalArgumentException {
        List<Node<E>> result = new ArrayList<>();

        if (p == null) return result;

        Node<E> left = p.left;
        Node<E> right = p.right;

        if (left != null) {
            result.add(left);
            for (Node<E> child : children(left)) {
                result.add(child);
            }
        }

        if (right != null) {
            result.add(right);
            for (Node<E> child : children(right)) {
                result.add(child);
            }
        }

        return result;
    }

    @Override
    public int numChildren(Node<E> p) throws IllegalArgumentException {
        int counter = 0;
        for(  Node<E> child: children(p)){
            counter++;
        }
        return counter;
    }

    @Override
    public boolean isInternal(Node<E> p) throws IllegalArgumentException {

        return p.left != null || p.right != null;
    }

    @Override
    public boolean isExternal(Node<E> p) throws IllegalArgumentException {

        return !isInternal(p);
    }

    @Override
    public boolean isRoot(Node<E> p) throws IllegalArgumentException {
        return p == root;
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
}