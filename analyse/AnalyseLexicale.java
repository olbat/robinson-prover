package adpo.analyse;

public abstract class AnalyseLexicale {
	/* private String expr; */
	protected int pos;
	/* a modifier, mettre en protected */
	public TableLexeme tableAnalyse;

	public AnalyseLexicale() {
		pos = 0;
		tableAnalyse = new TableLexeme();
	}
	
	public int getPos() {
		return pos;
	}

	public Lexeme getLexeme(int id) {
		return tableAnalyse.getLexeme(id);
	}

	/* methode qui se base sur la valeur courrante de pos */
	public abstract void lireCarSuivant();
	public abstract char getExprCarCourrant();
	public abstract char getExprCarSuivant();
	public abstract int getExprTaille();

	private final int 
		ET_ERREUR = -2,
		ET_SUCCES = -1,
		ET_DEBUT = 0,
		ET_IMPLIQUE = 1,
		ET_ET = 2,
		ET_OU = 3,
		ET_NON = 4,
		ET_POUR_TOUT = 5,
		ET_IL_EXISTE = 6,
		ET_PARENTHESE_OUVRANTE = 7,
		ET_PARENTHESE_FERMANTE = 8,
		ET_VIRGULE = 9,
		ET_VRAI = 10,
		ET_FAUX = 11,
		ET_VARIABLES = 12,
		ET_DIGITITERE = 13,
		ET_CAR0_E = 20;

	public int analyser() {
		int 	etat = ET_DEBUT,
			ptr = 0,
			id;
		String lex;

		if (pos >= getExprTaille()) {
			id = Lexeme.ID_EOF;
			lex = "__EOF__";
		} else {
			id = Integer.MAX_VALUE;
			lex = "";
		}

		while ((etat >= 0) && (id != Lexeme.ID_EOF)) {
			if (pos < getExprTaille()) {
				if (Lexeme.estSeparateur(getExprCarCourrant())) {
					if (etat == ET_DIGITITERE)
						etat = ET_SUCCES;
					else
						lireCarSuivant();
					continue;
				}
			} else {
				if (ptr >= lex.length()) 
					etat = ET_SUCCES;
				else
					etat = ET_ERREUR;
				break;
			}
			switch (etat) {
			case ET_DEBUT:
				switch (getExprCarCourrant()) {
				case 'E':
					etat = ET_CAR0_E;
					break;
				/* compilateur en carton
				 * case Lexeme.IMPLIQUE.charAt(0):
				 */
				case '=':
					lex = Lexeme.IMPLIQUE;
					id = Lexeme.ID_IMPLIQUE;
					etat = ET_IMPLIQUE;
					break;
				/* case Lexeme.OU.charAt(0): */
				case 'O':
					lex = Lexeme.OU;
					etat = ET_OU;
					id = Lexeme.ID_OU;
					break;
				/* case Lexeme.NON.charAt(0): */
				case 'N':
					lex = Lexeme.NON;
					etat = ET_NON;
					id = Lexeme.ID_NON;
					break;
				/* case Lexeme.POUR_TOUT.charAt(0): */
				case 'A':
					lex = Lexeme.POUR_TOUT;
					etat = ET_POUR_TOUT;
					id = Lexeme.ID_POUR_TOUT;
					break;
				/* case Lexeme.PARENTHESE_OUVRANTE.charAt(0): */
				case '(':
					lex = Lexeme.PARENTHESE_OUVRANTE;
					etat = ET_PARENTHESE_OUVRANTE;
					id = Lexeme.ID_PARENTHESE_OUVRANTE;
					break;
				/* case Lexeme.PARENTHESE_FERMANTE.charAt(0): */
				case ')':
					lex = Lexeme.PARENTHESE_FERMANTE;
					etat = ET_PARENTHESE_FERMANTE;
					id = Lexeme.ID_PARENTHESE_FERMANTE;
					break;
				/* case Lexeme.VIRGULE.charAt(0): */
				case ',':
					lex = Lexeme.VIRGULE;
					etat = ET_VIRGULE;
					id = Lexeme.ID_VIRGULE;
					break;
				/* case Lexeme.VRAI.charAt(0): */
				case 'V':
					lex = Lexeme.VRAI;
					etat = ET_VRAI;
					id = Lexeme.ID_VRAI;
					break;
				/* case Lexeme.FAUX.charAt(0): */
				case 'F':
					lex = Lexeme.FAUX;
					etat = ET_FAUX;
					id = Lexeme.ID_FAUX;
					break;
				default:
					switch (Lexeme.getEnsemble(getExprCarCourrant())) {
					case Lexeme.ID_VARIABLES:
						lex = getExprCarCourrant() + "";
						etat = ET_DIGITITERE;
						id = Lexeme.ID_VARIABLES;
						break;
					case Lexeme.ID_CONSTANTES:
						lex = getExprCarCourrant() + "";
						etat = ET_DIGITITERE;
						id = Lexeme.ID_CONSTANTES;
						break;
					case Lexeme.ID_FONCTIONS:
						lex = getExprCarCourrant() + "";
						etat = ET_DIGITITERE;
						id = Lexeme.ID_FONCTIONS;
						break;
					case Lexeme.ID_RELATIONS:
						lex = getExprCarCourrant() + "";
						etat = ET_DIGITITERE;
						id = Lexeme.ID_RELATIONS;
						break;
					default:		
						etat = ET_ERREUR;
						break;
					}
					break;
				}
				break;
			case ET_CAR0_E:
				if (((pos + 1) < getExprTaille()) 
				&& (getExprCarSuivant()
				 == Lexeme.ET.charAt(1))) {
					lex = Lexeme.ET;
					etat = ET_ET;
					id = Lexeme.ID_ET;
				} else {
					lex = Lexeme.IL_EXISTE;
					etat = ET_IL_EXISTE;
					id = Lexeme.ID_IL_EXISTE;
				}
				break;
			case ET_DIGITITERE:
				if (Character.isDigit(getExprCarCourrant()))
					lex = lex + getExprCarCourrant();
				else
					etat = ET_SUCCES;
				break;
			case ET_IMPLIQUE:
			case ET_ET:
			case ET_OU:
			case ET_NON:
			case ET_POUR_TOUT:
			case ET_IL_EXISTE:
			case ET_PARENTHESE_OUVRANTE:
			case ET_PARENTHESE_FERMANTE:
			case ET_VIRGULE:
			case ET_VRAI:
			case ET_FAUX:
				if (ptr >= lex.length())
					etat = ET_SUCCES;
				else if (getExprCarCourrant() != lex.charAt(ptr))
					etat = ET_ERREUR;
				break;
		}
		if ((pos < getExprTaille()) && (ptr < lex.length())
		&& (etat != ET_SUCCES)) {
			lireCarSuivant();
			ptr++;
		} else {
			if ((etat != ET_ERREUR) && (etat != ET_CAR0_E))
				etat = ET_SUCCES;
		}
	}
		if (etat == ET_ERREUR)
			return -2;
		else
			return tableAnalyse.ajoutLexeme(new Lexeme(id,lex));
	}
}
