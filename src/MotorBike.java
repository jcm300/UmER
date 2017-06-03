import java.util.ArrayDeque;

public class MotorBike extends Taxi implements TaxiQueue{
	
	//instace variables
	private ArrayDeque<InfoTravel> waitingList;

	//Constructors
	public MotorBike(){
		super();
		this.waitingList = new ArrayDeque<InfoTravel>();
	}	
	
	public MotorBike(MotorBike m){
		super(m);
		this.waitingList = m.getWaitingList();
	}

	public MotorBike(String plate, double avS, double ppkm, double rel, Point2D loc, ArrayDeque<InfoTravel> tw){
		super(plate, avS, ppkm, rel, loc);
		this.setWaitingList(tw);
	}

	//gets & sets
	public ArrayDeque<InfoTravel> getWaitingList(){
		ArrayDeque<InfoTravel> r = new ArrayDeque<InfoTravel>();
		for(InfoTravel t: this.waitingList)
			r.add(t.clone());
		return r;
	}

	public void setWaitingList(ArrayDeque<InfoTravel> ad){
		this.waitingList = new ArrayDeque<InfoTravel>();
                for(InfoTravel t: ad)
                	this.waitingList.add(t.clone());
	}

	
	public MotorBike clone(){
		return new MotorBike(this);
	}

	public void addWaitingList(InfoTravel t){
		this.waitingList.add(t.clone());
	}

	public InfoTravel removeWaitingList(){
		return this.waitingList.remove().clone();
	}

	public boolean containsWaitingList(InfoTravel t){
		return this.waitingList.contains(t);
	}

	public int compareTo(MotorBike m){
		return super.compareTo(m);	
	}	

    public boolean isAvailable(){
        return this.waitingList.isEmpty();
    }
}
