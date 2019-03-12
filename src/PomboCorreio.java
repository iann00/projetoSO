import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class PomboCorreio extends JFrame {

	private JPanel contentPane;
	public JLabel imagemVagao;

	Sprite vilao = new Sprite(8, 200, 300);
	ImageIcon fundo = new ImageIcon("src/ba2.png");
	boolean vai = true;		//ESSA VARIÁVEL PODERIA ESTAR NA CLASSE Sprite
	boolean volta = false;	
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

		imagemVagao = new JLabel("");
		imagemVagao.setBounds(0, 0, 120, 200);
		imagemVagao.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/teste.png"));

		// cria Jpanel com uma imagem de fundo
		contentPane =  new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Toolkit toolkit = Toolkit.getDefaultToolkit();

				Image image = toolkit.getImage(System.getProperty("user.dir") + "/src/ba2.png");		

				g.drawImage(image, 0, 0, null);
			}
		};


		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		getContentPane().add(imagemVagao);
		System.out.println("|||||||||");
		//	paraFrente();

		 Buffer buffer_usado = new Buffer();
		 Semaphore s1 = new Semaphore(1);
		 Semaphore s2 = new Semaphore(0);
		 Semaphore n = new Semaphore(5);
		 Semaphore mutex = new Semaphore(1);
		 Semaphore aptoViagem = new Semaphore(0);

		 Pombo pb = new Pombo(aptoViagem);
         Thread pombo = new Thread(pb);
         pombo.start();
		 
		 
		 //Inicializa 10 Threads Produtoras
         for (int i=0;i<10;i++){
             Pessoa p = new Pessoa(buffer_usado, s1, s2, n, mutex, aptoViagem);
             Thread p1 = new Thread(p);
             p1.start();
         }

		
		
		/*
		 * 
		Pombo p = new Pombo();
		p.setComponente(imagemVagao);
		Thread threadVagao = new Thread(p);
		threadVagao.start();

		*/
	}



	void paraFrente() throws InterruptedException {
		System.err.println("eixo x"+imagemVagao.getX());
		imagemVagao.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/teste.png"));
		imagemVagao.repaint();



	}

}
