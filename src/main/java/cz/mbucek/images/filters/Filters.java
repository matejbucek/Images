package cz.mbucek.images.filters;

import java.awt.image.BufferedImage;


public class Filters {
	
	public static BufferedImage applyFilter(Filter function, BufferedImage img) {
		return function.applyFilter(img);
	}

	public static BufferedImage applyNegative(BufferedImage image) {
		var buffImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		for(int x = 0; x < buffImg.getWidth(); x++) {
			for(int y = 0; y < buffImg.getHeight(); y++) {
				int rgb = image.getRGB(x, y);
				int tmpRGB = (255 - (rgb >> 16) & 0xFF);
				tmpRGB = (tmpRGB << 8) + (255 - (rgb >> 8) & 0xFF);
				tmpRGB = (tmpRGB << 8) + (255 - rgb & 0xFF);
				buffImg.setRGB(x, y, tmpRGB);
			}
		}
		return buffImg;
	}
}
