package application.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;



public class SelectionController {
	@FXML
	private ComboBox<String> chomeurComboBox;
	@FXML
	private ComboBox<String> itemComboBox;
	@FXML
    private ComboBox<String> attack1ComboBox;
    @FXML
    private ComboBox<String> attack2ComboBox;
    @FXML
    private ComboBox<String> attack3ComboBox;
    @FXML
    private ComboBox<String> attack4ComboBox;
    @FXML
    private Text description;
    

    // Chemin vers le dossier contenant les attaques
    private final String attacksDirectoryPath = "src/assets/attacks";
    
    // Chemin vers le dossier contenant les chomeurs
    private final String chomeursDirectoryPath = "src/assets/chomeurs";
    
    // Chemin vers le dossier contenant les items
    private final String itemsDirectoryPath = "src/assets/items";

    @FXML
    public void initialize() {
        // Charger les attaques dans chaque ComboBox
        load(attack1ComboBox, attacksDirectoryPath);
        load(attack2ComboBox, attacksDirectoryPath);
        load(attack3ComboBox, attacksDirectoryPath);
        load(attack4ComboBox, attacksDirectoryPath);
        load(chomeurComboBox, chomeursDirectoryPath);
        load(itemComboBox, itemsDirectoryPath);
        
    }

    // Charger les fichiers d'attaques dans une ComboBox
    private void load(ComboBox<String> comboBox, String directoryPath) {
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        comboBox.getItems().add(file.getName());
                    }
                }
            }
        } else {
            showAlert("Erreur", "Le dossier est introuvable !");
        }
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // gestion du buton description
    @FXML
    private void handleDescription() {
    	String chomeur = chomeurComboBox.getValue(); 
    	String cheminFichier = "src/assets/chomeurs/" + chomeur;
    	String descriptionChomeurSelected = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
            	// Si la ligne contient "name", on la saute
                if (ligne.contains("name") || ligne.contains("attack")) {
                	continue;
                }
            	if ( ligne.contains("speed")) {
            				descriptionChomeurSelected += System.lineSeparator();
            			}
            	descriptionChomeurSelected += ligne + " ";
            }
            description.setText(descriptionChomeurSelected);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
    
    // gestion du buton add chomeur
    @FXML
    private void handleSubmit() {
    	String chomeur = chomeurComboBox.getValue();
    	String item = itemComboBox.getValue();
        String attack1 = attack1ComboBox.getValue();
        String attack2 = attack2ComboBox.getValue();
        String attack3 = attack3ComboBox.getValue();
        String attack4 = attack4ComboBox.getValue();

        if (chomeur == null || item == null || attack1 == null || attack2 == null || attack3 == null || attack4 == null) {
            showAlert("Erreur", "Veuillez sélectionner un chomeur, un objet et toutes les attaques.");
        } else {
            showAlert("chomeur sélected : " + chomeur  + "\n" ,
            	"item selected : " + item  + "\n" +
                "Attack 1 : " + attack1 + "\n" +
                "Attack 2 : " + attack2 + "\n" +
                "Attack 3 : " + attack3 + "\n" +
                "Attack 4 : " + attack4);
        }
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Home.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Accueil");
            stage.show();

            System.out.println("Retour à l'accueil !");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du retour à l'accueil.");
        }
    }
}
