package dam.psp;

class Almacen { 
	int piezas = 8000;
	int maxPiezas = 20000;
	boolean trabajando = true;
	int periodo = 0;
	int dia = 1;

	public Almacen() { }
	
	public synchronized boolean getEstado() {
		return trabajando;
	}
	public synchronized void enviar(int piezasEntrada) {
		while (periodo == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (trabajando) {
			System.out.println("Llegan " + piezasEntrada + " piezas.");
			if (maxPiezas > piezas + piezasEntrada) {
				piezas += piezasEntrada;
				System.out.println("Hay " + piezas + " en el almacén.");
				periodo = (periodo + 1) % 4;
			} else {
				trabajando = false;
				System.out.println("¡No caben más piezas en el almacén!");
			}
		}
		notify();
	}
	public synchronized void retirar(int piezasSalida) {
		while (periodo > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (trabajando) {
			System.out.println("Día " + dia++);
			periodo = (periodo + 1) % 4;
			System.out.println("Pedido de " + piezasSalida + " piezas.");
			if (piezas > piezasSalida) {
				piezas -= piezasSalida;
				System.out.println("Hay " + piezas + " en el almacén.");
			} else {
				trabajando = false;
				System.out.println("¡No hay suficientes piezas en el almacén!");
			}
		}
		notify();
	}
}

class Envio extends Thread {
	Almacen almacen;

	public Envio(Almacen almacen) {
		this.almacen = almacen;
	}

	@Override
	public void run() {
		while (almacen.getEstado()) {
			try {
				sleep(800); // simulación de 8 horas
				almacen.enviar(400 + (int) (601 * Math.random()));

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
class Retirada extends Thread {
	Almacen almacen;

	public Retirada(Almacen almacen) {
		this.almacen = almacen;
	}

	@Override
	public void run() {
		while (almacen.getEstado()) {
			try {
				sleep(2400); // simulación de un día
				almacen.retirar(2000 + (int) (501 * Math.random()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
public class Principal {
	public static void main(String[] args){
		Almacen unAlmacen = new Almacen();
		Retirada retirada = new Retirada(unAlmacen);
		Envio envio = new Envio(unAlmacen);
		retirada.start();
		envio.start();
		try {
			retirada.join();
			envio.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
