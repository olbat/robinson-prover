package adpo.formule;

public class Fonction extends EnsembleTermes implements Terme {
	public Fonction(String s) {
		super(s);
	}

	public boolean estEquivalent(Terme t) {
		if (t instanceof Fonction) {
			return (this.nom == null ? t.getNom() == null
				: this.nom.equals(t.getNom()))
				&& (this.getTaille() == ((Fonction)t).getTaille());
		} else
			return false;
	}
}
