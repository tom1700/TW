
public class Consumer extends Thread{
	private Monitor buffer;
	public Consumer(Monitor buffer){
		this.buffer = buffer;
	}
	public void run(){
		for(int i=0;i<1000;i++){
			buffer.take();
		}
	}
}
