package dam.psp;

class Inutil {
	
	private int a,b = 0;
	
	public void marcar_5() {
		a = 5; 
		//Si se cuela un hilo aquí en medio esVerdad vale falso!
		System.out.println("Marcando... no me metas bulla");
		b = 5;
	}
	//Parece que devuelve siempre true...
	public boolean esVerdad() {
		return a == 0 || b == 5;
	}
}

class HiloA extends Thread {
	private Inutil in;
	
	public HiloA(Inutil in) {
		this.in = in;
	}
	@Override
	public void run() {
		in.marcar_5();
	}
}

class HiloB extends Thread {
	private Inutil in;
	
	public HiloB(Inutil in) {
		this.in = in;
	}
	@Override
	public void run() {
		if (in.esVerdad())
			System.out.println("Verdadero");
		else
			System.out.println("Más falso que Judas");
	}
	
}

public class PruebaHilosSincronizados {
	
	public static void main(String[] args) {
		Inutil in = new Inutil();
		
		HiloA ha = new HiloA(in);
		HiloB hb = new HiloB(in);
		
		
		ha.start();
		hb.start();
		
		try {
			ha.join();
			hb.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
