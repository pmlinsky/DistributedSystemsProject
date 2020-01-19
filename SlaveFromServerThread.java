package semesterProject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SlaveFromServerThread implements Runnable {

	private String hostName;
	private int portNumber;
	private ArrayList<String> requestName;
	
	public SlaveFromServerThread(String hostName, int portNumber, ArrayList<String> requestName) {
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.requestName = requestName;
	}
	
	@Override
	public void run() {
		
		try (
	            Socket slaveSocket = new Socket(hostName, portNumber);
	            ObjectInputStream jobFromMaster = new ObjectInputStream(slaveSocket.getInputStream());
	        ) {

			while (true) {
	        	Request job = (Request) jobFromMaster.readObject();
	        	requestName.add(job.getName());
	        	System.out.println("Received request "+job.getID()+" of importance "+job.getImportance());
			}
	        	
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + hostName);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " + hostName);
	            System.exit(1);
	        } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}

}
