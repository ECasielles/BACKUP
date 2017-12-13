package dam.psp;

/**
 * 2. Escribe una clase llamada Check que inicie dos hilos que accedan  a la vez a un buffer de
 * 10.000 enteros. Uno de ellos lee en el buffer y el otro escribe en él. El hilo escritor debe escribir
 * el mismo valor en todos los elementos del buffer incrementando en uno el valor en cada
 * pasada. El hilo lector debe ir comprobando que todos los valores del buffer son iguales,
 * mostrando un mensaje de error en caso contrario o un mensaje de correcto si la condición se
 * cumple. Para resolverlo, usar un monitor para acceder al buffer si así se indica por medio de un
 * parámetro en la ejecución del programa. En caso contrario, los hilos accederán al buffer sin
 * hacer uso del monitor.
 * 
 * @author Enrique Casielles Lapeira
 * @see Thread
 */

class HiloBuferLectura extends Thread {	
	Check check;
	int posicion;
	
	public HiloBuferLectura(Check check) { this.check = check; }
	
	@Override
	public void run() {
		if (check.monitor)
			while(!check.getFinLectura())
				check.leerSincronizado(posicion);
		System.out.println("Lector terminó");
	}	
}

class HiloBuferEscritura extends Thread {
	Check check;
	int posicion;
	
	public HiloBuferEscritura(Check check) { this.check = check; }
	
	@Override
	public void run() {
		if (check.monitor)
			while(!check.getFinEscritura())
				check.escribirSincronizado(posicion);
		System.out.println("Escritor terminó");
	}	
}

class Check {
	int indiceLectura = 0;
	int indiceEscritura = 0;
	int valorFinal = 10;
	int valor = 1;
	int pasadaLectura = 1;
	boolean monitor = true;
	boolean finEscritura = false;
	boolean finLectura = false;
	int[] bufer;
	
	public Check(int tamanoBufer, boolean monitor) {
		bufer = new int[tamanoBufer];
		this.monitor = monitor;
	}	
		
	//Leer/Escribir mientras no te digan párate
	public synchronized int getIndice(int indice) {
		while (indiceEscritura == indiceLectura)
			try {
				System.out.println("Esperando...");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return (indice + 1) % bufer.length;
	}
	
	//MONITORES
	public synchronized boolean getFinEscritura() {
		return finEscritura;
	}
	public synchronized boolean getFinLectura() {
		return finLectura;
	}
	public void escribirSincronizado(int posicion) {
		bufer[indiceEscritura] = valor;
		System.out.println("Escribiendo " + valor + " en " + indiceEscritura);
		indiceEscritura = (indiceEscritura + 1) % bufer.length;
		if(indiceEscritura == 0)
			valor++;
		if(valor > valorFinal)
			finEscritura = true;
		notify();
	}
	public void leerSincronizado(int posicion) {
		int valorLectura = bufer[indiceLectura];
		System.out.println("Leyendo " + valorLectura + " en " + indiceLectura);
		indiceLectura = (indiceLectura + 1) % bufer.length;
		if(indiceLectura == 0) {
			pasadaLectura++;
			if(valor > valorFinal)
				finLectura = true;
		}
		notify();
	}
	private synchronized boolean controlAvance(boolean esLector) {
		boolean bloqueo = false;
		//Coinciden en una posición
		if (indiceEscritura == indiceLectura)
			if(pasadaLectura == valor) {
				bloqueo = esLector;
			} else if (pasadaLectura < valor) {
				bloqueo = !esLector;
			}
		//No coinciden
		return bloqueo;
	}	
			
	//SEMAFOROS	
}
	
public class Ejercicio_2 {
	public static void main(String[] args) {
		
		int tamanoBufer = 100;
		boolean usaMonitor = true;
		Check check = new Check(tamanoBufer, usaMonitor);
		
		HiloBuferEscritura buferEscritura = new HiloBuferEscritura(check);
		HiloBuferLectura buferLectura = new HiloBuferLectura(check);
		buferEscritura.start();
		buferLectura.start();
		try {
			buferEscritura.join();
			buferLectura.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
