package semesterProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class SlaveToServerThread implements Runnable {

	private String hostName;
	private int portNumber;
	
	public SlaveToServerThread(String hostName, int portNumber) {
		this.hostName = hostName;
		this.portNumber = portNumber;
	}
	
	@Override
	public void run() {
		
		try (
	            Socket slaveSocket = new Socket(hostName, portNumber);
	            PrintWriter notifyServer = 
	                new PrintWriter(slaveSocket.getOutputStream(), true);
	        ) {

	        	System.out.println("Slave Connected.");
	        	//MAYBE NEED A LOOP HERE :)
	        	notifyServer.println("I am available.");
	        	//Request job = (Request) jobFromMaster.readObject();
	        	//System.out.println(job.getImportance());
	        	System.out.println("Received request ");
	        	TimeUnit.SECONDS.sleep(1);
	        	notifyServer.println("Finished request ");

	        	
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + hostName);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " + hostName);
	            System.exit(1);
	        } catch (InterruptedException e) {
				e.printStackTrace();
			} 
	}

}
