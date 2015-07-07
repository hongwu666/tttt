package bingfa;

import java.util.ArrayList;
import java.util.List;

/**
 * N个任务,平均分给M线程处理
 * 
 * @author XF
 */
public class NTaskPerThread {
	int task_num = 12;
	int thread_num = 3;
	List<Task> list = new ArrayList<NTaskPerThread.Task>();
	long total = 0;// 任务运行时间,用于比较不通线程数量的效率

	public static void main(String[] args) {
		NTaskPerThread perThread = new NTaskPerThread();
		perThread.test();
	}

	public NTaskPerThread() {
	}

	public void test() {

		for (int i = 0; i < task_num; i++) {
			list.add(new Task(i));
		}

		// 给每个线程分配任务,应list从索引0开始,所以分配任务编号从0开始
		int num = task_num / thread_num;// 这样子可能还有余数,应该把余数也分摊
		if (task_num % thread_num != 0) {
			num++;// 如果有余数(一定小于thread_num),则前面的线程分摊下,每个线程多做一个任务
		}

		for (int i = 0; i < thread_num; i++) {
			int start = i * num;
			int end = Math.min((i + 1) * num, list.size());// 最后一个线程任务可能不够
			new TaskThread(start, end).start();
		}

	}

	public class Task {
		private int n;

		public Task(int n) {
			this.n = n;
		}

		public void run() {
			System.out.println("run task num : " + n);
			for (int i = 0; i < 10000000; i++) {
				int s = 0;
				s += i;
			}
		}
	}

	public class TaskThread extends Thread {
		int start;
		int end;

		public TaskThread(int start, int end) {
			this.start = start;
			this.end = end;

		}

		@Override
		public void run() {
			long s = System.currentTimeMillis();
			for (; start < end; start++) {
				list.get(start).run();
			}
			total += (System.currentTimeMillis() - s);
			System.out.println(total);
		}
	}

}
