
public class Philosopher extends Thread{
	private int seat;
	private Fork forks[];
	private Butler butler;
	
	public Philosopher(int seat,Fork forks[],Butler butler){
		this.seat = seat;
		this.forks = forks;
		this.butler = butler;
	}
	
	public void run(){
		while(true){
			System.out.println("Philosopher "+seat+" is thinking.");
			butler.down();
			forks[seat].take();
			forks[(seat+1)%5].take();
			System.out.println("Philosopher "+seat+" is eating.");
			forks[seat].put();
			forks[(seat+1)%5].put();
			butler.up();
		}
	}
}
