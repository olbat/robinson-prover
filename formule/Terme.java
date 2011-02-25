package adpo.formule;

public interface Terme {
	public String getNom();
	public Terme[] getTermes();
	public boolean estEquivalent(Terme t);
}
