package adpo.analyse;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AnalyseLexicaleFile extends AnalyseLexicale {
	private FileReader fichier;
	private int length;
	private char courCar, suivCar;

	public AnalyseLexicaleFile(String filename) {
		super();
		File f = new File(filename);
		length = (int) f.length();
		try {
			fichier = new FileReader(f);
		} catch (FileNotFoundException fnfe) {
			System.err.println("Fichier introuvable");
			System.exit(1);
		}
		suivCar = 0;
		lireCarSuivant();
		lireCarSuivant();
		pos = 0;
	}

	public void lireCarSuivant() {
		courCar = suivCar;
		try {
			suivCar = (char) fichier.read();
		} catch (IOException ioe) {
			System.err.println("Erreur d'entree/sortie");
			System.exit(1);
		}
		pos++;
	}

	public char getExprCarCourrant() {
		return courCar;
	}

	public char getExprCarSuivant() {
		return suivCar;
	}

	public int getExprTaille() {
		return length;
	}
}
