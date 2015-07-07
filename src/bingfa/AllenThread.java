package bingfa;

/**
 * @author fuweiwei.pt
 * 
 */
public class AllenThread extends Thread {

	@Override
	public void run() {
		System.out.println("hello allen" );
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Thread t = new AllenThread();
			t.start();
		}
	}
}
