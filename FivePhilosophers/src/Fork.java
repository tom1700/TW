
public class Fork {
	private boolean free;
	
	public Fork(){
		free = true;
	}
	public synchronized void take(){
		while(free==false){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		free=false;
	}
	public synchronized void put(){
		free=true;
		notifyAll();
	}
}
