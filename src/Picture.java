import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Picture {
	BufferedImage image;
	float strength;

	public Picture(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Color color = new Color(0, true);
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				image.setRGB(x, y, color.getRGB());
			}
		}
	}

	public Color getColor(int x, int y) {
		return new Color(image.getRGB(x, y), true);
	}

	public void setColor(int x, int y, Color color) {
		image.setRGB(x, y, color.getRGB());
	}

	public void setStrength(float alphaMultiplier) {
		strength = alphaMultiplier;
	}

	public void draw(int x, int y, Color color) {
		color = setAlpha(color, (int) (color.getAlpha() * strength));
		Color oldColor = getColor(x, y);
		int newAlpha = color.getAlpha() + oldColor.getAlpha();
		newAlpha = Math.min(newAlpha, 255);
		double ratio = 1.0 * color.getAlpha() / newAlpha;
		Color newColor = colorLurp(oldColor, color, ratio);
		newColor = setAlpha(newColor, newAlpha);
		setColor(x, y, newColor);
	}

	public static Color setAlpha(Color color, int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}

	public static int lurp(int a, int b, double t) {
		return (int) (b * t + a * (1 - t));
	}

	public static Color colorLurp(Color a, Color b, double t) {
		return new Color(lurp(a.getRed(), b.getRed(), t), lurp(a.getGreen(), b.getGreen(), t),
				lurp(a.getBlue(), b.getBlue(), t));
	}

	public void exportToFile(String filePath) throws IOException {
		File outputfile = new File(filePath);
		ImageIO.write(image, "png", outputfile);
	}
}
