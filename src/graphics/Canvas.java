package src.graphics;

import javax.swing.Timer;
import javax.swing.JPanel;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.Graphics;

public class Canvas extends JPanel {
	private final int frameRate = 24;

	private int width;
	private int height;

	private static long elapsedTime;
	private static long currentTime;
	private static long previousTime;

	public Canvas() {
		this(400, 400);

		elapsedTime = 1;
		previousTime = System.currentTimeMillis();

		Timer timer = new Timer(1000 / frameRate, (event) -> gameLoop());

		timer.start();
	}

	public Canvas(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void resize(int size) {
		this.width = size;
		this.height = size;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void paintComponent(Graphics context) {
		Graphics2D graphics = (Graphics2D) context;

		super.paintComponent(graphics);
		this.setPreferredSize(new Dimension(1024, this.getHeight()));
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int x = (int) (Math.random() * this.getWidth());
		int y = (int) (Math.random() * this.getHeight());

		graphics.setColor(java.awt.Color.WHITE);
		graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		graphics.setColor(java.awt.Color.BLACK);
		graphics.fillRect(x, y, 50, 50);
		graphics.drawString("Frame rate: " + 1000 / elapsedTime + " fps", 32, 32);
	}

	private void gameLoop() {
		currentTime = System.currentTimeMillis();
		elapsedTime = currentTime - previousTime;

		if (elapsedTime >= 1000 / frameRate) {
			previousTime = currentTime;
			repaint();
		}
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public void setPreferredSize(Dimension preferredSize) {
		this.width = preferredSize.width;
		this.height = preferredSize.height;
	}

	public Dimension getPreferredSize() {
		return new Dimension(this.width, this.height);
	}
}
