package semesterProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SlaveToServerThread implements Runnable {

	private String hostName;
	private int portNumber;
	private ArrayList<String> requestName;
	
	public SlaveToServerThread(String hostName, int portNumber, ArrayList<String> requestName) {
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.requestName = requestName;
	}
	
	@Override
	public void run() {
		
		try (
	            Socket slaveSocket = new Socket(hostName, portNumber);
	            PrintWriter notifyServer = 
	                new PrintWriter(slaveSocket.getOutputStream(), true);
	        ) {

	        	System.out.println("Slave Connected. Available to work.");
	        	while (true) {
	        		//only if there is something to do...
	        		if (!requestName.isEmpty()) {
		        		System.out.println("Received request "+requestName.get(0));
		        		TimeUnit.SECONDS.sleep(1);
		        		System.out.println("Finished request "+requestName.get(0));
		        		notifyServer.println("Finished request. Now available again.");
		        		notifyServer.println(requestName.remove(0));
	        		}
	        	}
	        	

	        	
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
