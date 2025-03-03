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
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;



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
    
    private String team;
        

    // Chemin vers le dossier contenant les attaques
    private final String attacksDirectoryPath = "src/assets/attacks/";
    
    // Chemin vers le dossier contenant les chomeurs
    private final String chomeursDirectoryPath = "src/assets/chomeurs/";
    
    // Chemin vers le dossier contenant les items
    private final String itemsDirectoryPath = "src/assets/items/";

    @FXML
    public void initialize() {
        load(chomeurComboBox, chomeursDirectoryPath);
        // Écouter l'action (lorsqu'un élément est sélectionné)
        chomeurComboBox.setOnAction(e -> {
            displayDescription();
            // Charger les attaques dans chaque ComboBox
            loadAttack(attack1ComboBox);
            loadAttack(attack2ComboBox);
            loadAttack(attack3ComboBox);
            loadAttack(attack4ComboBox); 
            load(itemComboBox, itemsDirectoryPath);
        });
       
        
    }

    // Charger les fichiers dans une ComboBox
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
    
 // Charger les fichiers attaques dans une ComboBox
    private void loadAttack(ComboBox<String> comboBox) {
    	comboBox.getItems().clear();
    	String chomeur = chomeurComboBox.getValue() != null ? chomeurComboBox.getValue() : "macron.txt";
    	String chomeurTypes = getType(chomeursDirectoryPath + chomeur);
        File directory = new File(attacksDirectoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                	if (compare(getType(attacksDirectoryPath + file.getName()), "neutre")) {
                		comboBox.getItems().add(file.getName());
                	}
                	System.out.println(getType(attacksDirectoryPath + file.getName()));
                	System.out.println(chomeurTypes);
                    if (file.isFile()&&compare(getType(attacksDirectoryPath + file.getName()), chomeurTypes)) {
                    		comboBox.getItems().add(file.getName());
                                         
                    }
                }
            }
        } else {
            showAlert("Erreur", "Le dossier est introuvable !");
        }
    }
    
    // lire le type de chomeur.txt
    public String getType(String filePath) {

    	String type = "" ;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    switch (parts[0]) {
	                    case "type1":
	                    	type += parts[1] + " ";
	                        break;
	                    case "type2":
	                    	type += parts[1] + " ";
	                        break;
	                    case "type":
	                    	type += parts[1];
	                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return type;
    }
    
    public static boolean compare(String ligne1, String ligne2) {
        Set<String> motsLigne1 = new HashSet<>(Arrays.asList(ligne1.split(" ")));
        Set<String> motsLigne2 = new HashSet<>(Arrays.asList(ligne2.split(" ")));
        
        motsLigne1.retainAll(motsLigne2);

        if (!motsLigne1.isEmpty()) {
            System.out.println("Mots communs trouvés : " + motsLigne1);
            return true;
        } else {
            System.out.println("Aucun mot commun trouvé.");
            return false;
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
    
    // affiche description
    private void displayDescription() {
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
            attack1ComboBox.setValue("");
            attack2ComboBox.setValue("");
            attack3ComboBox.setValue("");
            attack4ComboBox.setValue("");
            loadAttack(attack1ComboBox);
            loadAttack(attack2ComboBox);
            loadAttack(attack3ComboBox);
            loadAttack(attack4ComboBox);
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
    
    // gestion du buton add chomeur
    @FXML
    private void handleAdd() {
    	String chomeur = chomeurComboBox.getValue();
    	String item = itemComboBox.getValue();
        String attack1 = attack1ComboBox.getValue();
        String attack2 = attack2ComboBox.getValue();
        String attack3 = attack3ComboBox.getValue();
        String attack4 = attack4ComboBox.getValue();

        
        if (chomeur == null || item == null || attack1 == null || attack2 == null || attack3 == null || attack4 == null) {
            showAlert("Erreur", "Veuillez sélectionner un chomeur, un objet et toutes les attaques.");
        } else {
            showAlert("chomeur sélected = " + chomeur  + "\n" ,
            	"item selected = " + item  + "\n" +
                "Attack 1 = " + attack1 + "\n" +
                "Attack 2 = " + attack2 + "\n" +
                "Attack 3 = " + attack3 + "\n" +
                "Attack 4 = " + attack4);
            addChomeur("\n"+
            "name =" + chomeur + "\n" + 
            "item =" + item + "\n" + 
            "attack1 =" + attack1 + "\n" +
            "attack2 =" + attack2 + "\n" +
            "attack3 =" + attack3 + "\n" +
            "attack4 =" + attack4 + "\n");
        }
    }
    
    private void addChomeur(String text) {
	    team += text;
    	
	    // Définir le chemin du dossier cible
	    String folderPath = "src/assets/team/";
	    
	    // Crée un fichier pour enregistrer le texte
	    File file = new File(folderPath, "team.txt");
	
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	        // Sauvegarde le texte dans le fichier
	        writer.write(team);
	        writer.newLine();  // Ajoute une nouvelle ligne pour garantir la fin correcte du fichier
	        showAlert("Succès","team.txt a été mis à jour");
	    } catch (IOException e) {
	        // Gère les erreurs de sauvegarde du fichier
	        showAlert("Erreur", "Erreur lors de la sauvegarde du fichier : " + e.getMessage());
	    }
    }
    
    @FXML
    private void handleClear() {
    	team = "";
    	addChomeur("");
    }
    
    @FXML
    private void handleRandom(ActionEvent event) {
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
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de " + fxmlPath);
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
