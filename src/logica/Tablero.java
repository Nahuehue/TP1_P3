package logica;

public class Tablero {
	
	private Celda[][] grilla;
	private int size;
	
	public Tablero(int size) {
		this.size = size;
		this.grilla = new Celda[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				grilla[i][j] = new Celda();
			}
		}
	}
	
	public void setCelda(int fila, int col, int estado) {
		grilla[fila][col].setEstado(estado);
	}
	
	public Celda getCelda(int fila, int col) {
		return grilla[fila][col];
	}
	
	public int getSize() {
		return size;
	}
	
	public void cambiarEstado(int fila, int col) {
		Celda celda = grilla[fila][col];
		int estado = celda.getEstado();
		estado = (estado + 1) % 3; // Ciclo  0 -> 1 -> 2 -> 0
		celda.setEstado(estado);
	}
	
	public void imprimir() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int estado = grilla[i][j].getEstado();
				char simbolo;
				if (estado == Celda.BLANCO) simbolo = '.';
				else if (estado == Celda.NEGRO) simbolo = ' ';
				else simbolo = 'X';
				System.out.print(simbolo + " ");
			}
			System.out.println();
		}
	}

}
