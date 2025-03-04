package application.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Chomeur {
	
	private String name;
	private float hp;
	private float hpMax;
	private float att;
	private float attSpe;
	private float def;
	private float defSpe;
	private float speed;
	
	private List<Effect> effects;
	
	private List<String> types;
	private List<Attack> attacks;
	
	public Chomeur() {
	    types = new ArrayList<>();
	    attacks = new ArrayList<>();
	    effects = new ArrayList<>();
	}
	
	public List<Attack> getAllAttacks() {
		return attacks;
	}
	
	public void addEffect(Effect newEffect) {
		boolean isFind = false;
		for(int i=0; i < effects.size(); i++) {
			if(effects.get(i).getName() == newEffect.getName()) {
				isFind = true;
				effects.get(i).setNumRound(newEffect.getNumRound());
			}
		}
		
		if(!isFind) {
			effects.add(newEffect);
		}
	}
	
	public void setName(String nameset) {
		name = nameset;
	}
	
	public boolean updateEffects() {
		boolean canPlay = true;
		for(int i=0; i < effects.size(); i++) {
			if(effects.get(i).getNumRound() > 0) {
				int numRound = effects.get(i).getNumRound();
				effects.get(i).setNumRound(numRound - 1);
				modifHp(-effects.get(i).getDamage());
				System.out.println(effects.get(i).getName());
				if(effects.get(i).getName().equals("stun")) {
					canPlay = false;
				}
			} else {
				effects.remove(i);
			}
		}
		return canPlay;
	}
	
	public float getHp() {
		return hp;
	}
	
	public float getHpMax() {
		return hpMax;
	}
	
	public List<Attack> getAllAttack() {
		return attacks;
	}
	
	public String getName() {
		return name;
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
	
	public List<String> getTypes() {
		return types;
	}
	
	public boolean modifHp(float add) {
		hp += add;
		boolean isDead = false;
		if(hp <= 0) {
			isDead = true;
		}
		return isDead;
	}
	
    public void FromFile(String filePath) {

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
                            hpMax = Float.parseFloat(parts[1]);
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
                        	newAttack(parts[1]);
                            break;
                        case "attack2":
                        	newAttack(parts[1]);
                            break;
                        case "attack3":
                        	newAttack(parts[1]);
                            break;
                        case "attack4":
                        	newAttack(parts[1]);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void newAttack(String nameAttack) {
    	if(!nameAttack.equals("none")) {
        	Attack newAttack = new Attack();
        	newAttack.FromFile(Paths.get(System.getProperty("user.dir"), "src", "assets", "attacks", nameAttack).toString());
        	attacks.add(newAttack);
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
    
    public void clearAttacks() {
    	attacks.clear();
    }
    
  /*  public void fromTeam(String filePath, int numberChomeur) {

    	boolean isValid = true;
    	int chunkSize = 7; // Taille du bloc
    	
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                lineCount++;
                System.out.println(line);
                if (lineCount % chunkSize == 0) { 
                  System.out.println("----- Fin du bloc -----\n");
                break;}
                if (parts.length == 2) {
                	if(isValid) {
	                    switch (parts[0]) {
	                        case "name":
	                            name = parts[1];
	                            setStat(name);
	                            break;
	                        case "attack1":
	                        	newAttack(parts[1]);
	                            break;
	                        case "attack2":
	                        	newAttack(parts[1]);
	                            break;
	                        case "attack3":
	                        	newAttack(parts[1]);
	                            break;
	                        case "attack4":
	                        	newAttack(parts[1]);
	                            break;
	                    }
                	}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    
    private void setStat(String nametxt) {
    	String path = Paths.get(System.getProperty("user.dir"), "src", "assets", "chomeurs", nametxt).toString();
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        String line;
        String newType;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                switch (parts[0]) {
                    case "name":
                        name = parts[1];
                        break;
                    case "hp":
                        hp = Float.parseFloat(parts[1]);
                        hpMax = Float.parseFloat(parts[1]);
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
    
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}