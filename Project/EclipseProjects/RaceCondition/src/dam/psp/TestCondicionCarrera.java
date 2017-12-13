package dam.psp;

import java.util.concurrent.atomic.AtomicInteger;

//El parámetro no es atómico porque no hay exclusión de hilos
class Contador {	
	//Para lecturas no cacheables y que sean siempre limpias
	public static volatile int cuenta = 0;
	//Con AtomicInteger y con final
	public static final AtomicInteger cuentaAtomica = new AtomicInteger(0);
}

class Sumador extends Thread {
	
	@Override
	public void run() {		
		for (int i = 0; i < 10000; i++) {
			Contador.cuenta++;
		}
		
		for (int i = 0; i < 10000; i++) {
			Contador.cuentaAtomica.incrementAndGet();
		}
	}
}
class Restador extends Thread {
	
	@Override
	public void run() {
		for (int i = 0; i < 5000; i++) {
			Contador.cuenta--;
		}
		for (int i = 0; i < 5000; i++) {
			Contador.cuentaAtomica.decrementAndGet();
		}
	}
}

public class TestCondicionCarrera {

	public static void main(String[] args) {
		Sumador s = new Sumador();
		Restador r = new Restador();
		s.start();
		r.start();
		
		try {
			s.join();
			r.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Valor final del contador " + Contador.cuenta);
		System.out.println("Valor final atómico " + Contador.cuentaAtomica);
		
	}

}
