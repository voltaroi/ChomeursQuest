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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

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
    
    @FXML
    private VBox image;
    
    private String team;
    
    private int numberChomeurTeam = 0;
    
    @FXML
    private HBox listChomeur;
        

    // Chemin vers le dossier contenant les attaques
    private final String attacksDirectoryPath = "src/assets/attacks/";
    
    // Chemin vers le dossier contenant les chomeurs
    private final String chomeursDirectoryPath = "src/assets/chomeurs/";
    
    // Chemin vers le dossier contenant les items
    private final String itemsDirectoryPath = "src/application/Game/items/";

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
        handleClear();
    }

    // Charger les fichiers dans une ComboBox
    private void load(ComboBox<String> comboBox, String directoryPath) {
        File directory = new File(directoryPath);
        comboBox.getItems().clear();
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
        File directory = new File(attacksDirectoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                	if (compare(getType(attacksDirectoryPath + file.getName()), "neutre")) {
                		comboBox.getItems().add(file.getName());
                	}
                    if (file.isFile() && compare(getListAttack(chomeursDirectoryPath + chomeur), file.getName())) {
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
    
    public String getListAttack(String filePath) {
    	String listAttack = "" ;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    switch (parts[0]) {
	                    case "listAttackSpe":
	                    	listAttack += parts[1];
	                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listAttack;
    }
    @FXML
    private void displayPicture() {
    	image.getChildren().clear();
    	String chomeur = chomeurComboBox.getValue() != null ? chomeurComboBox.getValue() : "macron.txt";
    	Image img = loadImage(getURI(chomeursDirectoryPath + chomeur));
    	ImageView imageView = new ImageView(img);

        imageView.setFitWidth(200);
        imageView.setFitHeight(150);

        //anti déformation
        imageView.setPreserveRatio(true);
        image.getChildren().add(imageView);
    }
    private Image loadImage(String path) {
        try {
            // 1. Si c'est un chemin absolu sur le système
            File file = new File(path);
            if (file.exists()) {
                return new Image(file.toURI().toString());
            }

            // 2. Si c'est une ressource du projet (dans src/main/resources)
            var resource = getClass().getResource(path);
            if (resource != null) {
                return new Image(resource.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }

        return null;
    }
    
    private String getURI(String filePath) {
       	String uri = "" ;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    switch (parts[0]) {
	                    case "uri":
	                    	uri += parts[1];
	                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
    
    public static boolean compare(String ligne1, String ligne2) {
        Set<String> motsLigne1 = new HashSet<>(Arrays.asList(ligne1.split(" ")));
        Set<String> motsLigne2 = new HashSet<>(Arrays.asList(ligne2.split(" ")));
        
        motsLigne1.retainAll(motsLigne2);

        if (!motsLigne1.isEmpty()) {
          //  System.out.println("Mots communs trouvés : " + motsLigne1);
            return true;
        } else {
          //  System.out.println("Aucun mot commun trouvé.");
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
    @FXML
    private void displayDescription() {
    	String chomeur = chomeurComboBox.getValue(); 
    	String cheminFichier = "src/assets/chomeurs/" + chomeur;
    	String descriptionChomeurSelected = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
            	// Si la ligne contient "name", on la saute
                if (ligne.contains("name") || ligne.contains("ttack")|| ligne.contains("uri")) {
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
            displayPicture();
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
    
    // gestion du buton add chomeur
    @FXML
    private void handleAdd() {
    	String chomeur = chomeurComboBox.getValue();
    	String item = itemComboBox.getValue();
        String attack1 = attack1ComboBox.getValue()!= "" ? attack1ComboBox.getValue() : "none";
        String attack2 = attack2ComboBox.getValue()!= "" ? attack2ComboBox.getValue() : "none";
        String attack3 = attack3ComboBox.getValue()!= "" ? attack3ComboBox.getValue() : "none";
        String attack4 = attack4ComboBox.getValue()!= "" ? attack4ComboBox.getValue() : "none";

        if (numberChomeurTeam > 5) {
        	showAlert("erreur", "impossible d'ajouter plus de 6 chomeurs");
        	return;
        }
        if (chomeur == null || item == null || attack1 == null || attack2 == null || attack3 == null || attack4 == null) {
            showAlert("Erreur", "Veuillez sélectionner un chomeur et un objet.");
        } else {
            showAlert("chomeur sélected = " + chomeur  + "\n" ,
            	"item selected = " + item  + "\n" +
                "Attack1=" + attack1 + "\n" +
                "Attack2=" + attack2 + "\n" +
                "Attack3=" + attack3 + "\n" +
                "Attack4=" + attack4);
            numberChomeurTeam ++;
            addChomeur("\n"+
            "name=" + chomeur + "\n" + 
            "item=" + item + "\n" + 
            "attack1=" + attack1 + "\n" +
            "attack2=" + attack2 + "\n" +
            "attack3=" + attack3 + "\n" +
            "attack4=" + attack4 + "\n", "team.txt");
            displayTeam();
        }
    }
    
    private void displayTeam() {
    	 listChomeur.getChildren().clear();
    	 List<String> noms = new ArrayList<>();
    	 String path = Paths.get(System.getProperty("user.dir"), "src", "assets", "team", "team.txt").toString();
    	 try (BufferedReader br = new BufferedReader(new FileReader(path))) {
             String ligne;
             while ((ligne = br.readLine()) != null) {
            	 if (ligne.startsWith("name=")) {
                     // Extraire la partie après "name="
                     String nom = ligne.substring(5); // 5 = longueur de "name="
                     noms.add(nom);
            	 }
             }
            
                 for (String nom : noms) {
                	 String filename = nom.replace(".txt", "");  // Supprime ".txt"
                     Button button = new Button(filename); // Crée un bouton avec le nom
                     button.setOnAction(event -> {
                         System.out.println(filename + " a été cliqué !");
                     }); // Ajouter un gestionnaire d'événements
                     listChomeur.getChildren().add(button); // Ajout du bouton au VBox
                 }
             
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
    
    private void addChomeur(String text, String teamName) {
	    team += text;
    	
	    // Définir le chemin du dossier cible
	    String folderPath = "src/assets/team/";
	    
	    // Crée un fichier pour enregistrer le texte
	    File file = new File(folderPath, teamName);
	
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	        // Sauvegarde le texte dans le fichier
	        writer.write(team);
	        writer.newLine();  // Ajoute une nouvelle ligne pour garantir la fin correcte du fichier
	       // showAlert("Succès","team.txt a été mis à jour");
	    } catch (IOException e) {
	        // Gère les erreurs de sauvegarde du fichier
	        showAlert("Erreur", "Erreur lors de la sauvegarde du fichier : " + e.getMessage());
	    }
    }
    private void clearTeam() {
    	team = "";
    	addChomeur("","team.txt");
    	addChomeur("","cpuTeam.txt");
    }
    @FXML
    private void handleClear() {
    	clearTeam();
    	displayTeam();
    }
    private String random(String directoryPath) {
    	File directory = new File(directoryPath);

        // Check if the path is a valid directory
        if (directory.exists() && directory.isDirectory()) {
            // Get the list of files (excluding subdirectories)
            File[] files = directory.listFiles(File::isFile);
            
            if (files != null && files.length > 0) {
                // Select a random file
                Random random = new Random();
                File randomFile = files[random.nextInt(files.length)];
        
                return randomFile.getName();
            }
        }
        return"";
    }
    @FXML
    private void handleRandom(ActionEvent event) {            
    	randomTeam("team.txt");
    	randomTeam("cpuTeam.txt");
    	navigateTo(event, "/views/Game.fxml", "Game");
    }
    private void randomTeam(String teamName) {
    	team = "";
    	Random random = new Random();
        int NbRandom = random.nextInt(4) + 3;
        for(int i= 0;i< NbRandom;i++) {
           String chomeur = random(chomeursDirectoryPath);
           String item = random(itemsDirectoryPath);
           String attack1 = attackRandom(chomeur);
           String attack2 = attackRandom(chomeur);
           String attack3 = attackRandom(chomeur);
           String attack4 = attackRandom(chomeur);
        
        	addChomeur("\n"+
                "name=" + chomeur + "\n" + 
                "item=" + item + "\n" + 
                "attack1=" + attack1 + "\n" +
                "attack2=" + attack2 + "\n" +
                "attack3=" + attack3 + "\n" +
                "attack4=" + attack4 + "\n",teamName);
    	}    	
    }
    private String attackRandom(String chomeur) {
    	List<String> list = new ArrayList<>();
    File directory = new File(attacksDirectoryPath);
    if (directory.exists() && directory.isDirectory()) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
            	if (compare(getType(attacksDirectoryPath + file.getName()), "neutre")) {
            		list.add(file.getName());
            	}
                if (file.isFile() && compare(getListAttack(chomeursDirectoryPath + chomeur), file.getName())) {
                		list.add(file.getName());
                                     
                }
            }
        }
    }
    return getRandomElement(list);
    }
    
    public static <T> T getRandomElement(List<T> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
    @FXML
    private void handleReady(ActionEvent event) {
    	if(numberChomeurTeam < 3) {
    		 showAlert("Erreur", "pas asseaz de chomeurs");
    	}
    	if(numberChomeurTeam > 6) {
   		 showAlert("Erreur", "trop de chomeurs");
     	}
    	if(numberChomeurTeam >= 3 && numberChomeurTeam <= 6) { 
    		navigateTo(event, "/views/Game.fxml", "Game");
    	}
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
