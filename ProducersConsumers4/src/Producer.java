
import java.util.ArrayList;
import java.util.Random;

public class Producer extends Thread{
	private BufferProxy buffer;
	private int maxSize;
	private Random rand;
	
	public Producer(BufferProxy buffer,int maxSize){
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
		while(!Thread.currentThread().isInterrupted()){
			arr = drawArray();
			buffer.put(arr);
		}
	}
}
