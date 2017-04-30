import java.util.ArrayList;

public class Client extends Account {
	
	//instance variavels
	private Point2D location;
	private ArrayList<Travel> travels;  


    // contrutores
    public Client(){
	    this.location = new Point2D();
	    this.travels = new ArrayList<Travel>();
    }

    public Client( Point2D location, ArrayList<Travel> travels){
    	this.location = location.clone();
    	this.travels = travels.clone();
	}

	public Client (Client c){
    	this.location = c.getLocation();
    	this.travels = c.getTravels();
	}

	//gets

	public Point2D getLocation(){
			return this.location.clone();
	}

	public ArrayList<Travel> getTravels(){
			return this.travels.clone();
	}
	
	//sets

	public void setLocation(Point2D l){
		this.location = l.clone();
	}

	public void setTravels(Driver t){
		this.travels = t.clone();
	}

	//clone, equals, toString, hashCode

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
		    r.append("Travels: ").append(this.travels.toString()).append("\n");
            return r.toString();
    }

    public int hashCode(){
            int r=7;
                
		
            r = r*11 + this.location.hashCode();
		    r = r*11 + this.travels.hashCode();
            return r;
    }

}




