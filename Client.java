package semesterProject;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

	public static void main(String[] args) {
		
		// Hardcode in IP and Port here
    	args = new String[] {"127.0.0.1", "30142", "30146"};
    	
        if (args.length != 3) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber1 = Integer.parseInt(args[1]);
        int portNumber2 = Integer.parseInt(args[2]);
        final int THREADSOFEACH = 1;
        
        try (Socket clientToServerSocket = new Socket(hostName, portNumber1);
   			 Socket clientFromServerSocket = new Socket(hostName, portNumber2);) {
   			
   			ArrayList<Thread> threads = new ArrayList<>();
   			for (int i = 0; i < THREADSOFEACH; i++) {
   				threads.add(new Thread(new ClientToServerThread(hostName, portNumber1)));
   				threads.add(new Thread(new ClientFromServerThread(hostName, portNumber2)));
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
   					"Exception caught when trying to listen on a port or listening for a connection");
   			System.out.println(e.getMessage());
   		}
	}

}
