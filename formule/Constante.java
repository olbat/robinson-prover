package adpo.formule;

public class Constante extends Ensemble implements Terme {
	public Constante(String s) {
		super(s);
	}
	
	public Terme[] getTermes() {
		Terme[] ret = { this };
		return ret;
	}

	public boolean estEquivalent(Terme t) {
		if (t instanceof Constante)
			return this.equals((Constante)t);
		else
			return false;
	}
}
