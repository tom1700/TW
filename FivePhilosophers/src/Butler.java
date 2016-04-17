
public class Butler {
	private int n;
	
	public Butler(){
		n=4;
	}
	public synchronized  void down(){
		while(n==0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		n--;
	}
	public synchronized  void up(){
		n++;
		notifyAll();
	}
}
