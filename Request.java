package semesterProject;
import java.util.Random;


public class Request 
{
	private int importance;
	private static int ctr;
	private int id;
	
	public Request()
	{
		Random random = new Random();
		importance = random.nextInt(1000) + 1;
		id = ++ctr;
		
	}
	public void incrementImportance()
	{
		importance += 50;
	}
	
	public int getID()
	{
		return id;
	}
	public int getImportance()
	{
		return importance;
	}
}
