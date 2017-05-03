import java.util.ArrayList;

public class Client extends Account {
	
	//instance variables
	private Point2D location;
	private ArrayList<Travel> travels;  

	// contructors
	public Client(){
		this.location = new Point2D();
		this.travels = new ArrayList<Travel>();
	}

	public Client( Point2D location, ArrayList<Travel> travels){
		this.location = location.clone();
		for(Travel t: travels) this.travels.add(t.clone());
	}

	public Client (Client c){
    		this.location = c.getLocation();
    		this.travels = c.getTravels();
	}

	//gets & sets
	public Point2D getLocation(){
		return this.location.clone();
	}

	public ArrayList<Travel> getTravels(){
		ArrayList<Travel> r = new ArrayList<Travel>();
		for(Travel t: this.travels) r.add(t.clone());
		return r;
	}

	public void setLocation(Point2D l){
		this.location = l.clone();
	}

	public void setTravels(ArrayList<Travel> l){
		ArrayList<Travel> a = new ArrayList<Travel>();
		for(Travel t: l) a.add(t.clone());
		this.travels = a;
	}

	public Client clone(){
		return new Client(this);
	}

	public boolean equals(Object o){
		if(o==this) return true;
		if((o==null) || (o.getClass()!=this.getClass())) return false;
		Client c = (Client)o;
		return (this.location.equals(c.getLocation()) && this.travels.equals(c.getTravels()));
	}

	public String toString(){
		StringBuilder r = new StringBuilder();
                
		r.append("Location: ").append(this.location.toString()).append("\n");
		r.append("Travels: ").append(this.travels.toString());
		return r.toString();
	}

	public int hashCode(){
		int r=7;
                	
		r = r*11 + this.location.hashCode();
		r = r*11 + this.travels.hashCode();
		return r;
	}


	// request a ride to the nearest taxi
	public void requestRide(){

	}

	// request a taxi for a ride
	public void requestTaxi(String plate){

	}

	public void bookTaxi(String plate){

	}
	
	//add a travel to the list
	public void addTravel(Travel t){
		this.travels.add(t.clone());
	}
}
