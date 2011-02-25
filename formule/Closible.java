package adpo.formule;

import java.util.Collection;

public interface Closible {
	/* analyse avant cloture universelle */
	public void clotureUniverselle(Collection<Variable> quantifie, Collection<Variable> tout);
}
