
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;

public class Tso extends JFrame{

	private JFrame frame;

	BufferedImage backBuffer;	
	int FPS = 30;
	int janelaW = 1200;
	int janelaH = 451;
	Sprite vilao = new Sprite(8, 200, 300);
	ImageIcon fundo = new ImageIcon("src/ba2.png");
	boolean vai = true;		//ESSA VARIÁVEL PODERIA ESTAR NA CLASSE Sprite
	boolean volta = false;	//ESSA VARIÁVEL PODERIA ESTAR NA CLASSE Sprite
	private JTextField textField;
	JLabel imagemVagao;
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tso window = new Tso();
					window.frame.setVisible(true);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Tso() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 451);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// cria Jpanel com uma imagem de fundo
		contentPane =  new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Toolkit toolkit = Toolkit.getDefaultToolkit();

				Image image = toolkit.getImage(System.getProperty("user.dir") + "/src/ba.png");		
				
				g.drawImage(image, 0, 0, null);
			}
		};


		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		System.out.println("passo y "+backBuffer);


		System.out.println("IN "+vai);
		int i;
		if(vai==true){
			for(i=0; i< 8; i++){
				vilao.cenas[i] = new ImageIcon("src/image/passaro/tile00"+i+".png");
			}
		}else{
			System.out.println("caiu no if");
			for(i=0;i<8;i++){
				vilao.cenas[i] = new ImageIcon("src/image/passaro/volta/tile00"+i+".png");
			}
		}
	}
	
	public void mover(){
		if(vai){  vilao.x += 5;  }
		if(volta){  vilao.x -= 5;  }

		if(vilao.x>600){ vai = false; volta = true; }
		if(vilao.x<100){ vai = true; volta = false; }
	}

	public void atualizar() {
		mover();//AQUI CHAMAMOS O MÉTODO MOVER!
	}
	
	public void desenharGraficos() {
		JPanel panel = new JPanel(){
		    BufferedImage background = loadImage();

		    private BufferedImage loadImage(){
		    	String s = System.getProperty("user.dir") + "/src/ba.png";
		    	System.out.println("te "+s);
		        File file = new File(System.getProperty("user.dir") + "/src/ba.png");
		        BufferedImage result = null;
		        try {
		            result = ImageIO.read(file);
		        } catch (IOException e) {
		            System.err.println("Errore, immagine non trovata");
		        }
		        return result;
		    }

		    @Override
		    public void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Dimension size = getSize();
		        g.drawImage(background, 0, 0,size.width, size.height,0, 0, background.getWidth(), background.getHeight(), null);
		    }
		};
		
		
		
		Graphics g = frame.getGraphics();	//ISSO JÁ ESTAVA AQUI
		Graphics bbg = backBuffer.getGraphics();//ISSO TAMBÉM JÁ ESTAVA AQUI...
		//AQUI VAMOS MANDAR DESENHAR ALGUNS IMAGENS NA TELA
		//AS DIMENSÕES ORIGINAIS DO FUNDO SÃO: 500X500 QUE É O TAMANHO DA NOSSA TELA!


		int i;
		String caminho = new String();

		caminho = vai? "src/image/passaro/tile00": "src/image/passaro/volta/tile00";
		for(i=0; i< 8; i++) 
			vilao.cenas[i] = new ImageIcon(caminho+i+".png");



		bbg.drawImage(vilao.cenas[vilao.cena].getImage(), vilao.x, vilao.y,null);
		vilao.animar();





		//==================================================================================	
	}


}
