import java.util.ArrayList;

public class Buffer {
	private ArrayList<Object> buffer;
	private int N,takeIndex,putIndex,toTake;

	public Buffer(int N){
		this.N = N;
		buffer = new ArrayList<Object>();
		for(int i=0;i<N;i++)
			buffer.add(new Object());
		toTake = 0;
		putIndex = 0;
		takeIndex = 0;
	}
	public void put(ArrayList<Object> elements){
		for(int i=0;i<elements.size();i++){
			buffer.set(putIndex,elements.get(i));
			putIndex = (putIndex+1)%N;
			toTake++;
		}
	}
	public ArrayList<Object> take(int n){
			ArrayList<Object> result = new ArrayList<Object>();
			for(int i=0;i<n;i++){
				result.add(buffer.get(takeIndex));
				takeIndex = (takeIndex+1)%N;
				toTake--;
			}
			return result;
	}
	public int getToTake(){
		return toTake;
	}
	public int getSize(){
		return N;
	}
}
