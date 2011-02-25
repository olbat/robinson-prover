package adpo.test;

import adpo.formule.*;
import adpo.resolution.*;

public class TestClauseResolution {
	public static void main(String[] args) {
		Tests.anaArgs(args);
		test(new FormeClausale(Tests.as.analyser()));
	}

	public static void test(FormeClausale fc) {
		/* Tests Résolution */
		System.out.println("---Resolution---");
		ClauseResolution cr = new ClauseResolution(fc);
		Clause res = cr.resoudre();

		if (res instanceof ClauseVide)
			System.out.println("Théorème (LP1)");
		else
			System.out.println("Pas Théorème (LP1)");
	}
}
