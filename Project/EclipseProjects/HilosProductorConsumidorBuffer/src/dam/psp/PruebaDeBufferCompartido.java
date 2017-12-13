package dam.psp;

public class PruebaDeBufferCompartido {

	public static void main(String[] args) {
		//Buffer bufferCompartido = new BufferCompartido();
		Buffer bufferCompartido = new BufferCompartidoCircular();
		
		Productor productor = new Productor(bufferCompartido, 10);
		Consumidor consumidor1 = new Consumidor(bufferCompartido, 6);
		Consumidor consumidor2 = new Consumidor(bufferCompartido, 4);
		
		StringBuffer encabezado = new StringBuffer("Operación");
		//encabezado.setLength(40);
		//encabezado.append("Bufer Contador ocupado");
		System.out.println(encabezado);
		System.out.println();
		
		bufferCompartido.mostrarEstado("Estado inicial");
		
		productor.start();
		consumidor1.start();
		consumidor2.start();
		
		try {
			productor.join();
			consumidor1.join();
			consumidor2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Hilo principal ha finalizado");
	}

}
