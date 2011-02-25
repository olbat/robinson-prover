package adpo.formule;

import adpo.resolution.Substitution;
import java.util.Collection;

public class Non extends UnaireFormule {
	public Non(Formule fils) {
		super(fils);
	}

	public Formule simplification() {
		Formule filss = fils.simplification();
		if (filss instanceof Vrai)
			return new Faux();
		if (filss instanceof Faux)
			return new Faux();
		filss.nier(true);
		return filss;
	}

	public Formule skolemiser() {
		return this;
	}

	public void nier(boolean b) {
		this.neg = b;
		fils.nier(!neg);
	}
			
	public Formule negation() {
		/* if (neg) */
			return fils.negation();
		/* 
		else
			return new Non(fils.negation());
		*/
	}

	public Formule skolemiser(Collection<Variable> quantifie, Substitution sub) {
		return new Non(fils.skolemiser(quantifie,sub));
	}

	public String toString() {
		return " NON " + fils;
	}
}
