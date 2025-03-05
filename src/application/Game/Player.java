package application.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private List<Chomeur> chomeurs;
	
	private int chomeurActif = 0;
	
	
	public Player() {
		chomeurs = new ArrayList<>();
		String path = Paths.get(System.getProperty("user.dir"), "src", "assets", "team", "team" + ".txt").toString();
		addTeam(path);	
	}
	public Player(List<Chomeur> chomeurs, int chomeurActif) {
		this.chomeurs = chomeurs;
		this.chomeurActif = chomeurActif;
	}
	
	public void addTeam(String path) {
		int numberChomeur = getNumberChomeur(path);
		for (int i = 0; i < numberChomeur; i++) {
			Chomeur newChomeur = new Chomeur();
			newChomeur = fromTeam(path, newChomeur, i + 1);
			chomeurs.add(newChomeur);
		}
	}
	
	public void addChomeur(String chomeur) {
		Chomeur newChomeur = new Chomeur();
		String path = Paths.get(System.getProperty("user.dir"), "src", "assets", "chomeurs", chomeur + ".txt").toString();
		newChomeur.FromFile(path);
		chomeurs.add(newChomeur);
	}
	
	public void addChomeur(Chomeur chomeur) {
		chomeurs.add(chomeur);
	}
	
	public void removeChomeur(Chomeur chomeur) {
		chomeurs.remove(chomeur);
	}
	
	public List<Chomeur> getChomeurs() {
		return chomeurs;
	}
	
	public Chomeur getChomeurActif() {
		return chomeurs.get(chomeurActif);
	}
	
	public int getNumberChomeurActif() {
		return chomeurActif;
	}
		
	public void setChomeurActif(int num) {// entre 0 et 5
		chomeurActif = num;
	}
	
	public int getNumberChomeur(String filePath) {
		 try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            int lineCount = 0;
	            while ((line = br.readLine()) != null) {
	                lineCount++;
	            } 
	            return (int) lineCount / 7; 
		 } catch (IOException e) {
	            e.printStackTrace();
	            return 3;
	        }	              
	}
	
	public Chomeur fromTeam(String filePath, Chomeur chomeur, int chomeurNumber) {
	    int chunkSize = 7;  // Taille du bloc (7 lignes par chômeur)
	    String chomeurPath = Paths.get(System.getProperty("user.dir"), "src", "assets", "chomeurs").toString();
	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        int lineCount = 0;
	        
	        // Ignorer les lignes jusqu'au chômeur numéro 'chomeurNumber'
	        for (int i = 0; i < chunkSize * (chomeurNumber - 1); i++) {
	            br.readLine();  // Ignore les lignes avant le chômeur souhaité
	        }

	        // Lire les 7 lignes du chômeur actuel
	        while ((line = br.readLine()) != null && lineCount < chunkSize) {
	        	String[] parts = line.split("=");  // Séparer la ligne en clé et valeur
	            lineCount++;
	            System.out.println(line);

	            if (parts.length == 2) {
	                    switch (parts[0]) {
	                        case "name":
	                            String name = parts[1];
	                            chomeur.setName(name);
	                            chomeur.FromFile(chomeurPath+"/"+name);
	                            chomeur.clearAttacks();	                            
	                            break;
	                        case "item":
	                        	String path = Paths.get(System.getProperty("user.dir"), "src", "assets", "items", parts[1] + ".txt").toString();
	                        	Item newItem = new Item();
	                        	newItem.FromFile(path);
	                        	chomeur.setItem(newItem);
	                        	break;
	                        case "attack1":
	                            chomeur.newAttack(parts[1]);
	                            break;
	                        case "attack2":
	                            chomeur.newAttack(parts[1]);
	                            break;
	                        case "attack3":
	                            chomeur.newAttack(parts[1]);
	                            break;
	                        case "attack4":
	                            chomeur.newAttack(parts[1]);
	                            break;
	                    }
	                }
	            }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return chomeur;
	}
}
