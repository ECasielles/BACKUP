package dam.psp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class ControladorDeFicheros {
	private PrintWriter fichero;	
	
	public ControladorDeFicheros(String nomFichero) {
		try {
			fichero = new PrintWriter(new FileWriter(nomFichero));			
		}
		catch(IOException e) {
			System.err.println("Error al crear el fichero: " + e.getMessage());
		}
	}
	
	public void println(String cadena) {
		//El objeto fichero es un perfecto semáforo
		synchronized(fichero) {
			for (int i = 0; i < cadena.length(); i++)
				fichero.print(cadena.substring(i, i+1));
			fichero.println("");
		}
	}
	
	public void print(String cadena) throws InterruptedException { fichero.print(cadena); }
	
	public void close() { fichero.close(); }
}

class Escritor extends Thread {	
	private ControladorDeFicheros destino;	
	private String contenido = "";
	
	public Escritor(ControladorDeFicheros fichero, String cadena) {
		this.destino = fichero;
		this.contenido += cadena;
	}
	
	@Override
	public void run() { destino.println(contenido); }
}

public class PruebaHilosSincronizadosFichero {
	public static void main(String[] args) throws InterruptedException {
		ControladorDeFicheros cf;
		
		cf = new ControladorDeFicheros("poema.txt");
		
		String parrafo1 = "Hola caracola.";
		String parrafo2 = "En un lugar de la Mancha...";	
		Escritor cervantes = new Escritor(cf, parrafo1);
		Escritor shakespeare = new Escritor(cf, parrafo2);
				
		shakespeare.start();
		cervantes.start();
		
		shakespeare.join();
		cervantes.join();
		
		cf.close();
		
		System.out.println("Los datos se han escrito en el fichero");
	}
}

