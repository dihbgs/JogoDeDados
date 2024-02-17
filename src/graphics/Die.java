package src.graphics;

import src.utils.ResourceLoader;
import java.awt.Graphics2D;
import java.awt.Image;
import src.utils.Dado;

public class Die extends Dado {
	private static int total = 0;

	private Image sprite;
	private int skin;
	private int size;
	private int x;
	private int y;

	public Die() {
		this(32, 0, 0);
	}

	public Die(int size, int x, int y) {
		this.sprite = ResourceLoader.loadImage(ResourceLoader.DICE_SPRITE);
		this.skin = Die.total;
		this.size = size;
		Die.total++;
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics2D graphics) {
		int startX = (this.getValue() - 1) * this.size;
		int startY = this.skin * this.size;
		int finalX = startX + this.size;
		int finalY = startY + this.size;

		graphics.drawImage(this.sprite, this.x, this.y, this.x + this.size, this.y + this.size, startX, startY, finalX,
				finalY, null);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getSize() {
		return this.size;
	}

	public int getSkin() {
		return this.skin;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSkin(int skin) throws IllegalArgumentException {
		if (skin < 0 || skin > 5) {
			throw new IllegalArgumentException("Skin must be between 0 and 5");
		}

		this.skin = skin;
	}
}
