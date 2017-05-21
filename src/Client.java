import java.util.Map;
import java.lang.Math;
import java.time.LocalDate;
import java.util.List;

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
	public Travel requestRide(Map<String,Taxi> l, Point2D dest) throws TaxiIndisponivelException{
		LocalDate curT = LocalDate.now();
        double dist = Double.MAX_VALUE, temp;
        Taxi closest = null;
        Travel aux=null;

        for(Taxi t : l.values())                    //search for the nearest taxi
            if((temp = this.location.getDist(t.getLocation())) < dist && t.getDriver().getStatus()){ 
                closest = t;
                dist = temp;
            }

        if(closest != null){
            aux = new Travel(closest.getPricePerKm()*dist,dist/closest.getAverageSpeed(),closest.getEffectiveTime(dist), dist,curT, dest, this.location);
            this.addTravel(aux);
            closest.addTravel(aux);
            this.location = new Point2D(dest);
        }else throw new TaxiIndisponivelException(plate + ": nao ha taxis disponiveis de momento.");

        return aux;

	}

	// request a taxi for a ride
	public Travel requestTaxi(String plate, Map<String,Taxi> l, Point2D dest) throws TaxiIndisponivelException{
        	Travel aux=null;
        	Taxi t=null;
        	double dist;
        	LocalDate curT = LocalDate.now();

        	if(l.containsKey(plate)){
            		t = l.get(plate);
            		if(t.getDriver().getStatus()){
                		dist = this.location.getDist(t.getLocation());
                		aux = new Travel(t.getPricePerKm()*dist, dist/t.getAverageSpeed(), t.getEffectiveTime(dist),dist,curT, dest, this.location);
                		this.addTravel(aux);
                		t.addTravel(aux);
                		this.location = new Point2D(dest);
            		}else throw new TaxiIndisponivelException(plate + ": taxi indisponivel.");
        	}else throw new TaxiIndisponivelException(plate + ": matricula nao encontrada.");
            return aux;
	}

    // request a specific taxi that isn't currently available
	public Travel bookTaxi(String plate, Map<String,Taxi> l, Point2D dest) throws TaxiIndisponivelException{
        Travel aux=null;
        Taxi t=null;
        LocalDate curT = LocalDate.now();
        double dist;

        if(l.containsKey(plate)){
            t = l.get(plate);              
            if(t instanceof TaxiQueue){         //supports a queue
                dist = this.location.getDist(t.getLocation());
                aux = new Travel(t.getPricePerKm()*dist, dist/t.getAverageSpeed(), t.getEffectiveTime(dist),dist,curT, dest, this.location.clone());
                ((TaxiQueue)t).addWaitingList(aux);
                this.location = new Point2D(dest);
            }else throw new TaxiIndisponivelException(plate +": nao suporta files de espera.")
        }else throw new TaxiIndisponivelException(plate + ": matricula nao encontrada.");
        return aux;
    }
}
