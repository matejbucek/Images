package cz.mbucek.images;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
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
	
	@FXML
	private RadioButton originalImg;
	
	@FXML
	private RadioButton modifiedImg;
	
	private File file;

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
		file = selectedFile;
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
		
		originalImg.setDisable(false);
		modifiedImg.setDisable(false);
		originalImg.setSelected(true);
		
	}
	
	public void save() throws IOException {
		FileChooser fileChooser = new FileChooser();
		File output = fileChooser.showSaveDialog(App.mainStage);
		output.createNewFile();
		
		FileInputStream is = new FileInputStream(file);
		FileOutputStream os = new FileOutputStream(output);
		
		byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) > 0) {
            os.write(buf, 0, len);
        }
        
        is.close();
        os.close();
	}
}
