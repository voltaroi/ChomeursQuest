package application.Game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private List<Chomeur> chomeurs;
	
	public Player() {
		chomeurs = new ArrayList<>();
	}
	
	public void addChomeur(String chomeur) {
		Chomeur newChomeur = new Chomeur();
		String path = "C:\\Users\\volta\\eclipse-workspace\\ChomeursQuest\\src\\assets\\chomeurs\\" + chomeur +".txt";
		newChomeur.FromFile(path);
		chomeurs.add(newChomeur);
	}
	
	public List<Chomeur> getChomeurs() {
		return chomeurs;
	}
}
