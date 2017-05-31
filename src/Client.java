import java.util.Map;
import java.lang.Math;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Client extends Account {
	
	//instance variables
	private Point2D location; 

	// constructors
	public Client(){
        super();
		this.location = new Point2D();
	}

	public Client(String email, String nome, String password, String address, String bday, List<Travel> tvl, Point2D location){
    	super(email,nome,password,address,bday,tvl);
		this.location = location.clone();
	}

	public Client (Client c){
    	super(c);
    	this.location = c.getLocation();
	}

	//gets & sets
	public Point2D getLocation(){
		return this.location.clone();
	}

	public void setLocation(Point2D l){
		this.location = l.clone();
	}

	public Client clone(){
		return new Client(this);
	}

	public boolean equals(Object o){
		if(o==this) return true;
		if((o==null) || (o.getClass()!=this.getClass())) return false;
		Client c = (Client)o;
		return (super.equals(c) && this.location.equals(c.getLocation()));
	}

	public String toString(){
		StringBuilder r = new StringBuilder();
        r.append(super.toString()).append("\n");
		r.append("Location: ").append(this.location.toString()).append("\n");
		return r.toString();
	}

	public int hashCode(){
		int r=7;
                
		r = r*11 + super.hashCode();	
		r = r*11 + this.location.hashCode();
		return r;
	}


    
	// request a ride to the nearest taxi
	public Driver requestRide(List<Account> l, Point2D dest) throws TaxiIndisponivelException{
        Driver closest=null;
        Taxi cAux=null;
        Travel auxT=null;
		LocalDate curT = LocalDate.now();
        double dist = Double.MAX_VALUE, temp;
        List<Driver> available=l.stream()
                                .filter(a->a.getClass().getSimpleName().equals("Driver"))
                                .map(a->(Driver)a)
                                .filter(a->((Driver)a).getStatus())
                                .collect(Collectors.toList());

        for(Driver d : available){           //search for the nearest driver
            System.out.println("Searching"); // TODO: remove after debugging
            if((temp = d.getCurPosition().getDist(this.location)) < dist){ 
                closest = d;
                dist = temp;
            }
        }

        if(closest != null){
            cAux=closest.getCar();
            cAux.genReliability();
            dist = dist + this.location.getDist(dest);
            auxT = new Travel(cAux.getPricePerKm()*dist,dist/cAux.getAverageSpeed(),cAux.getEffectiveTime(dist), dist,curT, dest, this.location);
            this.addTravel(auxT);
            closest.addTravel(auxT);
            closest.setNewPosition(dest);
            closest.addKmsTraveled(dist);
            this.location = new Point2D(dest);
        }else throw new TaxiIndisponivelException("Nao ha condutores disponiveis de momento.");

        return closest;
	}

	// request a taxi for a ride
	public Driver requestTaxi(String plate, List<Account> l, Point2D dest) throws TaxiIndisponivelException{
        Driver d=null;
        Travel auxT=null;
        LocalDate curT = LocalDate.now();
        double dist;

        for(Account a : l){
            if(a.getClass().getSimpleName().equals("Driver") && ((Driver)a).getCar().getPlate().equals(plate)) d = (Driver)a;
        }

        if(d!=null){
            if(d.getStatus()){
                Taxi t = d.getCar();
                t.genReliability();
                dist = this.location.getDist(t.getLocation()) + this.location.getDist(dest);
                auxT = new Travel(t.getPricePerKm()*dist, dist/t.getAverageSpeed(), t.getEffectiveTime(dist),dist,curT, dest, this.location);
                    this.addTravel(auxT);
                    this.location = new Point2D(dest);
                    d.addTravel(auxT);
                    d.setNewPosition(dest);
                    d.addKmsTraveled(dist);
            }else throw new TaxiIndisponivelException("Taxi unavailable.");
        }else throw new TaxiIndisponivelException("Taxi doesn't exist.");
        return d;
	}

    // request a specific taxi that isn't currently available
	public Driver bookTaxi(String plate, List<Account> l, Point2D dest) throws TaxiIndisponivelException{
        Travel auxT=null;
        Driver d=null;
        LocalDate curT = LocalDate.now();
        double dist;
        
        for(Account a : l){
            if(a.getClass().getSimpleName().equals("Driver") && ((Driver)a).getCar().getPlate().equals(plate)) d = (Driver)a;
        }

        if(d!=null){
            Taxi t = d.getCar();
            if(t instanceof TaxiQueue){ //supports a queue
                t.genReliability();
                dist = this.location.getDist(t.getLocation()) + this.location.getDist(dest);
                auxT = new Travel(t.getPricePerKm()*dist, dist/t.getAverageSpeed(), t.getEffectiveTime(dist),dist,curT, dest, this.location.clone());
                this.location = new Point2D(dest);
                ((TaxiQueue)t).addWaitingList(auxT);
                t.setLocation(dest);
                d.addKmsTraveled(dist);
            }else throw new TaxiIndisponivelException("Taxi unavailable.");
        }else throw new TaxiIndisponivelException("Taxi doesn't exist.");
        return d;
    }
}
