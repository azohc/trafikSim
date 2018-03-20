package tp.examples.threads.ex7;

public class MemoryModelEx2 {
	// add the keyword 'volatile' to the next like to make it well-synchronized
	//
	static int f = 0;
	static int g = 0;

	public static void main(String[] args) throws InterruptedException {

		new Thread() {
			public void run() {
				int x = f;
				int y = g;
				System.out.println(x+" "+y);
			};
		}.start();

		Thread.sleep(2000);
		f = 1;
		g = 2;
		System.out.println("Set f to " + f);
	}
}
