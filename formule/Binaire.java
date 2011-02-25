package adpo.formule;

public abstract class Binaire<EG,ED> extends Connecteur {
	protected EG fg;
	protected ED fd;

	public Binaire(EG fg, ED fd) {
		super();
		this.fg = fg;
		this.fd = fd;
	}

	public EG getFilsGauche() {
		return fg;
	}

	public ED getFilsDroit() {
		return fd;
	}
}
