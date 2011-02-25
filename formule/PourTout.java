package adpo.formule;

import java.util.Collection;
import adpo.resolution.Substitution;

public class PourTout extends Binaire<Variable,Formule> {
	public PourTout(Variable v, Formule f) {
		super(v,f);
	}

	public Formule simplification() {
		return new PourTout(fg,fd.simplification());
	}

	public void nier(boolean b) {
		this.neg = b;
		fd.nier(this.neg);
	}

	public Formule negation() {
		if (neg) 
			return new IlExiste(fg,fd.negation());
		else 
			return new PourTout(fg,fd.negation());
	}

	public void clotureUniverselle(Collection<Variable> quantifie, Collection<Variable> tout) {
		quantifie.add(fg);
		fd.clotureUniverselle(quantifie,tout);
	}

	public String toString() {
		return "(A" + fg + ")[" + fd + "]";
	}
	
	public Formule skolemiser(Collection<Variable> quantifie, Substitution sub) {
		Formule ret;
		quantifie.add(fg);
		ret = fd.skolemiser(quantifie,sub);
		quantifie.remove(fg);
		return ret;
	}
}
