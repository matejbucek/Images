package cz.mbucek.images;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import cz.mbucek.images.utils.ImageUtils;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

	@FXML
	private ToggleGroup imageStatus;

	private File file;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 500, 500);
		imageStatus = new ToggleGroup();
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

	public void about() {
		TextFlow textFlow = new TextFlow();
		textFlow.setLayoutX(40);
		textFlow.setLayoutY(40);
		Text text = new Text("Matěj Bucek V4A");
		text.setFill(Color.BLACK);
		text.setFont(Font.font("Arial", FontPosture.REGULAR, 14));
		textFlow.getChildren().addAll(text);

		Group group = new Group(textFlow);
		Scene scene = new Scene(group, 500, 150, Color.WHITE);
		var stage = new Stage();
		stage.setTitle("About me");
		stage.setScene(scene);
		stage.show();
	}

	public void generateImage() {
		var buffImg = new BufferedImage(600, 600, BufferedImage.TYPE_3BYTE_BGR);
		for(int x = 0; x < buffImg.getWidth(); x++) {
			for(int y = 0; y < buffImg.getHeight(); y++) {
				int rgb = (x % 128);
				rgb = (rgb << 8) + y % 128;
				rgb = (rgb << 8) + (x + y) % 128;
				buffImg.setRGB(x, y, rgb);
			}
		}
		image.setImage(SwingFXUtils.toFXImage(buffImg, null));
	}

	public void save() throws IOException {
		FileChooser fileChooser = new FileChooser();
		File output = fileChooser.showSaveDialog(App.mainStage);
		output.createNewFile();

		BufferedImage buffImg = SwingFXUtils.fromFXImage(image.getImage(), null);

		ImageIO.write(buffImg, ImageUtils.getFileExtention(output.getName()), output);
	}
}
