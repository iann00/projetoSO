import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class Pessoa implements Runnable {
	private  Buffer o_Buffer;
	private  Semaphore s1,s2, n, mutex, aptoViagem;


	public Pessoa(Buffer bufferpassado, Semaphore sem1, Semaphore sem2, Semaphore n, Semaphore mutex, Semaphore aptoViagem){
		super();
		o_Buffer = bufferpassado;
		s1 = sem1;
		s2 = sem2;
		this.n = n;
		this.mutex = mutex;
		this.aptoViagem = aptoViagem;
	}

	public void run() {
		// TODO Auto-generated method stub

		try {
			this.mutex.acquire(); // bloqueia o mutex
			this.n.acquire(); // bloqueia o contador de mensagens
			Mensagem mensagem = new Mensagem();
			mensagem.setPessoa(this);
			mensagem.setCorpo("Mensagem de: "+this.toString());
			mensagem.setId(1);
			o_Buffer.adicionaMensagem(mensagem);
			Thread.currentThread().sleep(1000);
			System.out.println("Thread: "+Thread.currentThread().getName()+" quantidade no buffer:"+o_Buffer.getMaximo());

			if(o_Buffer.getMaximo() == 3){
				System.out.println("zerando buffer "+o_Buffer.getMaximo());
				this.aptoViagem.release();
				o_Buffer.zerarBuffer();
			}
				
			
			
			this.n.release(); // libera o contador de mensagens
			this.mutex.release(); // libera o mutex
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
