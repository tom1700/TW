import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

public class Producer extends Thread{
	private Monitor buffer;
	private int maxSize;
	private Instant start;
	private Instant end;
	
	Random rand;
	public Producer(Monitor buffer,int maxSize){
		this.buffer = buffer;
		this.maxSize = maxSize;
		rand = new Random();
	}
	private ArrayList<Object> drawArray(){
		ArrayList<Object> arr= new ArrayList<Object>();
		for(int i=0;i<rand.nextInt(maxSize)+1;i++){
			arr.add((Object)1);
		}
		return arr;
	}
	public void run(){
		ArrayList<Object>arr;
		for(;;){
			try {
				arr = drawArray();
				start = Instant.now();
				buffer.put(arr);
				end = Instant.now();
				System.out.print(Duration.between(start, end).getNano()+" ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
