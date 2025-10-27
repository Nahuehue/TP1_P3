package logica;

public class Juego {
	
	// Ejemplo de solución fija (5x5) - ya no se usa, se genera dinámicamente
	// 1 = NEGRO, 0 = BLANCO
	private static final int [][] SOLUCION_EJEMPLO = {
			{1 ,0 ,0 ,1 ,0},
			{0, 1, 0, 1, 0},
	        {1, 1, 1, 0, 1},
	        {0, 0, 0, 1, 1},
	        {1, 0, 0, 0, 1}
	};
	
	/**
	 * Obtiene una solución generada dinámicamente
	 * @param size Tamaño del tablero
	 * @return Tablero con solución única
	 */
	public static int[][] getSolucion(int size) {
		return GeneradorTableros.generarTablero(size);
	}
	
	/**
	 * Obtiene la solución de ejemplo (para compatibilidad)
	 * @deprecated Usar getSolucion(int size) en su lugar
	 */
	@Deprecated
	public static int[][] getSolucion() {
		return SOLUCION_EJEMPLO;
	}
}
