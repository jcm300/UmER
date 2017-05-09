import java.util.ArrayList;
import java.util.Map;
import java.lang.Math;
import java.time.LocalDate;
import java.util.List;

public class Client extends Account {
	
	//instance variables
	private Point2D location; private ArrayList<Travel> travels;  

	// constructors
	public Client(){
	        super();
		this.location = new Point2D();
		this.travels = new ArrayList<Travel>();
	}

	public Client(String email, String nome, String password, String address, String bday, Point2D location, ArrayList<Travel> travels){
        super();
		this.location = location.clone();
		for(Travel t: travels) this.travels.add(t.clone());
	}

	public Client (Client c){
            	super(c);
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
		return (super.equals(c) && this.location.equals(c.getLocation()) && this.travels.equals(c.getTravels()));
	}

	public String toString(){
		StringBuilder r = new StringBuilder();
        r.append(super.toString()).append("\n");
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
	public void requestRide(Map<String,Taxi> l, Point2D dest){

        	LocalDate curT = LocalDate.now();
        	double dist = Double.MAX_VALUE, temp;
        	Taxi closest = null;
        	Travel aux;

        	for(Taxi t : l.values())                    //search for the nearest taxi
            		if((temp = this.location.getDist(t.getLocation())) < dist && t.getDriver().getStatus()){ 
                	closest = t;
                	dist = temp;
            	}

        	if(closest != null){
            		aux = new Travel(closest.getAverageSpeed()*dist,closest.getPricePerKm()*dist, dist,curT, dest);
            		travels.add(aux);
            		closest.addTravel(aux);
            		this.location = new Point2D(dest);
        	}    
	}

	// request a taxi for a ride
	public void requestTaxi(String plate, Map<String,Taxi> l, Point2D dest){

        	Travel aux;
        	Taxi t;
        	double dist;
        	LocalDate curT = LocalDate.now();

        	if(l.containsKey(plate)){
            		t = l.get(plate);
            		if(t.getDriver().getStatus()){
                		dist = this.location.getDist(t.getLocation());
                		aux = new Travel(t.getAverageSpeed()*dist,t.getPricePerKm()*dist, dist,curT, dest);
                		travels.add(aux);
                		t.addTravel(aux);
                		this.location = new Point2D(dest);
            		}
        	}
	}

    	// request a specific taxi that isn't currently available
	public boolean bookTaxi(String plate, Map<String,Taxi> l, Point2D dest){

        	Travel aux;
        	Taxi t;
        	LocalDate curT = LocalDate.now();
        	double dist;
			boolean book = false;

        	if(l.containsKey(plate)){
            		t = l.get(plate);              
            		if(t instanceof TaxiQueue){         //supports a queue
                		dist = this.location.getDist(t.getLocation());
                		aux = new Travel(t.getAverageSpeed()*dist,t.getPricePerKm()*dist, dist,curT, dest);
                		((TaxiQueue)t).addWaitingList(aux);
                		this.location = new Point2D(dest);
				book = true;
            		}
        	}
		return book;
	}
	
	//add a travel to the list
	public void addTravel(Travel t){
		this.travels.add(t.clone());
	}
}
