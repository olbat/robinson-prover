package adpo.formule;

import java.util.Collection;
import adpo.resolution.Substitution;

public class Vrai extends Connecteur implements Simplifiable {
	public Vrai() {
	}

	public Formule simplification() {
		return simplifier();
	}

	public Formule simplifier() {
		return this;
	}

	public void nier(boolean b) {
		this.neg = b;
	}

	public Formule negation() {
		if (neg)
			return new Faux();
		else
			return this;
	}
	
	public Formule skolemiser(Collection<Variable> quantifie, Substitution sub) {
		return this;
	}

	public void clotureUniverselle(Collection<Variable> quantifie, Collection<Variable> tout) {
	}

	public String toString() {
		return " VRAI ";
	}
}
