
public class TakeMethodRequest implements MethodRequest{
	private Future future;
	private Buffer activeObject;
	private int amount;
	
	public boolean guard(){
		if(activeObject.getToTake()>=amount)
			return true;
		return false;
	}
	public void execute(){
		future.SetResult(activeObject.take(amount));
		future.setAvailible(true);
	}
	public void setFuture(Future future){
		this.future = future;
	}
	public void setActiveObject(Buffer activeObject){
		this.activeObject = activeObject;
	}
	public void setAmount(int amount){
		this.amount = amount;
	}
}
