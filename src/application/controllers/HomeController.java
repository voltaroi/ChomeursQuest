package application.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class HomeController {
	
    @FXML
    private ImageView backgroundImage;  // L'ImageView défini dans FXML

    @FXML
    public void initialize() {
        try {
            // Charger l'image de fond
            Image image = new Image(getClass().getResourceAsStream("/images/background.jpeg"));

            // Vérifier si l'image est bien chargée
            if (image.isError()) {
                System.out.println("Erreur de chargement de l'image !");
            } else {
                System.out.println("Image chargée avec succès !");
                backgroundImage.setImage(image); // Appliquer l'image de fond à l'ImageView
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour aller vers Selection.fxml
    @FXML
    private void handlePlay(ActionEvent event) {
        navigateTo(event, "/views/Selection.fxml", "Sélection");
    }

    // Méthode pour aller vers Option.fxml
    @FXML
    private void handleOption(ActionEvent event) {
        navigateTo(event, "/views/Option.fxml", "Options");
    }
    
    // Méthode pour aller vers Game.fxml
    @FXML
    private void handleGame(ActionEvent event) {
        navigateTo(event, "/views/Game.fxml", "Game");
    }

    // Méthode utilitaire pour naviguer vers un autre FXML
    private void navigateTo(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

            System.out.println("Navigation vers : " + fxmlPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de " + fxmlPath);
        }
    }
}
