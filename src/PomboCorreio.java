import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class PomboCorreio extends JFrame {

	private JPanel contentPane;
	public JLabel imagePassaro;
	public JLabel placa;
	public JLabel textoPlaca;

	private JTextField textField;
	private JLabel lblMenu;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PomboCorreio frame = new PomboCorreio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 */
	public PomboCorreio() throws InterruptedException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 470);

		
		placa = new JLabel("");
		placa.setBounds(0, 300, 64, 64);
		placa.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/placa.png"));

		
		imagePassaro = new JLabel("");
		imagePassaro.setBounds(0, 150, 120, 200);
		imagePassaro.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/teste.png"));

	
		
		// cria Jpanel com uma imagem de fundo
		contentPane =  new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Toolkit toolkit = Toolkit.getDefaultToolkit();

				Image image = toolkit.getImage(System.getProperty("user.dir") + "/src/images/planoDeFundo.png");		

				g.drawImage(image, 0, 0, null);
			}
		};


		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		textoPlaca = new JLabel("");

		textoPlaca.setHorizontalAlignment(SwingConstants.CENTER);
		textoPlaca.setBounds(4, 312, 60, 16);
		getContentPane().add(textoPlaca);
		textoPlaca.setForeground(Color.white);
		textoPlaca.setText("10");
		getContentPane().add(placa);
		getContentPane().add(imagePassaro);
		System.out.println("|||||||||");
		//	paraFrente();

		/*
		 * Inicianliza Buffer, Semáforos, Objetos Pessoas e pombo.
		 */
		
		
		Buffer buffer_usado = new Buffer(textoPlaca);
		buffer_usado.setMaximoCaixa(10);
		buffer_usado.setPassaroVoando(false);
		Semaphore s1 = new Semaphore(1);
		Semaphore s2 = new Semaphore(0);
		Semaphore n = new Semaphore(10);
		Semaphore mutex = new Semaphore(1);
		Semaphore aptoViagem = new Semaphore(0);
		Semaphore semaforoCaixaPostal = new Semaphore(0);

		Random gerador = new Random(); // variável que auxilia na geração de números randômicos.

		//Construindo Pombo
		int tempoCarga=8, tempoDescarga=5, tempoViagem = gerador.nextInt((20 - 15) + 1) + 15;
		Pombo pb = new Pombo(aptoViagem, buffer_usado, semaforoCaixaPostal, imagePassaro,tempoCarga, tempoDescarga, tempoViagem);
		
		textField = new JTextField();
		textField.setBounds(820, 57, 114, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblMenu = new JLabel("MENU");
		lblMenu.setBounds(970, 26, 70, 15);
		contentPane.add(lblMenu);
		
		Thread pombo = new Thread(pb);
		pombo.start(); // Inicia Thread Pombo (ver classe pombo)

		//Inicializa 10 Threads Produtoras
		for (int i=0;i<10;i++){
			Pessoa p = new Pessoa(i, buffer_usado, gerador.nextInt((20 - 15) + 1) + 15, semaforoCaixaPostal, s1, s2, n, mutex, aptoViagem);
			Thread p1 = new Thread(p);
			p1.start(); // inicia Thread Pessoa (ver Classe pessoa)
		}

	}
}
