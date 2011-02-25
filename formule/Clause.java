package adpo.formule;

import java.util.List;
import java.util.LinkedList;

public class Clause {
	private LinkedList<Atome> apositifs;
	private LinkedList<Atome> anegatifs;

	public Clause() {
		apositifs = new LinkedList<Atome>();
		anegatifs = new LinkedList<Atome>();
	}

	public Clause(Formule f) {
		this();
		ajouter(f);
	}

	public Clause(Clause c) {
		this();
		ajouter(c);
	}

	public void ajouter(Formule f) {
		/* comme la formule doit etre niee a cette etape : 
		 * si les operateurs Binaires ne sont pas valides,
		 * on ne fait rien
		 * les verifications sont faites dans FormeClausale */
		if (f instanceof Ou) {
			ajouter(((Ou)f).getFilsGauche());
			ajouter(((Ou)f).getFilsDroit());
		} else if (f instanceof Atome) {
			ajouter((Atome) f);
		}
	}

	public Clause ajouter(Atome a) {
		if (a.estNegatif()) {
			if (!anegatifs.contains(a))
				anegatifs.add(a);
		} else {
			if (!apositifs.contains(a))
				apositifs.add(a);
		}
		return this;
	}
	
	public Clause ajouter(Clause c) {
		anegatifs.addAll(c.anegatifs);
		apositifs.addAll(c.apositifs);
		return this;
	}

	public Clause retirerTout(Atome a) {
		LinkedList<Atome> psupprl = new LinkedList<Atome>(),
			nsupprl = new LinkedList<Atome>();
		for (Atome ai : apositifs) {
			if (ai.estEquivalent(a))
				psupprl.add(ai);
		}

		for (Atome ai : anegatifs) {
			if (ai.estEquivalent(a))
				nsupprl.add(ai);
		}

		for (Atome ai : psupprl)
			retirer(ai);

		for (Atome ai : nsupprl)
			retirer(ai);

		return this;
	}

	public Clause retirer(Atome a) {
		if (a.estNegatif())
			anegatifs.remove(a);
		else
			apositifs.remove(a);
		return this;
	}

	public Clause retirer(List<Atome> list) {
		anegatifs.removeAll(list);
		apositifs.removeAll(list);
		return this;
	}

	public boolean remplacer(Atome ancien, Atome nouveau) {
		return (remplacerPos(ancien,nouveau) || remplacerNeg(ancien,nouveau));
	}

	private boolean remplacer(LinkedList<Atome> list, Atome ancien, Atome nouveau) {
		int ind = list.indexOf(ancien);
		if (ind >= 0) {
			list.set(ind,nouveau);
			return true;
		} else
			return false;
	}

	public boolean remplacerPos(Atome ancien, Atome nouveau) {
		return remplacer(apositifs,ancien,nouveau);
	}

	public boolean remplacerNeg(Atome ancien, Atome nouveau) {
		return remplacer(anegatifs,ancien,nouveau);
	}

	public boolean contient(Atome a) {
		return (apositifs.contains(a) || anegatifs.contains(a));
	}

	public boolean contientPositif(Atome a) {
		return apositifs.contains(a);
	}

	public boolean contientNegatif(Atome a) {
		return anegatifs.contains(a);
	}

	public boolean estSimplifiable() {
		return (getOpposeIntersection(this).size() > 0);
	}

	public List<Atome> getAtomes() {
		List<Atome> ret = getPositifs();
		ret.addAll(getNegatifs());
		return ret;
	}

	public List<Atome> getPositifs() {
		return new LinkedList<Atome>(apositifs);
	}

	public List<Atome> getNegatifs() {
		return new LinkedList<Atome>(anegatifs);
	}

	public List<Atome> getOpposeIntersection(Clause c) {
		LinkedList<Atome> ret = new LinkedList<Atome>();
		Atome tmpa;
		
		for (Atome a: c.apositifs) {
			tmpa = getOppose(a);
			if (tmpa != null)
				ret.add(tmpa);
		}

		for (Atome a: c.anegatifs) {
			tmpa = getOppose(a);
			if (tmpa != null)
				ret.add(tmpa);
		}
		return ret;
	}

	public Atome getOppose(Atome a) {
		LinkedList<Atome> oppliste = 
			(a.estNegatif() ? apositifs : anegatifs);
		int ind = oppliste.indexOf(a);
		if (ind >= 0)
			return oppliste.get(ind);
		else
			return null;
	}

	/* retourne vrai si la clause est un ensemble vide de clauses
	 * (a ne pas confondre avec la clause vide)
	 */
	public boolean estVide() {
		return ((anegatifs.size() <= 0) && (apositifs.size() <= 0));
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Atome a: apositifs)
			sb.append(a.toString()).append(" OU ");
		for (Atome a: anegatifs)
			sb.append(a.toString()).append(" OU ");
		if (sb.length() >= 4)
			sb.delete(sb.length()-4,sb.length()-1);
		return sb.toString();
	}
}
