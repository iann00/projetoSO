import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;


public class PomboCorreio extends JFrame {
	private JPanel jPContentPane;
	public  JLabel jLImagePassaro;
	private JLabel placa;
	private JLabel jLTextoPlaca;
	private JLabel placaPessoa;
	private JLabel jLPlacaDormindo;
	private JLabel jLPlacaColando;
	private JLabel jLTextoPlacaPessoa;
	private JTextField mTextFieldTempoEscrita;
	private JLabel lblMenu;
	private JTextField mTextFieldIDElininarPessoa;
	private int posX, posY;
	private Buffer buffer;
//	buffer.setMaximoCaixa(10);
//	buffer.setPassaroVoando(false);
	private Semaphore semaforo1;
	private Semaphore semaforo2;
	private Semaphore n;
	private Semaphore mutex;
	private Semaphore auxSemaforoCaixaPostal;
	private Semaphore aptoViagem;
	private Semaphore semaforoCaixaPostal;
	private Random gerador; // variável que auxilia na geração de números randômicos.
	private int contadorPessoas = 1;
	private JButton jButtonEliminarPessoa;
	private JTextField jTextFiledTempoCargap;
	private JTextField jTextFieldTempoDescargap;
	private JTextField jTextFieldTempoviagemp;
	private JTextField jTextFieldNumeromensagensp;
	private Pombo pb;
	private JTextField jTextFieldMaximoCaixa;
	private JButton jButtonConfigurarCaixa;
	private JLabel jLTextoPlacaColando;
	private JLabel jLTextoPlacaDormindo;
	private JLabel jLabelTempoEscrita;
	private JLabel jLabelID;
	private JLabel jLabelTC;
	private JLabel jLabellTD;
	private JLabel jLabelTV;
	private JLabel jLabelNM;

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


	public PomboCorreio() throws InterruptedException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 470);

		placa = new JLabel("");
		placa.setBounds(0, 300, 64, 64);
		placa.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/placa.png"));

		placaPessoa = new JLabel("");
		placaPessoa.setBounds(820, 250, 240, 124);
		placaPessoa.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/auxMensagem.png"));

		jLPlacaDormindo = new JLabel("");
		jLPlacaDormindo.setBounds(820, 310, 240, 124);
		jLPlacaDormindo.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/auxMensagem.png"));
		
		jLPlacaColando = new JLabel("");
		jLPlacaColando.setBounds(820, 370, 240, 124);
		jLPlacaColando.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/auxMensagem.png"));

		jLImagePassaro = new JLabel("");
		jLImagePassaro.setBounds(0, 150, 120, 200);
		jLImagePassaro.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/teste.png"));

		/**
		 * Cria o JPanel com uma magem de fundo
		 */
		jPContentPane =  new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Toolkit toolkit = Toolkit.getDefaultToolkit();

				Image image = toolkit.getImage(System.getProperty("user.dir") + "/src/images/planoDeFundo.png");
				g.drawImage(image, 0, 0, null);
			}
		};


		jPContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jPContentPane);
		jPContentPane.setLayout(null);

		jLTextoPlaca = new JLabel("");
		jLTextoPlaca.setHorizontalAlignment(SwingConstants.CENTER);
		jLTextoPlaca.setBounds(4, 312, 60, 16);
		jLTextoPlaca.setForeground(Color.white);
		jLTextoPlaca.setText("10");

		getContentPane().add(jLTextoPlaca);

		jLTextoPlacaPessoa = new JLabel("");
		jLTextoPlacaPessoa.setHorizontalAlignment(SwingConstants.CENTER);
		jLTextoPlacaPessoa.setBounds(820, 280, 240, 50);
		jLTextoPlacaPessoa.setText("Iniciando");

		getContentPane().add(jLTextoPlacaPessoa);
		
		jLTextoPlacaColando = new JLabel("");
		jLTextoPlacaColando.setHorizontalAlignment(SwingConstants.CENTER);
		jLTextoPlacaColando.setText("...");
		jLTextoPlacaColando.setBounds(820, 350, 240, 15);
		jPContentPane.add(jLTextoPlacaColando);
		
		jLTextoPlacaDormindo = new JLabel("texto placaDormindo");
		jLTextoPlacaDormindo.setHorizontalAlignment(SwingConstants.CENTER);
		jLTextoPlacaDormindo.setText("...");
		jLTextoPlacaDormindo.setBounds(820, 420, 240, 15);
		jPContentPane.add(jLTextoPlacaDormindo);

		getContentPane().add(placa);
		getContentPane().add(placaPessoa);
		getContentPane().add(jLPlacaColando);
		getContentPane().add(jLPlacaDormindo);
		getContentPane().add(jLImagePassaro);
		System.out.println("|||||||||");

		mTextFieldTempoEscrita = new JTextField();
		mTextFieldTempoEscrita.setBounds(840, 57, 50, 19);
		jPContentPane.add(mTextFieldTempoEscrita);
		mTextFieldTempoEscrita.setColumns(10);

		lblMenu = new JLabel("Menu");
		lblMenu.setBounds(970, 26, 70, 15);
		jPContentPane.add(lblMenu);

		mTextFieldIDElininarPessoa = new JTextField();
		mTextFieldIDElininarPessoa.setBounds(840, 80, 50, 19);
		jPContentPane.add(mTextFieldIDElininarPessoa);
		mTextFieldIDElininarPessoa.setColumns(10);

		JButton btnCriarPessoa = new JButton("Criar Pessoa");
		btnCriarPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int tempoEscrita = 0;

				if(mTextFieldTempoEscrita.getText().equals("")) {
					JOptionPane.showMessageDialog(null,
							"Entre com um número");
				} else {
					tempoEscrita = Integer.parseInt(mTextFieldTempoEscrita.getText());
				}

				if (contadorPessoas == 1) posY = 200;
				else if(contadorPessoas < 5){
					posX = 820;
					posY = posY + 80;
				}
				
				Pessoa pessoa = new Pessoa(auxSemaforoCaixaPostal,
						semaforoCaixaPostal, semaforo1, semaforo2, n, mutex, aptoViagem);

				       pessoa.setId(contadorPessoas);
				       pessoa.setComponente(jLTextoPlacaPessoa);
				       pessoa.setTextoDormir(jLTextoPlacaDormindo);
				       pessoa.setTextoColando(jLPlacaColando);
				       pessoa.setBuffer(buffer);
				       pessoa.setTempoEscrita(tempoEscrita);

				Thread thread = new Thread(pessoa);
				thread.start();

				contadorPessoas++;
			}
		});

		btnCriarPessoa.setBounds(910, 57, 240, 19);
		jPContentPane.add(btnCriarPessoa);

		jButtonEliminarPessoa = new JButton("Eliminar Pessoa");
		jButtonEliminarPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(mTextFieldIDElininarPessoa.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o ID do usuário");
				} else {
					int idPessoa = Integer.parseInt(mTextFieldIDElininarPessoa.getText());
					buffer.setEliminarPessoa(idPessoa);
					// aqui prepara para eliminar. só irá eliminar de fato quando a thread for executar
				}
			}
		});

		
		jButtonEliminarPessoa.setBounds(910, 80, 240, 19);
		jPContentPane.add(jButtonEliminarPessoa);
		
		jTextFiledTempoCargap = new JTextField();
		jTextFiledTempoCargap.setBounds(840, 140, 50, 19);
		jPContentPane.add(jTextFiledTempoCargap);
		jTextFiledTempoCargap.setColumns(10);
		
		jTextFieldTempoDescargap = new JTextField();
		jTextFieldTempoDescargap.setBounds(940, 140, 50, 19);
		jPContentPane.add(jTextFieldTempoDescargap);
		jTextFieldTempoDescargap.setColumns(10);
		
		jTextFieldTempoviagemp = new JTextField();
		jTextFieldTempoviagemp.setBounds(1040, 140, 50, 19);
		jPContentPane.add(jTextFieldTempoviagemp);
		jTextFieldTempoviagemp.setColumns(10);
		
		jTextFieldNumeromensagensp = new JTextField();
		jTextFieldNumeromensagensp.setBounds(1140, 140, 50, 19);
		jPContentPane.add(jTextFieldNumeromensagensp);
		jTextFieldNumeromensagensp.setColumns(10);
		
		JButton btnConfigurarPombo = new JButton("Configurar Pombo");
		btnConfigurarPombo.setBounds(950, 180, 170, 25);

		btnConfigurarPombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(jTextFiledTempoCargap.getText().equals("")
						|| jTextFieldTempoDescargap.getText().equals("")
						|| jTextFieldTempoviagemp.getText().equals("")
						|| jTextFieldNumeromensagensp.getText().equals("") ) {

					JOptionPane.showMessageDialog(null,
							"Você deve especificar os valores para criar o Pombo");
				} else {
					int tempoCarga          = Integer.parseInt(jTextFiledTempoCargap.getText());
					int tempoDescarga       = Integer.parseInt(jTextFieldTempoDescargap.getText());
					int tempoViagem         = Integer.parseInt(jTextFieldTempoviagemp.getText());
					int quantidadeMensagens = Integer.parseInt(jTextFieldNumeromensagensp.getText());

					buffer = new Buffer(jLTextoPlaca, pb);
					buffer.setPassaroVoando(false);
					buffer.setMaximo(0);

					Pombo pombo = new Pombo();
						  pombo.setSemaforoAptoViagem(aptoViagem);
						  pombo.setBuffer(buffer);
						  pombo.setSemaforoCaixPostal(semaforoCaixaPostal);
						  pombo.setjLComponente(jLImagePassaro);
						  pombo.setTempoCarga(tempoCarga);
						  pombo.setTempoDescarga(tempoDescarga);
						  pombo.setTempoViagem(tempoViagem);
						  pombo.setQuantidadeMensagem(quantidadeMensagens);

					buffer.setPombo(pombo);

					Thread thread = new Thread(pombo);
					       thread.start();
				}

			}
		});

		jPContentPane.add(btnConfigurarPombo);
		
		jTextFieldMaximoCaixa = new JTextField();
		jTextFieldMaximoCaixa.setBounds(840, 229, 50, 19);
		jPContentPane.add(jTextFieldMaximoCaixa);
		jTextFieldMaximoCaixa.setColumns(10);
		
		jButtonConfigurarCaixa = new JButton("Configurar caixa");
		jButtonConfigurarCaixa.setBounds(900, 229, 170, 19);
		jButtonConfigurarCaixa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(jTextFieldMaximoCaixa.getText().equals("")) {
					JOptionPane.showMessageDialog(null,
							"Erro ao configurar o Pombo. Informe os dados corretos");
				} else {
					int maximoCaixa = Integer.parseInt(jTextFieldMaximoCaixa.getText());
					buffer.setMaximoCaixa(maximoCaixa);
				}

			}
		});

		jPContentPane.add(jButtonConfigurarCaixa);
		
		jLabelTempoEscrita = new JLabel("T.E");
		jLabelTempoEscrita.setBounds(810, 59, 40, 15);
		jPContentPane.add(jLabelTempoEscrita);
		
		jLabelID = new JLabel("ID");
		jLabelID.setBounds(820, 80, 70, 15);
		jPContentPane.add(jLabelID);
		
		jLabelTC = new JLabel("T.C");
		jLabelTC.setBounds(815, 140, 70, 15);
		jPContentPane.add(jLabelTC);
		
		jLabellTD = new JLabel("T.D");
		jLabellTD.setBounds(915, 140, 70, 15);
		jPContentPane.add(jLabellTD);
		
		jLabelTV = new JLabel("T.V");
		jLabelTV.setBounds(1015, 140, 70, 15);
		jPContentPane.add(jLabelTV);
		
		jLabelNM = new JLabel("N.M");
		jLabelNM.setBounds(1110, 140, 70, 15);
		jPContentPane.add(jLabelNM);
	}
}
