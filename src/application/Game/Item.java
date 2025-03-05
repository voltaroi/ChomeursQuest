package application.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
	
    public void FromFile(String filePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    switch (parts[0]) {
                        case "name":
                            name = parts[1];
                            break;
                        case "effect":
                            effect = parts[1];
                            break;
                        case "value":
                            value = Float.parseFloat(parts[1]);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public abstract Chomeur actuEffect(Chomeur chomeur);
}
