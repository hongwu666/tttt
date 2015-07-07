package bingfa;

import java.util.ArrayList;
import java.util.List;

/**
 * N������,ƽ���ָ�M�̴߳���
 * 
 * @author XF
 */
public class NTaskPerThread {
	int task_num = 12;
	int thread_num = 3;
	List<Task> list = new ArrayList<NTaskPerThread.Task>();
	long total = 0;// ��������ʱ��,���ڱȽϲ�ͨ�߳�������Ч��

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

		// ��ÿ���̷߳�������,Ӧlist������0��ʼ,���Է��������Ŵ�0��ʼ
		int num = task_num / thread_num;// �����ӿ��ܻ�������,Ӧ�ð�����Ҳ��̯
		if (task_num % thread_num != 0) {
			num++;// ���������(һ��С��thread_num),��ǰ����̷߳�̯��,ÿ���̶߳���һ������
		}

		for (int i = 0; i < thread_num; i++) {
			int start = i * num;
			int end = Math.min((i + 1) * num, list.size());// ���һ���߳�������ܲ���
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
