import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler extends Thread{
	MethodRequest request;
	private Queue<PutMethodRequest> prodQueue;
	private Queue<TakeMethodRequest> conQueue;
	private Queue<MethodRequest> prodConQueue;
	public Lock l;
	Condition empty;
	
	public Scheduler(){
		l = new ReentrantLock();
		empty = l.newCondition();
		prodQueue = new LinkedList<PutMethodRequest>();
		conQueue = new LinkedList<TakeMethodRequest>();
		prodConQueue = new LinkedList<MethodRequest>();
	}
	public void enqueueProd(PutMethodRequest m){
		l.lock();
		try{
			prodQueue.add(m);
			prodConQueue.add(m);
			empty.signal();
		}finally{
			l.unlock();
		}
	}
	public void enqueueCon(TakeMethodRequest m){
		l.lock();
		try{
			conQueue.add(m);
			prodConQueue.add(m);
			empty.signal();
		}finally{
			l.unlock();
		}
	}
	public void run(){
		while(!Thread.currentThread().isInterrupted()){
			l.lock();
			try{
				if(prodConQueue.size()==0){
					empty.await();
				}
				request = prodConQueue.poll();
				if(request instanceof PutMethodRequest){
					request = prodQueue.poll();
					if(request.guard()){
						request.execute();
					}
					else{
						prodConQueue.add(request);
						prodQueue.add((PutMethodRequest)request);
					}
				}
				else if(request instanceof TakeMethodRequest){
					request = conQueue.poll();
					if(request.guard()){
						request.execute();
					}
					else{
						prodConQueue.add(request);
						conQueue.add((TakeMethodRequest)request);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				l.unlock();
			}
		}
	}
	
}
