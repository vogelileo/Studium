public class Node<E> {

    private E element;
    private Node<E> next;

    public Node(E elem) {
        element = elem;
        next = null;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getNext() {
        return next;
    }

    public E getElement() {
        return element;
    }
}
