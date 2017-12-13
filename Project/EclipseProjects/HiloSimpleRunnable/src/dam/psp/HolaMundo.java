package dam.psp;

class HiloHolaMundo implements Runnable {

	Thread hilo;
	
	//El constructor instancia y arranca su propio hilo
	HiloHolaMundo() {
		//Nombre que le damos al hilo para poder depurarlo
		hilo = new Thread(this, "Nuevo hilo creado");
		System.out.println("Creando hilo nuevo... " + hilo);
		hilo.start();
	}
	
	@Override
	public void run() {
		//Primera instrucción
		System.out.println("Hilo nuevo ha empezado a ejecutarse...");
		
		//Aquí hará cosas
		try {
			hilo.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Última instrucción
		System.out.println("Hilo finalizando...");
	}
	
	public void espera() {
		try {
			hilo.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}

public class HolaMundo {

	public static void main(String[] args) {
		System.out.println("Hola desde el hilo principal (main)");

		//En este caso el hilo hijo finaliza después del padre
		//new HiloHolaMundo();		
		//En este caso el hilo hijo finaliza antes del padre
		HiloHolaMundo hilo = new HiloHolaMundo();
		hilo.espera();		
		
		System.out.println("Hilo principal (main) finalizando...");		
	}

}
