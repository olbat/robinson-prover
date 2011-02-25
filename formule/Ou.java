package adpo.formule;

import adpo.resolution.FormeClausale;
import adpo.resolution.Substitution;
import java.util.Collection;

public class Ou extends BinaireFormule implements OperateurClausal {
	public Ou(Formule fg, Formule fd) {
		super(fg,fd);
	}

	public Formule simplification() {
		Formule fgs = fg.simplification(), fds = fd.simplification();
		if ((fgs instanceof Vrai) || (fds instanceof Vrai))
			return new Vrai();
		if (fgs instanceof Faux)
			return fds;
		if (fds instanceof Faux)
			return fgs;
		return new Ou(fgs,fds);
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
			return new Et(fg.negation(),fd.negation());
		else 
			return new Ou(fg.negation(),fd.negation());
	}

	public FormeClausale formeClausale() {
		FormeClausale ret = new FormeClausale();
		if ((fg instanceof OperateurClausal)
		  && (fd instanceof OperateurClausal)) {
			return (((OperateurClausal)fg).formeClausale()
				).getComposition(((OperateurClausal)fd
					).formeClausale());
		}
		
		if ((fg instanceof Atome) && (fd instanceof Atome)) {
			if (((Atome)fg).estOppose((Atome)fd))
				return ret;
			else
				return ret.ajouter(new Clause(this));
		}

		if (fg instanceof Atome) 
			ret.ajouter(new Clause(fg));

		if (fd instanceof Atome)
			ret.ajouter(new Clause(fd));

		if (fg instanceof OperateurClausal) {
			return ret.getComposition(
				((OperateurClausal)fg).formeClausale());
		}

		if (fg instanceof OperateurClausal) {
			return ret.getComposition(
				((OperateurClausal)fg).formeClausale());
		}

		return ret;
	}

	public Formule skolemiser(Collection<Variable> quantifie, Substitution sub) {
		return new Ou(fg.skolemiser(quantifie,sub),fd.skolemiser(quantifie,sub));
	}

	public String toString() {
		return "{" + fg + " OU " + fd + "}";
	}
}
