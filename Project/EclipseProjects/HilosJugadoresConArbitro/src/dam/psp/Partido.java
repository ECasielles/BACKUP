package dam.psp;

class Jugador extends Thread {

	int dorsal;
	Arbitro arbitro;
	
	public Jugador(Arbitro arbitro, int dorsal) {
		this.arbitro = arbitro;
		this.dorsal = dorsal;
	}
	
	@Override
	public void run() {
		while (!arbitro.seAcabo()) {
			//Si aquí se mete código para que pierda el tiempo y fallará si
			//no se pone seAcabo() a synchronized
			try {
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			arbitro.jugar(dorsal, (int) Math.floor(Math.random() * arbitro.maximo));
		}
		System.out.println("Jugador " + dorsal + " ha dejado de jugar...");
	}
}

class Arbitro {
	int numJug;
	int turno;
	int objetivo;
	boolean acertado;
	int maximo = 100;
	
	public Arbitro(int numJug) {
		this.turno = (int) (numJug * Math.random());
		this.numJug = numJug;
		this.acertado = false;
		this.objetivo = (int) Math.floor(Math.random() * maximo);		
		System.out.println("Número secreto es " + objetivo);
	}
	
	public int esTurnoDe() { return turno; }
	
	//Hay que sincronizar aquí para evitar que los hilos entren a la SC
	//cuando ya se ha acabado el juego
	public synchronized boolean seAcabo() { return acertado; }
	
	public synchronized void jugar(int dorsal, int intento) {
		while(dorsal != turno && !acertado)
			try {
				System.out.println("[PRE] Jugador " + dorsal + " y es turno de " + turno);
				wait();
				System.out.println("[POST] Jugador " + dorsal + " y es turno de " + turno);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		//IMPORTANTE: Tener en cuenta que se puede salir de la guarda por dos razones
		if(!acertado) {
			System.out.println("El jugador " + dorsal + " prueba con " + intento);
			if(intento == objetivo) {
				acertado = true;
				System.out.println("JUGADOR " + dorsal + " WINS!");
			}
			else
				turno = (1 + turno) % numJug;		
		}
		//No sé qué hilo es el siguiente, por eso notifyAll
		notifyAll();
	}
		
}

public class Partido {

	public static void main(String[] args) {
	
		int numJugadores = 4;
		Arbitro arbitro = new Arbitro(numJugadores);
		
		Jugador[] jugadores = new Jugador[numJugadores];	 
		for (int i = 0; i < jugadores.length; i++) {
			jugadores[i] = new Jugador(arbitro, i);
			jugadores[i].start();
		}
		for (int i = 0; i < jugadores.length; i++) {
			try {
				jugadores[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
