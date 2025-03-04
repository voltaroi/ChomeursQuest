package application.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Attack {
	private String name;
	private boolean isAttackSpe = false;
	private float att;
	private String type;
	private Effect effect = new Effect();
	
	public String getName() {
		return name;
	}
	
	public boolean isAttackSpe() {
		return isAttackSpe;
	}
	
	public float getAtt() {
		return att;
	}
	
	public String getType() {
		return type;
	}
	
	public Effect getEffect() {
		return effect;
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
                        case "isAttackSpe":
                            isAttackSpe = Boolean.parseBoolean(parts[1]);
                            break;
                        case "att":
                            att = Float.parseFloat(parts[1]);
                            break;
                        case "type":
                            type = parts[1];
                            break;
                        case "effect":
                            effect.setName(parts[1]);
                            System.out.println(parts[1]);
                            break;
                        case "effectTime":
                        	effect.setNumRound(Integer.parseInt(parts[1]));
                            break;
                        case "effectDamage":
                        	effect.setDamage(Integer.parseInt(parts[1]));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getString() {
        return "name=" + name + "\n" +
               "isAttackSpe=" + isAttackSpe + "\n" +
               "att=" + att + "\n" +
               "type=" + type + "\n" +
               "effect=" + effect;
    }

}
