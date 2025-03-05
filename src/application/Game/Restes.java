package application.Game;

public class Restes extends Item {

	public Chomeur actuEffect(Chomeur chomeur) {
		float newHp = chomeur.getHpMax() / 100 * 8;
		newHp = chomeur.getHp() + newHp;
		if(newHp > chomeur.getHpMax()) {
			chomeur.setHp(chomeur.getHpMax());
		} else {
			chomeur.modifHp(newHp);
		}
		return chomeur;
	}

}
