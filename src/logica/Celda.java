package logica;

public class Celda {
	
	public static final int BLANCO = 0;
	public static final int	NEGRO = 1;
	public static final int	X = 2;
	
	private int estado;
	
	public Celda() {
		this.estado = BLANCO;
	}
	
	public int getEstado() {
		return estado;
	}
	public void setEstado( int estado) {
		this.estado = estado;
	}

}
