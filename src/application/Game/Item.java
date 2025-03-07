package application.Game;

public abstract class Item {
	private String name;
	private String effect;
	private float value;
	protected boolean oneAction = true;
	
	public void setName(String newName) {
		name = newName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setEffect(String newEffect) {
		effect = newEffect;
	}
	
	public String getEffect() {
		return effect;
	}
	
	public void setValue(float newValue) {
		value = newValue;
	}
	
	public float getValue() {
		return value;
	}
	
    public abstract String actuEffect(Chomeur chomeur);
}
