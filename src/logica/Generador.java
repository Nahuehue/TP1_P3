package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generador {
    
    private static final Random random = new Random();

    public static int[][] generarTablero(int size) {
        return generarTableroAleatorio(size);
    }
    
    //Genera un tablero aleatorio

    private static int[][] generarTableroAleatorio(int size) {
        int[][] tablero = new int[size][size];
        double densidad = 0.25 + random.nextDouble() * 0.3; // 25% a 55%      
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) 
                tablero[i][j] = random.nextDouble() < densidad ? 1 : 0;            
        }                                 
        return comprobarFilasYColumnasVacias(size, tablero);
    }
    
    // Asegurar que no haya filas o columnas completamente vacías
	private static int[][] comprobarFilasYColumnasVacias(int size, int[][] tablero) {
		for (int i = 0; i < size; i++) {
            boolean filaVacia = true;
            for (int j = 0; j < size; j++) {
                if (tablero[i][j] == 1) 
                    filaVacia = false;                                 
            }
            if (filaVacia) 
                tablero[i][random.nextInt(size)] = 1;            
        }
		for (int j = 0; j < size; j++) {
            boolean colVacia = true;
            for (int i = 0; i < size; i++) {
                if (tablero[i][j] == 1) 
                    colVacia = false;              
            }
            if (colVacia) 
                tablero[random.nextInt(size)][j] = 1;            
        }
		return tablero;
	}
    
    //Genera múltiples tableros para diferentes dificultades
    public static List<int[][]> generarTableros(int cantidad, int size) {
        List<int[][]> tableros = new ArrayList<>();       
        for (int i = 0; i < cantidad; i++) 
            tableros.add(generarTablero(size));       
        return tableros;
    }
    

}
