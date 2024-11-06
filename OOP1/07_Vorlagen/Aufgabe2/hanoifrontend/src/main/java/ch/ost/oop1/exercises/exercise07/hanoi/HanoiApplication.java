package ch.ost.oop1.exercises.exercise07.hanoi;

import ch.ost.oop1.exercises.exercise07.logic.HanoiLogic;
import ch.ost.oop1.exercises.exercise07.hanoi.gui.HanoiGUI;


public class HanoiApplication {
	private static final int NOF_DISKS = 10;
	private final HanoiGUI gui;
	private final HanoiLogic logic;

	public HanoiApplication() {
		gui = new HanoiGUI(NOF_DISKS);
		logic = new HanoiLogic(NOF_DISKS);
		logic.registerListener((diskNo, fromPileNo, toPileNo) -> {
			System.out.println(" MOVE " + diskNo + " FROM " + fromPileNo + " TO " + toPileNo);
			gui.move(diskNo, fromPileNo, toPileNo);
		});
	}

	public void run() {
		logic.runHanoi(NOF_DISKS, 0, 1, 2);
	}

	public static void main(String[] args) {
		new HanoiApplication().run();
	}
}
