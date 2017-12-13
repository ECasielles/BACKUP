package dam.psp;

class Compartido {
	public static int c1, c2 = 0;
	
	private static final Object mutex1 = new Object();
	private static final Object mutex2 = new Object();
	
	public static void incrementarC1() {
		//<SC1>
		synchronized (mutex1) {
			c1++;
		}
		//</SC1>
	}
	public static void incrementarC2() {
		//<SC2>
		//Usando aqui mutex1 se observa que tarda el doble 
		synchronized (mutex2) {
			c2++;
		}
		//</SC2>
	}
}

class HilosMutex extends Thread {
	
	@Override
	public void run() {
		Compartido.incrementarC1();
		Compartido.incrementarC2();
	}
}

public class HilosExclusionMutua {

	public static void main(String[] args) {
		
		int N = Integer.parseInt(args[0]);
		
		HilosMutex hilos[] = new HilosMutex[N];
		System.out.println("Creando " + N + " hilos");
		
		for (int i = 0; i < hilos.length; i++) {
			hilos[i] = new HilosMutex();
			hilos[i].start();
		}
		for (int i = 0; i < hilos.length; i++) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("C1 = " + Compartido.c1);
		System.out.println("C2 = " + Compartido.c2);
	}

}
