package cz.mbucek.images;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Stage mainStage;
    
    
    @Override
    public void start(Stage stage) throws IOException {
    	mainStage = stage;
        scene = new Scene(loadFXML("app"));
        stage.setScene(scene);
        stage.setTitle("Images");
        stage.setMinHeight(620);
        stage.setMinWidth(950);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}