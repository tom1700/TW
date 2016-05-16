
import java.util.Random;

public class Consumer extends Thread{
	private BufferProxy buffer;
	private int maxSize;
	private Random rand;
	
	public Consumer(BufferProxy buffer,int maxSize){
		this.buffer = buffer;
		this.maxSize = maxSize;
		rand = new Random();
	}
	public void run(){
		int num;
		while(!Thread.currentThread().isInterrupted()){
			num = rand.nextInt(maxSize)+1;
			buffer.take(num);
		}
	}
}
