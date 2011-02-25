package adpo.formule;

import java.util.LinkedList;
import java.util.Collection;
import adpo.resolution.Substitution;

public class Atome extends EnsembleTermes implements Formule, Comparable<Atome>,    Simplifiable {
	private boolean neg;

	public Atome(String s) {
		super(s);
		this.neg = false;
	}

	public Formule simplification() {
		return simplifier();
	}

	public Atome dupliquer() {
		Atome ret = new Atome(this.nom);
		ret.neg = this.neg;
		ret.termes = new LinkedList<Terme>(this.termes);
		return ret;
	}

	public Formule simplifier() {
		return dupliquer();
	}

	public void nier(boolean b) {
		if (b)
			this.neg = !this.neg;
	}

	public Formule negation() {
		return this;
	}

	public Formule skolemiser() {
		return this;
	}

	public boolean estNegatif() {
		return neg;
	}

	public boolean estOppose(Atome a) {
		return ((this.equals(a)) && (this.neg != a.neg)); 
	}
	
	public boolean estEquivalent(Atome a) {
		return (this.nom == null ? a.getNom() == null
			: this.nom.equals(a.getNom()))
			&& (this.getTaille() == a.getTaille());
	}

	public int compareTo(Atome a) {
		int ret;
		if (this.equals(a)) {
			if (neg == a.estNegatif())
				ret = 0;
			else if (this.neg == false)
				ret = 1;
			else
				ret = -1;
		}
		else
			ret = -2;
		return ret;
	}

	public void sufixeVariables(String sufix) {
		for (Terme t : termes) {
			if (t instanceof Variable)
				((Variable) t).setNom(t.getNom() + sufix);
		}
	}
	
	public void clotureUniverselle(Collection<Variable> quantifie, Collection<Variable> tout) {
		for (Terme t : termes) {
			if (t instanceof Variable)
				tout.add((Variable)t);
		}
	}

	public Formule skolemiser(Collection<Variable> quantifie, Substitution sub) {
		Atome tmp = this.dupliquer();
		sub.applique(tmp);
		return tmp;
	}

	public String toString() {
		return (neg ? "¬":"") + super.toString();
	}
}
