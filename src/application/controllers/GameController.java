package application.controllers;

import java.util.ArrayList;
import java.util.List;

import application.Game.Attack;
import application.Game.Chomeur;
import application.Game.Effect;
import application.Game.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    
    @FXML
    private HBox listChomeur;
    
    private Player player1 = new Player();
    private Player player2 = new Player();
    
    private boolean round = true;
    
    private boolean endGame = false;
    
    @FXML
    public void initialize() {
    	
    	gc = myCanvas.getGraphicsContext2D();
    	myCanvas.setMouseTransparent(true);
    	chomeur1.setMouseTransparent(true);
    	chomeur2.setMouseTransparent(true);

    	gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        
        initPlayer(player1, chomeur1);
        initPlayer(player2, chomeur2);
        displayTeam();
    }
    
    public void initPlayer(Player player, VBox chomeurVBox) {
    	initView(player, chomeurVBox);
    	initAttack(player);
    }
    
    public void initAttack(Player player) {
    	List<Attack> attacks = player.getChomeurActif().getAllAttack();
    	attackHBox1.getChildren().clear();
    	attackHBox2.getChildren().clear();
    	for (int i = 0; i < attacks.size(); i++) {
    		Attack attack = attacks.get(i);
    	    final int numAttack = i;
    		Button button = new Button(attack.getName());
    		button.setOnAction(event -> { buttonAttack(attack.getName()); });
    		button.setFont(new Font(16));
    		if(numAttack < 3) {
            	attackHBox1.getChildren().add(button);
    		} else {
            	attackHBox2.getChildren().add(button);
    		}
    	}
		Platform.runLater(() -> {
		    attackHBox1.layout();
		    attackHBox2.layout();
		});
    }

    public void buttonAttack(String attackName) { 
    	if(round) {
    		
    		round = false;
    		attack(attackName, player1, player2, chomeur2);		
    		
    		attackEnemy();
    		
    	} else {
    		addMessage("Ce n'est pas ton tour");
    	}
    }
    
    public void attackEnemy() {
        Chomeur chomeur = player2.getChomeurActif();
        List<Attack> attacks = chomeur.getAllAttack();
        
        int randomIndex = (int) Math.floor(Math.random() * attacks.size());
        Attack attack = attacks.get(randomIndex);
        attack(attack.getName(), player2, player1, chomeur1);
        round = true;
    }
    
    public void attack(String attackName, Player player, Player player2 , VBox vbox) {
    	
    	if(endGame) {
    		return;
    	}
    	
		Chomeur chomeur = player.getChomeurActif();
		Attack attack = chomeur.getAttackByName(attackName);
		
		float damageResult = 0;
		Chomeur chomeurEnemy = player2.getChomeurActif();
		
    	if(player.getChomeurActif().updateEffects()) {
    		damageResult = attackSpe(damageResult, attack, chomeurEnemy);
    		addMessage("La " + chomeur.getName() + " de " + player + " fait " + damageResult + " avec "+ attackName);
    		attackEffect(chomeurEnemy, attack);
    	}
    	
		chomeurEnemy = modifHp(chomeurEnemy, damageResult);
		
		initChomeur(chomeurEnemy, vbox);
		initAttack(this.player1);
    }
    
    public void attackEffect(Chomeur chomeurEnemy, Attack attack) {
    	if(!attack.getEffect().getName().equals("none")) {
    		Effect effect = new Effect();
    		effect.setName(attack.getEffect().getName());
    		effect.setNumRound(attack.getEffect().getNumRound());
    		effect.setDamage(attack.getEffect().getDamage());
    		chomeurEnemy.addEffect(effect);
    	}
    }
    
    public Chomeur modifHp(Chomeur chomeurEnemy, float damageResult) {
		if(chomeurEnemy.modifHp(-damageResult)) {
			addMessage(chomeurEnemy.getName() + " est KO");
			List<Chomeur> chomeurs = player2.getChomeurs();
			boolean isFind = false;
			int lastI = 0;
			
			for(int i = 0; i < chomeurs.size(); i++) {
				if(chomeurs.get(i).getHp() > 0) {
					isFind = true;
					lastI = i;
				}
			}
			
			if(!isFind) {
				endGame = true;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Fin de partie");
				alert.setHeaderText(null);
				alert.setContentText(player2 + " a perdu");
				alert.showAndWait();
			} else {
				System.out.println(lastI);
				player2.setChomeurActif(lastI);
				chomeurEnemy = player2.getChomeurActif();
			}
		}
		return chomeurEnemy;
    }
    
    public float attackSpe(float damageResult, Attack attack, Chomeur chomeurEnemy) {
		if(attack.isAttackSpe()) {
    		boolean typeEqual = false;
    		for(String type : chomeurEnemy.getTypes()) {
    			if(type.equals(attack.getType())) {
    				typeEqual = true;
    			}
    		}
    		if(typeEqual) {
    			damageResult = attack.getAtt() / 2;
    		} else {
    			damageResult = attack.getAtt();
    		}
		} else {
    		damageResult = attack.getAtt();
		}
    	return damageResult;
    }
    
    public void initView(Player player, VBox chomeurVBox) {
    	initChomeur(player.getChomeurActif(), chomeurVBox);
    }
    
    public void initChomeur(Chomeur chomeur, VBox chomeurVBox) {
    	chomeurVBox.getChildren().clear();
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
    
    private void displayTeam() {
	   	 listChomeur.getChildren().clear();
	   	 List<Chomeur> ArrayListChomeur = player1.getChomeurs();
	   	 List<String> noms = new ArrayList<>();
	   	 for (Chomeur chomeur : ArrayListChomeur) {
	   		 noms.add(chomeur.getName());
	   	 }
    }
   	 
//   	 String path = Paths.get(System.getProperty("user.dir"), "src", "assets", "team", "team.txt").toString();
//   	 try (BufferedReader br = new BufferedReader(new FileReader(path))) {
//            String ligne;
//            while ((ligne = br.readLine()) != null) {
//           	 if (ligne.startsWith("name=")) {
//                    // Extraire la partie après "name="
//                    String nom = ligne.substring(5); // 5 = longueur de "name="
//                    noms.add(nom);
//           	 }
//            }
//           
//            for (String nom : noms) {
//           	 String filename = nom.replace(".txt", "");  // Supprime ".txt"
//                Button button = new Button(filename); // Crée un bouton avec le nom
//                button.setOnAction(event -> {
//                    System.out.println(filename + " a été cliqué !");
//                }); // Ajouter un gestionnaire d'événements
//                listChomeur.getChildren().add(button); // Ajout du bouton au VBox
//            }
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//   }
}
