import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class Consumer extends Thread{
	private Monitor buffer;
	private int maxSize;
	private Random rand;
	private Instant start;
	private Instant end;
	
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
				start = Instant.now();
				buffer.take(num);
				end = Instant.now();
				System.out.print(Duration.between(start, end).getNano()+" ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
