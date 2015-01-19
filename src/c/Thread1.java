package c;

public class Thread1 extends Thread{
	Model MyModel;
	Thread1(Model MyModel) {
		this.MyModel = MyModel;
	}

	@Override
	public void run() {
		while (true) {
			MyModel.compare();
			MyModel.fixagrupperna();
			try {
				sleep(1);
			} catch (InterruptedException e) {
				System.out.println("FEEL");
			}
		}
		
	}
}