public class Stack {
	private final String[] elements;
	private int position;
	
	public Stack(int capacity)
	{
		if( capacity < 0 || capacity> 65536){
			throw new IllegalArgumentException();
		}
		elements = new String[capacity];
	}
	
	public void push(String element) throws StackOverflowException {
		if(element == null){
			throw new IllegalArgumentException();
		}
		if (position < elements.length) {
			elements[position++] = element;
		} else {
			throw new StackOverflowException();
		}
	}
	
	public String pop() throws StackUnderflowException {
		if (position == 0) {
			throw new StackUnderflowException();
		}
		String element = elements[--position];
		elements[position] = null;
		return element;
	}
	
	public int size() {
		return position;
	}
	
	public boolean isEmpty() {
		return position == 0;
	}
	
	public boolean isFull() {
		return position == elements.length;
	}
}
