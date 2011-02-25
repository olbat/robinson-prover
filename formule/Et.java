package adpo.formule;

import adpo.resolution.FormeClausale;
import adpo.resolution.Substitution;
import java.util.Collection;

public class Et extends BinaireFormule implements OperateurClausal {
	public Et(Formule fg, Formule fd) {
		super(fg,fd);
	}

	public Formule simplification() {
		Formule fgs = fg.simplification(), fds = fd.simplification();
		if ((fgs instanceof Faux) || (fds instanceof Faux))
			return new Faux();
		if (fgs instanceof Vrai)
			return fds;
		if (fds instanceof Vrai)
			return fgs;
		return new Et(fgs,fds);
	}

	public void nier(boolean b) {
		this.neg = b;
		if (b) {
			fg.nier(true);
			fd.nier(true);
		} else {
			fg.nier(false);
			fd.nier(false);
		}
	}

	public Formule negation() {
		if (neg) 
			return new Ou(fg.negation(),fd.negation());
		else 
			return new Et(fg.negation(),fd.negation());
	}

	public FormeClausale formeClausale() {
		FormeClausale ret = new FormeClausale();
		if (fg instanceof Atome) {
			ret.ajouter(new Clause(fg));
		} else if (fg instanceof OperateurClausal) {
			ret.ajouter(((OperateurClausal)fg).formeClausale());
		}
		if (fd instanceof Atome) {
			ret.ajouter(new Clause(fd));
		} else if (fd instanceof OperateurClausal) {
			ret.ajouter(((OperateurClausal)fd).formeClausale());
		}
		return ret;
	}

	public Formule skolemiser(Collection<Variable> quantifie, Substitution sub) {
		return new Et(fg.skolemiser(quantifie,sub),fd.skolemiser(quantifie,sub));
	}

	public String toString() {
		return "[" + fg + " ET " + fd + "]";
	}
}
