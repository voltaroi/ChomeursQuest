package application.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import application.Game.Attack;
import application.Game.Player;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class GameController {
	
    @FXML
    private Canvas myCanvas;
    
    private GraphicsContext gc;
    
    private Player player1;
    private Player player2;
    
    @FXML
    public void initialize() {
    	
    	readAttackFromFile("d");
    	
    	player1.addChomeurs(null);
    	player2.addChomeurs(null);
    	
    }
    
    public Attack readAttackFromFile(String filePath) {
        String name = "";
        boolean isAttackSpe = false;
        float att = 0;
        String type = "";
        String effect = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    switch (parts[0]) {
                        case "name":
                            name = parts[1];
                            break;
                        case "isAttackSpe":
                            isAttackSpe = Boolean.parseBoolean(parts[1]);
                            break;
                        case "att":
                            att = Float.parseFloat(parts[1]);
                            break;
                        case "type":
                            type = parts[1];
                            break;
                        case "effect":
                            effect = parts[1];
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Attack(name, isAttackSpe, att, type, effect);
    }

}
