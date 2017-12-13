package dam.psp;

public class Productor extends Thread {
	
	private Buffer compartido;
	private int veces; //veces que se tiene que interar las producciones de enteros
	
	public Productor(Buffer elBufer, int nveces) {
		compartido = elBufer;
		veces = nveces;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < veces; i++) {
			try {
				Thread.sleep((int) Math.random() * 3001);
				compartido.escribir(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
		System.out.println(getName() + " terminó de producir datos");
		
	}

}
