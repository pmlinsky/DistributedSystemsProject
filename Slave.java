package semesterProject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

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

        try (
            Socket slaveSocket = new Socket(hostName, portNumber);
            PrintWriter notifyServer = // stream to tell server when done
                new PrintWriter(slaveSocket.getOutputStream(), true);
            ObjectInputStream jobFromMaster = new ObjectInputStream(slaveSocket.getInputStream());
        ) {

        	//MAYBE NEED A LOOP HERE :)
        	Request job = (Request) jobFromMaster.readObject();
        	TimeUnit.SECONDS.sleep(1);
        	notifyServer.println("Finished request "+job.getID());

        	
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
