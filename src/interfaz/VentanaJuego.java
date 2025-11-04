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
import logica.Generador;
import logica.Pistas;
import logica.Tablero;
import logica.Validador;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.Random;

public class VentanaJuego extends JFrame {

	private static final long serialVersionUID = 1L;

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
	private JPanel panelGrillaDebug = new JPanel();;
	private JButton[][] botonesDebug;
	private boolean isDebugOn = false;
	
	private JPanel panelGrillaAyuda = new JPanel();;
	private JButton[][] botonesAyuda;
	private JButton[][] botones;
	private Tablero tablero; // Lógica
	private int[][] solucion;
	private int size;
	private List<List<Integer>> ayudas = new ArrayList<>();
	private int ayudasDadas = 0;
	
	private JPanel panelFilas;
	private JPanel panelColumnas;
	private JLabel lblTiempo;
	private JLabel lblPuntuacion;
	private JLabel lblAyudas;
	private long tiempoInicio;
	private int puntuacion;
	private Timer timer;

	public VentanaJuego() {
		this(5); // Tamaño por defecto
	}
	
	public VentanaJuego(int size) {
		this.size = size;
		this.botones = new JButton[size][size];
		this.tablero = new Tablero(size);
		this.solucion = Generador.generarTablero(size);
		this.tiempoInicio = System.currentTimeMillis();
		this.puntuacion = 0;
		this.botonesDebug = new JButton[size][size];
		this.botonesAyuda = new JButton[size][size];
		
		setTitle("Nanograma - " + size + "x" + size);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (size == 5) {
			if (isDebugOn)
				setBounds(100, 100, 550 + size * 55, 400 + size * 30);
			else
				setBounds(100, 100, 500 + size * 20, 400 + size * 20);
		}
		if (size == 7) {
			if (isDebugOn)
				setBounds(100, 100, 700 + size * 55, 400 + size * 30);
			else
				setBounds(100, 100, 650 + size * 20, 400 + size * 20);
		}
		if (size == 10) {
			if (isDebugOn)
				setBounds(100, 100, 900 + size * 55, 400 + size * 30);
			else
				setBounds(100, 100, 800 + size * 20, 400 + size * 20);
		}
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
		
		// Ayudas
		lblAyudas = new JLabel("Ayudas usadas:" + ayudasDadas + "/" + size);
		lblAyudas.setFont(new Font("Arial", Font.BOLD, 14));
		lblAyudas.setForeground(new Color(50, 50, 50));
		GridBagConstraints gbcAyudas = new GridBagConstraints();
		gbcAyudas.gridx = 50;
		gbcAyudas.gridy = 0;
		gbcAyudas.anchor = GridBagConstraints.CENTER;
		gbcAyudas.insets = new Insets(5, 5, 5, 5);
		panelSuperior.add(lblAyudas, gbcAyudas);
		
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
		panelFilas.setBounds(10, 100, 80, size * 40);
		panelFilas.setBackground(new Color(220, 220, 220));
		panelFilas.setLayout(new GridLayout(size, 1, 2, 2));
		getContentPane().add(panelFilas);
		
		// Panel de pistas de columnas
		panelColumnas = new JPanel();
		panelColumnas.setBounds(90, 60, size * 40, 40);
		panelColumnas.setBackground(new Color(220, 220, 220));
		panelColumnas.setLayout(new GridLayout(1, size, 2, 2));
		getContentPane().add(panelColumnas);
		
		// Panel de la grilla de juego
		JPanel panelGrilla = new JPanel();
		panelGrilla.setBounds(90, 100, size * 40, size * 40);
		panelGrilla.setBackground(Color.WHITE);
		panelGrilla.setLayout(new GridLayout(size, size, 1, 1));
		getContentPane().add(panelGrilla);
		
		// Panel de controles
		JPanel panelControles = new JPanel();
		if (size == 5)
			panelControles.setBounds(0, 110 + size * 40 , getWidth() + 40, 50);
		if (size == 7)
			panelControles.setBounds(0, 110 + size * 40 , getWidth(), 50);
		if (size == 10)
			panelControles.setBounds(0, 110 + size * 40 , getWidth() - 60, 50);
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
		
		JButton btnPista = new JButton("Pista");
		btnPista.setFont(new Font("Arial", Font.BOLD, 14));
		btnPista.setBackground(new Color(50, 100, 200));
		btnPista.setForeground(Color.WHITE);
		btnPista.setFocusPainted(false);
		panelControles.add(btnPista);
		
		// Event listeners para los botones
		btnComprobar.addActionListener(e -> comprobarSolucion());
		btnReiniciar.addActionListener(e -> reiniciarJuego());
		btnNuevo.addActionListener(e -> nuevoJuego());
		btnPista.addActionListener(e -> pedidoDeAyuda());		
		
		// Crear los botones de la grilla
		crearBotonesGrilla(panelGrilla);		
		
		if (isDebugOn)
			crearGrillaDebug();
		
		// Actualizar las pistas
		actualizarPistas(solucion);
		
		encontrarAyudasRandom();
		
	}
	
	private void encontrarAyudasRandom() { 
		ayudasDadas = 0;
		actualizarAyudas();	
		ayudas.removeAll(ayudas);
		Random random = new Random();
		int solucionRandom = 0;
		crearGrillaAyuda();
		int cantAyudasEncontradas = 0;		
		while (cantAyudasEncontradas < size) {
			int random1 = random.nextInt(size);
			int random2 = random.nextInt(size);
			solucionRandom = solucion[random1][random2];
			if (solucionRandom == 1 && ayudas.size() == 0) {
				List<Integer> subLista = new ArrayList<>();
				subLista.add(random1);
				subLista.add(random2);
				ayudas.add(subLista);
				cantAyudasEncontradas++;	
			}
			if (solucionRandom == 1) {	
				boolean acum = false;
				for (int i = 0 ; i < ayudas.size() ; i++) {
					if(ayudas.get(i).get(0) == random1 && ayudas.get(i).get(1) == random2) 
						acum = true;							
				}
				if (acum == false) {
					List<Integer> subLista = new ArrayList<>();
					subLista.add(random1);
					subLista.add(random2);
					ayudas.add(subLista);
					cantAyudasEncontradas++;
				}
			}
		}
		
	}

	private void pedidoDeAyuda() {
		if (ayudasDadas < size) {
			List<Integer> lista = new ArrayList<>();
			lista = ayudas.get(ayudasDadas);
			botonesAyuda[lista.get(0)][lista.get(1)].setBackground(Color.BLACK);
			ayudasDadas++;
			actualizarAyudas();
		}
	}
	
	private void crearGrillaAyuda() { 
		panelGrillaAyuda.removeAll();
		//120 en vez de 90 para que este separado al menos 1 cuadrito de la grilla del juego, 
		//y size*30 para que se adapte a los distintos tamaños grilla	
		panelGrillaAyuda.setBounds(130 + (size * 40), 100, size * 40, size * 40);
		panelGrillaAyuda.setBackground(Color.WHITE);
		panelGrillaAyuda.setLayout(new GridLayout(size, size, 1, 1));
		getContentPane().add(panelGrillaAyuda);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {	
				JButton btn = new JButton();
				btn.setFont(new Font("Arial", Font.BOLD, 12));
				btn.setFocusPainted(false);				
				btn.setBackground(Color.WHITE);
				botonesAyuda[i][j] = btn;
				panelGrillaAyuda.add(btn);				
			}
		}
	}
	
	private void actualizarAyudas() {
		lblAyudas.setText("Ayudas usadas:" + ayudasDadas + "/" + size);
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
			boton.setBackground(Color.WHITE);
			boton.setText("");
		} else if (celda.getEstado() == Celda.NEGRO) {
			boton.setBackground(Color.BLACK);
			boton.setText("");
		} else if (celda.getEstado() == Celda.X) {
			boton.setBackground(Color.WHITE);
			boton.setText("x");
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
	                              .orElse("0") + "\n";
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
			
			limpiarBotones();		
			actualizarPuntuacion();
		}
	}
	
	private void nuevoJuego() {
		int opcion = JOptionPane.showConfirmDialog(this, 
				"Estas seguro de que quieres empezar un nuevo juego?", 
				"Nuevo Juego", JOptionPane.YES_NO_OPTION);
		
		if (opcion == JOptionPane.YES_OPTION) {
			// Generar un nuevo tablero con la misma dificultad
			this.solucion = Generador.generarTablero(size);
			this.tablero = new Tablero(size);
			this.puntuacion = 0;
			this.tiempoInicio = System.currentTimeMillis();
			
			limpiarBotones();
			
			// Actualizar las pistas con el nuevo tablero
			actualizarPistas(solucion);
			actualizarPuntuacion();
			// Actualizar las soluciones del modo debug
			if (isDebugOn) {
				limpiarBotonesDebug();
		    	actualizarSolucionesDebug(solucion);
		    }
			encontrarAyudasRandom();
		}
	}

	private void limpiarBotones() {
		// Limpiar el estado de todos los botones
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				botones[i][j].setBackground(Color.LIGHT_GRAY);
				botones[i][j].setText("");
			}
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
	
		//---------------------------------------------------------------------------------------------------------------
		//--------------------------------------------- DEBUG -----------------------------------------------------------
		//---------------------------------------------------------------------------------------------------------------
		private void crearGrillaDebug() { 
			//120 en vez de 90 para que este separado al menos 1 cuadrito de la grilla del juego, 
			//y size*30 para que se adapte a los distintos tamaños grilla
			if (size == 5) {				
				panelGrillaDebug.setBounds(320 + (size * 50), 100, size * 40, size * 40);
			}
			if (size == 7) {
				panelGrillaDebug.setBounds(380 + (size * 50), 100, size * 40, size * 40);
			}
			if (size == 10) {
				panelGrillaDebug.setBounds(420 + (size * 55), 100, size * 40, size * 40);
			}
			panelGrillaDebug.setBackground(Color.WHITE);
			panelGrillaDebug.setLayout(new GridLayout(size, size, 1, 1));
			getContentPane().add(panelGrillaDebug);
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {	
					JButton btn = new JButton();
					btn.setFont(new Font("Arial", Font.BOLD, 12));
					btn.setFocusPainted(false);				
					if (solucion[i][j] == 1) 
						btn.setBackground(Color.BLACK);				
					else 
						btn.setBackground(Color.WHITE);
					botonesDebug[i][j] = btn;
					panelGrillaDebug.add(btn);				
				}
			}
		}
		
		private void actualizarSolucionesDebug(int [][] solucion) {
			panelGrillaDebug.removeAll();
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {	
					JButton btn = new JButton();
					btn.setFont(new Font("Arial", Font.BOLD, 12));
					btn.setFocusPainted(false);				
					if (solucion[i][j] == 1) 
						btn.setBackground(Color.BLACK);				
					else 
						btn.setBackground(Color.WHITE);
					botonesDebug[i][j] = btn;
					panelGrillaDebug.add(btn);				
				}
			}
		}
		
		private void limpiarBotonesDebug() {
			// Limpiar el estado de todos los botones
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					botonesDebug[i][j].setBackground(Color.WHITE);
					botonesDebug[i][j].setText("");
				}
			}
		}
		
		//---------------------------------------------------------------------------------------------------------------
		//---------------------------------------------------------------------------------------------------------------
		//---------------------------------------------------------------------------------------------------------------
}