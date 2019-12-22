package semesterProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
//import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		
		// Hardcode in IP and Port here
    	args = new String[] {"127.0.0.1", "30142"};
    	
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket clientSocket = new Socket(hostName, portNumber);
           // PrintWriter requestWriter = // stream to write text requests to server
           //     new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader responseReader= // stream to read text response from server
                new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())); 
           // BufferedReader stdIn = // standard input stream to get user's requests
           //     new BufferedReader(
           //         new InputStreamReader(System.in));
           
           ObjectOutputStream submitRequest = new ObjectOutputStream(clientSocket.getOutputStream());
        	
        ) {
        	
        	for (int i = 0; i < 10; i++) {
        		Request request = new Request();
        		submitRequest.writeObject(request);
        		System.out.println("Sending request "+request.getID()+" to Master.");
        		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	String response;
        	while ((response = responseReader.readLine()) != null) {
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
