import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JLabel;



public class Pombo implements Runnable{

	private JLabel componente;
	private Semaphore aptoViagem;

	public Pombo(Semaphore aptoViagemP) {
		// TODO Auto-generated constructor stub
		this.aptoViagem = aptoViagemP;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		int i;
		try {
			while(true){ // Esse while serve para sempre o bompo fazer a viagem
				this.aptoViagem.acquire();

				for(i=0;i<3;i++){
					Thread.currentThread().sleep(500);
					System.out.println("pombo viajando ");

				}
				/*
				int x = componente.getX();
				componente.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/teste.png"));
				componente.repaint();

				while(x < 550){
					System.out.println("passa aqui " +x);
					componente.setBounds(x+1, 240, 100, 38);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					x++;
				}
				*/

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
