package dam.psp;

public class BufferCompartidoCircular implements Buffer {

	private int bufer[] = {-1, -1, -1};
	private int contadorOcupado = 0;
	private int posLectura = 0;
	private int posEscritura = 0;
	
	@Override
	public synchronized int leer() {
		String hiloLlamador = Thread.currentThread().getName();
		
		while(contadorOcupado == 0) {
			try {
				System.out.println(hiloLlamador + " trata de leer");
				mostrarEstado("bufer vacío, " + hiloLlamador + " debe esperar");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int valorLeido = bufer[posLectura];
		contadorOcupado--;
		posLectura = (posLectura + 1) % bufer.length;
		mostrarEstado(hiloLlamador + " consigue leer " + valorLeido);
		mostrarSalida();
		
		notify();
		return valorLeido;
	}

	@Override
	public synchronized void escribir(int valor) {
		String hiloLlamador = Thread.currentThread().getName();
		
		while(contadorOcupado == bufer.length) {
			try {
				System.out.println(hiloLlamador + " trata de escribir");
				mostrarEstado("bufer lleno, " + hiloLlamador + " debe esperar");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		bufer[posEscritura] = valor;
		contadorOcupado++;
		posEscritura = (posEscritura + 1) % bufer.length;
		mostrarEstado(hiloLlamador + " consigue escribir " + valor);
		mostrarSalida();
		notify();
	}

	@Override
	public void mostrarEstado(String cadena) {
		StringBuffer linea = new StringBuffer(cadena);
		//linea.setLength(80);
		//linea.append(bufer + " " + contadorOcupado);
		System.out.println(linea);
		System.out.println();
	}
	
	public String mostrarSalida() {
		String salida = "(huecos ocupados: " + contadorOcupado + ")\nhuecos:    ";
		for (int i = 0; i < bufer.length; i++) {
			salida += " " + bufer[i] + " ";
		}
		salida += "\n            ";
		for (int i = 0; i < bufer.length; i++) {
			if(i == posEscritura && posEscritura == posLectura)
				salida += " EL ";
			else if (i == posEscritura)
				salida += " E  ";
			else if (i == posLectura)
				salida += " L  ";
			else
				salida += "    ";
		}
		salida += '\n';
		System.out.println(salida);
		return salida;
	}

}
