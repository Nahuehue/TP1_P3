package logica;

import java.util.ArrayList;
import java.util.List;

public class Pistas {
	// Genera las pistas para todas las filas
	public static List<List<Integer>> generarPistasFilas(int[][] solucion) {
		
		List<List<Integer>> pistasFilas = new ArrayList<>();
		
		for( int i = 0; i < solucion.length; i++) {
			pistasFilas.add(generarPistaLinea(solucion[i]));
		}
		return pistasFilas;
	}
	
	// Genera las pistas para todas las columnas
	public static List<List<Integer>> generarPistasColumnas(int[][] solucion) {
		int n = solucion.length;
		List<List<Integer>> pistasColumnas = new ArrayList<>();
		
		for (int col = 0; col < n; col++) {
			int[] columna = new int[n];
			for (int fila = 0; fila < n; fila++) {
				columna[fila] = solucion[fila][col];
			}
			pistasColumnas.add(generarPistaLinea(columna));
		}
		return pistasColumnas;
	}
	
	// Genera la pista para una linea (fila o columna)
	public static List<Integer> generarPistaLinea(int[] linea) {
		List<Integer> pista = new ArrayList<>();
		int contador = 0;
		
		for (int celda : linea) {
			if ( celda == 1) {
				contador++;
			} else {
				if (contador > 0) {
					pista.add(contador);
					contador = 0;
				}
			}
		}
		if (contador > 0) {
			pista.add(contador);
		}
		
		// Si la linea no tiene negros, la pista es vacía o "0"
		if (pista.isEmpty()) {
			pista.add(0);
		}
		
		return pista;
	}
}
