package adpo.resolution;

import adpo.formule.*;
import java.util.LinkedList;
import java.util.*;

public class Substitution {
	 public static class Couple {
		protected Variable v;
		protected Terme t;

		public Couple(Variable v, Terme t) {
			this.v = v;
			this.t = t;
		}

		public Terme getTerme() {
			return t;
		}
		
		public Terme getVariable() {
			return v;
		}

		public void setTerme(Terme t) {
			this.t = t;
		}

		public void setVariable(Variable v) {
			this.v = v;
		}

		public boolean equals(Object o) {
			if (o instanceof Couple) {
				return 	this.v.equals(((Couple)o).v)
					&& this.t.equals(((Couple)o).t);
			} else
				return false;
		}

		public String toString() {
			return v.toString() + "=" + t.toString();
		}
	}

	private LinkedList<Couple> substitutions;

	public Substitution() {
		substitutions = new LinkedList<Couple>();
	}

	public Substitution(Variable v, Terme t) {
		this();
		ajouter(v,t);
	}

	public void ajouter(Couple c) {
		if (!contient(c))
			substitutions.add(c);
	}

	public void ajouter(Variable v, Terme t) {
		ajouter(new Couple(v,t));
	}

	public void retirer(Couple c) {
		substitutions.remove(c);
	}

	public boolean contient(Couple c) {
		return substitutions.contains(c);
	}

	public boolean contientVariable(Variable v) {
		for (Couple c: substitutions) {
			if (c.getVariable().equals(v))
				return true;
		}
		return false;
	}

	public boolean estVide() {
		return (substitutions.size() <= 0);
	}

	public List<Couple> getSubstitutions() {
		return new LinkedList<Couple>(substitutions);
	}

	public Terme getSubstitution(Variable v) {
		for (Couple c : substitutions) {
			if (c.getVariable().equals(v))
				return c.getTerme();
		}
		return null;
	}

	public void applique(EnsembleTermes et) {
		Terme[] ts = et.getTermes();
		Terme tmp;
		for (int i = 0; i < ts.length; i++) {
			if (ts[i] instanceof Variable) {
				tmp = getSubstitution((Variable)ts[i]);
				if (tmp != null)
					et.setTerme(i,tmp);
			} else if (ts[i] instanceof EnsembleTermes)
				applique((EnsembleTermes)ts[i]);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("Sigma = {");
		for (Couple c : substitutions)
			sb.append(c.toString()).append(",");
		sb.append((substitutions.size() > 0 ? "\010" : "") + "}");
		return sb.toString();
	}
}
