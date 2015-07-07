package bingfa;

import java.util.ArrayList;
import java.util.List;

public class Test {

	private int count = 0;

	private List<Integer> list = new ArrayList<Integer>();

	public static void main(String[] args) {
		final Test test = new Test();
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					test.a();
				}
			}).start();
		}
	}

	public void a() {
		count++;
		System.out.println(count);
	}
}
