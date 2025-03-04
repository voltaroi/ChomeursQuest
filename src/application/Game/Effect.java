package application.Game;

public class Effect {
	
	private String name;
	private int numRound = 0;
	private float damage = 0;
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public int getNumRound() {
		return numRound;
	}
	
	public void setNumRound(int newNumRound) {
		numRound = newNumRound;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public void setDamage(float newDamage) {
		damage = newDamage;
	}
}
