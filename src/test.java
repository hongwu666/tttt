import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

public class test {

	private static ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) {

		run1();
		run2();

	}

	class run1 extends Thread {
		
		
		
	}

	public static void run2() {
		lock.lock();
		lock.lock();
		Runnable run = new Runnable() {

			@Override
			public void run() {
				String text = "run2";
				System.out.println(text);

			}
		};
		new Thread(run).start();
	}
}
