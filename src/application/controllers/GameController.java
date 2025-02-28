package application.controllers;

import java.util.List;

import application.Game.Chomeur;
import application.Game.Player;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameController {
	
    @FXML
    private Canvas myCanvas;
    
    private GraphicsContext gc;
    
    private Player player1;
    private Player player2;
    
    @FXML
    public void initialize() {
    	
    	player1 = new Player();
    	player1.addChomeur("macron");
    	for(Chomeur chomeur : player1.getChomeurs()) {
        	System.out.println(chomeur.getString());
    	}
    }
}
