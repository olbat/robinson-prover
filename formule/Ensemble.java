package adpo.formule;

public abstract class Ensemble {
	protected String nom;

	public Ensemble(String s) {
		this.nom = s;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String s) {
		nom = s;
	}

	public boolean equals(Object o) {
		if (o instanceof Ensemble) {
			return (this.nom == null ? ((Ensemble)o).getNom() == null
				: this.nom.equals(((Ensemble)o).getNom()));
		} else
			return false;
	}

	public String toString() {
		return nom;
	}
}
