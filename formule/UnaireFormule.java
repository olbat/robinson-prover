package adpo.formule;

import java.util.Collection;

public abstract class UnaireFormule extends Unaire<Formule> {
	public UnaireFormule(Formule f) {
		super(f);
	}

	public void clotureUniverselle(Collection<Variable> quantifie, Collection<Variable> tout) {
		fils.clotureUniverselle(quantifie,tout);
	}
}
