package bingfa;

import java.io.File;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueTest {
	public static File extFile = new File("");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BlockingQueue queue = new LinkedBlockingQueue<File>(100);
		ExecutorService exe = Executors.newFixedThreadPool(5);
		exe.submit(new WriteRunnable(queue));
		exe.submit(new WriteRunnable(queue));
		exe.submit(new WriteRunnable(queue));
		exe.submit(new WriteRunnable(queue));
		exe.submit(new WriteRunnable(queue));
		exe.submit(new WriteRunnable(queue));
		for (int i = 0; i < 4; i++) {
			exe.submit(new ReadRunnable("线程" + i, queue));
		}
		exe.shutdown();
	}
}

class WriteRunnable implements Runnable {
	private BlockingQueue queue;

	public BlockingQueue getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue queue) {
		this.queue = queue;
	}

	public WriteRunnable(BlockingQueue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			writeFile(new File("d://fun"));
			writeFile(new File(""));
			// queue.put(new File("D://jboss"));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeFile(File file) throws InterruptedException {
		if (file.isDirectory()) {
			for (File newFile : file.listFiles()) {
				writeFile(newFile);
			}
		} else {
			queue.put(file);
			// if (file != new File("D://jboss"))
			if (file != new File(""))
				System.out.println("队列添加:" + file.getName());
		}
	}
}

class ReadRunnable implements Runnable {
	private BlockingQueue queue;
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public BlockingQueue getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue queue) {
		this.queue = queue;
	}

	public ReadRunnable(String str, BlockingQueue queue) {
		this.queue = queue;
		this.str = str;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				Thread.sleep(1000);
				File file = (File) queue.take();
				// if (file.getName() .equals( new File("D://jboss").getName()))
				// {
				if (file.getName() == new File("").getName()) {
					queue.put(file);
					break;
				} else
					System.out.println(this.str + "从队列中取出文件：" + file.getName());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}