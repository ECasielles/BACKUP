package dam.psp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//Este método de comunicar procesos sólo funciona con procesos bloqueantes
public class ComunicadorDeProcesos {

	public static void main(String[] args) {
		
		ProcessBuilder procesoA = new ProcessBuilder("/bin/ls", "-l", "/home/usuario");
		ProcessBuilder procesoB = new ProcessBuilder("/usr/bin/tr", "a", "@");
		
		try {
			Process pA = procesoA.start();
			Process pB = procesoB.start();			
			
			BufferedReader salidaA = new BufferedReader(new InputStreamReader(pA.getInputStream(), "utf-8"));
			BufferedWriter entradaB = new BufferedWriter(new OutputStreamWriter(pB.getOutputStream(), "utf-8"));
			BufferedReader salidaB =  new BufferedReader(new InputStreamReader(pB.getInputStream(), "utf-8"));
			
			//Bucle de lectura y escritura
			String linea;
			while((linea = salidaA.readLine()) != null) {
				entradaB.write(linea);
				entradaB.newLine();
			}
			System.out.println("1");
			
			String salida;
			//Salida de pB por consola
			while((salida = salidaB.readLine()) != null) {
				System.out.println(salida);
			}
			System.out.println("2");
			
			salidaA.close();
			entradaB.close();
			salidaB.close();
			System.out.println("3");
			
			int finA = pA.waitFor();
			int finB = pB.waitFor();
			System.out.println("4");			
			
			System.out.println("Procesos A y B finalizados con resultado: " + finA + " y " + finB);
			
		} catch (IOException e) {
			System.out.println("Error de E/S o excepción por interrupción");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
