package application.Game;

import java.util.List;

public class Chomeur {
	
	private String name;
	private float hp;
	private float att;
	private float attSpe;
	private float def;
	private float defSpe;
	private float speed;
	
	private List<String> types;
	private List<Attack> attacks;
	
	public Chomeur(String name, float hp, float att, float attSpe, float def, float defSpe, float speed, List<Attack> Attacks, List<String> types) {
		this.name = name;
		this.hp = hp;
		this.att = att;
		this.attSpe = attSpe;
		this.def = def;
		this.defSpe = defSpe;
		this.speed = speed;
		this.attacks = Attacks;
		this.types = types;
	}
	
	public List<Attack> getAllAttacks() {
		return attacks;
	}
	
	public float getHp() {
		return hp;
	}
	
	public void modifAtt(float add) {
		att += add;
	}
	
	public void modifAttSpe(float add) {
		attSpe += add;
	}
	
	public void modifDef(float add) {
		def += add;
	}
	
	public void modifDefSpe(float add) {
		defSpe += add;
	}
	
	public void modifSpeed(float add) {
		speed += add;
	}
	
	public Attack getAttackByName(String name) {
		for (Attack attack : attacks) {
			if(attack.getName().equals(name)) {
				return attack;
			}
		}
		return null;
	}
}
