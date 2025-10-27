package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuPrincipal extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuPrincipal frame = new MenuPrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public MenuPrincipal() {
        setTitle("Nanograma - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 240, 240));
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(240, 240, 240));
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.setLayout(new GridBagLayout());
        
        // Título
        JLabel titulo = new JLabel("NANOGRAMA");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(new Color(50, 50, 50));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.insets = new Insets(20, 10, 30, 10);
        panelPrincipal.add(titulo, gbcTitulo);
        
        // Subtitulo
        JLabel subtitulo = new JLabel("Selecciona la dificultad");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(100, 100, 100));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcSubtitulo = new GridBagConstraints();
        gbcSubtitulo.gridx = 0;
        gbcSubtitulo.gridy = 1;
        gbcSubtitulo.gridwidth = 2;
        gbcSubtitulo.insets = new Insets(0, 10, 20, 10);
        panelPrincipal.add(subtitulo, gbcSubtitulo);
        
        JButton btnFacil = new JButton("Facil (5x5)");
        btnFacil.setFont(new Font("Arial", Font.BOLD, 14));
        btnFacil.setBackground(new Color(100, 200, 100));
        btnFacil.setForeground(Color.WHITE);
        btnFacil.setFocusPainted(false);
        GridBagConstraints gbcFacil = new GridBagConstraints();
        gbcFacil.gridx = 0;
        gbcFacil.gridy = 2;
        gbcFacil.insets = new Insets(10, 10, 10, 10);
        gbcFacil.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(btnFacil, gbcFacil);
        
        JButton btnMedio = new JButton("Medio (7x7)");
        btnMedio.setFont(new Font("Arial", Font.BOLD, 14));
        btnMedio.setBackground(new Color(255, 165, 0));
        btnMedio.setForeground(Color.WHITE);
        btnMedio.setFocusPainted(false);
        GridBagConstraints gbcMedio = new GridBagConstraints();
        gbcMedio.gridx = 1;
        gbcMedio.gridy = 2;
        gbcMedio.insets = new Insets(10, 10, 10, 10);
        gbcMedio.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(btnMedio, gbcMedio);
        
        JButton btnDificil = new JButton("Dificil (10x10)");
        btnDificil.setFont(new Font("Arial", Font.BOLD, 14));
        btnDificil.setBackground(new Color(200, 50, 50));
        btnDificil.setForeground(Color.WHITE);
        btnDificil.setFocusPainted(false);
        GridBagConstraints gbcDificil = new GridBagConstraints();
        gbcDificil.gridx = 0;
        gbcDificil.gridy = 3;
        gbcDificil.insets = new Insets(10, 10, 10, 10);
        gbcDificil.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(btnDificil, gbcDificil);
        
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setBackground(new Color(100, 100, 100));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        GridBagConstraints gbcSalir = new GridBagConstraints();
        gbcSalir.gridx = 1;
        gbcSalir.gridy = 3;
        gbcSalir.insets = new Insets(10, 10, 10, 10);
        gbcSalir.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(btnSalir, gbcSalir);
        
        // Event listeners
        btnFacil.addActionListener(e -> iniciarJuego(5));
        btnMedio.addActionListener(e -> iniciarJuego(7));
        btnDificil.addActionListener(e -> iniciarJuego(10));
        btnSalir.addActionListener(e -> System.exit(0));
    }
    
    private void iniciarJuego(int size) {
        this.setVisible(false);
        VentanaJuego ventanaJuego = new VentanaJuego(size);
        ventanaJuego.setVisible(true);
    }
}
