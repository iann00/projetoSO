import java.util.ArrayList;


public class Buffer {

	private int maximo = 0;
	private ArrayList<Mensagem> quantidadeAtual = new ArrayList<Mensagem>();


	public void zerarBuffer(){
		this.setQuantidadeAtual(new ArrayList<Mensagem>());
		this.setMaximo(0);
	}
	
	//adiciona na caixa.
	public void adicionaMensagem(Mensagem mensagem){
		this.quantidadeAtual.add(mensagem);
		this.setMaximo(this.getMaximo() + 1);
	}

	
	public ArrayList<Mensagem> getQuantidadeAtual() {
		return quantidadeAtual;
	}

	public void setQuantidadeAtual(ArrayList<Mensagem> quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}

	public int getMaximo() {
		return maximo;
	}

	public void setMaximo(int maximo) {
		this.maximo = maximo;
	}

}
