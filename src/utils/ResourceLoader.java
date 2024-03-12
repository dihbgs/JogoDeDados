package src.utils;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ResourceLoader {
	public static final String DICE_SPRITE = "data/resources/img/dice.png";

	public Image loadImage(String path) {
		Image image = null;

		try {
			image = ImageIO.read(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}
}
