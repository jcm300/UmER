import java.util.ArrayDeque;
import java.util.ArrayList;

public class MotorBike extends Taxi {
	
	//instace variables
	private ArrayDeque<Travel> waitingList;

	//Constructors
	public MotorBike(){
		super();
		this.waitingList = new ArrayDeque<Travel>();
	}	
	
	public MotorBike(MotorBike m){
		super(m);
		this.waitingList = m.getWaitingList();
	}

	public MotorBike(String plate, double avS, double ppkm, double rel, Point2D loc, Driver d, ArrayList<Travel> tr, ArrayDeque<Travel> tw){
		super(plate, avS, ppkm, rel, loc, d, tr);
		this.setWaitingList(tw);
	}

	//gets & sets
	public ArrayDeque<Travel> getWaitingList(){
		ArrayDeque<Travel> r = new ArrayDeque<Travel>();
		for(Travel t: this.waitingList)
			r.add(t.clone());
		return r;
	}

	public void setWaitingList(ArrayDeque<Travel> ad){
		this.waitingList = new ArrayDeque<Travel>();
                for(Travel t: ad)
                	this.waitingList.add(t.clone());
	}

	
	public MotorBike clone(){
		return new MotorBike(this);
	}

	public void addWaitingList(Travel t){
		this.waitingList.add(t.clone());
	}

	public Travel removeWaitingList(){
		return this.waitingList.remove().clone();
	}

	public boolean containsWaitingList(Travel t){
		return this.waitingList.contains(t);
	}

	public int compareTo(MotorBike m){
		return super.compareTo(m);	
	}	
}
