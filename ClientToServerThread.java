package semesterProject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientToServerThread implements Runnable {

	private String hostName;
	private int portNumber;
	
	public ClientToServerThread(String hostName, int portNumber) {
		this.hostName = hostName;
		this.portNumber = portNumber;
	}
	
	@Override
	public void run() {

        try (
            Socket clientSocket = new Socket(hostName, portNumber);
            ObjectOutputStream submitRequest = new ObjectOutputStream(clientSocket.getOutputStream());        	
        ) {
        	
        	Scanner keyboard = new Scanner(System.in);
        	while (true) {
        		//String name = keyboard.nextLine();
        		Request request = new Request(keyboard.nextLine());
        		submitRequest.writeObject(request);
        		System.out.println("Sending request "+request.getID()+" to Master.");
        	}
        	
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
        	System.out.println(e);
            System.err.println("Couldn't get I/O for the connection to " +hostName);
            System.exit(1);
        } 
	}
}
