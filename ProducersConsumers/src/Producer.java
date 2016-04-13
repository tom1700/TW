
public class Producer extends Thread{
	private Monitor buffer;
	public Producer(Monitor buffer){
		this.buffer = buffer;
	}
	public void run(){
		for(int i=0;i<1000;i++){
			buffer.put((Object)5);
		}
	}
}
