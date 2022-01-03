package cz.mbucek.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import cz.mbucek.images.filters.Filter;
import cz.mbucek.images.filters.FilterName;
import cz.mbucek.images.filters.Filters;
import cz.mbucek.images.utils.ImageUtils;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
	
	@FXML
	private Menu filters;
	
	private Image originalImage;
	private Image modifiedImage;

	private File file;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 500, 500);
		imageStatus.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
		    image.setImage((originalImg.isSelected())? originalImage : modifiedImage);
		}));
		
		//Radši tuto část kódu neřešte :)
		var methods = Filters.class.getMethods();
		Stream.of(methods).filter(m -> m.getAnnotation(FilterName.class) != null).forEach(m -> {
			var item = new MenuItem(m.getAnnotation(FilterName.class).value());
			item.setOnAction(event -> {
				applyFilter((BufferedImage img) -> {
					Object value = null;
					try {
						value = m.invoke(null, img);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
					return (value instanceof BufferedImage)? (BufferedImage) value : null;
				});
			});
			filters.getItems().add(item);
		});
		
	}

	public void close() {
		System.exit(0);
	}

	public void chooseFile() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(App.mainStage);
		if(selectedFile != null) {
			file = selectedFile;
			Image img = new Image(selectedFile.toURI().toString());
			originalImage = img;
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
	}

	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText(null);
		alert.setContentText("Matěj Bucek V4A");
		alert.showAndWait();
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
	
	private void applyFilter(Filter filter) {
		originalImage = image.getImage();
		if(image.getImage() == null) return;
		modifiedImage = SwingFXUtils.toFXImage(Filters.applyFilter(filter, SwingFXUtils.fromFXImage(image.getImage(), null)), null);
		modifiedImg.setSelected(true);
	}

	public void save() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setSelectedExtensionFilter(new ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
		File output = fileChooser.showSaveDialog(App.mainStage);
		if(output != null) {
			output.createNewFile();
			
			BufferedImage buffImg = SwingFXUtils.fromFXImage(image.getImage(), null);
			ImageIO.write(buffImg, ImageUtils.getFileExtention(output.getName()), output);
		}
	}
}
