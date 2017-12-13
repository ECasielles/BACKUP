package dam.psp;

//Caso sencillo con un sólo cambio de guarda de bucle infinito
//de bloqueo de hilos y un notifyAll con esquema FIFO de planificador.
class Bienvenida {
	
	private boolean comienzaLaClase;
	
	public Bienvenida() {
		this.comienzaLaClase = false;
	}
	
	//Ver "Spurius Wakeup" para comprobar la necesidad de bucle(wait())
	public synchronized void saludarAlProfesor(String alumno) {
		System.out.println("El Alumno: " + alumno + " quiere saludar");
		//Bloqueo de los hilos hasta que tengan permiso
		//Este tipo de permiso es interrumplible
		while(!comienzaLaClase)
			try {
				wait();
				//En este caso se puede poner el mensaje aquí
				//por las circunstancias del ejercicio, porque
				//nada más hay un saludo.
				//Sin embargo no es válido en la vida real.
				//System.out.println(alumno + " dice ¡Buenos días!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println(alumno + " dice ¡Buenos días!");
		
	}
	
	//Puede salidar el primero porque el resto de hilos se encuentran
	//bloqueados en el wait y el objeto compartido está liberado
	public synchronized void profesorSaluda(String profesor) {
		System.out.println(profesor + " dice: Buenos días");
		//Aquí se cambia la condición de los bucles while
		//que controlan los bloqueos de los hilos
		this.comienzaLaClase = true;
		this.notifyAll();
	}
}
class Profesor extends Thread {
	Bienvenida saludo;
	String nombre;
	
	public Profesor(Bienvenida bienvenida, String nombre) {
		this.saludo = bienvenida;
		this.nombre = nombre;
	}	
	
	@Override
	public void run() {
		try {
			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		saludo.profesorSaluda(nombre);
	}
	
}

class Alumno extends Thread {	
	Bienvenida saludo;
	String nombre;
	
	public Alumno(Bienvenida bienvenida, String nombre) {
		this.saludo = bienvenida;
		this.nombre = nombre;
	}
	
	@Override
	public void run() {
		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		saludo.saludarAlProfesor(nombre);
	}
}

public class SaludoProfesorAlumnos {

	public static void main(String[] args) {
		//Objeto compartido
		Bienvenida saludo = new Bienvenida();
		
		int nAlumnos = Integer.parseInt(args[0]);
		Alumno[] alumnos = new Alumno[nAlumnos];
		
		for (int i = 0; i < alumnos.length; i++) {
			alumnos[i] = new Alumno(saludo, "Alumno " + i);
			alumnos[i].start();
		}
		Profesor profe = new Profesor(saludo, "Eliseo Moreno");
		profe.start();
		
		//Se ve que el planificador los despacha con
		//un planificador FIFO
				
		try {
			for (int i = 0; i < alumnos.length; i++) {
				alumnos[i].join();
			}
			profe.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
