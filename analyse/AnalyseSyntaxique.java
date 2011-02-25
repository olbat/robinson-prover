package adpo.analyse;

import adpo.formule.*;
import adpo.resolution.*;

import java.util.LinkedList;

public abstract class AnalyseSyntaxique {
	private Lexeme lexCour;
	private boolean erreur;
	private AnalyseLexicale anaLex;

	public AnalyseSyntaxique() {
		lexCour = null;
		erreur = false;
		anaLex = null;
	}

	protected abstract AnalyseLexicale getAnaLex();

	public boolean getErreur() {
		return erreur;
	}

	private boolean lire() {
		int id;
		boolean ret = ((id = anaLex.analyser()) >= 0);
		if (ret)
			lexCour = anaLex.getLexeme(id);
		return ret;
	}	

	public Formule analyser() {
		erreur = false;
		/* anaLex = new AnalyseLexicaleString(formule); */
		anaLex = getAnaLex();

		lire();

		Formule f = anaFormule();

		if ((erreur) || (lexCour.getType() != Lexeme.ID_EOF) || (f == null))
			return null;
		else 
			return f;
	}

	private String ana(int id) {
		String ret = "";
		if (!erreur) {
			if (lexCour.getType() == id) {
				switch(id) {
				case Lexeme.ID_CONSTANTES:
				case Lexeme.ID_VARIABLES:
				case Lexeme.ID_RELATIONS:
				case Lexeme.ID_FONCTIONS:
					ret = lexCour.getValeur();
					break;
				default:
					break;
				}
				lire();
			} else
				erreur = true;
		}
		return ret;
	}
	
	private Formule anaFormule() {
		if (!erreur) {
			switch (lexCour.getType()) {
			case Lexeme.ID_NON:
			case Lexeme.ID_RELATIONS:
			case Lexeme.ID_VRAI:
			case Lexeme.ID_FAUX:
			case Lexeme.ID_PARENTHESE_OUVRANTE:
			case Lexeme.ID_IL_EXISTE:
			case Lexeme.ID_POUR_TOUT:
				Formule fg =  anaDisjonction();
				return anaFormulePrime(fg);
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}

	private Formule anaFormulePrime(Formule fg) {
		if (!erreur) {
			switch (lexCour.getType()) {
			case Lexeme.ID_IMPLIQUE:
				ana(Lexeme.ID_IMPLIQUE);
				return new Implique(fg,anaFormule());
			case Lexeme.ID_PARENTHESE_FERMANTE:
			case Lexeme.ID_EOF:
				return fg;
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}

	private Formule anaDisjonction() {
		if (!erreur) {
			switch (lexCour.getType()) {
			case Lexeme.ID_NON:
			case Lexeme.ID_RELATIONS:
			case Lexeme.ID_VRAI:
			case Lexeme.ID_FAUX:
			case Lexeme.ID_PARENTHESE_OUVRANTE:
			case Lexeme.ID_IL_EXISTE:
			case Lexeme.ID_POUR_TOUT:
				Formule fg = anaConjonction();
				return anaDisjonctionD(fg);
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}

	private Formule anaDisjonctionD(Formule fg) {
		if (!erreur) {
			switch (lexCour.getType()) {
			case Lexeme.ID_OU:
				ana(Lexeme.ID_OU);
				Formule fd = anaConjonction();
				/* Formule fD = anaDisjonctionD(fg); */
				return anaDisjonctionD(new Ou(fg,fd));
			case Lexeme.ID_PARENTHESE_FERMANTE:
			case Lexeme.ID_EOF:
			case Lexeme.ID_IMPLIQUE:
				return fg;
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}

	private Formule anaConjonction() {
	 	if (!erreur) {
			switch (lexCour.getType()) {
			case Lexeme.ID_NON:
			case Lexeme.ID_RELATIONS:
			case Lexeme.ID_VRAI:
			case Lexeme.ID_FAUX:
			case Lexeme.ID_PARENTHESE_OUVRANTE:
			case Lexeme.ID_IL_EXISTE:
			case Lexeme.ID_POUR_TOUT:
				Formule fg = anaFacteur();
				return anaConjonctionD(fg);
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}

	private Formule anaConjonctionD(Formule fg) {
	 	if (!erreur) {
			switch (lexCour.getType()) {
			case Lexeme.ID_ET:
				ana(Lexeme.ID_ET);
				Formule fd = anaFacteur();
				/* Formule fD = anaConjonctionD(fg); */
				return anaConjonctionD(new Et(fg,fd));
			case Lexeme.ID_PARENTHESE_FERMANTE:
			case Lexeme.ID_OU:
			case Lexeme.ID_EOF:
			case Lexeme.ID_IMPLIQUE:
				return fg;
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}
		
	private Formule anaFacteur() {
		if (!erreur) {
			switch (lexCour.getType()) {
			case Lexeme.ID_NON:
				ana(Lexeme.ID_NON);
				return new Non(anaFacteur());
			case Lexeme.ID_RELATIONS:
			case Lexeme.ID_VRAI:
			case Lexeme.ID_FAUX:
			case Lexeme.ID_PARENTHESE_OUVRANTE:
			case Lexeme.ID_IL_EXISTE:
			case Lexeme.ID_POUR_TOUT:
				return anaFormuleQ();
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}

	private Formule anaFormuleQ() {
		if (!erreur) {
			String nom;
			Formule ret;

			switch (lexCour.getType()) {
			case Lexeme.ID_RELATIONS:
			case Lexeme.ID_VRAI:
			case Lexeme.ID_FAUX:
				return anaAtome();
			case Lexeme.ID_PARENTHESE_OUVRANTE:
				ana(Lexeme.ID_PARENTHESE_OUVRANTE);
				ret = anaFormule();
				ana(Lexeme.ID_PARENTHESE_FERMANTE);
				return ret;
			case Lexeme.ID_IL_EXISTE:
				ana(Lexeme.ID_IL_EXISTE);
				nom = ana(Lexeme.ID_VARIABLES);
				return new IlExiste(new Variable(nom),anaFormuleQ());
			case Lexeme.ID_POUR_TOUT:
				ana(Lexeme.ID_POUR_TOUT);
				nom = ana(Lexeme.ID_VARIABLES);
				return new PourTout(new Variable(nom),anaFormuleQ());
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}

	private Formule anaAtome() {
		if (!erreur) {
			switch (lexCour.getType()) {
			case Lexeme.ID_RELATIONS:
				String nom = ana(Lexeme.ID_RELATIONS);
				ana(Lexeme.ID_PARENTHESE_OUVRANTE);
				EnsembleTermes et =
					anaListeTermes(new Atome(nom));
				ana(Lexeme.ID_PARENTHESE_FERMANTE);
				if (et instanceof Atome)
					return (Atome) et;
				else
					erreur = true;
			case Lexeme.ID_VRAI:
				ana(Lexeme.ID_VRAI);
				return new Vrai();
			case Lexeme.ID_FAUX:
				ana(Lexeme.ID_FAUX);
				return new Faux();
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}

	private EnsembleTermes anaListeTermes(EnsembleTermes et) {
		if (!erreur) {
			switch (lexCour.getType()) {
			case Lexeme.ID_VARIABLES:
			case Lexeme.ID_CONSTANTES:
			case Lexeme.ID_FONCTIONS:
				et.ajouter(anaTerme());
				return anaListeTermesPrime(et);
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}

	private EnsembleTermes anaListeTermesPrime(EnsembleTermes et) {
		if (!erreur) {
			switch (lexCour.getType()) {
			case Lexeme.ID_PARENTHESE_FERMANTE:
				return et;
			case Lexeme.ID_VIRGULE:
				ana(Lexeme.ID_VIRGULE);
				anaListeTermes(et);
				return et;
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}

	private Terme anaTerme() {
		if (!erreur) {
			String nom;
			switch (lexCour.getType()) {
			case Lexeme.ID_VARIABLES:
				Variable var;

				nom = ana(Lexeme.ID_VARIABLES);
				var = new Variable(nom);
					return var;
			case Lexeme.ID_CONSTANTES:
				nom = ana(Lexeme.ID_CONSTANTES);
				return new Constante(nom);
			case Lexeme.ID_FONCTIONS:
				nom = ana(Lexeme.ID_FONCTIONS);
				ana(Lexeme.ID_PARENTHESE_OUVRANTE);
				EnsembleTermes et = 
					anaListeTermes(new Fonction(nom));
				ana(Lexeme.ID_PARENTHESE_FERMANTE);
				if (et instanceof Fonction)
					return (Fonction) et;
				else
					erreur = true;
			default:
				erreur = true;
				break;
			}
		}
		return null;
	}
}
