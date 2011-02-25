package adpo.resolution;

import adpo.formule.*;

import java.util.LinkedList;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public abstract class Resolution {
	protected LinkedList<Clause> clauses;
	
	public Resolution(FormeClausale fc) {
		clauses = new LinkedList<Clause>(fc.getClauses());
	}
	
	public abstract Clause resoudre();
	public abstract void afficherSysteme();

	public void afficherClauses() {
		System.out.println("-> Clauses actuellement en résolution:");
		for (int i = 0; i < clauses.size(); i++)
			System.out.println((i+1) + "| " + clauses.get(i));
		System.out.println("(0 pour arreter)\n");
	}
	
	protected int lireChoix(String message, int binf, int bsup) {
		BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));
		int choix = -1;
		String schoix = null;

		while ((choix < binf) || (choix > bsup)) {
			System.out.print(message);
			try {
				schoix = in.readLine();
			} catch (IOException ioe) {
				System.err.println("Erreur d'entrée/sorties");
				System.exit(1);
			}
			try {
				choix = Integer.parseInt(schoix);;
			} catch (NumberFormatException nfe) {
				System.out.println("Mauvaise saisie");
			}
			if ((choix < 0) || (choix > clauses.size()))
				System.out.println("Numero invalide");
		}
		return choix;
	}

	protected int lireChoixClause(String message) {
		return lireChoix(message,0,clauses.size());
	}
}
