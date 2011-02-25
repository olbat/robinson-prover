package adpo.resolution;

import adpo.formule.*;
import java.util.LinkedList;

public class Unification {
	private class Couple {
		protected Terme tg, td;
		protected Substitution.Couple c;
		
		public Couple(Terme t1, Terme t2) {
			this.tg = t1;
			this.td = t2;
			this.c = null;
		}

		public Couple(Substitution.Couple c) {
			this.c = c;
		}

		public Couple(Variable v, Terme t) {
			this.c = new Substitution.Couple(v,t);
		}

		public Terme getTermeG() {
			return tg;
		}

		public Terme getTermeD() {
			return td;
		}

		public Terme getTerme() {
			return (c == null ? null : c.getTerme());
		}
		
		public Terme getVariable() {
			return (c == null ? null : c.getVariable());
		}

		public Substitution.Couple getCouple() {
			return c;
		}

		public void setTermeG(Terme t) {
			tg = t;
		}
		
		public void setTermeD(Terme t) {
			td = t;
		}

		public void setCouple(Substitution.Couple c) {
			this.c = c;
		}

		public void setCouple(Variable v, Terme t) {
			this.c = new Substitution.Couple(v,t);
		}
	}

	public static class UnificationEchecException extends Exception { }
	
	private Atome a1, a2;
	private Substitution sub;
	private LinkedList<Couple> couples;

	public Unification(Atome a1, Atome a2) {
		this.a1 = a1.dupliquer();
		this.a2 = a2.dupliquer();
		this.sub = new Substitution();
		this.couples = new LinkedList<Couple>();
		if (a1.estEquivalent(a2)) {
			Terme[] ts1 = a1.getTermes(),
				ts2 = a2.getTermes();
			for (int i = 0; i < ts1.length; i++)
				couples.add(new Couple(ts1[i],ts2[i]));
		}
	}

	public void retirer(Couple c) {
		couples.remove(c);
	}

	public void ajouter(Couple c) {
		couples.add(c);
	}

	public void ajouter(Terme t1, Terme t2) {
		ajouter(new Couple(t1,t2));
	}

	public void ajouter(Variable v, Terme t) {
		/* sub.ajouter(v,t); */
		ajouter(new Couple(v,t));
	}

	private void miseAJourSubstitution() {
		miseAJourCouples();
	
		for (Couple c : couples) {
			if (c.getCouple() != null)
				sub.ajouter(c.getCouple());
		}
	}

	private void miseAJourCouples() {
		Terme tg, td;
		for (Couple c : couples) {
			tg = c.getTermeG();
			td = c.getTermeD();
			if (tg instanceof Variable) {
				c.setCouple((Variable)tg,td);
			}
		}
	}

	private void miseAJourAtomes() {
		miseAJourSubstitution();
		sub.applique(a1);
		sub.applique(a2);
	}

	public Substitution getSubstitution() {
		return sub;
	}

	public Atome getAtome1() {
		return a1;
	}

	public Atome getAtome2() {
		return a2;
	}

	public boolean unifier() {
		if (!(a1.estEquivalent(a2)))
			return false;
		while (true) {
			simplifier();
			try {
				if (decomposer())
					continue;
				inverser();
				if (eliminer())
					continue;
				break;
			} catch (UnificationEchecException uee) {
				return false;
			}
		}
		miseAJourSubstitution();
		miseAJourAtomes();
		return !sub.estVide();
	}

	public void simplifier() {
		Terme tg, td;
		LinkedList<Couple> supprl = new LinkedList<Couple>();
		for (Couple c : couples) {
			tg = c.getTermeG();
			td = c.getTermeD();
			if ((td instanceof Variable)
			&& (tg instanceof Variable)
			&& (tg.estEquivalent(td))) {
			/* && (td.getNom().equals(tg.getNom()))) { */
				supprl.add(c);
			} else if ((td instanceof Constante)
			&& (tg instanceof Constante)
			&& (td.estEquivalent(tg))) {
			/* && (td.getNom().equals(tg.getNom()))) { */
				supprl.add(c);
			}
		}
		for (Couple c : supprl)
			retirer(c);
	}

	/* retourne vrai si il y a eu un changement */
	public boolean decomposer() throws UnificationEchecException {
		Terme tg, td;
		boolean ret = false;
		LinkedList<Couple> supprl = new LinkedList<Couple>(),
			addl = new LinkedList<Couple>();
		for (Couple c : couples) {
			tg = c.getTermeG();
			td = c.getTermeD();
			if ((td instanceof Fonction)
			&& (tg instanceof Fonction)) {
				if (td.estEquivalent(tg)) {
					supprl.add(c);
					Terme[] tgs = tg.getTermes(),
						tds = td.getTermes();
					for (int i = 0; i < tgs.length; i++)
						addl.add(new Couple(tgs[i],tds[i]));
					ret = true;
				} else
					throw new UnificationEchecException();
			} else if ((
				((td instanceof Constante)
				&& (tg instanceof Constante)
				&& !(tg.estEquivalent(td)))
				) || (
				((td instanceof Constante)
				&& (tg instanceof Fonction))
				) || (
				((tg instanceof Constante)
				&& (td instanceof Fonction))
				)) {
				throw new UnificationEchecException();
			}
		}
		for (Couple c : supprl)
			retirer(c);
		for (Couple c : addl)
			ajouter(c);
		return ret;
	}

	public void inverser() {
		Terme tg, td;
		for (Couple c : couples) {
			tg = c.getTermeG();
			td = c.getTermeD();
			if ((td instanceof Variable)
			&& !(tg instanceof Variable)) {
			/* && !(td.getNom().equals(tg.getNom()))) { */
				c.setTermeG(td);
				c.setTermeD(tg);
				/* substitution(td,tg) */
			}
		}
		
	}

	/* retourne vrai si il y a eu un changement */
	public boolean eliminer() throws UnificationEchecException {
		Terme tg, td;
		boolean ret = false;
		LinkedList<Couple> supprl = new LinkedList<Couple>();
		miseAJourSubstitution();
		for (Couple c : couples) {
			tg = c.getTermeG();
			td = c.getTermeD();
			if ((td instanceof Fonction)
			&& (tg instanceof Variable)) {
				if (((Fonction)td).contient(tg) && !td.equals(tg))
					throw new UnificationEchecException();
				
				else {
					/* retirer(c); */
					supprl.add(c);
					sub.ajouter(c.getCouple());
					substituer((Variable)tg,td);
					ret = true;
				}
			}
		}
		for (Couple c : supprl)
			retirer(c);

		return ret;
	}

	private void substituer(Variable v, Terme t) {
		Terme tg, td;
		miseAJourAtomes();
		for (Couple c : couples) {
			tg = c.getTermeG();
			td = c.getTermeD();
			if (tg instanceof Fonction)
				substituer((Fonction)tg,v,t);
			else if ((tg != null) && (tg.equals(v)))
				c.setTermeG(t);

			if (td instanceof Fonction)
				substituer((Fonction)td,v,t);
			else if ((td != null) && (td.equals(v)))
				c.setTermeD(t);
		}
	}

	private void substituer(Fonction f, Variable v, Terme t) {
		Terme[] ft = f.getTermes();
		miseAJourAtomes();
		for (int i = 0; i < ft.length; i++) {
			if (ft[i] instanceof Fonction)
				substituer((Fonction)ft[i],v,t);
			else if (ft[i].equals(v)) 
				f.setTerme(i,t);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("Unification: [");
		String tmp;
		for (Couple c: couples) {
			tmp = c.getVariable() + "=" + c.getTerme() + " {" 
				+ c.getTermeG() + "=" + c.getTermeD() + "},";
			sb.append(tmp);
		}
		sb.append((couples.size() > 0 ? "\010" : "") + "]");
		return sb.toString();
	}
}
