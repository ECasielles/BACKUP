package dam.psp;

/**
* 5.Escribe una clase llamada SuperModerno que implemente el caso anterior,
* pero ahora los clientes se colocan en una única cola.
* Cuando cualquier caja esté disponible, el primero de la cola será atendido en esa caja.
* Calcula el tiempo medio de espera por cliente y compáralo con el tiempo medio
* del ejercicio anterior, para decidir que solución es más óptima.
*
* @author Enrique Casielles Lapeira
*/

class ClienteModerno extends Thread {
	int numCliente;
	SupermercadoModerno supermercadoModerno;
	int turno;
	int pago;
	
	public ClienteModerno(int numCliente, SupermercadoModerno supermercadoModerno) {
		this.numCliente = numCliente;
		this.supermercadoModerno = supermercadoModerno;
		this.pago = (int) Math.round(1000 * Math.random());
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(1000 + 100 * Math.round(10 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int turno = supermercadoModerno.pideTurno();
		System.out.println("Cliente " + numCliente + " hace cola con turno " + turno);
		supermercadoModerno.colaCajas(numCliente, turno);
		supermercadoModerno.despacha(numCliente);
		System.out.println("Cliente " + numCliente + " paga " + pago + " euros");
	}
	
}

class CajaModerna {
	SupermercadoModerno supermercado;
	int numCaja;
	boolean ocupada;
	
	public CajaModerna(int numCaja, SupermercadoModerno supermercado) {
		this.supermercado = supermercado;
		this.numCaja = numCaja;
		this.ocupada = false;
	}
}

class SupermercadoModerno {
	CajaModerna[] cajas;
	int turno;
	int numOperaciones;
	int numCajas;
	int despacho;
	int cajasDisponibles;

	public SupermercadoModerno(int numOperaciones, int numCajas) {
		this.numOperaciones = numOperaciones;
		this.numCajas = numCajas;
		this.turno = numCajas;
		this.despacho = 0;
		this.cajasDisponibles = numCajas;
		this.cajas = new CajaModerna[numCajas];			
		for (int i = 0; i < cajas.length; i++)
			cajas[i] = new CajaModerna(i, this);
	}

	public synchronized int pideTurno() {
		return despacho++;
	}
	public synchronized void colaCajas(int numCliente, int turnoCliente) {
		try {
			//Espera su turno
			while(turno < turnoCliente)
				wait();
		} catch (InterruptedException e) {				
			e.printStackTrace();
		}
		turno++;
		notifyAll();
	}
	public synchronized void despacha(int numCliente) {
		int numCaja = 0;
		//Busca la caja disponible
		while(cajas[numCaja].ocupada)
			numCaja = (numCaja + 1) % numCajas;
		cajas[numCaja].ocupada = true;
		try {
			Thread.sleep(1000 + 100 * Math.round(10 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Despachado " + numCliente + " en caja " + numCaja);
		cajas[numCaja].ocupada = false;
	}
		
}

public class Ejercicio_5 {

	public static void main(String[] args) {
		int nClientes = Integer.parseInt(args[0]);
		int nCajas = Integer.parseInt(args[1]);
		SupermercadoModerno supermercado = new SupermercadoModerno(nClientes, nCajas);		
		ClienteModerno[] clientes = new ClienteModerno[nClientes];
		long msInicio = System.currentTimeMillis();
				
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new ClienteModerno(i, supermercado);
		}
		for (int i = 0; i < clientes.length; i++) {
			clientes[i].start();
		}
		for (int i = 0; i < clientes.length; i++)
			try {
				clientes[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		long msFin = System.currentTimeMillis();
		System.out.println("Duración rebajas: " + (msFin - msInicio) + " milisegundos");
	}
}
