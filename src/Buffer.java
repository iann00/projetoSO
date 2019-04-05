import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.JLabel;


public class Buffer {
	private JLabel    textoPlaca;
	private Boolean   caixaCheia;
	private Semaphore semaforo;
	private int       maximo = 0;
	private int       eliminarPessoa;
	private ArrayList<Mensagem> quantidadeAtual = new ArrayList<Mensagem>();
	private int       maximoCaixa;
	private Boolean   passaroVoando;
	private JLabel    textoQuantidadeMensagens;
	private Pombo     pombo;
	
	public Buffer(JLabel Jplaca, Pombo pb) {
		setPombo(pb);
		textoPlaca = Jplaca;
	}
	
	public void zerarBuffer(){
		this.setQuantidadeAtual(new ArrayList<Mensagem>());
		this.setMaximo(0);
		this.setMaximoCaixa(getMaximoCaixa()+3);
	}

	public void adicionaMensagem(Mensagem mensagem, Semaphore s){
		this.quantidadeAtual.add(mensagem);
		this.setMaximo(this.getMaximo() + 1); // CRESCE ATÉ ATINGIR 3
		this.setMaximoCaixa(this.getMaximoCaixa() - 1); // CRESCE ATÉ ATINGIR 3

		textoPlaca.setText(""+ (this.getMaximoCaixa()));
		caixaCheia = getMaximoCaixa()==0? true : false;
		setCaixaCheia(caixaCheia);

		if (getMaximoCaixa() == 0) s.release();
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

	public Boolean getCaixaCheia() {
		return caixaCheia;
	}

	public void setCaixaCheia(Boolean caixaCheia) {
		this.caixaCheia = caixaCheia;
	}

	public int getEliminarPessoa() {
		return eliminarPessoa;
	}

	public void setEliminarPessoa(int eliminarPessoa) {
		this.eliminarPessoa = eliminarPessoa;
	}

	public Pombo getPombo() {
		return pombo;
	}

	public void setPombo(Pombo pombo) {
		this.pombo = pombo;
	}

}
