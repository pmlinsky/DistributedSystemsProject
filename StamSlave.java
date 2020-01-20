package semesterProject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class StamSlave {

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

        try (
        		Socket slaveSocket = new Socket(hostName, portNumber);
	            PrintWriter notifyServer = 
	                new PrintWriter(slaveSocket.getOutputStream(), true);
        		ObjectInputStream jobFromMaster = new ObjectInputStream(slaveSocket.getInputStream());
        ) {

			notifyServer.println("Slave Connected. Available to work.");
			while (true) {
				
	        	Request job = (Request) jobFromMaster.readObject();
	        	System.out.println("Received request "+job.getID()+" of importance "+job.getImportance());			
        		TimeUnit.SECONDS.sleep(30);
        		System.out.println("Finished request "+job.getName());
        		notifyServer.println("Finished request. Now available again.");
        		notifyServer.println(job.getName());
        	}
   			
        	
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}

}