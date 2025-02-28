package application.Game;

public class Attack {
	private String name;
	private boolean isAttackSpe = false;
	private float att;
	private String type;
	private String effect;
	
	public Attack(String name, boolean isAttackSpe, float att, String type, String effect) {
		this.name = name;
		this.isAttackSpe = isAttackSpe;
		this.att = att;
		this.type = type;
		this.effect = effect;
	}
	
	public String getName() {
		return name;
	}
}
