
public class Test {
	public static void main(String[]args){
		Monitor m = new Monitor();
		for(int i=0;i<100;i++){
			new Consumer(m).start();
			new Producer(m).start();
		}
	}
}
