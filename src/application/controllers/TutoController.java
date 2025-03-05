package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class TutoController {

    @FXML
    private TextArea textArea1;
    
    @FXML
    private TextArea textArea2;
    
    @FXML
    private ImageView imageView; // Liaison avec l'ImageView du FXML
    
    private Stage stage; // Référence à la fenêtre principale
    
    private String uri;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        // Texte avec des sauts de ligne
        String text1 = "name=Macron" + System.lineSeparator() +
                      "hp=12" + System.lineSeparator() +
                      "att=4" + System.lineSeparator() +
                      "attSpe=6" + System.lineSeparator() +
                      "def=3" + System.lineSeparator() +
                      "defSpe=4" + System.lineSeparator() +
                      "speed=1" + System.lineSeparator() +
                      "type1=politique" + System.lineSeparator() +
                      "type2=none" + System.lineSeparator() +
                      "attack1=attackTest" + System.lineSeparator() +
                      "attack2=none" + System.lineSeparator() +
                      "attack3=none" + System.lineSeparator() +
                      "attack4=none" + System.lineSeparator() +
                      "listAttackSpe=test1.txt test2.txt" + System.lineSeparator() +
                      "uri=" + uri;
                      

        // Affichage dans le TextArea1
        textArea1.setText(text1);
        
     // Texte avec des sauts de ligne
        String text2 = "name=test" + System.lineSeparator() +
                      "isAttackSpe = false" + System.lineSeparator() +
                      "att= 50" + System.lineSeparator() +
                      "type=poison" + System.lineSeparator() +
                      "effect=poison" + System.lineSeparator() +
                      "effectTime=2" + System.lineSeparator() +
              		  "effectDamage=0" ;
        
        
        //affichage dans le textarea2
        textArea2.setText(text2);
    }
    private String getName(TextArea textArea) {
        // Récupère le texte du TextArea
        String text = textArea.getText();

        // Crée un BufferedReader à partir d'un StringReader
        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Vérifie si la ligne commence par "name="
                if (line.startsWith("name=")) {
                    // Retourne le nom après le "="
                    return line.split("=")[1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si aucun "name=" n'est trouvé
        return null;
    }

    @FXML
    private void handleSubmitButtonAction1() {
        // Récupère le texte du TextArea, y compris les retours à la ligne
        String text = textArea1.getText();
       
        // Définir le chemin du dossier cible
        String folderPath = "src/assets/chomeurs/";
        
        // Crée un fichier pour enregistrer le texte
        File file = new File(folderPath, this.getName(textArea1) + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Sauvegarde le texte dans le fichier
            writer.write(text);
            writer.newLine();  // Ajoute une nouvelle ligne pour garantir la fin correcte du fichier
            showAlert("Succès", "Le texte a été sauvegardé avec succès dans " + this.getName(textArea1) + ".txt");
        } catch (IOException e) {
            // Gère les erreurs de sauvegarde du fichier
            showAlert("Erreur", "Erreur lors de la sauvegarde du fichier : " + e.getMessage());
        }
        
    }

    @FXML
    private void handleSubmitButtonAction2() {
        // Récupère le texte du TextArea, y compris les retours à la ligne
        String text = textArea2.getText();
        
        // Définir le chemin du dossier cible
        String folderPath = "src/assets/attacks/";
        
        // Crée un fichier pour enregistrer le texte
        File file = new File(folderPath, this.getName(textArea2) + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Sauvegarde le texte dans le fichier
            writer.write(text);
            writer.newLine();  // Ajoute une nouvelle ligne pour garantir la fin correcte du fichier
            showAlert("Succès", "Le texte a été sauvegardé avec succès dans " + this.getName(textArea2) + ".txt");
        } catch (IOException e) {
            // Gère les erreurs de sauvegarde du fichier
            showAlert("Erreur", "Erreur lors de la sauvegarde du fichier : " + e.getMessage());
        }
    }
    
    // Méthode utilitaire pour afficher des alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    
    // Méthode appelée lorsque l'utilisateur clique sur "Select Image"
    @FXML
    private void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");

        // Filtrer pour ne montrer que les images
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Ouvrir le sélecteur de fichier
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                // Charger et afficher l'image sélectionnée
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                uri = selectedFile.getAbsolutePath();
                initialize();
               // System.out.println("Image loaded: " + selectedFile.getAbsolutePath());
            } catch (Exception e) {
                showError("Error loading image", e.getMessage());
            }
        }
    }

    // Afficher une alerte en cas d'erreur
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
