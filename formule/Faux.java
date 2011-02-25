package adpo.formule;

import java.util.Collection;
import adpo.resolution.Substitution;

public class Faux extends Connecteur implements Simplifiable {
	public Faux() {
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
			return new Vrai();
		else
			return this;
	}
	
	public Formule skolemiser(Collection<Variable> quantifie, Substitution sub) {
		return this;
	}

	public void clotureUniverselle(Collection<Variable> quantifie, Collection<Variable> tout) {
	}

	public String toString() {
		return "FAUX";
	}
}
