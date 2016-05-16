import java.util.ArrayList;

public class PutMethodRequest implements MethodRequest{
	private Future future;
	private Buffer activeObject;
	private ArrayList<Object> elements;
	
	public boolean guard(){
		if(activeObject.getSize()-activeObject.getToTake()>=elements.size())
			return true;
		return false;
	}
	public void execute(){
		activeObject.put(elements);
		future.setAvailible(true);
		
	}
	public void setFuture(Future future){
		this.future = future;
	}
	public void setActiveObject(Buffer activeObject){
		this.activeObject = activeObject;
	}
	public void setElements(ArrayList<Object>elements){
		this.elements = elements;
	}
}
