package application.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.GameClient;
import net.GameServer;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

import application.GameState;

public class HomeController {
	
    @FXML
    private ImageView backgroundImage;  // L'ImageView défini dans FXML

    @FXML
    public void initialize() {

        // Charger l'image de fond
        Image image = new Image(getClass().getResourceAsStream("/images/background.jpeg"));
            
        backgroundImage.setImage(image); // Appliquer l'image de fond à l'ImageView
       
    }
    
    @FXML
    private void handleHost(ActionEvent event) {
    	GameState.startServer();
		GameState.getServer().start();
		
		GameState.setIsMulti(true);
		GameState.setIsServer(true);
		
        navigateTo(event, "/views/Selection.fxml", "Sélection");
    }
    
    @FXML
    private void handleJoin(ActionEvent event) {

    	GameState.startClient();
		GameState.getClient().start();
		
		GameState.setIsMulti(true);
		
		GameState.getClient().sendData("ping", 1);
	    navigateTo(event, "/views/Selection.fxml", "Sélection");
	    GameState.getClient().sendData("join", 0);
    }
    
    @FXML
    private void handleQuit(ActionEvent event) {
    	if(GameState.getIsServer()) {
    		GameState.getServer().stopServer();
    	}
        Platform.exit();
    }
    
    @FXML
    private void handlePlay(ActionEvent event) {
        navigateTo(event, "/views/Selection.fxml", "Sélection");
    }

    // Méthode pour aller vers Option.fxml
    @FXML
    private void handleOption(ActionEvent event) {
        navigateTo(event, "/views/Option.fxml", "Options");
    }
    
    // Méthode pour aller vers Tuto.fxml
    @FXML
    private void handleTuto(ActionEvent event) {
        navigateTo(event, "/views/Tuto.fxml", "Tuto");
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
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de " + fxmlPath);
        }
    }
}
