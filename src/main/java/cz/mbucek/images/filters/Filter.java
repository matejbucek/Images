package cz.mbucek.images.filters;

import java.awt.image.BufferedImage;

public interface Filter {
	public BufferedImage applyFilter(BufferedImage img);
}
