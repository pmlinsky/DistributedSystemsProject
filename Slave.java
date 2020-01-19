package semesterProject;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Slave {

	public static void main(String[] args) {
		
		// Hardcode in IP and Port here
    	args = new String[] {"127.0.0.1", "30123"};
    	
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        final int THREADSOFEACH = 2;

        try (
            Socket slaveToServerSocket = new Socket(hostName, portNumber);
        	Socket slaveFromServerSocket = new Socket(hostName, portNumber);
        ) {

        	ArrayList<Thread> threads = new ArrayList<>();
   			for (int i = 0; i < THREADSOFEACH; i++) {
   				
   				ArrayList<String> requestName = new ArrayList<>();
   				threads.add(new Thread(new SlaveToServerThread(hostName, portNumber, requestName)));
   				threads.add(new Thread(new SlaveFromServerThread(hostName, portNumber, requestName)));
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
        	
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        } 
	}

}
