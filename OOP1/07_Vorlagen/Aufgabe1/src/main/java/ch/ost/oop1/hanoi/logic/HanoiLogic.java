package ch.ost.oop1.hanoi.logic;

import java.util.ArrayList;
import java.util.List;

public class HanoiLogic {
	private final List<MoveListener> moveListeners = new ArrayList<MoveListener>();

	public HanoiLogic(int nofDisks) {
		runHanoi(nofDisks, 0, 1, 2);
	}

	public void registerListener(MoveListener listener) {
		moveListeners.add(listener);
	}

	public void runHanoi(int nofDisks, int source, int target, int reserve) {
		if (nofDisks > 1) {
			runHanoi(nofDisks - 1, source, reserve, target);
		}
		move(nofDisks, source, target);
		if (nofDisks > 1) {
			runHanoi(nofDisks - 1, reserve, target, source);
		}
	}

	private void move(int diskNo, int fromPileNo, int toPileNo) {
		for (MoveListener listener : moveListeners) {
			listener.move(diskNo, fromPileNo, toPileNo);
		}
	}
}
