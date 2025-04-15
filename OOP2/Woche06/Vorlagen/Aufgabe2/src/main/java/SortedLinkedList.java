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
        //head == null -> new node = head

        if(this.head == null){
            this.head = newNode;
            return;
        }
        Node<E> runningHigherNode = this.head;
        boolean foundHigherNode = false;
        while(!foundHigherNode){
            int result = comparator.compare(runningHigherNode.getElement(),newNode.getElement());

            if(result < 0){
                if(runningHigherNode.getNext() == null){
                    foundHigherNode = true;
                    newNode.setNext(runningHigherNode.getNext());
                    runningHigherNode.setNext(newNode);
                }
                runningHigherNode = runningHigherNode.getNext();
            }else{
                foundHigherNode = true;
                newNode.setNext(runningHigherNode.getNext());
                runningHigherNode.setNext(newNode);
            }

        }

        //loop through node downwards from head to find lower/higher in list
        //-> when found change setNext of running to newNode
        //-> setNext of newNode to lowerNode

        //TODO implement insertion sort
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
