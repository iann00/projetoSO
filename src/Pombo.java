import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Implementei getters e setters para amenizar a sobrecarga
 * de atributos no construtor da classe
 *
 * @author Ivan updated on 02/04/2019
 */
public class Pombo implements Runnable {
	private int tempoViagem;
	private int tempoCarga;
	private int tempoDescarga;
	private int quantidadeMensagem;

	private JLabel    jLComponente;
	private Semaphore semaforoAptoViagem;
	private Buffer    mBuffer;
	private Semaphore semaforoCaixPostal;

	public Pombo() {
		
	}

	public void setTempoViagem(int tempoViagem) {
		this.tempoViagem = tempoViagem;
	}

	public int getTempoViagem() {
		return tempoViagem;
	}

	public void setTempoCarga(int tempoCarga) {
		this.tempoCarga = tempoCarga;
	}

	public int getTempoCarga() {
		return tempoCarga;
	}

	public void setTempoDescarga(int tempoDescarga) {
		this.tempoDescarga = tempoDescarga;
	}

	public int getTempoDescarga() {
		return tempoDescarga;
	}

	public void setQuantidadeMensagem(int quantidadeMensagem) {
		this.quantidadeMensagem = quantidadeMensagem;
	}

	public int getQuantidadeMensagem() {
		return quantidadeMensagem;
	}

	public void setjLComponente(JLabel jLComponente) {
		this.jLComponente = jLComponente;
	}

	public JLabel getjLComponente() {
		return jLComponente;
	}

	public void setSemaforoAptoViagem(Semaphore semaforoAptoViagem) {
		this.semaforoAptoViagem = semaforoAptoViagem;
	}

	public void setBuffer(Buffer buffer) {
		this.mBuffer = buffer;
	}

	public void setSemaforoCaixPostal(Semaphore semaforoCaixPostal) {
		this.semaforoCaixPostal = semaforoCaixPostal;
	}

	public Semaphore getSemaforoCaixPostal() {
		return semaforoCaixPostal;
	}

	public void run() {
		try { while(true) { // Esse while serve para simular a viagem do pombo
				this.semaforoAptoViagem.acquire(); // Pássaro dorme.

				Thread.currentThread().sleep(tempoCarga * 1000); //Thread Pombo espera, fazendo o papel de carregar as mensagens

				int pos_x = jLComponente.getX();
				jLComponente.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/ida0.png"));
				jLComponente.repaint();
				jLComponente.setBounds(pos_x, 0, 100, 38);

				int contadorImagensPassaro = 1;
				while(pos_x <= 500) {
					if(contadorImagensPassaro == 8) contadorImagensPassaro = 1;

					jLComponente.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/ida"+contadorImagensPassaro+".gif"));
					jLComponente.repaint();
					jLComponente.setBounds(pos_x, 0, 120, 200);

					Thread.currentThread().sleep(tempoViagem);
					pos_x = pos_x + 1;
					contadorImagensPassaro++;
				}

				// Aqui ele começa a descarregar as mensagens
				Thread.currentThread().sleep(tempoDescarga * 1000); //Thread Pombo espera, fazendo o papel de carregar as mensagens
				
				while(pos_x >= 0) {
					if(contadorImagensPassaro == 8) contadorImagensPassaro = 1;

					jLComponente.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/volta"+contadorImagensPassaro+".png"));
					jLComponente.repaint();
					jLComponente.setBounds(pos_x, 0, 120, 200);

					Thread.currentThread().sleep(tempoViagem);
					pos_x--;
					contadorImagensPassaro++;
				}

				DateFormat formato = new SimpleDateFormat("HH:mm:ss.SSS");
				System.out.println("hora=: "+formato.toString());
				/*
				 * Após terminar a viagem, pássaro se prepara para voar novamente
				 * atribuição de 0 ao maximo => valor 0 para quantidade de mensagens que ele tem.
				 * mBuffer.setMaximoCaixa(mBuffer.getMaximoCaixa()+3) => quando ele volta da cidade B,
				 * 		os passageiros que estavam dormindo com suas mensagens prontas já podem adicionar mais mensagens.
				 */
				mBuffer.setMaximo(0);
				mBuffer.setMaximoCaixa(mBuffer.getMaximoCaixa()+3); // C
				semaforoCaixPostal.release(quantidadeMensagem); // Quando a caixa enche, a pessoa dorme. Essa linha acorda quem tava dormindo (Acorda todos, porém a Thread pessoa só permite que 1 pessoa por vez adicione a mensagem.
				mBuffer.setPassaroVoando(false);
				
				// A thread passaro está em loop infinito. Após essa última linha ele retorna para começar tudo de novo.
 				// Porém a instrução da primeira linha desse loop é pra ele dormir e só executa o resto (acorda) quanto for acionado.

			}

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
