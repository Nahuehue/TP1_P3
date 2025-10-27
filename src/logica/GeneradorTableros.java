package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorTableros {
    
    private static final Random random = new Random();
    
    /**
     * Genera un tablero de nanograma aleatorio
     * @param size Tamaño del tablero (5x5, 7x7, etc.)
     * @return Tablero aleatorio
     */
    public static int[][] generarTablero(int size) {
        return generarTableroAleatorio(size);
    }
    
    /**
     * Genera un tablero aleatorio con patrones más interesantes
     */
    private static int[][] generarTableroAleatorio(int size) {
        int[][] tablero = new int[size][size];
        
        // Generar patrones más variados
        int patron = random.nextInt(4);
        
        switch (patron) {
            case 0:
                // Patron de densidad variable por filas
                for (int i = 0; i < size; i++) {
                    double densidadFila = 0.2 + random.nextDouble() * 0.4; // 20% a 60%
                    for (int j = 0; j < size; j++) {
                        tablero[i][j] = random.nextDouble() < densidadFila ? 1 : 0;
                    }
                }
                break;
                
            case 1:
                // Patron de densidad variable por columnas
                for (int j = 0; j < size; j++) {
                    double densidadCol = 0.2 + random.nextDouble() * 0.4; // 20% a 60%
                    for (int i = 0; i < size; i++) {
                        tablero[i][j] = random.nextDouble() < densidadCol ? 1 : 0;
                    }
                }
                break;
                
            case 2:
                // Patron de bloques aleatorios
                int numBloques = 3 + random.nextInt(5); // 3 a 7 bloques
                for (int b = 0; b < numBloques; b++) {
                    int filaInicio = random.nextInt(size);
                    int colInicio = random.nextInt(size);
                    int alto = 1 + random.nextInt(Math.min(3, size - filaInicio));
                    int ancho = 1 + random.nextInt(Math.min(3, size - colInicio));
                    
                    for (int i = filaInicio; i < filaInicio + alto && i < size; i++) {
                        for (int j = colInicio; j < colInicio + ancho && j < size; j++) {
                            tablero[i][j] = 1;
                        }
                    }
                }
                break;
                
            case 3:
            default:
                // Patron completamente aleatorio con densidad fija
                double densidad = 0.25 + random.nextDouble() * 0.3; // 25% a 55%
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        tablero[i][j] = random.nextDouble() < densidad ? 1 : 0;
                    }
                }
                break;
        }
        
        // Asegurar que no haya filas o columnas completamente vacías
        for (int i = 0; i < size; i++) {
            boolean filaVacia = true;
            for (int j = 0; j < size; j++) {
                if (tablero[i][j] == 1) {
                    filaVacia = false;
                    break;
                }
            }
            if (filaVacia) {
                tablero[i][random.nextInt(size)] = 1;
            }
        }
        
        for (int j = 0; j < size; j++) {
            boolean colVacia = true;
            for (int i = 0; i < size; i++) {
                if (tablero[i][j] == 1) {
                    colVacia = false;
                    break;
                }
            }
            if (colVacia) {
                tablero[random.nextInt(size)][j] = 1;
            }
        }
        
        return tablero;
    }
    
    /**
     * Verifica si un tablero tiene solución única
     */
    private static boolean tieneSolucionUnica(int[][] tablero) {
        List<List<Integer>> pistasFilas = Pistas.generarPistasFilas(tablero);
        List<List<Integer>> pistasColumnas = Pistas.generarPistasColumnas(tablero);
        
        // Crear un tablero vacío para resolver
        int size = tablero.length;
        int[][] tableroVacio = new int[size][size];
        
        // Intentar resolver el puzzle
        int soluciones = contarSoluciones(tableroVacio, pistasFilas, pistasColumnas, 0, 0);
        
        return soluciones == 1;
    }
    
    /**
     * Cuenta cuántas soluciones tiene un puzzle
     */
    private static int contarSoluciones(int[][] tablero, List<List<Integer>> pistasFilas, 
                                      List<List<Integer>> pistasColumnas, int fila, int col) {
        int size = tablero.length;
        
        if (fila >= size) {
            return 1; // Solución encontrada
        }
        
        int siguienteFila = fila;
        int siguienteCol = col + 1;
        if (siguienteCol >= size) {
            siguienteFila++;
            siguienteCol = 0;
        }
        
        int soluciones = 0;
        
        // Probar con celda negra
        tablero[fila][col] = 1;
        if (esValidaHastaAhora(tablero, pistasFilas, pistasColumnas, fila, col)) {
            soluciones += contarSoluciones(tablero, pistasFilas, pistasColumnas, siguienteFila, siguienteCol);
        }
        
        // Probar con celda blanca
        tablero[fila][col] = 0;
        if (esValidaHastaAhora(tablero, pistasFilas, pistasColumnas, fila, col)) {
            soluciones += contarSoluciones(tablero, pistasFilas, pistasColumnas, siguienteFila, siguienteCol);
        }
        
        // Limpiar
        tablero[fila][col] = 0;
        
        return soluciones;
    }
    
    /**
     * Verifica si el tablero es válido hasta la posición actual
     */
    private static boolean esValidaHastaAhora(int[][] tablero, List<List<Integer>> pistasFilas,
                                            List<List<Integer>> pistasColumnas, int fila, int col) {
        // Verificar fila actual
        if (col == tablero.length - 1) { // Última columna de la fila
            List<Integer> pistaFila = generarPistaLinea(tablero[fila]);
            if (!pistaFila.equals(pistasFilas.get(fila))) {
                return false;
            }
        }
        
        // Verificar columna actual
        if (fila == tablero.length - 1) { // Última fila de la columna
            int[] columna = new int[tablero.length];
            for (int i = 0; i < tablero.length; i++) {
                columna[i] = tablero[i][col];
            }
            List<Integer> pistaCol = generarPistaLinea(columna);
            if (!pistaCol.equals(pistasColumnas.get(col))) {
                return false;
            }
        }
        
        return true;
    }
    
    
    /**
     * Genera múltiples tableros para diferentes dificultades
     */
    public static List<int[][]> generarTableros(int cantidad, int size) {
        List<int[][]> tableros = new ArrayList<>();
        
        for (int i = 0; i < cantidad; i++) {
            tableros.add(generarTablero(size));
        }
        
        return tableros;
    }
    
    /**
     * Genera la pista para una linea (fila o columna) - copia del método de Pistas
     */
    private static List<Integer> generarPistaLinea(int[] linea) {
        List<Integer> pista = new ArrayList<>();
        int contador = 0;
        
        for (int celda : linea) {
            if (celda == 1) {
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
