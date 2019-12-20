package semesterProject;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class ServerThread implements Runnable {
	
	// A reference to the server socket is passed in, all threads share it
	private ServerSocket serverSocket = null;
	//private ServerSocket slaveSocket = null;
	int id; 
	public ServerThread(ServerSocket s, int id, ArrayList<Request> requests)
	{
		serverSocket = s;
		//slaveSocket = s;
		this.id = id;
	}
	
	@Override
	public void run() {

		// This thread accepts its own client socket from the shared server socket
		try (Socket clientSocket = serverSocket.accept();
			 Socket slaveSocket = serverSocket.accept();
				PrintWriter responseWriter = new PrintWriter(clientSocket.getOutputStream(), true);
				//BufferedReader requestReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				ObjectInputStream clientRequest = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream assignToSlave = new ObjectOutputStream(slaveSocket.getOutputStream());
				ObjectInputStream slaveResponse = new ObjectInputStream(slaveSocket.getInputStream()); ) {
			
			Request request;
			while ((request = (Request) clientRequest.readObject()) != null) {
				System.out.println("Request "+request.getID() + " received from client: " + id);
				responseWriter.println("Received your request "+request.getID());
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + serverSocket.getLocalPort() + " or listening for a connection");
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

