package application.controllers;

import java.io.File;
import java.util.List;

import application.GameInstance;
import application.Game.Attack;
import application.Game.Chomeur;
import application.Game.Effect;
import application.Game.Player;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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
   	private VBox effect;
    
    @FXML
    private HBox attackHBox1;
    
    @FXML
    private HBox attackHBox2;
    
    @FXML
    private HBox listChomeur;
    
    private Player player1 = new Player("Player 1");
    private Player player2 = new Player("Player 2");
    
    private boolean endGame = false;
    
    @FXML
    public void initialize() {
    	
    	if(GameInstance.getIsMulti()) {
    		if(GameInstance.getSecondePlayerConnected()) {
    			System.out.print("seconde player connected");
    		}
    	}
    	
    	gc = myCanvas.getGraphicsContext2D();
    	myCanvas.setMouseTransparent(true);
    	chomeur1.setMouseTransparent(true);
    	chomeur2.setMouseTransparent(true);
    	effect.setMouseTransparent(true);
    	
        gc.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        
        Image backgroundImage = loadImage("/images/arene.jpg");

        gc.drawImage(backgroundImage, 0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        
        initPlayer(player1, chomeur1);
        initPlayer(player2, chomeur2);
        displayTeam();
    }
    
    public void initPlayer(Player player, VBox chomeurVBox) {
    	initView(player, chomeurVBox);
    	if(player == player1) {
        	initAttack(player);
    	}
    }
    
    public void initAttack(Player player) {
    	List<Attack> attacks = player.getChomeurActif().getAllAttack();
    	attackHBox1.getChildren().clear();
    	attackHBox2.getChildren().clear();
    	for (int i = 0; i < attacks.size(); i++) {
    		Attack attack = attacks.get(i);
    	    final int numAttack = i;
    		Button button = new Button(attack.getName());
    		System.out.println(attack.getName());
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
    	if(!GameInstance.getIsMulti()) {
        	if(GameInstance.getRound()) {
        		
        		GameInstance.setRound(false);
        		attack(attackName, player1, player2, chomeur2);		
        		
        		attackEnemy();
        		
        	}
    	}
    }
    
    public void attackEnemy() {
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Chomeur chomeur = player2.getChomeurActif();
        List<Attack> attacks = chomeur.getAllAttack();
        
        int randomIndex = (int) Math.floor(Math.random() * attacks.size());
        Attack attack = attacks.get(randomIndex);
        attack(attack.getName(), player2, player1, chomeur1);
        GameInstance.setRound(true);
        
        player1.updateItem();
        player2.updateItem();
    }
    
    public void attack(String attackName, Player player, Player player2 , VBox vbox) {
    	if(endGame) {
    		return;
    	}
    	
		Chomeur chomeur = player.getChomeurActif();
		Attack attack = chomeur.getAttackByName(attackName);
		
		float damageResult = 0;
		Chomeur chomeurEnemy = player2.getChomeurActif();
		
    	if(chomeur.updateEffects()) {
    		damageResult = attackSpe(damageResult, attack, chomeur, chomeurEnemy);
    		for(int i = 0; i < chomeur.getEffects().size(); i++) {
    			Effect effect = chomeur.getEffects().get(i);
    			
    			switch (effect.getName()) {
    		    case "confusedMe":
    		        chomeur.modifHp(damageResult / 8);
    		        break;
    		    case "confusedYou":
    		        chomeurEnemy.modifHp(damageResult / 8);
    		        break;
    		    case "attDef":
    		        chomeur.modifAtt(effect.getDamage());
    		        break;
    		    case "def":
    		        chomeur.modifDef(effect.getDamage());
    		        break;
    		    case "defYou":
    		        chomeurEnemy.modifDef(effect.getDamage());
    		        break;
    		    default:
    		        System.out.println("Effet inconnu : " + effect);
    		}

    		}
    		addMessage("La " + chomeur.getName() + " de " + player.getName() + " fait " + damageResult + " avec "+ attackName);
    		attackEffect(chomeurEnemy, attack);
    	}
    	
		chomeurEnemy = modifHp(chomeurEnemy, damageResult, player2);
		
		initChomeur(chomeurEnemy, vbox);
		initAttack(this.player1);
		displayTeam();
        displayEffet();

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
    
    public Chomeur modifHp(Chomeur chomeurEnemy, float damageResult, Player player) {
		if(chomeurEnemy.modifHp(-damageResult)) {
			
			addMessage(chomeurEnemy.getName() + " est KO");
			List<Chomeur> chomeurs = player.getChomeurs();
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
				alert.setContentText(player + " a perdu");
				alert.showAndWait();
			} else {
				player.setChomeurActif(lastI);
				chomeurEnemy = player.getChomeurActif();
				System.out.println(chomeurEnemy.getName());
			}
		}
		return chomeurEnemy;
    }
    
    public float attackSpe(float damageResult, Attack attack, Chomeur chomeur, Chomeur chomeurEnemy) {
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
    		
    		damageResult =  (float) (chomeur.getAttSpe() * (damageResult / chomeurEnemy.getDefSpe()) * (0.85 + Math.random() * (1 - 0.85)));
		} else {
    		damageResult = attack.getAtt();
    		damageResult =  (float) (chomeur.getAtt() * (damageResult / chomeurEnemy.getDef()) * (0.85 + Math.random() * (1 - 0.85)));
		}
    	return damageResult;
    }
    
    public void initView(Player player, VBox chomeurVBox) {
    	initChomeur(player.getChomeurActif(), chomeurVBox);
    }
    
    public void initChomeur(Chomeur chomeur, VBox chomeurVBox) {
    	chomeurVBox.getChildren().clear();
        drawText(chomeur.getName(), chomeurVBox);
        addImage(chomeur, chomeurVBox);
        addProgressBar(chomeur.getHp(), chomeur.getHpMax(), chomeurVBox);
    }
    
    public void addImage(Chomeur chomeur, VBox chomeurVBox) {
    	Image image = getDefaultImage();

        try {
            // 1. Vérifier si l'URI n'est ni null ni vide
            if (chomeur.getUri() != null && !chomeur.getUri().isEmpty()) {

                Image loadedImage = loadImage(chomeur.getUri());
                if (loadedImage != null) {
                    image = loadedImage;
                } else {
                    System.err.println("Erreur : Fichier introuvable -> " + chomeur.getUri());
                }
            } else {
                System.err.println("Erreur : URI vide ou null.");
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }

    	ImageView img = new ImageView(image);

        img.setFitWidth(300);
        img.setFitHeight(200);

        //anti déformation
        img.setPreserveRatio(true);
    	chomeurVBox.getChildren().add(img);
    }
    
    private Image getDefaultImage() {
        return new Image(getClass().getResource("/images/background.jpeg").toExternalForm());
    }

    private void displayEffet() {

        Image image = loadImage("/images/effet.png");
        ImageView imageView = new ImageView(image);
        
        effect.getChildren().add(imageView);

        // Supprimer l'image après 1 seconde
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            // Effacer la zone où l'image était dessinée
            effect.getChildren().clear();
        });
        pause.play();
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
    
    public void addMessage(String message) {
        Label label = new Label(message);
        info.getChildren().add(label);
    }
    
    public void drawText(String text, VBox chomeurVBox) {
        Label label = new Label(text);
        label.setFont(new Font(20));
        label.setStyle(
            "-fx-background-color: lightblue;" +  // Couleur de fond
            "-fx-background-radius: 20;" +        // Rayon des coins
            "-fx-padding: 10;"                    // Marge interne
        );

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
	   	 List<Chomeur> arrayListChomeur = player1.getChomeurs();
	     for (Chomeur  chomeur : arrayListChomeur) {
             Button button = new Button(chomeur.getName() + " " + chomeur.getHp() + "/" + chomeur.getHpMax() );
             button.setOnAction(event -> {
             	if(GameInstance.getRound()) {
             		if(player1.getChomeurActif() != chomeur) {
             			if(chomeur.getHp() > 0) {
                    		GameInstance.setRound(false);
    	   	               	player1.setChomeurActif(arrayListChomeur.indexOf(chomeur));
    	   	               	initPlayer(player1, chomeur1);
    	   	               	initAttack(player1);
    	   	               	displayTeam();
    	               		attackEnemy();
             			}
             		}
            		
            	} else {
            		addMessage("Ce n'est pas ton tour");
            	}
             });
             listChomeur.getChildren().add(button);
         }
    }
   	 
}
