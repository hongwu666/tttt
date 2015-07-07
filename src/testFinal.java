public class testFinal {

	public static void main(String[] args) {
		final StringBuffer str = new StringBuffer();
		str.append("a");
		str.setLength(0);
		str.append("asd");
		System.out.println(str.toString());
	}
}
