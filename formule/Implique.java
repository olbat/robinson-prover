package adpo.formule;

import adpo.resolution.Substitution;
import java.util.Collection;

public class Implique extends BinaireFormule {
	public Implique(Formule fg, Formule fd) {
		super(fg,fd);
	}

	public Formule simplification() {
		Formule fgs = fg.simplification(), fds = fd.simplification();
		if ((fgs instanceof Faux) || (fds instanceof Vrai))
			return new Vrai();
		if (fgs instanceof Vrai)
			return fds;
		if (fds instanceof Faux)
			return fgs;
		return new Implique(fgs,fds);
	}

	public void nier(boolean b) {
		this.neg = b;
		if (b) {
			fg.nier(false);
			fd.nier(true);
		} else {
			fg.nier(true);
			fd.nier(false);
		}
	}

	public Formule negation() {
		if (neg) 
			return new Et(fg.negation(),fd.negation());
		else 
			return new Ou(fg.negation(),fd.negation());
	}

	public Formule skolemiser(Collection<Variable> quantifie, Substitution sub) {
		return new Et(fg.skolemiser(quantifie,sub),fd.skolemiser(quantifie,sub));
	}

	public String toString() {
		return fg + " => " + fd;
	}
}
