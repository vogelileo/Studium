import java.util.Comparator;

public class SortedLinkedList<E> {

    private Node<E> head;
    private final Comparator<E> comparator;
    int count;

    public SortedLinkedList(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void append(E value) {
        Node<E> newNode = new Node<>(value);
        Node<E> previous = head;

        if (head == null || comparator.compare(head.getElement(), newNode.getElement()) > 0) {
            previous = head;
            head = newNode;
            head.setNext(previous);
        } else {
            Node<E> currentNode = head;
            while (comparator.compare(currentNode.getElement(), newNode.getElement()) <= 0 && currentNode.getNext() != null) {
                previous = currentNode;
                currentNode = currentNode.getNext();
            }
            if (currentNode.getNext() == null && comparator.compare(currentNode.getElement(), newNode.getElement()) <= 0) {
                currentNode.setNext(newNode);
            } else {
                previous.setNext(newNode);
                newNode.setNext(currentNode);
            }
        }
        count++;
    }

    public int count() {
        return count;
    }

    @Override
    public String toString() {
        Node<E> currentNode = head;
        StringBuilder builder = new StringBuilder();
        while (currentNode != null) {
            builder.append(currentNode.getElement().toString());
            builder.append("\n");
            currentNode = currentNode.getNext();
        }
        return builder.toString();
    }

    public Node<E> getHead() {
        return head;
    }

    //This method is used for the unit tests to fill the array list from an array
    public void fillListFromArray(E[] array) {
        for (E element : array) {
            append(element);
        }
    }

    //This method is just used to compare the linked list to the arrays for tests
    public boolean compareToArray(E[] array) {
        Node<E> currentNode = head;
        if (count() != array.length) {
            return false;
        }
        int index = 0;
        while (currentNode != null) {
            if (comparator.compare(currentNode.getElement(), array[index]) != 0) {
                return false;
            }
            index++;
            currentNode = currentNode.getNext();
        }
        return true;
    }
}
