import java.util.ArrayList;

import javax.swing.JLabel;


public class Buffer {

	private JLabel textoPlaca;
	
	public Buffer(JLabel Jplaca) {
		textoPlaca = Jplaca;
		// TODO Auto-generated constructor stub
	}
	private int maximo = 0;
	private ArrayList<Mensagem> quantidadeAtual = new ArrayList<Mensagem>();
	private int maximoCaixa;
	private Boolean passaroVoando;
	private JLabel textoQuantidadeMensagens;
	
	public void zerarBuffer(){
		this.setQuantidadeAtual(new ArrayList<Mensagem>());
		this.setMaximo(0);
		this.setMaximoCaixa(getMaximoCaixa()+3);
	}
	
	//adiciona na caixa.
	public void adicionaMensagem(Mensagem mensagem){
		this.quantidadeAtual.add(mensagem);
		this.setMaximo(this.getMaximo() + 1); // CRESCE ATÉ ATINGIR 3
		this.setMaximoCaixa(this.getMaximoCaixa() - 1); // CRESCE ATÉ ATINGIR 3
		textoPlaca.setText(""+ (this.getMaximoCaixa()));
	}

	
	
	
	
	
	/*
	 * Apenas métodos de getters e setters
	 */
	
	public Boolean getPassaroVoando() {
		return passaroVoando;
	}

	public void setPassaroVoando(Boolean passaroVoando) {
		this.passaroVoando = passaroVoando;
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

	public int getMaximoCaixa() {
		return maximoCaixa;
	}

	public void setMaximoCaixa(int maximoCaixa) {
		this.maximoCaixa = maximoCaixa;
	}

}
