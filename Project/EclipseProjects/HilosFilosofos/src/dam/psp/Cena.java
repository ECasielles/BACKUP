package dam.psp;

class Palillo {
	int posicion;
	boolean ocupado;
	
	public Palillo(int posicion) {
		this.posicion = posicion;
		ocupado = false;
	}
	
	public synchronized void cogerPalillo() {
		while(ocupado) {
			System.out.println("Palillo " + posicion + " en uso, espera.");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ocupado = true;
		System.out.println("Palillo " + posicion + " ocupado");
		//Aquí no va un notify porque no hay que avisar a nadie de que
		//se ha cogido un palillo
	}
	public synchronized void soltarPalillo() {		
		ocupado = false;
		System.out.println("Palillo " + posicion + " libre");
		//Aunque hacemos las pruebas con notifyAll, sabemos
		//que sólo se compite con un solo filósofo
		notify();
	}
	
}

class Filosofo implements Runnable {
	private int posicion;
	private Palillo izquierdo, derecho;
	private Thread hiloFilosofo;
	
	public Filosofo(int posicion, Palillo izquierdo, Palillo derecho) {
		this.posicion = posicion;
		this.izquierdo = izquierdo;
		this.derecho = derecho;
		//Si se implementa la interfaz runnable,
		//debemos encapsular el hilo
		this.hiloFilosofo = new Thread(this);
	}
	//Encapsulan los métodos del hilo
	public void empezar() {
		hiloFilosofo.start();		
	}
	public void acabar() {
		try {
			hiloFilosofo.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	public void hablar() {
		try {
			System.out.println("Filosofo " + posicion + " habla...");
			Thread.sleep(400 * (1 + 10 * Math.round(Math.random())));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void comer() {
		if(posicion % 2 == 0) {
			izquierdo.cogerPalillo();
			System.out.println("Filosofo " + posicion + " coge palillo " + izquierdo.posicion);
			derecho.cogerPalillo();
			System.out.println("Filosofo " + posicion + " coge palillo " + derecho.posicion);
		} else {
			derecho.cogerPalillo();
			System.out.println("Filosofo " + posicion + " coge palillo " + derecho.posicion);
			izquierdo.cogerPalillo();
			System.out.println("Filosofo " + posicion + " coge palillo " + izquierdo.posicion);
		}
		System.out.println("Filosofo " + posicion + " come...");
		try {
			Thread.sleep(500 * (1 + 5 * Math.round(Math.random())));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		izquierdo.soltarPalillo();
		derecho.soltarPalillo();
	}
	
	@Override
	public void run() {		
		for (int i = 0; i < 3; i++) {
			//Eliseo lo divide más:
			//	pensar
			//	cogerPalillos
			//	comer
			//	soltarPalillos
			hablar();
			comer();
		}
		System.out.println("Filosofo " + posicion + " ha terminado");
	}
	
}

public class Cena {

	public static void main(String[] args) {
		
		int individuos = 5;
		Palillo[] palillos = new Palillo[individuos];
		Filosofo[] filosofos = new Filosofo[individuos];
		
		for (int i = 0; i < individuos; i++)
			palillos[i] = new Palillo(i);
		for (int i = 0; i < individuos; i++)
			filosofos[i] = new Filosofo(i, palillos[i], palillos[(i+1)%individuos]);
		for (int i = 0; i < individuos; i++)
			filosofos[i].empezar();
		for (int i = 0; i < individuos; i++)
			filosofos[i].acabar();
	}

}
