import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.JLabel;


public class Pessoa implements Runnable {
	private  Buffer o_Buffer;
	private  Semaphore s1,s2, n, mutex, aptoViagem;
	Semaphore semaforoCaixaPostal, auxSemaforoCaixaPostal;
	private JLabel componente;
	private JLabel textoDormir;
	private JLabel textoColando;

	private Boolean passaroVoando;

	// Atributos próprios da Pessoa
	
	public JLabel getComponente() {
		return componente;
	}

	public void setComponente(JLabel componente) {
		this.componente = componente;
	}

	private int id;
	private int tempoEscrita;
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

	// Construtor da Classe Pessoa
	public Pessoa(int idPessoa, JLabel comp, JLabel txtDormindo, JLabel txtColando, Buffer bufferpassado, int tempoEscrita, Semaphore auxSCaixa, Semaphore sCaixaPostal, Semaphore sem1, Semaphore sem2, Semaphore n, Semaphore mutex, Semaphore aptoViagem){
		super();
		componente = comp;
		textoDormir = txtDormindo;
		textoColando = txtColando;
		o_Buffer = bufferpassado;
		s1 = sem1;
		s2 = sem2;
		id = idPessoa;
		this.tempoEscrita = tempoEscrita;
		this.n = n;
		this.mutex = mutex;
		this.aptoViagem = aptoViagem;
		semaforoCaixaPostal = sCaixaPostal;
		auxSemaforoCaixaPostal = auxSCaixa;
	}

	// Método que a Thread Pessoa executa quando é instanciada.
	public void run() {
		// TODO Auto-generated method stub

		try {
			while(true){

				if(o_Buffer.getEliminarPessoa()==this.getId()){
					System.out.println("Pessoa "+this.getId()+ " foi excluida");

					componente.setText("Pessoa "+this.getId()+ " foi excluida");
					break;
				}
				
				componente.setText("Pessoa "+this.getId()+ " escrevendo");

				System.out.println("Pessoa "+this.getId()+ " escrevendo ...");
				Thread.currentThread().sleep(this.getTempoEscrita()*1000);
				Mensagem mensagem = new Mensagem();
				mensagem.setPessoa(this);
				mensagem.setCorpo("Mensagem de: "+this.toString());
				mensagem.setId(1);



				/* OBSERVAÇÃO BUFFER LOTAÇÃO CAIXA
				 *  caso a caixa esteja cheia, dorme e espera a caixa esvaziar para poder adicionar
				 */

				mutex.acquire(); // bloqueia o mutex
				System.out.println("QTDE "+o_Buffer.getMaximoCaixa());
				while(o_Buffer.getMaximoCaixa()==0) {
					textoDormir.setText("Pessoa "+this.getId()+ " vai dormir");

					System.out.println("Pessoa "+this.getId()+ " vai dormir ...");
					
//					auxSemaforoCaixaPostal.acquire();

						mutex.release();
	//					auxSemaforoCaixaPostal.release();
						semaforoCaixaPostal.acquire();

				}

				textoColando.setText("Pessoa "+this.getId()+ " colando mensagem");

				System.out.println("Pessoa "+this.getId()+ " colando mensagem ..."+o_Buffer.getMaximoCaixa());

				o_Buffer.adicionaMensagem(mensagem, semaforoCaixaPostal);
				//semaforoCaixaPostal.release(1);


				// ACORDA O PASSARO CASO ATINJA O NUMERO DE MENSAGENS
				if(o_Buffer.getMaximo() == 2 && o_Buffer.getPassaroVoando()==false){
					System.out.println("Pombo pode viajar");
					this.aptoViagem.release();
					o_Buffer.setPassaroVoando(true);
					o_Buffer.zerarBuffer();
					// Quando viajar, capacidade do buffer da caixa é aumentada em 3
				}


				mutex.release(); // libera o mutex
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


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
}
