import java.util.List;

public interface IBinarySearchTree<E> extends Tree<E> {
    Node<E> left(Node<E> p);
    Node<E> right(Node<E> p);
    Node<E> sibling(Node<E> p);
    Node<E> addNode(E value);
    Node<E> getRoot();
    List<E> inorderTraversal();
    int size();
}
