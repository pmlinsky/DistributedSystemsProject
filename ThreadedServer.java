package semesterProject;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;
import java.util.concurrent.PriorityBlockingQueue;

public class ThreadedServer {

	public static void main(String[] args) {
		
		// Hardcode port number if necessary
		args = new String[] {"30146", "30142", "30123"};
		
		if (args.length != 3)
		{
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		
		
		int portNumber1 = Integer.parseInt(args[0]);
		int portNumber2 = Integer.parseInt(args[1]);
		int portNumber3 = Integer.parseInt(args[2]);

		final int THREADSOFEACH = 2;
		PriorityBlockingQueue<Request> prioritizedRequests = new PriorityBlockingQueue<>();
		
		try (ServerSocket serverToClientSocket = new ServerSocket(portNumber1);
			 ServerSocket serverFromClientSocket = new ServerSocket(portNumber2);
			 ServerSocket serverToAndFromSlaveSocket = new ServerSocket(portNumber3)) {
			
			Vector<Thread> threads = new Vector<>();
			for (int i = 0; i < THREADSOFEACH; i++) {
				
				Vector<Request> receivedRequests = new Vector<>();
				Vector<String> completedRequest = new Vector<>();
				threads.add(new Thread(new ServerToClientThread
						(serverToClientSocket, receivedRequests, completedRequest)));
				threads.add(new Thread(new ServerFromClientThread
						(serverFromClientSocket, receivedRequests, prioritizedRequests)));
				threads.add(new Thread(new ServerToAndFromSlaveThread
						(serverToAndFromSlaveSocket, prioritizedRequests, completedRequest)));
				
			}
			threads.add(new Thread(new AgingThread(prioritizedRequests)));
			for (Thread t : threads)
				t.start();
			
			
			for (Thread t: threads)
			{
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on a port or listening for a connection");
			System.out.println(e.getMessage());
		}
		
	}

}
