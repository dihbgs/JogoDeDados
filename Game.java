import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.graphics.Canvas;

public class Game extends JFrame {
	private Canvas canvas;

	public static void main(String[] args) {
		Game game = new Game();

		game.setVisible(true);
	}

	public Game() {
		canvas = new Canvas();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Dice Game");
		setSize(1024, 640);

		JPanel mainPanel = new JPanel();

		mainPanel.setLayout(new BorderLayout());

		canvas.setPreferredSize(new Dimension(1024, 640));

		mainPanel.add(canvas, BorderLayout.CENTER);

		add(mainPanel);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setSize(this.getWidth(), 640);
		this.setResizable(false);
	}
}
