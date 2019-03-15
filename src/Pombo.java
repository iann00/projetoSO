import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;



public class Pombo implements Runnable{

	private JLabel componente;
	private Semaphore aptoViagem;
	private Buffer o_Buffer;
	private Semaphore semaforoCaixPostal;

	// Abaixo atributos do pombo 
	private int tempoViagem, tempoCarga, tempoDescarga;


	public Pombo(Semaphore aptoViagemP, Buffer buffer, Semaphore sCaixPostal, JLabel imagemPassaro, int tCarga, int tDescarga, int tViagem) {
		// TODO Auto-generated constructor stub
		this.aptoViagem = aptoViagemP;
		o_Buffer = buffer;
		semaforoCaixPostal = sCaixPostal;
		componente = imagemPassaro;
		tempoCarga = tCarga;
		tempoDescarga = tDescarga;
		tempoViagem = tViagem;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		int i;
		try {
			while(true){ // Esse while serve para sempre o pombo fazer a viagem
				this.aptoViagem.acquire(); // Pássaro dorme.

				Thread.currentThread().sleep(tempoCarga*1000); //Thread Pombo espera, fazendo o papel de carregar as mensagens

				int x = componente.getX();
				componente.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/ida0.png"));
				componente.repaint();
				componente.setBounds(x, 0, 100, 38);

				int contadorImagensPassaro = 1;
				while(x <= 500){
					if(contadorImagensPassaro == 8) contadorImagensPassaro = 1;
					componente.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/ida"+contadorImagensPassaro+".gif"));
					componente.repaint();
					componente.setBounds(x, 0, 120, 200);
					Thread.currentThread().sleep(tempoViagem);
					x = x+1;
					contadorImagensPassaro++;
				}

				// Aqui ele começa a descarregar as mensagens
				Thread.currentThread().sleep(tempoDescarga*1000); //Thread Pombo espera, fazendo o papel de carregar as mensagens
				
				while(x >= 0){
					if(contadorImagensPassaro == 8) contadorImagensPassaro = 1;
					componente.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/volta"+contadorImagensPassaro+".png"));
					componente.repaint();
					componente.setBounds(x, 0, 120, 200);
					Thread.currentThread().sleep(tempoViagem);
					x--;
					contadorImagensPassaro++;
				}

				
				/*
				 * Após terminar a viagem, pássaro se prepara para voar novamente
				 * atribuição de 0 ao maximo => valor 0 para quantidade de mensagens que ele tem.
				 * o_Buffer.setMaximoCaixa(o_Buffer.getMaximoCaixa()+3) => quando ele volta da cidade B,
				 * 		os passageiros que estavam dormindo com suas mensagens prontas já podem adicionar mais mensagens.
				 */
				o_Buffer.setMaximo(0);
				o_Buffer.setMaximoCaixa(o_Buffer.getMaximoCaixa()+3); // C
				semaforoCaixPostal.release(); // Quando a caixa enche, a pessoa dorme. Essa linha acorda quem tava dormindo (Acorda todos, porém a Thread pessoa só permite que 1 pessoa por vez adicione a mensagem.
				o_Buffer.setPassaroVoando(false); 
				
				// A thread passaro está em loop infinito. Após essa última linha ele retorna para começar tudo de novo.
 				// Porém a instrução da primeira linha desse loop é pra ele dormir e só executa o resto (acorda) quanto for acionado.

			}

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TODO Auto-generated method stub

	}

	public JLabel getComponente() {
		return componente;
	}

	public void setComponente(JLabel componente) {
		this.componente = componente;
	}

}
