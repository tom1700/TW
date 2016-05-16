import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
	private ArrayList<Object> buffer;
	private int N,takeIndex,putIndex,toTake;
	private Lock lock;
	private Lock lock2;
	private Lock lock3;
	private Condition producers;
	private Condition consumers;

	public Monitor(int N){
		this.N = N;
		buffer = new ArrayList<Object>();
		for(int i=0;i<N;i++)
			buffer.add(new Object());
		toTake = 0;
		putIndex = 0;
		takeIndex = 0;
		
		lock = new ReentrantLock();
		lock2 = new ReentrantLock();
		lock3 = new ReentrantLock();
		producers = lock.newCondition();
		consumers = lock.newCondition();
	}
	public void put(ArrayList<Object> element) throws InterruptedException{
		lock2.lock();
		try{
			lock.lock();
			try{
				while(toTake+element.size() > N){
					producers.await();
				}
				for(int i=0;i<element.size();i++){
					buffer.set(putIndex,element.get(i));
					putIndex = (putIndex+1)%N;
					toTake++;
				}
				consumers.signal();
			}finally{
				lock.unlock();
			}
		}finally{
			lock2.unlock();
		}
	}
	public Object take(int n) throws InterruptedException{
		lock3.lock();
		try{
			ArrayList<Object> result = new ArrayList<Object>();
			lock.lock();
			try{
				while(toTake-n < 0){
					consumers.await();
				}
				for(int i=0;i<n;i++){
					result.add(buffer.get(takeIndex));
					takeIndex = (takeIndex+1)%N;
					toTake--;
				}
				producers.signal();
				return result;
			}finally{
				lock.unlock();
			}
		}finally{
			lock3.unlock();
		}
	}
}
