package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class SelectionController {

    @FXML
    private void handleBack() {
        try {
            // Charger le fichier FXML de la page d'accueil (Home.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Home.fxml"));
            VBox root = loader.load();
            
            // Créer une nouvelle scène avec le fichier Home.fxml
            Scene scene = new Scene(root);
            
            // Récupérer la scène du stage actuel
            Stage stage = (Stage) root.getScene().getWindow();
            
            // Mettre à jour la scène avec la page d'accueil
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
