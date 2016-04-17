
public class Test {
	public static void main(String[]args){
		Monitor m = new Monitor(100);
		for(int i=0;i<10;i++){
			new Consumer(m,50).start();
			new Producer(m,50).start();
		}
	}
}
