package semesterProject;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ThreadedServer {

	public static void main(String[] args) {
		
		// Hardcode port number if necessary
		args = new String[] {"30121", "30142"};
		
		if (args.length != 2)
		{
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		
		
		int portNumber1 = Integer.parseInt(args[0]);
		int portNumber2 = Integer.parseInt(args[1]);
		final int THREADSOFEACH = 2;
		ArrayList<Request> requests = new ArrayList<>();
		
		try (ServerSocket clientSocket = new ServerSocket(portNumber1);
			 ServerSocket slaveSocket = new ServerSocket(portNumber2);) {
			ArrayList<Thread> threads = new ArrayList<Thread>();
			for (int i = 0; i < THREADSOFEACH; i++) {
				threads.add(new Thread(new ServerThread(clientSocket, i, requests)));
				threads.add(new Thread(new ServerThread(slaveSocket, i, requests)));
			}
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
					"Exception caught when trying to listen on port " + "30121"  + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

}
