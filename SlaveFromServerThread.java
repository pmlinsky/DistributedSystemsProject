package semesterProject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SlaveFromServerThread implements Runnable {

	private String hostName;
	private int portNumber;
	
	public SlaveFromServerThread(String hostName, int portNumber) {
		this.hostName = hostName;
		this.portNumber = portNumber;
	}
	
	@Override
	public void run() {
		
		try (
	            Socket slaveSocket = new Socket(hostName, portNumber);
	            ObjectInputStream jobFromMaster = new ObjectInputStream(slaveSocket.getInputStream());
	        ) {

	        	System.out.println("Slave Connected.");
	        	//MAYBE NEED A LOOP HERE :)
	        	Request job = (Request) jobFromMaster.readObject();
	        	System.out.println(job.getImportance());
	        	System.out.println("Received request "+job.getID());
	        	
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
