package cz.mbucek.images.filters;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Filters {
	
	public static BufferedImage applyFilter(Filter function, BufferedImage img) {
		return function.applyFilter(img);
	}

	@FilterName("Negative")
	public static BufferedImage applyNegative(BufferedImage image) {
		var buffImg = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
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
	
	@FilterName("Threshold")
	public static BufferedImage applyThresholdFilter(BufferedImage image) throws IOException {
		var buffImg = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		var stage = new Stage();
		stage.setTitle("ThresholdFilter settings");
		var pane = new AnchorPane();
		var applyButton = new Button("Apply");
		applyButton.setOnAction(event -> stage.close());
		var slider = new Slider(0, 256, 128);
		pane.getChildren().add(applyButton);
		pane.getChildren().add(slider);
		stage.setScene(new Scene(pane, 300, 150));
		stage.showAndWait();
		int threshold = (int) slider.getValue();
		
		int black = 0x000000;
		int white = 0xFFFFFF;
		for(int x = 0; x < buffImg.getWidth(); x++) {
			for(int y = 0; y < buffImg.getHeight(); y++) {
				int rgb = image.getRGB(x, y);
				buffImg.setRGB(x, y, (((rgb >> 16) & 0xFF) > threshold || 
									  ((rgb >> 8) & 0xFF) > threshold || 
									  (rgb & 0xFF) > threshold
									  )? white : black);
			}
		}
		return buffImg;
	}
}
