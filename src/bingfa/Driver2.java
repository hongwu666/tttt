package bingfa;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Driver2 { // ...
	public static void main(String[] str) throws InterruptedException {
		int N = 10;
		CountDownLatch doneSignal = new CountDownLatch(N);
		Executor e = Executors.newFixedThreadPool(9);

		for (int i = 0; i < N; ++i)
			// create and start threads
			e.execute(new WorkerRunnable(doneSignal, i));

		doneSignal.await(); // wait for all to finish
	}
}

class WorkerRunnable implements Runnable {
	private final CountDownLatch doneSignal;
	private final int i;

	WorkerRunnable(CountDownLatch doneSignal, int i) {
		this.doneSignal = doneSignal;
		this.i = i;
	}

	public void run() {
		doWork(i);
		doneSignal.countDown();

	}

	public void doWork(int i) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("dowork" + i);
	}
}