package adpo.analyse;

public class Lexeme {
	/* <static> */
	public static final String
		IMPLIQUE = "=>",
		ET = "ET",
		OU = "OU",
		NON = "NON",
		POUR_TOUT = "A",
		IL_EXISTE = "E",
		PARENTHESE_OUVRANTE = "(",
		PARENTHESE_FERMANTE = ")",
		VIRGULE = ",",
		VRAI = "VRAI",
		FAUX = "FAUX",
		VARIABLES = "[xyz][0-9]*",
		CONSTANTES = "[abc][0-9]*",
		FONCTIONS = "[fgh][0-9]*",
		RELATIONS = "[pqr][0-9]*",
		SEPARATEURS = "\\s";

	public static final int 
		ID_EOF = 0,
		ID_IMPLIQUE = 1,
		ID_ET = 2,
		ID_OU = 3,
		ID_NON = 4,
		ID_POUR_TOUT = 5,
		ID_IL_EXISTE = 6,
		ID_PARENTHESE_OUVRANTE = 7,
		ID_PARENTHESE_FERMANTE = 8,
		ID_VIRGULE = 9,
		ID_VRAI = 10,
		ID_FAUX = 11,
		ID_VARIABLES = 20,
		ID_CONSTANTES = 21,
		ID_FONCTIONS = 22,
		ID_RELATIONS = 23;

	public static int getEnsemble(char c) {	
		String tmp = c + "";
		if (tmp.matches(Lexeme.VARIABLES))
			return ID_VARIABLES;
		else if (tmp.matches(Lexeme.CONSTANTES))
			return ID_CONSTANTES;
		else if (tmp.matches(Lexeme.FONCTIONS))
			return ID_FONCTIONS;
		else if (tmp.matches(Lexeme.RELATIONS))
			return ID_RELATIONS;
		else
			return -1;
	}

	public static boolean estSeparateur(char c) {
		return (c + "").matches(Lexeme.SEPARATEURS);
	}
	/* </static> */
	
	private int type;
	private String valeur;

	public Lexeme(int t, String v) {
		type = t;
		valeur = v;
	}

	public int getType() {
		return type;
	}

	public String getValeur() {
		return valeur;
	}

	public boolean equals(Object o) {
		if (o instanceof Lexeme) {
			return (((Lexeme)o).type == type)
				&& (valeur == null ? ((Lexeme)o).valeur == null
					: valeur.equals(((Lexeme)o).valeur)); 
		} else
			return false;
	}
	
	public int hashCode() {
		return valeur.hashCode() + (type*31^valeur.length());
	}

	public String toString() {
		return "(" + type + "," + valeur + ")";
	}
}
