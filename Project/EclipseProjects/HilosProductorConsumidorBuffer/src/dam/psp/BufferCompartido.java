package dam.psp;

public class BufferCompartido implements Buffer {
	
	private int bufer = -1;
	private int contadorOcupado = 0;

	@Override
	public synchronized int leer() {
		while (contadorOcupado == 0)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		mostrarEstado(Thread.currentThread().getName() + "consigue leer " + bufer);
		//Se consume la lectura y ya no puede leer otro hilo
		contadorOcupado = 0;
		notifyAll();
		return bufer;
	}

	@Override
	public synchronized void escribir(int valor) {
		while (contadorOcupado == 1)
			try {
				wait();
				System.out.println("Productor " 
						+ Thread.currentThread().getName()
						+ " trata de escribir.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		bufer = valor;
		mostrarEstado(Thread.currentThread().getName() + "consigue escribir " + bufer);
		contadorOcupado = 1;
		notifyAll();
	}
	
	@Override
	public void mostrarEstado(String cadena) {
		StringBuffer linea = new StringBuffer(cadena);
		linea.setLength(80);
		//linea.append(bufer + " " + contadorOcupado);
		System.out.println(linea);
		System.out.println();		
	}

}
