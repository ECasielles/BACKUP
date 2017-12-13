package dam.psp;


/**
 * 1.Escribe un clase llamada Orden que inicie dos hilos y fuerce que la escritura del segundo sea 
 * siempre anterior a la escritura por pantalla del primero.
 *	Ejemplo:
 *	Hola, soy el hilo número 1
 *
 * @author Enrique Casielles Lapeira
 * @see Thread
 */

class HiloLlamada extends Thread {
	int numero;
	Orden orden;
	
	public HiloLlamada(int numero, Orden orden) {
		this.numero = numero;
		this.orden = orden;
	}
	
	@Override
	public void run() {
		orden.llamar(numero);
	}
	
}

class Orden {
	public int elementos;
	public Orden(int hilos) {
		this.elementos = hilos;
	}
	
	public synchronized void llamar(int numero) {
		while(elementos > numero) {
			try {
				System.out.println(numero + " esperando...");
				wait();
				System.out.println(numero + " lo intenta");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Hola, soy el hilo número " + elementos);
		elementos--;
		//Cuando se usan más de 2 hilos es necesario notificar con notifyAll()
		//porque se bloquea si el siguiente hilo no puede tener el turno.
		notifyAll();
	}
	
}

public class Ejercicio_1 {

	public static void main(String[] args) {
		int nElementos = 15;
		Orden orden = new Orden(nElementos);
		HiloLlamada[] hilos = new HiloLlamada[orden.elementos];
		
		for (int i = 0; i < nElementos; i++) {
			hilos[i] = new HiloLlamada(i + 1, orden);			
			hilos[i].start();
		}
		for (int i = 0; i < nElementos; i++) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
