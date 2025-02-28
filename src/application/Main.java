package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    	// Charger le fichier FXML correctement
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Home.fxml"));
        Parent root = loader.load();  // Charge le layout depuis FXML

        
        // Créer une scène avec le VBox comme racine
        Scene scene = new Scene(root, 600, 600);
        
        // Définir la scène sur le stage et afficher
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chomeur's Quest");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
