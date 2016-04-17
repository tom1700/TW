import java.util.Random;

public class Consumer extends Thread{
	private Monitor buffer;
	private int maxSize;
	private Random rand;
	public Consumer(Monitor buffer,int maxSize){
		this.buffer = buffer;
		this.maxSize = maxSize;
		rand = new Random();
	}
	public void run(){
		int num;
		for(;;){
			try {
				num = rand.nextInt(maxSize)+1;
				buffer.take(num);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
