package bingfa;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CountDownLatch latch = new CountDownLatch(2);
		for (int i = 0; i < 3; i++) {
			new Thread(new HelloThread("hello " + i)).start();
			
		}
		latch.countDown();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main 主线程结束");
	}

}
