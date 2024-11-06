package ch.ost.oop1.exercises.exercise07.model;

import java.util.Stack;

public class Pile {
	private final Stack<Integer> stack = new Stack<Integer>();

	public void push(int diskNo) {
		if (stack.isEmpty() || stack.peek() > diskNo) {
			stack.push(diskNo);
		} else {
			throw new AssertionError("Invalid disk move!");
		}
	}

	public void pop(int diskNo) {
		if (stack.isEmpty() || stack.pop() != diskNo) {
			throw new AssertionError("Invalid disk move!");
		}
	}

	public int[] inspect() {
		int[] result = new int[stack.size()];
		for (int i = 0; i < stack.size(); i++) {
			result[i] = stack.get(i);
		}
		return result;
	}
}
