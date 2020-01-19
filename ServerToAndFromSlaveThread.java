package semesterProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class ServerToAndFromSlaveThread implements Runnable {
	
	// A reference to the server socket is passed in, all threads share it
	private ServerSocket serverFromSlaveSocket;
	private PriorityQueue<Request> requests;
	private ArrayList<String> completedRequest;
	
	public ServerToAndFromSlaveThread(ServerSocket serverSocket, PriorityQueue<Request> requests,
			ArrayList<String> completedRequest) {
		serverFromSlaveSocket = serverSocket;
		this.requests = requests;
		this.completedRequest = completedRequest;
	}
	
	@Override
	public void run() {

		// This thread accepts its own client socket from the shared server socket
		try (Socket serverSocket = serverFromSlaveSocket.accept();
			 BufferedReader slaveStatus = 
					 new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			ObjectOutputStream jobAssignment = new ObjectOutputStream(serverSocket.getOutputStream());
			) {
			
			while (true) {
				if (!requests.isEmpty()) {
					String status = slaveStatus.readLine();
					System.out.println("Slave said :"+status);
					completedRequest.add(slaveStatus.readLine());
					//now we know that slave finished a request and is available
					jobAssignment.writeObject(requests.remove());
				}
			}
			
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + serverFromSlaveSocket.getLocalPort() +" or listening for a connection");
			System.out.println(e);
			e.printStackTrace();
		} 
	}
}


