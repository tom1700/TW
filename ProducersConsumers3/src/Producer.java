import java.util.ArrayList;
import java.util.Random;

public class Producer extends Thread{
	private Monitor buffer;
	private int maxSize;
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
				buffer.put(arr);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("Producer finished putting");
	}
}
