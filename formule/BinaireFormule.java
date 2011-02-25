package adpo.formule;

import java.util.Collection;

public abstract class BinaireFormule extends Binaire<Formule,Formule> {
	public BinaireFormule(Formule fg, Formule fd) {
		super(fg,fd);
	}

	public void clotureUniverselle(Collection<Variable> quantifie, Collection<Variable> tout) {
		fg.clotureUniverselle(quantifie,tout);
		fd.clotureUniverselle(quantifie,tout);
	}
}
