import java.util.ArrayList;

class BufferProxy {
   private Scheduler scheduler;
   private Buffer activeObject;

   public BufferProxy(Buffer activeObject) {
      this.scheduler = new Scheduler();
      scheduler.start();
      this.activeObject = activeObject;
   }

   public Future put(ArrayList<Object> elements) {
      Future future = new Future();
      PutMethodRequest request = new PutMethodRequest();
      request.setElements(elements);
      request.setFuture(future);
      request.setActiveObject(this.activeObject);
      scheduler.enqueueProd(request);
      return future;
   }

   public Future take(int amount) {
	  Future future = new Future();
      TakeMethodRequest request = new TakeMethodRequest();
      request.setAmount(amount);
      request.setFuture(future);
      request.setActiveObject(activeObject);
      scheduler.enqueueCon(request);
      return future;
   }
   
   public void interruptScheduler(){
	   this.scheduler.interrupt();
   }
}
