package cz.mbucek.images.utils;

public class ImageUtils {

	public static String getFileExtention(String filename) {
		return filename.replaceAll("^.*\\.", "");
	}
	
}
