package interfaz;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import logica.Celda;
import logica.GeneradorTableros;
import logica.Pistas;
import logica.Tablero;
import logica.Validador;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VentanaJuego extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal menu = new MenuPrincipal();
					menu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JButton[][] botones;
	private Tablero tablero; // Lógica
	private int[][] solucion;
	private int size;
	
	private JPanel panelFilas;
	private JPanel panelColumnas;
	private JLabel lblTiempo;
	private JLabel lblPuntuacion;
	private long tiempoInicio;
	private int puntuacion;
	private Timer timer;

	/**
	 * Create the frame.
	 */
	public VentanaJuego() {
		this(5); // Tamaño por defecto
	}
	
	public VentanaJuego(int size) {
		this.size = size;
		this.botones = new JButton[size][size];
		this.tablero = new Tablero(size);
		this.solucion = GeneradorTableros.generarTablero(size);
		this.tiempoInicio = System.currentTimeMillis();
		this.puntuacion = 0;
		
		setTitle("Nanograma - " + size + "x" + size);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400 + size * 20, 350 + size * 20);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(240, 240, 240));
		
		crearInterfaz();
		iniciarTimer();
	}
	
	private void crearInterfaz() {
		// Panel superior con información
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBounds(10, 10, getWidth() - 20, 40);
		panelSuperior.setBackground(new Color(240, 240, 240));
		getContentPane().add(panelSuperior);
		panelSuperior.setLayout(new GridBagLayout());
		
		// Tiempo
		lblTiempo = new JLabel("Tiempo: 00:00");
		lblTiempo.setFont(new Font("Arial", Font.BOLD, 14));
		lblTiempo.setForeground(new Color(50, 50, 50));
		GridBagConstraints gbcTiempo = new GridBagConstraints();
		gbcTiempo.gridx = 0;
		gbcTiempo.gridy = 0;
		gbcTiempo.anchor = GridBagConstraints.WEST;
		gbcTiempo.insets = new Insets(5, 5, 5, 5);
		panelSuperior.add(lblTiempo, gbcTiempo);
		
		// Puntuacion
		lblPuntuacion = new JLabel("Puntuacion: 0");
		lblPuntuacion.setFont(new Font("Arial", Font.BOLD, 14));
		lblPuntuacion.setForeground(new Color(50, 50, 50));
		GridBagConstraints gbcPuntuacion = new GridBagConstraints();
		gbcPuntuacion.gridx = 1;
		gbcPuntuacion.gridy = 0;
		gbcPuntuacion.anchor = GridBagConstraints.CENTER;
		gbcPuntuacion.insets = new Insets(5, 5, 5, 5);
		panelSuperior.add(lblPuntuacion, gbcPuntuacion);
		
		// Boton de menu
		JButton btnMenu = new JButton("Menu Principal");
		btnMenu.setFont(new Font("Arial", Font.PLAIN, 12));
		btnMenu.setBackground(new Color(100, 100, 100));
		btnMenu.setForeground(Color.WHITE);
		btnMenu.setFocusPainted(false);
		GridBagConstraints gbcMenu = new GridBagConstraints();
		gbcMenu.gridx = 2;
		gbcMenu.gridy = 0;
		gbcMenu.anchor = GridBagConstraints.EAST;
		gbcMenu.insets = new Insets(5, 5, 5, 5);
		panelSuperior.add(btnMenu, gbcMenu);
		
		btnMenu.addActionListener(e -> {
			this.dispose();
			MenuPrincipal menu = new MenuPrincipal();
			menu.setVisible(true);
		});
		
		// Panel de pistas de filas
		panelFilas = new JPanel();
		panelFilas.setBounds(10, 90, 80, size * 30);
		panelFilas.setBackground(new Color(220, 220, 220));
		panelFilas.setLayout(new GridLayout(size, 1, 2, 2));
		getContentPane().add(panelFilas);
		
		// Panel de pistas de columnas
		panelColumnas = new JPanel();
		panelColumnas.setBounds(90, 60, size * 30, 30);
		panelColumnas.setBackground(new Color(220, 220, 220));
		panelColumnas.setLayout(new GridLayout(1, size, 2, 2));
		getContentPane().add(panelColumnas);
		
		// Panel de la grilla de juego
		JPanel panelGrilla = new JPanel();
		panelGrilla.setBounds(90, 90, size * 30, size * 30);
		panelGrilla.setBackground(Color.WHITE);
		panelGrilla.setLayout(new GridLayout(size, size, 1, 1));
		getContentPane().add(panelGrilla);
		
		// Panel de controles
		JPanel panelControles = new JPanel();
		panelControles.setBounds(10, 90 + size * 30 + 10, getWidth() - 20, 50);
		panelControles.setBackground(new Color(240, 240, 240));
		getContentPane().add(panelControles);
		
		JButton btnComprobar = new JButton("Comprobar Solucion");
		btnComprobar.setFont(new Font("Arial", Font.BOLD, 14));
		btnComprobar.setBackground(new Color(50, 150, 50));
		btnComprobar.setForeground(Color.WHITE);
		btnComprobar.setFocusPainted(false);
		panelControles.add(btnComprobar);
		
		JButton btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.setFont(new Font("Arial", Font.BOLD, 14));
		btnReiniciar.setBackground(new Color(200, 100, 50));
		btnReiniciar.setForeground(Color.WHITE);
		btnReiniciar.setFocusPainted(false);
		panelControles.add(btnReiniciar);
		
		JButton btnNuevo = new JButton("Nuevo Juego");
		btnNuevo.setFont(new Font("Arial", Font.BOLD, 14));
		btnNuevo.setBackground(new Color(50, 100, 200));
		btnNuevo.setForeground(Color.WHITE);
		btnNuevo.setFocusPainted(false);
		panelControles.add(btnNuevo);
		
		// Event listeners para los botones
		btnComprobar.addActionListener(e -> comprobarSolucion());
		btnReiniciar.addActionListener(e -> reiniciarJuego());
		btnNuevo.addActionListener(e -> nuevoJuego());
		
		// Crear los botones de la grilla
		crearBotonesGrilla(panelGrilla);
		
		// Actualizar las pistas
		actualizarPistas(solucion);
	}
	
	private void crearBotonesGrilla(JPanel panelGrilla) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				JButton btn = new JButton();
				btn.setFont(new Font("Arial", Font.BOLD, 12));
				btn.setFocusPainted(false);
				botones[i][j] = btn;
				
				// Arranca en blanco
				actualizarBoton(btn, tablero.getCelda(i, j));
				
				// Cuando se clickea
				final int fila = i;
				final int col = j;
				btn.addActionListener(e -> {
					tablero.cambiarEstado(fila, col); // Lógica
					actualizarBoton(btn, tablero.getCelda(fila, col)); // Vista
					actualizarPuntuacion();
				});
				
				panelGrilla.add(btn);
			}
		}
	}
	
	// Actualiza el estado de las celdas
	private void actualizarBoton(JButton boton, Celda celda) {
		if (celda.getEstado() == Celda.BLANCO) {
			boton.setBackground(Color.LIGHT_GRAY);
			boton.setText("");
		} else if (celda.getEstado() == Celda.NEGRO) {
			boton.setBackground(Color.BLACK);
			boton.setText("");
		} else if (celda.getEstado() == Celda.X) {
			boton.setBackground(Color.WHITE);
			boton.setText("X");
		}
	}
	
	private void actualizarPistas(int[][] solucion) {
		panelFilas.removeAll();
		panelColumnas.removeAll();
		
		List<List<Integer>> pistasFilas = Pistas.generarPistasFilas(solucion);
		List<List<Integer>> pistasColumnas = Pistas.generarPistasColumnas(solucion);
		
		// Panel de filas
		for (int i = 0; i < size; i++) {
			List<Integer> fila = pistasFilas.get(i);
			String texto = fila.stream()
								.map(String::valueOf)
								.reduce((a,b) -> a + " " + b)
								.orElse("0");
			JLabel lblFila = new JLabel(texto, SwingConstants.CENTER);
			lblFila.setFont(new Font("Arial", Font.BOLD, 10));
			lblFila.setBackground(Color.WHITE);
			lblFila.setOpaque(true);
			panelFilas.add(lblFila);
		}
		
		// Panel de columnas
	    for (int i = 0; i < size; i++) {
	    	List<Integer> columna = pistasColumnas.get(i);
	        String texto = columna.stream()
	                              .map(String::valueOf)
	                              .reduce((a,b) -> a + " " + b)
	                              .orElse("0");
	        JLabel lblCol = new JLabel(texto, SwingConstants.CENTER);
	        lblCol.setFont(new Font("Arial", Font.BOLD, 10));
	        lblCol.setBackground(Color.WHITE);
	        lblCol.setOpaque(true);
	        panelColumnas.add(lblCol);
	    }
	    
	    panelFilas.revalidate();
	    panelFilas.repaint();
	    panelColumnas.revalidate();
	    panelColumnas.repaint();
	}
	
	private void comprobarSolucion() {
		if (Validador.esCorrecto(tablero, solucion)) {
			timer.stop();
			long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
			int segundos = (int) (tiempoTotal / 1000);
			int minutos = segundos / 60;
			segundos = segundos % 60;
			
			// Calcular puntuacion basada en tiempo y tamano
			int puntosTiempo = Math.max(0, 1000 - (int)(tiempoTotal / 1000));
			int puntosTamano = size * 100;
			int puntuacionFinal = puntosTiempo + puntosTamano + puntuacion;
			
			JOptionPane.showMessageDialog(this, 
					"Felicidades! Has completado el Nanograma\n" +
					"Tiempo: " + String.format("%02d:%02d", minutos, segundos) + "\n" +
					"Puntuacion: " + puntuacionFinal,
					"Victoria!", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, 
					"La solucion aun no es correcta. Sigue intentando.", 
		            "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void reiniciarJuego() {
		int opcion = JOptionPane.showConfirmDialog(this, 
				"Estas seguro de que quieres reiniciar el juego?", 
				"Reiniciar", JOptionPane.YES_NO_OPTION);
		
		if (opcion == JOptionPane.YES_OPTION) {
			tablero = new Tablero(size);
			puntuacion = 0;
			tiempoInicio = System.currentTimeMillis();
			
			// Limpiar todos los botones
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					actualizarBoton(botones[i][j], tablero.getCelda(i, j));
				}
			}
			
			actualizarPuntuacion();
		}
	}
	
	private void nuevoJuego() {
		int opcion = JOptionPane.showConfirmDialog(this, 
				"Estas seguro de que quieres empezar un nuevo juego?", 
				"Nuevo Juego", JOptionPane.YES_NO_OPTION);
		
		if (opcion == JOptionPane.YES_OPTION) {
			// Generar un nuevo tablero con la misma dificultad
			this.solucion = GeneradorTableros.generarTablero(size);
			this.tablero = new Tablero(size);
			this.puntuacion = 0;
			this.tiempoInicio = System.currentTimeMillis();
			
			// Limpiar todos los botones
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					actualizarBoton(botones[i][j], tablero.getCelda(i, j));
				}
			}
			
			// Actualizar las pistas con el nuevo tablero
			actualizarPistas(solucion);
			actualizarPuntuacion();
		}
	}
	
	private void actualizarPuntuacion() {
		// Calcular puntuación basada en celdas correctas
		int celdasCorrectas = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int estadoJugador = tablero.getCelda(i, j).getEstado();
				int esperado = solucion[i][j];
				
				if ((esperado == 1 && estadoJugador == Celda.NEGRO) ||
					(esperado == 0 && estadoJugador != Celda.NEGRO)) {
					celdasCorrectas++;
				}
			}
		}
		
		puntuacion = celdasCorrectas * 10;
		lblPuntuacion.setText("Puntuacion: " + puntuacion);
	}
	
	private void iniciarTimer() {
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				long tiempoActual = System.currentTimeMillis();
				long tiempoTranscurrido = tiempoActual - tiempoInicio;
				int segundos = (int) (tiempoTranscurrido / 1000);
				int minutos = segundos / 60;
				segundos = segundos % 60;
				
				lblTiempo.setText("Tiempo: " + String.format("%02d:%02d", minutos, segundos));
			}
		});
		timer.start();
	}
}