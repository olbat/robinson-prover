package adpo;

import adpo.analyse.*;
import adpo.formule.*;
import adpo.resolution.*;

public class Main {
	public static final int
		FICHIER = 0x1000,
		FORMULE = 0x2000;

	public static int verifArgs(String[] args) {
		if (args.length <= 0) {
			System.out.println("usage:\tjava fichierMain "
				+ "-e \"<formule>\"");
			System.out.println("\tjava fichierMain "
				+ "[-f] <fichier>");
			System.exit(0);
		}
		if (args.length > 1 && "-e".equals(args[0]))
			return FORMULE + 1;
		else if (args.length > 1 && "-f".equals(args[0]))
			return FICHIER + 1;
		else
			return FICHIER;
	}

	public static int getDecalage(int nb) {
		return ((nb & ~FICHIER) & ~FORMULE);
	}

	public static boolean estFichier(int nb) {
		return (nb & FICHIER) != 0;
	}

	public static boolean estFormule(int nb) {
		return (nb & FORMULE) != 0;
	}

	public static void checkNull(Object o, String msg) {
		if (o == null) {
			System.err.println("Erreur lors de : " + msg);
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		AnalyseLexicale al = null;
		AnalyseSyntaxique as = null;
		Formule f;
		FormeClausale fc;
		RobinsonResolution rr;
		Clause res;

		int type = verifArgs(args);
		String s = args[getDecalage(type)];

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

		checkNull(al,"analyse lexicale");
		checkNull(as,"analyse syntaxique creation");
		
		f = as.analyser();
		checkNull(f,"analyse syntaxique");
		System.out.println("Formule : " + f);

		fc = new FormeClausale(f);
		checkNull(fc,"mise sous forme clausale");
		System.out.println("Forme clausale de la formule : " + fc);

		rr = new RobinsonResolution(fc);
		checkNull(rr,"RobinsonResolution");

		res = rr.resoudre();
		if (res instanceof ClauseVide)
			System.out.println("\nLa formule est un théorème (LP1)");
		else
			System.out.println("\nLa formule n'est pas Théorème (LP1)");
	}
}
