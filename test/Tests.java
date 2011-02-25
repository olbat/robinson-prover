package adpo.test;

import adpo.Main;
import adpo.analyse.*;
import adpo.formule.*;
import adpo.resolution.*;

public class Tests {
	public static AnalyseLexicale al = null;
	public static AnalyseSyntaxique as = null;

	/* a deplacer dans adpo.Main, sans attributs */
	public static void anaArgs(String[] args) {
		int type = Main.verifArgs(args);
		String s = args[Main.getDecalage(type)];

		if (Main.estFormule(type)) {
			al = new AnalyseLexicaleString(s);
			as = new AnalyseSyntaxiqueString(s);
		} else if (Main.estFichier(type)) {
			al = new AnalyseLexicaleFile(s);
			as = new AnalyseSyntaxiqueFile(s);
		} else {
			System.err.println("Erreur innatendue");
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		anaArgs(args);

		TestAnalyse.test(al,as);
		TestFormule.test(as);
		FormeClausale fc = new FormeClausale(as.analyser());
		TestClauseResolution.test(fc);
		TestRobinsonResolution.test(fc);
	}
}
