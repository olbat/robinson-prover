package adpo.resolution;

import adpo.formule.*;
import java.util.LinkedList;

public class RobinsonResolution extends Resolution {
	private class Couple {
		private Atome a1, a2;
		private Substitution sub;
		public Couple(Atome a1, Atome a2, Substitution s) {
			this.a1 = a1;
			this.a2 = a2;
			this.sub = s;
		}

		public Atome getAtome1() {
			return a1;
		}

		public Atome getAtome2() {
			return a2;
		}

		public Substitution getSubstitution() {
			return sub;
		}

		public String toString() {
			return "[" + a1 + "," + a2 + "] " + sub;
		}
	}
	
	public RobinsonResolution(FormeClausale fc) {
		super(fc);
	}
	
	private int choixSubstitution(LinkedList<Couple> unipos) {
		int ret;
		if (unipos.size() <= 0) {
			System.out.println("\n-> Pas d'unification"
				+ " possible entre ces deux règles.");
			return -1;
		}
		System.out.println("-> Liste des substitutions" 
			+ " possibles:");
		ret = 1;
		for (Couple c : unipos)
			System.out.println("[" + ret++ + "]" + c);
		ret = lireChoix("<- Substitution à appliquer: "
			,1, unipos.size());
		return ret;
	}

	public Clause resoudre() {
		if (clauses.size() == 1)
			return clauses.get(0);
		if (clauses.size() <= 0)
			return null;

		Clause ret = null, c1 = null, c2 = null;
		Couple tmp = null;
		LinkedList<Couple> unipos;
		int choix;

		afficherSysteme();

		while ((!(ret instanceof ClauseVide)) && (clauses.size() > 1)) {
			System.out.println("\n---");
			afficherClauses();
			choix = lireChoix("<- Numero de la règle à utiliser: ",0,3);
			if (choix == 0) 
				return ret;
			else if (choix == 3) {
				choix = lireChoixClause("<- Première clause sur"
					+ " laquelle appliquer la règle: ");
				c1 = clauses.get(choix-1);

				choix = lireChoixClause("<- Seconde clause sur"
					+ " laquelle appliquer la règle: ");
				c2 = clauses.get(choix-1);

				unipos = regleRes(c1,c2);
				choix = choixSubstitution(unipos);

				if (choix < 0)
					continue;

				tmp = unipos.get(choix-1);

				c1.retirerTout(tmp.getAtome1());
				if (tmp.getSubstitution() != null) {
					for (Atome a : c1.getAtomes())
						tmp.getSubstitution().applique(a);
				}

				c2.retirerTout(tmp.getAtome2());
				if (tmp.getSubstitution() != null) {
					for (Atome a : c2.getAtomes())
						tmp.getSubstitution().applique(a);
				}
				
				c1.ajouter(c2);
				clauses.remove(c2);
				if ((c1.estVide()) && (c2.estVide()))
					ret = new ClauseVide();
				else if (c1.estVide())
					clauses.remove(c1);
				else if (c2.estVide())
					clauses.remove(c2);
			} else {
				c1 = clauses.get(
					lireChoixClause("<- Clause sur laquelle"
						+ " appliquer la règle: ")-1
						);
				if (choix == 1)
					unipos = regleFacPlus(c1);
				else
					unipos = regleFacMoins(c1);

				choix = choixSubstitution(unipos);

				if (choix < 0)
					continue;

				tmp = unipos.get(choix-1);

				c1.retirerTout(tmp.getAtome1());
				c1.ajouter(tmp.getAtome1());
				if (tmp.getSubstitution() != null) {
					for (Atome a : c1.getAtomes())
						tmp.getSubstitution().applique(a);
				}

				if (c1.estVide())
					clauses.remove(c1);
			} 

		}
		return ret;	
	}

	private LinkedList<Couple> regleFacPlus(Clause c) {
		Unification u;
		LinkedList<Couple> ret = new LinkedList<Couple>();

		for (Atome a1 : c.getPositifs()) {
			for (Atome a2 : c.getPositifs()) {
				if (a1 != a2) {
					if (a1.equals(a2))
						ret.add(new Couple(a1,a2,null));
					else {
						u = new Unification(a1,a2);
						if (u.unifier())
							ret.add(new Couple(a1,a2,u.getSubstitution()));
					}
				}
			}
		}
		return ret;
	}

	private LinkedList<Couple> regleFacMoins(Clause c) {
		Unification u;
		LinkedList<Couple> ret = new LinkedList<Couple>();

		for (Atome a1 : c.getNegatifs()) {
			for (Atome a2 : c.getNegatifs()) {
				if (a1 != a2) {
					if (a1.equals(a2))
						ret.add(new Couple(a1,a2,null));
					else {
						u = new Unification(a1,a2);
						if (u.unifier())
							ret.add(new Couple(a1,a2,u.getSubstitution()));
					}
				}
			}
		}
		return ret;
	}

	private LinkedList<Couple> regleRes(Clause c1, Clause c2) {
		Unification u;
		LinkedList<Couple> ret = new LinkedList<Couple>();
		for (Atome a1 : c1.getPositifs()) {
			for (Atome a2 : c2.getNegatifs()) {
				if (a1.equals(a2))
					ret.add(new Couple(a1,a2,null));
				else {
				u = new Unification(a1,a2);
					if (u.unifier())
						ret.add(new Couple(a1,a2,u.getSubstitution()));
				}
			}
		}

		for (Atome a1 : c1.getNegatifs()) {
			for (Atome a2 : c2.getPositifs()) {
				if (a1.equals(a2))
					ret.add(new Couple(a1,a2,null));
				else {
				u = new Unification(a1,a2);
					if (u.unifier())
						ret.add(new Couple(a1,a2,u.getSubstitution()));
				}
			}
		}

		return ret;
	}

	public void afficherSysteme() {
		System.out.println("Utilisation du système formel de Robinson");
		System.out.println("F = (cl(L),0,R={fac+,fac-,res})");
		System.out.println("*[1] fac-: A OU NON a1 OU NON a2"
			+ " -> sigma(A) OU sigma(NON a1)");
		System.out.println("*[2] fac+: A OU a1 OU a2"
			+ " -> sigma(A) OU sigma(a1)");
		System.out.println("*[3] res: a1 OU A ; NON a2 OU B"
			+ " -> sigma(A) OU sigma(B) avec [sigma(a1) = sigma(a2)]");
		System.out.println();
	}
}
