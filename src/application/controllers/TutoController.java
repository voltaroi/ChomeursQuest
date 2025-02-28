package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TutoController {

    @FXML
    private TextArea textArea1;
    
    @FXML
    private TextArea textArea2;
    
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
                      "attack4=none";

        // Affichage dans le TextArea1
        textArea1.setText(text1);
        
     // Texte avec des sauts de ligne
        String text2 = "name=test" + System.lineSeparator() +
                      "isAttackSpe = false" + System.lineSeparator() +
                      "att= 50" + System.lineSeparator() +
                      "type=poison" + System.lineSeparator() +
                      "effect=poison" + System.lineSeparator() ;
        
        
        //affichage dans le textarea2
        textArea2.setText(text2);
    }

    @FXML
    private void handleSubmitButtonAction1() {
        // Récupère le texte du TextArea, y compris les retours à la ligne
        String text = textArea1.getText();

        // Crée un fichier pour enregistrer le texte
        File file = new File("sauvegarde.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Sauvegarde le texte dans le fichier
            writer.write(text);
            writer.newLine();  // Ajoute une nouvelle ligne pour garantir la fin correcte du fichier
            showAlert("Succès", "Le texte a été sauvegardé avec succès dans 'sauvegarde.txt'.");
        } catch (IOException e) {
            // Gère les erreurs de sauvegarde du fichier
            showAlert("Erreur", "Erreur lors de la sauvegarde du fichier : " + e.getMessage());
        }
        
    }

    @FXML
    private void handleSubmitButtonAction2() {
        // Récupère le texte du TextArea, y compris les retours à la ligne
        String text = textArea2.getText();

        // Crée un fichier pour enregistrer le texte
        File file = new File("sauvegarde1.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Sauvegarde le texte dans le fichier
            writer.write(text);
            writer.newLine();  // Ajoute une nouvelle ligne pour garantir la fin correcte du fichier
            showAlert("Succès", "Le texte a été sauvegardé avec succès dans 'sauvegarde1.txt'.");
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
}
