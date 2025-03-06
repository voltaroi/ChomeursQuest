package application.Game.items;

import application.Game.Chomeur;
import application.Game.Item;

public class OvniPicture extends Item {

	public Chomeur actuEffect(Chomeur chomeur) {
		if(oneAction) {
			oneAction = false;
			chomeur.modifDefSpe(6);
		}
		
		return chomeur;
	}

}
