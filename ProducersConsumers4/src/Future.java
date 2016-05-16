import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Future {
	boolean availible;
	private ArrayList<Object>result;
	private Instant start;
	private Instant end;
	
	public Future(){
		this.availible = false;
		start = Instant.now();
	}
	public boolean isAvailible(){
		return availible;
	}
	public Object get(){
		return result;
	}
	public void setAvailible(boolean availible){
		end = Instant.now();
		this.availible = availible;
		System.out.print(Duration.between(start, end).getNano()+" ");
	}
	public void SetResult(ArrayList<Object>result){
		this.result = result;
	}
}
