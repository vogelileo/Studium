package ch.ost.oop1.hanoi.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class HanoiGUI {
	private final JFrame frame = new JFrame();
	private final HanoiCanvas canvas = new HanoiCanvas();
	private final int DELAY_PER_STEP_MILLIS = 100;
	
	public HanoiGUI(int nofDisks) {
		showGUI(nofDisks);
	}
	
	public void move(final int diskNo, final int fromPileNo, final int toPileNo) {
		SwingUtilities.invokeLater(() -> {
    	    canvas.pop(fromPileNo, diskNo);
    	    canvas.push(toPileNo, diskNo);
	    });
		try {
			Thread.sleep(DELAY_PER_STEP_MILLIS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void showGUI(final int nofDisks) {
	    frame.setSize(800, 400);
	    frame.setTitle("Towers of Hanoi");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add(canvas);
	    SwingUtilities.invokeLater(() -> {
    		for (int i = nofDisks; i > 0; i--) {
		    	canvas.push(0, i);
		    }
    	    frame.setVisible(true);
	    });
	}
}
