import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;


/**
 * Retirei a sobrecarga de parâmetros no construtor da classe e
 * deixei a cargo dos setters
 *
 * @author Ivan updated on 03/04/2019
 */
public class Pessoa implements Runnable {
	private static final String TAG = "Pessoa";

	private Buffer    mBuffer;
	private Semaphore s1;
	private Semaphore s2;
	private Semaphore n;
	private Semaphore mutex;
	private Semaphore aptoViagem;
	private Semaphore semaforoCaixaPostal;
	private Semaphore auxSemaforoCaixaPostal;
	private JLabel    componente;
	private JLabel    textoDormir;
	private JLabel    textoColando;
	private Boolean   passaroVoando;
	private int       id;
	private int       tempoEscrita;

	public Pessoa(Semaphore auxSCaixa, Semaphore sCaixaPostal,
				  Semaphore s1, Semaphore s2, Semaphore n,
				  Semaphore mutex, Semaphore aptoViagem){

		this.s1 = s1;
		this.s2 = s2;
		this.n = n;
		this.mutex = mutex;
		this.aptoViagem = aptoViagem;
		this.semaforoCaixaPostal = sCaixaPostal;
		this.auxSemaforoCaixaPostal = auxSCaixa;
	}

	public JLabel getComponente() {
		return componente;
	}

	public void setComponente(JLabel componente) {
		this.componente = componente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTempoEscrita() {
		return tempoEscrita;
	}

	public void setTempoEscrita(int tempoEscrita) {
		this.tempoEscrita = tempoEscrita;
	}

	public Boolean getPassaroVoando() {
		return passaroVoando;
	}

	public void setPassaroVoando(Boolean passaroVoando) {
		this.passaroVoando = passaroVoando;
	}

	public JLabel getTextoColando() {
		return textoColando;
	}

	public void setTextoColando(JLabel textoColando) {
		this.textoColando = textoColando;
	}

	public JLabel getTextoDormir() {
		return textoDormir;
	}

	public void setTextoDormir(JLabel textoDormir) {
		this.textoDormir = textoDormir;
	}

	public void setBuffer(Buffer buffer) {
		this.mBuffer = buffer;
	}

	public Buffer getBuffer() {
		return mBuffer;
	}

	/**
	 * Esse método simula o método {@link Thread#sleep(long)}. Basicamente
	 * obtemos a hora do sistema em segundos, criamos um loop infinito e só
	 * o paramos quando a subtração do tempo atual pelo o atual (em segundos,
	 * também, claro) for igual ao tempo passa como parâmetro.
	 *
	 * @see {@link Thread}
	 * @param s
	 */
	public void dormir(long s) {
		long timeMillis = System.currentTimeMillis();
		long start      = TimeUnit.MILLISECONDS.toSeconds(timeMillis);

		while (true) {
			long end = TimeUnit.MILLISECONDS
					.toSeconds(System.currentTimeMillis());
			if ((end - start) == s) break;
		}
	}

	/**
	 * Esse método é herdado da implementação de Runnable
	 */
	public void run() {
		try {
			while(true){
				if(mBuffer.getEliminarPessoa() == this.getId()) {
					componente.setText("Pessoa "+this.getId()+ " foi excluida");

					Log.w(TAG, "Pessoa "+this.getId()+ " foi excluida");
					break;
				} else {

				}
				
				componente.setText("Pessoa "+this.getId()+ " escrevendo");

				Log.w(TAG, "Pessoa "+this.getId()+ " escrevendo");


				//Thread.currentThread().sleep(this.getTempoEscrita()*1000);
				this.dormir(this.getTempoEscrita());


				/* OBSERVAÇÃO BUFFER LOTAÇÃO CAIXA
				 *  caso a caixa esteja cheia, dorme e espera a caixa esvaziar para poder adicionar
				 */

				mutex.acquire(); // bloqueia o mutex

				Log.w(TAG, "Quantida do buffer "+mBuffer.getMaximoCaixa());

				while(mBuffer.getMaximoCaixa()==0) {
					textoDormir.setText("Pessoa "+this.getId()+ " vai dormir");

					System.out.println("Pessoa "+this.getId()+ " vai dormir ...");
					
//					auxSemaforoCaixaPostal.acquire();

						mutex.release();
	//					auxSemaforoCaixaPostal.release();
						semaforoCaixaPostal.acquire();

				}

				textoColando.setText("Pessoa "+this.getId()+ " colando mensagem");

				String msgColando = String.format("Pessoa %d colando mensagem. Limite da caixa: %d",
						this.getId(), mBuffer.getMaximoCaixa());

				Log.w(TAG, msgColando);

				Mensagem mensagem = new Mensagem();
						 mensagem.setPessoa(this);
					     mensagem.setCorpo("Mensagem de "+this.toString());
						 mensagem.setId(1);

				mBuffer.adicionaMensagem(mensagem, semaforoCaixaPostal);
				//semaforoCaixaPostal.release(1);


				/**
				 * Esse trecho é importante, pois caso se atinja o número máximo
				 * de mensagens na caixa, ele acordará
				 */
				if(mBuffer.getMaximo() == 2
						&& mBuffer.getPassaroVoando() == false){

					this.aptoViagem.release();
					this.mBuffer.setPassaroVoando(true);
					this.mBuffer.zerarBuffer();
					// Quando viajar, capacidade do buffer da caixa é aumentada em 3

					Log.w(TAG, "Agora o pombo pode viajar");
				}

				mutex.release(); // libera o mutex
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
