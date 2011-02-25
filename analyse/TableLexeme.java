package adpo.analyse;

import java.util.*;

public class TableLexeme {
	private HashMap<Lexeme,Integer> table;
	private int compteur;

	public TableLexeme() {
		table = new HashMap<Lexeme,Integer>();
		compteur = 0;
	}

	public int getId(Lexeme lex) {
		Integer tmp = table.get(lex);
		if (tmp != null)
			return tmp.intValue();
		else
			return -1;
	}

	public Lexeme getLexeme(int id) {
		Lexeme ret = null;
		if (this.contientId(id)) {
			Map.Entry<Lexeme,Integer> tmp;
			Iterator<Map.Entry<Lexeme,Integer>> it =
				table.entrySet().iterator();
			while (it.hasNext()) {
				tmp = it.next();
				if (tmp.getValue() == id) {
					ret = tmp.getKey();
					break;
				}
			}
		}
		return ret;
	}

	public boolean contient(Lexeme lex) {
		return table.containsKey(lex);
	}

	public boolean contientId(int id) {
		return table.containsValue(id);
	}

	public int ajoutLexeme(Lexeme lex) {
		int ret;
		if (this.contient(lex))
			ret = table.get(lex).intValue();
		else
			table.put(lex,new Integer((ret = compteur++)));
		return ret;
	}	
}
