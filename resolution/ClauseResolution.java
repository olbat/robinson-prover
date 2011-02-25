package adpo.resolution;

import adpo.formule.*;
import java.util.List;

public class ClauseResolution extends Resolution {
	public ClauseResolution(FormeClausale fc) {
		super(fc);
	}

	public Clause resoudre() {
		if (clauses.size() == 1)
			return clauses.get(0);
		if (clauses.size() <= 0)
			return null;

		Clause c1 = null, c2 = null,ret = null;
		int choix;
		
		afficherSysteme();
		while ((!(ret instanceof ClauseVide)) && (clauses.size() > 1)) {
			afficherClauses();
			choix = lireChoixClause("<-Numero de la première clause"
				+ " à faire rentrer en résolution: ");
			if (choix != 0) {
				c1 = clauses.get(choix-1);
				choix = lireChoixClause("<-Numero de la seconde"
				    + "clause à faire rentrer en résolution: ");
			}
			
			if (choix == 0)
				return ret;
			
			c2 = clauses.get(choix-1);

			if (regleRes(c1,c2))
				ret = new ClauseVide();
		}

		return ret;
	}

	private boolean regleRes(Clause c1, Clause c2) {
		List<Atome> oppinter;
		oppinter = c1.getOpposeIntersection(c2);

		if (oppinter.size() > 0) {
			c1.retirer(oppinter);
			c2.retirer(oppinter);
			c1.ajouter(c2);
			clauses.remove(c2);
			if (c1.estVide())
				return true;
		}
		return false;
	}

	public void afficherSysteme() {
		System.out.println("-> Utilisation du système formel basé sur des clauses");
		System.out.println("F = (C(P),0,res)");
		System.out.println("* res: (c1 OU r OU c2 ; c3 OU ¬r OU c4)"
			+ " -> c1 OU c2 OU c3 OU c4");
		System.out.println();
	}
}
