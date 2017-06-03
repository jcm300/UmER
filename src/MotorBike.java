import java.util.ArrayDeque;
import java.util.ArrayList;

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
		this.waitingList.add(t);
	}

	public InfoTravel removeWaitingList(){
		return this.waitingList.remove();
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

    public ArrayList<Travel> dispatchQueue(){
        InfoTravel aux;
        Travel t=null;
        ArrayList<Travel> ret = new ArrayList<Travel>();

        while(!this.waitingList.isEmpty()){
            aux = this.removeWaitingList();
            t = aux.getTravel();
            aux.getClient().addTravel(t);
            ret.add(t);
        }
        return ret;
    }   
}
