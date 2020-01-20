package semesterProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.PriorityBlockingQueue;

public class ServerToAndFromSlaveThread implements Runnable {

	// A reference to the server socket is passed in, all threads share it
	private ServerSocket serverToAndFromSlaveSocket;
	private PriorityBlockingQueue<Request> prioritizedRequests;
	private Vector<String> completedRequest;

	public ServerToAndFromSlaveThread(ServerSocket serverSocket, PriorityBlockingQueue<Request> requests,
			Vector<String> completedRequest) {
		serverToAndFromSlaveSocket = serverSocket;
		this.prioritizedRequests = requests;
		this.completedRequest = completedRequest;
	}

	@Override
	public void run() {

		// This thread accepts its own client socket from the shared server socket
		try (Socket serverSocket = serverToAndFromSlaveSocket.accept();
				BufferedReader slaveStatus = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
				ObjectOutputStream jobAssignment = new ObjectOutputStream(serverSocket.getOutputStream());) {

			System.out.println(slaveStatus.readLine());
			while (true) {
				//synchronized(prioritizedRequests) {
					if (!prioritizedRequests.isEmpty()) {
					
						Request request = prioritizedRequests.remove();
						System.out.println("Sending request " + request.getName() + " to slave.");
						jobAssignment.writeObject(request);
						String status = slaveStatus.readLine();
						System.out.println("Slave said: " + status);
						completedRequest.add(slaveStatus.readLine());
						// now we know that slave finished a request and is available
					}
				//}
			}

		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
					+ serverToAndFromSlaveSocket.getLocalPort() + " or listening for a connection");
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
