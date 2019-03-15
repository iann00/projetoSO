import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class Pessoa implements Runnable {
	private  Buffer o_Buffer;
	private  Semaphore s1,s2, n, mutex, aptoViagem, semaforoCaixaPostal;

	private Boolean passaroVoando;

	// Atributos próprios da Pessoa
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
	public Pessoa(int idPessoa, Buffer bufferpassado, int tempoEscrita, Semaphore sCaixaPostal, Semaphore sem1, Semaphore sem2, Semaphore n, Semaphore mutex, Semaphore aptoViagem){
		super();
		o_Buffer = bufferpassado;
		s1 = sem1;
		s2 = sem2;
		id = idPessoa;
		this.tempoEscrita = tempoEscrita;
		this.n = n;
		this.mutex = mutex;
		this.aptoViagem = aptoViagem;
		this.semaforoCaixaPostal = sCaixaPostal;
	}

	// Método que a Thread Pessoa executa quando é instanciada.
	public void run() {
		// TODO Auto-generated method stub

		try {
			while(true){

				System.out.println("Pessoa "+this.getId()+ " escrevendo ...");
				Thread.currentThread().sleep(this.getTempoEscrita()*1000);
				Mensagem mensagem = new Mensagem();
				mensagem.setPessoa(this);
				mensagem.setCorpo("Mensagem de: "+this.toString());
				mensagem.setId(1);



				/* OBSERVAÇÃO BUFFER LOTAÇÃO CAIXA
				 *  caso a caixa esteja cheia, dorme e espera a caixa esvaziar para poder adicionar
				 */

				this.mutex.acquire(); // bloqueia o mutex

				// ESSE IF É PARA VERIFICAR SE ATINGIU O MAXIMO DA CAIXA
				if(this.o_Buffer.getMaximoCaixa()==0) System.out.println("Pessoa "+this.getId()+ " vai dormir ...");

				while(this.o_Buffer.getMaximoCaixa()==0){
					System.out.println("vai dormir aqui 1 "+Thread.currentThread().getName());
					semaforoCaixaPostal.acquire();
				}


				System.out.println("Pessoa "+this.getId()+ " colando mensagem ...");
				o_Buffer.adicionaMensagem(mensagem);
				this.semaforoCaixaPostal.release();


				// ACORDA O PASSARO CASO ATINJA O NUMERO DE MENSAGENS
				if(o_Buffer.getMaximo() == 2 && o_Buffer.getPassaroVoando()==false){
					System.out.println("Pombo pode viajar");
					this.aptoViagem.release();
					o_Buffer.setPassaroVoando(true);
					o_Buffer.zerarBuffer();
					// Quando viajar, capacidade do buffer da caixa é aumentada em 3
				}


				this.mutex.release(); // libera o mutex
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
