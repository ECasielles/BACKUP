package dam.psp;

class HiloF extends Thread {
	
	// f0+f1=f2
	// 0+1=1
	// 1+1=2=2*1+0 	2=(1,0)
	// 1+2=3=2*1+1	3=(1,1)
	// 2+3=5=2*2+1	5=(2,1)
	// 3+5=8=2*3+2	8=(3,2)
	// 5+8=13=2*5+3		13=(5,3)
	// 8+13=21=2*8+5	21=(8,5)
	//
	// 21 = 
	//
	@Override
	public void run() {

		int valPrevio = 0;
		int val = 1;
		
		for (int i = 0; i < 20; i++) {
			val = 2*val + valPrevio;
			valPrevio = val;
			System.out.println(val);
		}
	}	
	
}

public class Fibonacci {

	public static void main(String[] args) {
		HiloF fibonacci = new HiloF();
		fibonacci.start();
		try {
			fibonacci.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
