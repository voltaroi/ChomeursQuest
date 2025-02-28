package application.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public Chomeur() {
	    types = new ArrayList<>();
	    attacks = new ArrayList<>();
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
	
    public void FromFile(String filePath) {

    	String nameAttack;
    	String newType;
    	
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    switch (parts[0]) {
                        case "name":
                            name = parts[1];
                            break;
                        case "hp":
                            hp = Float.parseFloat(parts[1]);
                            break;
                        case "att":
                            att = Float.parseFloat(parts[1]);
                            break;
                        case "attSpe":
                            attSpe = Float.parseFloat(parts[1]);
                            break;
                        case "def":
                            def = Float.parseFloat(parts[1]);
                            break;
                        case "defSpe":
                            defSpe = Float.parseFloat(parts[1]);
                            break;
                        case "speed":
                            speed = Float.parseFloat(parts[1]);
                            break;
                        case "type1":
                        	newType = parts[1];
                        	types.add(newType);
                            break;
                        case "type2":
                        	newType = parts[1];
                        	types.add(newType);
                            break;
                        case "attack1":
                        	nameAttack = parts[1];
                        	if(!nameAttack.equals("none")) {
                            	Attack newAttack = new Attack();
                            	newAttack.FromFile("C:\\Users\\volta\\eclipse-workspace\\ChomeursQuest\\src\\assets\\attacks\\" + nameAttack +".txt");
                            	attacks.add(newAttack);
                        	}
                            break;
                        case "attack2":
                        	nameAttack = parts[1];
                        	if(!nameAttack.equals("none")) {
                        		System.out.println(nameAttack);
                            	Attack newAttack = new Attack();
                            	newAttack.FromFile("C:\\Users\\volta\\eclipse-workspace\\ChomeursQuest\\src\\assets\\attacks\\" + nameAttack +".txt");
                            	attacks.add(newAttack);
                        	}
                            break;
                        case "attack3":
                        	nameAttack = parts[1];
                        	if(!nameAttack.equals("none")) {
                            	Attack newAttack = new Attack();
                            	newAttack.FromFile("C:\\Users\\volta\\eclipse-workspace\\ChomeursQuest\\src\\assets\\attacks\\" + nameAttack +".txt");
                            	attacks.add(newAttack);
                        	}
                            break;
                        case "attack4":
                        	nameAttack = parts[1];
                        	if(!nameAttack.equals("none")) {
                            	Attack newAttack = new Attack();
                            	newAttack.FromFile("C:\\Users\\volta\\eclipse-workspace\\ChomeursQuest\\src\\assets\\attacks\\" + nameAttack +".txt");
                            	attacks.add(newAttack);
                        	}
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name=").append(name).append("\n")
          .append("hp=").append(hp).append("\n")
          .append("att=").append(att).append("\n")
          .append("attSpe=").append(attSpe).append("\n")
          .append("def=").append(def).append("\n")
          .append("defSpe=").append(defSpe).append("\n")
          .append("speed=").append(speed).append("\n")
          .append("types=").append(types).append("\n");

        if (attacks != null && !attacks.isEmpty()) {
            sb.append("attacks=\n");
            for (Attack attack : attacks) {
                sb.append("  - ").append(attack.getName()).append("\n");
            }
        } else {
            sb.append("attacks=None\n");
        }

        return sb.toString();
    }

}
