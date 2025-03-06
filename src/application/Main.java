package application;

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Home.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 600);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chomeur's Quest");
        primaryStage.show();
        
        String path = Paths.get(System.getProperty("user.dir"), "resources", "sound" , "Pokemon.wav").toString();
        new SoundManager(path, -20.0f);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
