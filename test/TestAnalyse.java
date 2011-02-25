package adpo.test;

import adpo.analyse.*;
import adpo.formule.*;

public class TestAnalyse {
	public static void main(String[] args) {
		Tests.anaArgs(args);
		TestAnalyse.test(Tests.al,Tests.as);
	}

	public static void test(AnalyseLexicale al, AnalyseSyntaxique as) {
		int ret,i;

		/* Tests AnalyseLexicale */
		System.out.println("---AnalyseLexicale---");
		i = 0;
		do {
			ret = al.analyser();
			System.out.println("[Appel " + i++ + "] <id:" + ret 
			+ " | lex:" + al.tableAnalyse.getLexeme(ret) + ">");
		} while ((ret >= 0) && (al.tableAnalyse.getLexeme(ret).getType()
			!= Lexeme.ID_EOF));
		if (ret == -2)
			System.out.println("ERREUR");
		else
			System.out.println("SUCCES");	


		/* Tests AnalyseSyntaxique */
		System.out.println("---AnalyseSyntaxique---");
		Formule f = as.analyser();
		if (f == null)
			System.out.println("ERREUR");
		else {
			System.out.println("SUCCES");
			System.out.println(f);
		}
	}
}
