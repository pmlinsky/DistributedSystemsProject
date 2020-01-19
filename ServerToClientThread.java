package semesterProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerToClientThread implements Runnable {
	
	// A reference to the server socket is passed in, all threads share it
	private ServerSocket serverToClientSocket;
	private ArrayList<Request> requests;
	private ArrayList<String> completedRequest;
	
	public ServerToClientThread(ServerSocket serverSocket, ArrayList<Request> requests,
			ArrayList<String> completedRequests) {
		serverToClientSocket = serverSocket;
		this.requests = requests;
		this.completedRequest = completedRequests;
	}
	
	@Override
	public void run() {

		// This thread accepts its own client socket from the shared server socket
		try (Socket serverSocket = serverToClientSocket.accept();
			 PrintWriter responseWriter = new PrintWriter(serverSocket.getOutputStream(), true);
			) 
		{
			
			while (true) {
				if (!requests.isEmpty()) {
					Request request = requests.remove(0);
					System.out.println("Request "+request.getID() + " received from client.");
					responseWriter.println("Received your request "+request.getID());	
				}
				if (!completedRequest.isEmpty()) {
					responseWriter.println("Completed your request "+completedRequest.remove(0));
				}
			}
			
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + serverToClientSocket.getLocalPort() +" or " + serverToClientSocket.getLocalPort()+" or listening for a connection");
			System.out.println(e);
			e.printStackTrace();
		}
	}
}

