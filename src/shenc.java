import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class shenc {

	private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
	private static int i = 0;

	public static void product() {
		ExecutorService service = Executors.newFixedThreadPool(1);
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					i++;
					queue.add(i);

				}
			}
		};

		service.execute(runnable);
		service.shutdown();
	}

	public static void cosume() {
		ExecutorService service = Executors.newFixedThreadPool(1);
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				while (true) {
					System.out.println(queue.poll());
				}

			}
		};
		service.execute(runnable);
		service.shutdown();
	}

	public static void main(String[] args) {
		product();
		cosume();
	}
}
