package logica;

public class Validador {
	
	public static boolean esCorrecto(Tablero jugador, int[][] solucion) {
		int n = jugador.getSize();
		
		for (int i = 0; i < n; i++) {
			for ( int j = 0; j < n; j++) {
				int estadoJugador = jugador.getCelda(i, j).getEstado();
				
				// La solucion tiene 1 para negro, 0 para blanco
				int esperado = solucion[i][j];
				
				if (esperado == 1 && estadoJugador != Celda.NEGRO) {
					return false; // debería ser negro y no lo es
				}
				
				if (esperado == 0 && estadoJugador == Celda.NEGRO) {
					return false; // está negro donde no debería
				}
			}
		}
		return true; //todas las celdas coinciden
	}
}
