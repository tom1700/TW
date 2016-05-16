
public class Test {
	public static void main(String args[]){
		Butler b = new Butler();
		Fork forks[] = new Fork[5];
		Philosopher philosophers[] = new Philosopher[5];
		for(int i=0;i<5;i++){
			forks[i]=new Fork();
		}
		for(int i=0;i<5;i++){
			(philosophers[i] = new Philosopher(i,forks,b)).start();	
		}
	}
}
