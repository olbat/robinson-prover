package adpo.formule;

import adpo.resolution.Substitution;
import java.util.Collection;

public interface Formule extends Closible {
	/* simplification, suppression des FAUX et VRAI */
	/* ATTENTION : la simplificaiton ne dois pas être appliquée sur 
	 * une formule niee */
	public Formule simplification();
	/* descente des negations */
	public void nier(boolean b);
	/* mise a jour de la formule avec les operateurs nies */
	public Formule negation();
	/* skolemisation de la formule */
	public Formule skolemiser(Collection<Variable> quantifie, Substitution sub);
}
