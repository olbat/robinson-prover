package adpo.analyse;

public class AnalyseSyntaxiqueString extends AnalyseSyntaxique {
	private String formule;

	public AnalyseSyntaxiqueString(String s) {
		super();
		formule = s;
	}

	protected AnalyseLexicale getAnaLex() {
		return new AnalyseLexicaleString(formule);
	}
}
