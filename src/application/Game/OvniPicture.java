package application.Game;

public class OvniPicture extends Item {

	public Chomeur actuEffect(Chomeur chomeur) {
		if(oneAction) {
			oneAction = false;
			chomeur.modifDefSpe(6);
		}
		
		return chomeur;
	}

}
