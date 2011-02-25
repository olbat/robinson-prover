package adpo.formule;

public class Skolemisation {
	public static int compteur = 0;

	public static String getFonctionId() {
		return "U" + compteur++;
	}
}
