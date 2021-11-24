package cz.mbucek.images;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class PrimaryController implements Initializable{
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private ImageView image;
	
	@FXML
	private AnchorPane imageViewPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 500, 500);
	}

	public void close() {
		System.exit(0);
	}
	
	public void chooseFile() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(App.mainStage);
		Image img = new Image(selectedFile.toURI().toString());
		image.setImage(img);
		image.setFitWidth(imageViewPane.getWidth() * 0.7);
		image.setFitHeight(imageViewPane.getHeight() * 0.7);
		
		imageViewPane.widthProperty().addListener((obs, oldVal, newVal) -> {
			image.setFitWidth(imageViewPane.getWidth() * 0.7);
		});

		imageViewPane.widthProperty().addListener((obs, oldVal, newVal) -> {
			image.setFitHeight(imageViewPane.getHeight() * 0.7);
		});
	}
}
