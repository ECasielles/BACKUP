package dam.psp;

/**
 * 4. Escribe una clase llamada Supermercado que implemente el funcionamiento de N cajas.
 * Los M clientes del supermercado estarán un tiempo aleatorio comprando y después seleccionarán
 * de forma aleatoria en qué caja se colocan para pagar. Cuando les toque su turno serán atendidos
 * pagando e ingresando en la variable Resultados del supermercado. Se deben crear tantos
 * hilos como clientes y los parámetros N y M se ingresan como parámetros de la aplicación.
 * Para simplificar, el valor del pago de cada cliente puede ser aleatorio en el momento del pago.
 *
 * @author Enrique Casielles Lapeira
 */

class Cliente extends Thread {	
	Supermercado supermercado;
	int numCliente;
	int numCaja;
	Caja miCaja;
	int pago;
	int turno;
	
	public Cliente(int numCliente, Supermercado supermercado, int numCaja) {
		this.numCliente = numCliente;
		this.supermercado = supermercado;
		this.numCaja = numCaja;
		this.miCaja = supermercado.cajas[numCaja];
		this.pago = (int) Math.round(1000 * Math.random());
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(1000 + 100 * Math.round(10 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int turno = miCaja.pideTurno();
		System.out.println("Cliente " + numCliente + " hace cola en caja " + numCaja + " con turno " + turno);
		miCaja.colaCaja(numCliente, numCaja, turno);
		System.out.println("Cliente " + numCliente + " paga " + pago + " euros");
	}
}

class Supermercado {
	Caja[] cajas;
	int numResultados;
	int numCajas;
	
	public Supermercado(int numOperaciones, int numCajas) {
		this.numResultados = numOperaciones;
		this.numCajas = numCajas;
		this.cajas = new Caja[numCajas];			
		for (int i = 0; i < cajas.length; i++)
			cajas[i] = new Caja(i, this);
	}
}

class Caja {
	Supermercado supermercado;
	int numCaja;
	int turno;
	int despacho;
	
	public Caja(int nCaja, Supermercado supermercado) {
		this.numCaja = nCaja;
		this.supermercado = supermercado;
		this.turno = 0;
		this.despacho = 0;
	}

	//Da turno en orden de llegada
	public synchronized int pideTurno() {
		return despacho++;
	}
	//Despacha clientes en orden de llegada
	public synchronized void colaCaja(int numCliente, int numCaja, int turnoCliente) {
		while(turno < turnoCliente)
			try {
				System.out.println("Cliente " + numCliente + " espera con turno " + turnoCliente);
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		try {
			Thread.sleep(1000 + 100 * Math.round(10 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		turno++;
		//Debe ser notifyAll para poder saltarse el planificador
		//e imponerle FIFO en vez de LIFO, aunque aumenta el tiempo y el consumo
		notifyAll();
	}	
}

public class Ejercicio_4 {

	public static void main(String[] args) {
		int nClientes = Integer.parseInt(args[0]);
		int nCajas = Integer.parseInt(args[1]);
		Supermercado supermercado = new Supermercado(nClientes, nCajas);		
		Cliente[] clientes = new Cliente[nClientes];
		int numCaja = 0;
		long msInicio = System.currentTimeMillis();
				
		for (int i = 0; i < clientes.length; i++) {
			numCaja = (int) Math.floor(Math.random() * supermercado.numCajas);
			clientes[i] = new Cliente(i, supermercado, numCaja);
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
		//Test 1: 12904 milisegundos
		//Test 2: 11304 milisegundos
		//Test 3: 11006 milisegundos
		//
		//Promedio: 11738 milisegundos

	}

}
