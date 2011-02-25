package adpo.analyse;

public class AnalyseLexicaleString extends AnalyseLexicale {
	private String expr;

	public AnalyseLexicaleString(String s) {
		super();
		expr = s;
	}

	public void lireCarSuivant() {
		pos++;
	}

	public char getExprCarCourrant() {
		return expr.charAt(pos);
	}

	public char getExprCarSuivant() {
		return expr.charAt(pos + 1);
	}

	public int getExprTaille() {
		return expr.length();
	}
}
