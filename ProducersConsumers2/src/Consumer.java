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
		for(int i=0;i<1000;i++){
			try {
				num = rand.nextInt(maxSize)+1;
				buffer.take(num);
				System.out.println(i+" Consumer takes:"+num);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
