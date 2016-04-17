import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
	private ArrayList<Object> buffer;
	private int N,takeIndex,putIndex,toTake;
	private Lock lock;
	private Condition firstProd;
	private Condition restProd;
	private Condition firstCon;
	private Condition restCon;
	private boolean isFirstProd;
	private boolean isFirstCon;

	public Monitor(int N){
		this.N = N;
		buffer = new ArrayList<Object>();
		for(int i=0;i<N;i++)
			buffer.add(new Object());
		toTake = 0;
		putIndex = 0;
		takeIndex = 0;
		
		lock = new ReentrantLock();
		firstProd = lock.newCondition();
		restProd = lock.newCondition();
		firstCon = lock.newCondition();
		restCon = lock.newCondition();
		isFirstProd = true;
		isFirstCon = true;
	}
	public void put(ArrayList<Object> element) throws InterruptedException{
		lock.lock();
		try{
			if(isFirstProd==false)
				restProd.await();
			isFirstProd=false;
			while(toTake+element.size() > N){
				firstProd.await();
			}
			for(int i=0;i<element.size();i++){
				buffer.set(putIndex,element.get(i));
				putIndex = (putIndex+1)%N;
				toTake++;
			}
			System.out.println("Left:"+toTake);
			isFirstProd = true;
			restProd.signal();
			firstCon.signal();
		}finally{
			lock.unlock();
		}
	}
	public Object take(int n) throws InterruptedException{
		lock.lock();
		try{
			if(isFirstCon == false)
				restCon.await();
			isFirstCon = false;
			while(toTake-n < 0){
				firstCon.await();
			}
			ArrayList<Object> result = new ArrayList<Object>();
			for(int i=0;i<n;i++){
				result.add(buffer.get(takeIndex));
				takeIndex = (takeIndex+1)%N;
				toTake--;
			}
			System.out.println("Left:"+toTake);
			isFirstCon = true;
			restCon.signal();
			firstProd.signal();
			return result;
		}finally{
			lock.unlock();
		}
	}
}
