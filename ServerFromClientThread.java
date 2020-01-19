package semesterProject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class ServerFromClientThread implements Runnable {
	
	// A reference to the server socket is passed in, all threads share it
	private ServerSocket serverFromClientSocket;
	private ArrayList<Request> receivedRequests = new ArrayList<>();
	private PriorityQueue<Request> prioritizedRequests = new PriorityQueue<>();
	
	public ServerFromClientThread(ServerSocket serverSocket, ArrayList<Request> requests,
										PriorityQueue<Request> prioritizedRequests) {
		serverFromClientSocket = serverSocket;
		this.receivedRequests = requests;
		this.prioritizedRequests = prioritizedRequests;
	}
	
	@Override
	public void run() {

		// This thread accepts its own client socket from the shared server socket
		try (Socket serverSocket = serverFromClientSocket.accept();
			 ObjectInputStream requestFromClient = new ObjectInputStream(serverSocket.getInputStream());
			) 
		{
			
			while (true) {
				Request request = (Request) requestFromClient.readObject();
				receivedRequests.add(request);
				prioritizedRequests.add(request);				
			}
			
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port or " + serverFromClientSocket.getLocalPort()+" or listening for a connection");
			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

