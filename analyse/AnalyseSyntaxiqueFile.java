package adpo.analyse;

public class AnalyseSyntaxiqueFile extends AnalyseSyntaxique {
	private String filename;

	public AnalyseSyntaxiqueFile(String s) {
		super();
		filename = s;
	}

	protected AnalyseLexicale getAnaLex() {
		return new AnalyseLexicaleFile(filename);
	}
}

