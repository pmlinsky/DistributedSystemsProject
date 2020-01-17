package semesterProject;

import java.io.BufferedReader;
//import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;


public class ServerThread implements Runnable {
	
	// A reference to the server socket is passed in, all threads share it
	private ServerSocket serverSlaveSocket;
	private ServerSocket serverClientSocket;
	int id; 
	PriorityQueue<Request> requests;
	
	public ServerThread(ServerSocket c, ServerSocket s, int id, PriorityQueue<Request> requests)
	{
		serverClientSocket = c;
	    serverSlaveSocket = s;
		this.id = id;
		this.requests = requests;
	}
	
	@Override
	public void run() {

		// This thread accepts its own client socket from the shared server socket
		try (Socket clientSocket = serverClientSocket.accept();
			 Socket slaveSocket = serverSlaveSocket.accept();
				PrintWriter responseWriter = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader requestReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				ObjectInputStream clientRequest = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream assignToSlave = new ObjectOutputStream(slaveSocket.getOutputStream());
				//ObjectInputStream slaveResponse = new ObjectInputStream(slaveSocket.getInputStream()); 
				) 
		{
			

			Request request;
			while ((request = (Request) clientRequest.readObject()) != null) {
				System.out.println("Request "+request.getID() + " received from client " + (id+1));
				responseWriter.println("Received your request "+request.getID());
				requests.add(request);
				
				assignToSlave.writeObject(requests.remove());
				System.out.println("Assigning request "+request.getID()+" to slave " + (id+1));
				
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + serverSlaveSocket.getLocalPort() +" or " + serverClientSocket.getLocalPort()+" or listening for a connection");
			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

