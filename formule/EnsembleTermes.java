package adpo.formule;

import java.util.LinkedList;
import java.util.Iterator;

public abstract class EnsembleTermes extends Ensemble {
	protected LinkedList<Terme> termes;

	public EnsembleTermes(String s) {
		super(s);
		termes = new LinkedList<Terme>();
	}

	public EnsembleTermes(String s, Terme t) {
		this(s);
		ajouter(t);
	}

	public void ajouter(Terme t) {
		termes.add(t);
	}

	public void ajouterTout(EnsembleTermes et) {
		termes.addAll(et.termes);
	}

	public Terme[] getTermes() {
		/* return (Terme[]) termes.toArray(); */
		Terme[] ret = new Terme[termes.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = termes.get(i);
		return ret;
	}

	public void setTerme(int ind, Terme t) {
		termes.set(ind,t);
	}

	public int getTaille() {
		return termes.size();
	}

	public boolean contient(Terme t) {
		return termes.contains(t);
	}

	public boolean equals(Object o) {
		if ((super.equals(o)) && (o instanceof EnsembleTermes)) {
			Iterator itt = termes.iterator(),
				 ito = ((EnsembleTermes)o).termes.iterator();
			boolean ret = true;

			while ((itt.hasNext()) && (ret)) {
				if (ito.hasNext())
					ret = (itt.next().equals(ito.next()));
				else
					ret = false;
			}
			return ret;
		} else
			return false;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("(");
		if (termes.size() <= 0)
			sb.append(" ");
		else {
			for (Terme t : termes)
				sb.append(t.toString()).append(",");
		}
		sb.append("\010)");
		return sb.toString();
	}
}
