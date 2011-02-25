package adpo.formule;

public abstract class Unaire<E> extends Connecteur {
	protected E fils;

	public Unaire(E fils) {
		super();
		this.fils = fils;
	}

	public E getFils() {
		return fils;
	}
}
