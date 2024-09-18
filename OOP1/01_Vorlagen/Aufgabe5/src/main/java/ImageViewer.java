import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImageViewer {
	private static String IMAGE_FILE_NAME = "ost.jpg";
	private BufferedImage image;
	private final JLabel imageLabel;
	
	private ImageViewer() {
		readInitialImage();
		JFrame frame = new JFrame("Viewer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imageLabel = new JLabel(new ImageIcon(image));
		frame.getContentPane().add(imageLabel, BorderLayout.CENTER);
		JPanel buttonPanel = createButtonPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		SwingUtilities.invokeLater(() -> {
			frame.pack();
			frame.setVisible(true);
		});
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton invertButton = new JButton("Invert");
		buttonPanel.add(invertButton);
		invertButton.addActionListener(event -> apply(ImageProcessing::invert));
		JButton rotateButton = new JButton("Rotate");
		buttonPanel.add(rotateButton);
		rotateButton.addActionListener(event -> apply(ImageProcessing::rotate));
		JButton mirrorButton = new JButton("Mirror");
		buttonPanel.add(mirrorButton);
		mirrorButton.addActionListener(event -> apply(ImageProcessing::mirror));
		JButton grayButton = new JButton("Gray");
		buttonPanel.add(grayButton);
		grayButton.addActionListener(event -> apply(ImageProcessing::gray));
		JButton resetButton = new JButton("Reset");
		buttonPanel.add(resetButton);
		resetButton.addActionListener(event -> {
			readInitialImage();
			updateImage();
		});
		return buttonPanel;
	}
	
	@FunctionalInterface
	private interface ImageFilter {
		int[][] process(int[][] pixels);
	}
	
	private void apply(ImageFilter filter) {
		image = createImage(filter.process(extractImage(image)));
		updateImage();
	}
	
	private void updateImage() {
		imageLabel.setIcon(new ImageIcon(image));
	}
	
	private void readInitialImage() {
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(IMAGE_FILE_NAME);
			image = ImageIO.read(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private int[][] extractImage(BufferedImage image) {
		int height = image.getHeight();
		int width = image.getWidth();
		int[][] pixels = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[y][x] = image.getRGB(x, y);
			}
		}
		return pixels;
	}

	private BufferedImage createImage(int[][] pixels) {
		int height = pixels.length;
		int width = pixels[0].length;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				image.setRGB(x, y, pixels[y][x]);
			}
		}
		return image;
	}
	
	public static void main(String[] args) {
		new ImageViewer();
	}
}
