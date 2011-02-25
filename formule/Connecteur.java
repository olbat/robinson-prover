package adpo.formule;

public abstract class Connecteur implements Formule {
	protected boolean neg;

	public Connecteur() {
		neg = false;
	}

	public Connecteur(boolean b) {
		neg = b;
	}

	public void nier() {
		this.nier(true);
	}
}
