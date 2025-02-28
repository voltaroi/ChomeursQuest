package application.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Attack {
	private String name;
	private boolean isAttackSpe = false;
	private float att;
	private String type;
	private String effect;
	
	public String getName() {
		return name;
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
                            effect = parts[1];
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
