package application.controllers;

import java.util.List;

import application.Game.Chomeur;
import application.Game.Player;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class GameController {
	
    @FXML
    private Canvas myCanvas;
    
    @FXML
    private VBox info;
    private GraphicsContext gc;
    
    @FXML
	private VBox chomeur1;
    
    @FXML
	private VBox chomeur2;
    
    private Player player1;
    private Player player2;
    
    @FXML
    public void initialize() {
    	
    	gc = myCanvas.getGraphicsContext2D();
    	gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        
    	player1 = new Player();
    	player1.addChomeur("macron");
    	for(Chomeur chomeur : player1.getChomeurs()) {
        	addMessage(chomeur.getString());
    	}
    	
    	List<Chomeur> chomeurs = player1.getChomeurs();
    	for(Chomeur chomeur : chomeurs) {
    		initChomeur(chomeur, chomeur1);
    	}
    	
    	player2 = new Player();
    	player2.addChomeur("Brigitte");
    	for(Chomeur chomeur : player2.getChomeurs()) {
        	addMessage(chomeur.getString());
    	}
    	
    	chomeurs = player2.getChomeurs();
    	for(Chomeur chomeur : chomeurs) {
    		initChomeur(chomeur, chomeur2);
    	}
    }
    
    public void initChomeur(Chomeur chomeur, VBox chomeurVBox) {
    	gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, myCanvas.getWidth()/2, myCanvas.getHeight()/2);
        drawText(chomeur.getName(), chomeurVBox);
        addProgressBar(chomeur.getHp(), chomeur.getHpMax(), chomeurVBox);
    }
    
    public void addMessage(String message) {
        Label label = new Label(message);
        info.getChildren().add(label);
    }
    
    public void drawText(String text, VBox chomeurVBox) {
        Label label = new Label(text);
        label.setFont(new Font(20));
        chomeurVBox.getChildren().add(label);
    }
    
    public void addProgressBar(float value, float maxValue, VBox chomeurVBox) {
        ProgressBar pb = new ProgressBar();
        pb.setPrefWidth(200);
        pb.setProgress(value / maxValue);
        chomeurVBox.getChildren().add(pb);
    }
}
