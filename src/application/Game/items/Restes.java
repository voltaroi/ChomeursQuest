package application.Game.items;

import application.Game.Chomeur;
import application.Game.Item;

public class Restes extends Item {

	public Chomeur actuEffect(Chomeur chomeur) {
		float newHp = (chomeur.getHpMax() / 100) * 2;
		if(chomeur.getHp() + newHp < chomeur.getHpMax()) {
			chomeur.setHp(chomeur.getHpMax());
		} else {
			chomeur.modifHp(newHp);
		}
		return chomeur;
	}

}
