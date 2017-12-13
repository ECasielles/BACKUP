package dam.psp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunProcessConectados {
	
	public static void main(String[] args) {
		if(args.length <= 0) {
			System.err.println("Se necesita como argumento un proceso");
			System.exit(-1);
		}
		
		ProcessBuilder pb = new ProcessBuilder(args);
		
		//Activo que el flujo de error del proceso hijo
		//sea capturado por el proceso padre
		pb.redirectErrorStream(true);
		
		try {
			Process proceso = pb.start();
			MostrarSalidaProceso(proceso);
			System.exit(0);
			
		} catch(InterruptedException | IOException e) {
			System.err.println("Error de E/S");
			System.exit(-1);
		}
	}	
	

	private static void MostrarSalidaProceso(Process proceso) throws InterruptedException, IOException {
		int contador = 0;			
		String linea;
		//int retorno = proceso.waitFor();  <- Lo comento porque si no, el ping no acaba
			
		//System.out.println("El proceso hijo ha devuelto " + retorno);
		//Creamos un lector flujo de bytes a cadena
		InputStreamReader lector = new InputStreamReader(proceso.getInputStream(), "utf-8");
		//Creamos un almacén del lector flujo de cadena
		BufferedReader bufer = new BufferedReader(lector);  
							
		while ((linea = bufer.readLine()) != null && contador <= 5) {
			System.out.println(linea);
			contador++;
		}
				
		bufer.close();
		lector.close();
		if (proceso != null) proceso.destroy();
		
	}
	
}

