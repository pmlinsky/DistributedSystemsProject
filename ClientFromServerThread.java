package semesterProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientFromServerThread implements Runnable {

	private String hostName;
	private int portNumber;
	
	public ClientFromServerThread(String hostName, int portNumber) {
		this.hostName = hostName;
		this.portNumber = portNumber;
	}
	
	@Override
	public void run() {

        try (
        	Socket clientSocket = new Socket(hostName, portNumber);
            BufferedReader responseReader= 
            		new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));      	
        ) {
        	
        	while (true) {
        		String response = responseReader.readLine();
            	System.out.println("Master says: "+response);
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

