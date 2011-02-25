package adpo.resolution;

import adpo.formule.*;
import java.util.List;
import java.util.LinkedList;

public class FormeClausale {
	private LinkedList<Clause> clauses;

	public FormeClausale() {
		clauses = new LinkedList<Clause>();
	}

	public FormeClausale(Clause c) {
		this();
		ajouter(c);
	}

	public FormeClausale(Formule f) {
		this();
		ajouter(f);
	}

	/* ajouter une conjonction d'une clause */
	public FormeClausale ajouter(Clause c) {
		clauses.add(c);
		return this;
	}

	public FormeClausale ajouter(Formule f) {
		LinkedList<Variable> quantifie,tout;
		Substitution sub;

		/* simplification de la formule */
		f = f.simplification();
		
		/* cloture universelle */
		quantifie = new LinkedList<Variable>();
		tout = new LinkedList<Variable>();
		f.clotureUniverselle(quantifie,tout);

		tout.removeAll(quantifie);
		for (Variable v : tout)
			f = new PourTout(v,f);
		/* negation et descente des negations */
		f.nier(true);
		f = f.negation();

		/* skolemisation de la formule */
		quantifie = new LinkedList<Variable>();
		sub = new Substitution();
		f = f.skolemiser(quantifie,sub);

		if (f instanceof OperateurClausal)
			clauses = ((OperateurClausal)f).formeClausale().clauses;
		else
			return null;
		/* ajouter(new Clause(f)); */
		renommeVariables();
		return this.simplifier();
	}

	public void renommeVariables() {
		int i = 0;
		for (Clause c : clauses) {
			for (Atome a : c.getAtomes())
				a.sufixeVariables(i + "");
			i++;
		}
	}
	
	public void ajouter(FormeClausale fc) {
		this.clauses.addAll(fc.clauses);
	}

	public FormeClausale getComposition(FormeClausale fc) {
		FormeClausale ret = new FormeClausale();
		for (Clause c : fc.clauses) {
			ret.ajouter(getComposition(c));
		}
		return ret;
	}

	public FormeClausale simplifier() {
		LinkedList<Clause> suppr = new LinkedList<Clause>();
		for (Clause c : clauses) {
			if (c.estSimplifiable())
				suppr.add(c);
		}
		for (Clause c : suppr)
			clauses.remove(c);
		return this;
	}

	public FormeClausale getComposition(Clause c) {
		FormeClausale ret = new FormeClausale();
		for (Clause c1 : this.clauses) {
			ret.ajouter((new Clause(c1)).ajouter(c));
		}
		return ret;
	}

	/* Attention aux effets de bords */
	public List<Clause> getClauses() {
		return clauses;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Clause c: clauses)
			sb.append("| ").append(c.toString()).append("\n");
		return sb.toString();
	}
}
