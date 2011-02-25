package adpo.formule;

public class Variable extends Ensemble implements Terme {
	public Variable(String s) {
		super(s);
	}

	public Terme[] getTermes() {
		Terme[] ret = { this };
		return ret;
	}

	public boolean estEquivalent(Terme t) {
		if (t instanceof Variable)
			return this.equals((Variable)t);
		else
			return false;
	}
}
