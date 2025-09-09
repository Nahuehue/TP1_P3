import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NonogramaLogica {
	private int[][] solucion; // 0 = vacio, 1 = negro, 2 = X
	private int[][] estadoActual; // Estado actual del jugador
    private int[][] filaPistas; // Pistas de filas
    private int[][] columPistas; // Pistas de columnas
    private int tamanio;
    
    public NonogramaLogica(int tamanio) {
        this.tamanio = tamanio;
        this.solucion = new int[tamanio][tamanio];
        this.estadoActual = new int[tamanio][tamanio];
        this.filaPistas = new int[tamanio][];
        this.columPistas = new int[tamanio][];
        generarTablero();
    }
    
    // Genera un tablero aleatorio
    private void generarTablero() {
        // Generar solucion aleatoria
        Random rand = new Random();
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                solucion[i][j] = rand.nextBoolean() ? 1 : 0;
            }
        }
        
        // Generar pistas basadas en la solucion
        generarPistas();
    }
    
    // Genera las pistas numericas
    private void generarPistas() {
        // Pistas de filas
        for (int f = 0; f < tamanio; f++) {
            filaPistas[f] = generarPistasPorFila(solucion[f]);
        }
        
        // Pistas de columnas
        for (int j = 0; j < tamanio; j++) {
            int[] column = new int[tamanio];//me creo un array con la columna para recorrerla con el mismo metodo
            for (int i = 0; i < tamanio; i++) {
                column[i] = solucion[i][j];
            }
            columPistas[j] = generarPistasPorFila(column);
        }
    }
    
    // Genera pistas para una línea (fila o columna)
    private int[] generarPistasPorFila(int[] linea) {
        List<Integer> pistas = new ArrayList<>();
        int contador = 0;
        
        //si esta pintada de negro(valor = 1 ) incrementaos en 1
        for (int celda : linea) {
            if (celda == 1) {
                contador++;
            } else {
            	//si esta vacia reiniciamos el contador paraque en las pistas quede asi 1 1 3 etc
                if (contador > 0) {
                    pistas.add(contador);//so tocamon un blanco guardamos todo lo pintado
                    contador = 0;
                }
            }
        }
        //casos borde
        
        //si recorrimos todo y nos quedo el contador con algo lo guardamos
        if (contador > 0) {
            pistas.add(contador);
        }
        
        //si por alguna razon la fil o col esta vacia no guardamos nada
        if (pistas.isEmpty()) {
            pistas.add(0);
        }
        
        //casteamos de integer a una list de int
        int[] resultado = new int[pistas.size()];
        for (int k = 0; k < pistas.size(); k++) {
            resultado[k] = pistas.get(k);
        }
        return resultado;
    }
    
    // Acciones del jugador
    public void pintarCelda(int fil, int col) {
        if (esPosicionValida(fil, col)) {
            estadoActual[fil][col] = 1; // Negro
        }
    }
    
    public void marcarCelda(int fil, int col) {
        if (esPosicionValida(fil, col)) {
            estadoActual[fil][col] = 2; // X
        }
    }
    
    public void limpiarCelda(int fil, int col) {
        if (esPosicionValida(fil, col)) {
            estadoActual[fil][col] = 0; // Vacio
        }
    }
    
    // Verifica si la posicion es valida
    private boolean esPosicionValida(int fil, int col) {
        return fil >= 0 && fil < tamanio && col >= 0 && col < tamanio;
    }
    
    // Evalua la solucion del jugador
    public boolean esSolucion() {
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                if (solucion[i][j] == 1 && estadoActual[i][j] != 1) {
                    return false;
                }
                if (solucion[i][j] == 0 && estadoActual[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Getters
    public int[][] obtenerEstadoActual() { return estadoActual; }
    public int[][] obtenerSolucion() { return solucion.clone(); }
    public int[][] obtenerPistasFila() { return filaPistas; }
    public int[][] obtenerPistasColumna() { return columPistas; }
    public int obtenerTamanio() { return tamanio; }
    
    
    
    // Obtiene el estado de una celda
    public int obtenerEstadoCelda(int row, int col) {
        if (esPosicionValida(row, col)) {
            return estadoActual[row][col];
        }
        return -1;
    }
    
    // Reinicia el juego
    public void reiniciar() {
        estadoActual = new int[tamanio][tamanio];
    }
    
    // Genera un nuevo puzzle
    public void nuevoTablero() {
        solucion = new int[tamanio][tamanio];
        estadoActual = new int[tamanio][tamanio];
        generarTablero();
    }

}
