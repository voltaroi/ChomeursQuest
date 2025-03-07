package application.Game.items;

import application.Game.Chomeur;
import application.Game.Item;

public class Restes extends Item {

	public String actuEffect(Chomeur chomeur) {
		float hp = chomeur.getHp();
		float newHp = (chomeur.getHpMax() / 100) * 2;
		if(hp + newHp < chomeur.getHpMax()) {
			chomeur.modifHp(newHp);
		} else {
			chomeur.setHp(chomeur.getHpMax());
		}
		System.out.println(chomeur.getHp());
		return chomeur.getName() + " a regagner " + newHp;
	}

}
