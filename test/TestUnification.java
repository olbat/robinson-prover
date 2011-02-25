package adpo.test;

import adpo.analyse.*;
import adpo.formule.*;
import adpo.resolution.*;

public class TestUnification {
	public static void main(String[] args) {
		TestUnification.test();
	}

	public static void test() {
		Atome 	a1 = new Atome("r"),
			a2 = new Atome("r");
		Fonction f1 = new Fonction("f"),
			f2 = new Fonction("f");
		Unification u;

		f2.ajouter(new Variable("z"));
		f1.ajouter(new Constante("a"));

		a1.ajouter(f1);
		a1.ajouter(new Constante("b"));
		a2.ajouter(f2);
		a2.ajouter(new Variable("y"));

		u = new Unification(a1,a2);
		
		System.out.println(a1 + " " + a2);
		System.out.println(u.unifier());
		System.out.println(a1 + " " + a2);
		System.out.println(u.getSubstitution());
		System.out.println(u.getAtome2() + " " + u.getAtome1().equals(u.getAtome2()));


	}
}
