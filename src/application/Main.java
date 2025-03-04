package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.sound.sampled.*;

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
        String path = Paths.get(System.getProperty("user.dir"), "resources", "sound" , "Pokemon.wav").toString();
        playSound(path);
        
    }
    
    public static void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Joue en boucle
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
