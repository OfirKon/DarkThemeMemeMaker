import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) throws IOException {
		File imageFile1 = new File("light_theme.png");
		BufferedImage image1 = ImageIO.read(imageFile1);
		File imageFile2 = new File("dark_theme.png");
		BufferedImage image2 = ImageIO.read(imageFile2);

		if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
			throw new RuntimeException("Images have different size!");
		}

		Picture picture = new Picture(image1.getWidth(), image1.getHeight());

		Color darkThemeBackgroundColor = new Color(26, 26, 27);
		Color lightThemeBackgroundColor = Color.WHITE;

		picture.setStrength(0.5f);
		applyImageOnBackgrounds(picture, image2, lightThemeBackgroundColor, darkThemeBackgroundColor);
		applyImageOnBackgrounds(picture, image1, darkThemeBackgroundColor, lightThemeBackgroundColor);

		picture.exportToFile("result_meme.png");
		System.out.println("done!");
	}

	private static void applyImageOnBackgrounds(Picture result, BufferedImage addedImage, Color colorToDraw,
			Color assumedBackground) {
		boolean drawDark = getWhite(colorToDraw) < getWhite(assumedBackground);
		for (int x = 0; x < addedImage.getWidth(); x++) {
			for (int y = 0; y < addedImage.getHeight(); y++) {
				Color color = new Color(addedImage.getRGB(x, y), true);
				int alpha = drawDark ? 255 - getWhite(color) : getWhite(color);
				color = Picture.setAlpha(colorToDraw, alpha);
				result.draw(x, y, color);
			}
		}
	}

	private static int getWhite(Color color) {
		return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
	}
}
