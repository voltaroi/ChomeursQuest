package application.Game;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private List<Chomeur> chomeurs;
	
	private int chomeurActif = 0;
	
	public Player() {
		chomeurs = new ArrayList<>();
		Chomeur newChomeur = new Chomeur();
		String path = Paths.get(System.getProperty("user.dir"), "src", "assets", "team", "random" + ".txt").toString();
		newChomeur.fromTeam(path,1);
		chomeurs.add(newChomeur);
	}
	
	public void addChomeur(String chomeur) {
		Chomeur newChomeur = new Chomeur();
		String path = Paths.get(System.getProperty("user.dir"), "src", "assets", "chomeurs", chomeur + ".txt").toString();
		newChomeur.FromFile(path);
		chomeurs.add(newChomeur);
	}
	
	public List<Chomeur> getChomeurs() {
		return chomeurs;
	}
	
	public Chomeur getChomeurActif() {
		return chomeurs.get(chomeurActif);
	}
	
	public void setChomeurActif(int num) {
		chomeurActif = num;
	}
}
