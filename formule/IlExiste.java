package adpo.formule;

import java.util.Collection;
import adpo.resolution.Substitution;

public class IlExiste extends Binaire<Variable,Formule> {
	public IlExiste(Variable v, Formule f) {
		super(v,f);
	}

	public Formule simplification() {
		return new IlExiste(fg,fd.simplification());
	}

	public void nier(boolean b) {
		this.neg = b;
		fd.nier(this.neg);
	}

	public Formule negation() {
		if (neg) 
			return new PourTout(fg,fd.negation());
		else 
			return new IlExiste(fg,fd.negation());
	}

	public void clotureUniverselle(Collection<Variable> quantifie, Collection<Variable> tout) {
		quantifie.add(fg);
		fd.clotureUniverselle(quantifie,tout);
	}
	
	public Formule skolemiser(Collection<Variable> quantifie, Substitution sub) {
		Formule ret;
		Substitution.Couple couple;
		Fonction fonct;

		fonct = new Fonction(Skolemisation.getFonctionId());
		for (Variable v : quantifie)
			fonct.ajouter(v);
		couple = new Substitution.Couple(fg,fonct);
		
		sub.ajouter(couple);
		ret = fd.skolemiser(quantifie,sub);
		sub.retirer(couple);
		return ret;
	}

	public String toString() {
		return "(E" + fg + ")[" + fd + "]";
	}
}
