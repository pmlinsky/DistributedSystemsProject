package semesterProject;

import java.io.Serializable;
import java.util.Random;

public class Request implements Serializable, Comparable<Request> {
	
	private int importance;
	private static int ctr;
	private int id;
	private String name;

	public Request(String name) {
		Random random = new Random();
		importance = random.nextInt(1000) + 1;
		id = ++ctr;
		this.name = name;

	}

	public void incrementImportance() {
		importance += 50;
	}

	public int getID() {
		return id;
	}

	public int getImportance() {
		return importance;
	}

	public int compareTo(Request r) {
		return r.importance - this.importance;
	}
	
	public String getName() {
		return name;
	}
}
