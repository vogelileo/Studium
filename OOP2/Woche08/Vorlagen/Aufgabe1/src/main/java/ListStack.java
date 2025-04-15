public class ListStack<T> implements Stack<T> {

    private Node<T> top;
    private int size;
    private int max;

    public ListStack(int max) {
        this.max = max;
    }

    //TODO Implement

    @Override
    public int size() {
        Node<T> runnningNode = top;
        int counter = 0;

        while ( runnningNode != null){
            runnningNode = runnningNode.getNext();
            counter ++;
        }

        return counter;
    }

    @Override
    public boolean isEmpty() {
        return this.top == null;
    }

    @Override
    public T top() throws EmptyStackException {
        if(this.top == null){
            throw new EmptyStackException("Stack is empty");
        }
        return this.top.getElement();
    }

    @Override
    public void push(T element) throws StackOverflowException {
        if(this.size() >= this.max){
            throw new StackOverflowException("Stack already full");
        }
        Node<T> newNode = new Node<>(element);
        newNode.setNext(this.top);
        this.top = newNode;
        System.out.println(this.size());
    }

    @Override
    public T pop() throws EmptyStackException {
        Node<T> topNode = this.top;
        this.top = topNode.getNext();
        return topNode.getElement();
    }
}
