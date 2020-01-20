package semesterProject;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class AgingThread implements Runnable {
	
	private PriorityBlockingQueue<Request> prioritizedRequests;
	
	public AgingThread(PriorityBlockingQueue<Request> prioritizedRequests) {
		this.prioritizedRequests = prioritizedRequests;
	}
	
	@Override
	public void run() {
		
		try {
			TimeUnit.SECONDS.sleep(100);
			for (Request request: prioritizedRequests) {
				request.incrementImportance();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
