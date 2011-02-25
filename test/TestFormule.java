package adpo.test;

import adpo.analyse.*;
import adpo.formule.*;
import adpo.resolution.FormeClausale;

public class TestFormule {
	public static void main(String[] args) {
		Tests.anaArgs(args);
		test(Tests.as);
	}

	public static void test(AnalyseSyntaxique as) {
		/* Tests Formule */
		System.out.println("---Formule---");
		Formule f = as.analyser();
		System.out.println(f);

		/* Tests simplification */
		System.out.println("---Simplification---");
		Formule fs = f.simplification(); 
		System.out.println(fs);

		/* Tests negation */
		System.out.println("---Negation---");
		Formule nf = null;
		fs.nier(true);
		nf = fs.negation();
		System.out.println(nf);

		/* Tests FormeClausale */
		System.out.println("---FormeClausale---");
		FormeClausale fc = new FormeClausale(f);
		System.out.println(fc);


		/* Tests simplification */
		System.out.println("---FormeClausale_simplification---");
		System.out.println(fc.simplifier());
	}
}
