package bingfa;

public class HelloThread implements Runnable {

	private String name;

	public HelloThread(String name) {
		super();
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println("asd");
	}

}