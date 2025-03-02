package application.controllers;

import java.util.List;

import application.Game.Attack;
import application.Game.Chomeur;
import application.Game.Player;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
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
    
    @FXML
    private HBox attackHBox1;
    
    @FXML
    private HBox attackHBox2;
    
    private Player player1;
    private Player player2;
    
    private boolean round = true;
    
    @FXML
    private void testButton() {
        Button test = new Button("Test");
        test.setOnAction(event -> System.out.println("Bouton test cliqué !"));
        attackHBox1.getChildren().add(test);
    }

    
    @FXML
    public void initialize() {
    	
    	testButton();
    	
//    	int num = (int) (Math.random() * 2);
//    	if(num == 0) {
//    		round = true;
//    	} else {
//    		round = false;
//    	}
    	
    	gc = myCanvas.getGraphicsContext2D();
    	gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        
    	player1 = new Player();
    	player1.addChomeur("macron");
    	for(Chomeur chomeur : player1.getChomeurs()) {
        	addMessage(chomeur.getString());
    	}
    	
    	initAttack(player1);
    	
    	List<Chomeur> chomeurs = player1.getChomeurs();
    	initChomeur(chomeurs.get(0), chomeur1);
    	
    	player2 = new Player();
    	player2.addChomeur("Brigitte");
    	for(Chomeur chomeur : player2.getChomeurs()) {
        	addMessage(chomeur.getString());
    	}
    	
    	chomeurs = player2.getChomeurs();
    	initChomeur(chomeurs.get(0), chomeur2);
    }
    
    public void initPlayer(Player player, VBox chomeurVBox) {
    	initView(player, chomeurVBox);
    	initAttack(player);
    }
    
    public void initAttack(Player player) {
    	List<Chomeur> chomeurs = player.getChomeurs();
    	List<Attack> attacks = chomeurs.get(0).getAllAttack();
    	attackHBox1.getChildren().clear();
    	int numAttack = 0;
    	for(Attack attack : attacks) {
    		numAttack += 1;
    		Button button = new Button(attack.getName());
    		button.setOnAction(event -> { buttonAttack(attack); });
    		button.setFont(new Font(16));
    		if(numAttack < 3) {
            	attackHBox1.getChildren().add(button);
    		} else {
            	attackHBox2.getChildren().add(button);
    		}
    	}
    }

    public void buttonAttack(Attack attack) { 
    	if(round) {
    		addMessage(attack.getName());
    	} else {
    		addMessage("Ce n'est pas ton tour");
    	}
    }
    
    public void initView(Player player, VBox chomeurVBox) {
    	
    	player = new Player();
    	player.addChomeur("Brigitte");
    	for(Chomeur chomeur : player.getChomeurs()) {
        	addMessage(chomeur.getString());
    	}
    	
    	List<Chomeur> chomeurs = player.getChomeurs();
    	initChomeur(chomeurs.get(0), chomeurVBox);
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
