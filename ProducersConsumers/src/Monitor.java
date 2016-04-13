import java.util.ArrayList;

public class Monitor {
	private ArrayList<Object> buffer;
	private int N = 100,takeIndex,putIndex,toTake;
	public Monitor(){
		buffer = new ArrayList<Object>();
		for(int i=0;i<N;i++)
			buffer.add(new Object());
		toTake = 0;
		putIndex = 0;
		takeIndex = 0;
	}
	public synchronized void put(Object element){
		while(toTake == N){
			try {
				wait();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		if(toTake==0)
			notify();
		System.out.println("Producer puts "+toTake);
		buffer.set(putIndex,element);
		putIndex = (putIndex+1)%N;
		toTake++;
	}
	public synchronized Object take(){
		while(toTake ==0){
			try{
				wait();
			}catch (InterruptedException e) {e.printStackTrace();}
		}
		if(toTake==N)
			notify();
		System.out.println("Consumer takes "+toTake);
		Object result = buffer.get(takeIndex);
		takeIndex = (takeIndex+1)%N;
		toTake--;
		return result;
	}
}
