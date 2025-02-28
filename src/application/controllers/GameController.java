package application.controllers;

import application.Game.Attack;
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
    	Attack attack = new Attack();
    	String name = "attackTest";
    	attack.FromFile("C:\\Users\\volta\\eclipse-workspace\\ChomeursQuest\\src\\assets\\" + name +".txt");
    	System.out.println(attack.getString());
    	
    	player1 = new Player();
    	player2 = new Player();
    	
    	player1.addChomeurs(null);
    	player2.addChomeurs(null);
    	
    }
}
