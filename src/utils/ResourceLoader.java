package src.utils;

import java.awt.Image;

import javax.imageio.ImageIO;

public class ResourceLoader {
	public static final String DICE_SPRITE = "../../data/resources/img/dice.png";

	public static Image loadImage(String path) {
		Image image = null;

		try {
			image = ImageIO.read(ResourceLoader.class.getResource(path));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}
}
