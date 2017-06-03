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

    public void addTravel(Travel t){
        super.addTravel(t);
        this.location = new Point2D(t.getDest());
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
        Driver closestDriver=null;
        Taxi cAux=null;
        Travel auxT=null;
		LocalDate curT = LocalDate.now();
        double dist = Double.MAX_VALUE, temp, temp2;
        List<Driver> available=l.stream()
                                .filter(a->a.getClass().getSimpleName().equals("Driver"))
                                .map(a->(Driver)a)
                                .filter(a->((Driver)a).getStatus())
                                .collect(Collectors.toList());

        for(Driver d : available){           //search for the nearest driver
            if((temp = d.getCurPosition().getDist(this.location)) < dist){ 
                closestDriver = d;
                dist = temp;
            }
        }

        if(closestDriver != null){
            cAux=closestDriver.getCar();
            dist=dist+this.location.getDist(dest);
            temp=1.0f*dist/cAux.getAverageSpeed();
            temp2=1.0f*cAux.getEffectiveTime(dist);
            auxT=new Travel(cAux.getPricePerKm()*dist,temp2,temp, dist,curT, dest, this.location);
            this.addTravel(auxT);
            closestDriver.addTravel(auxT);
            closestDriver.setNewPosition(dest);
            closestDriver.addKmsTraveled(dist);
            closestDriver.setPunctuality((temp-(temp2-temp)%temp)/temp);
            this.location=new Point2D(dest);
        }else throw new TaxiIndisponivelException("Nao ha condutores disponiveis de momento.");

        return closestDriver;
	}

	// request a taxi for a ride
	public Driver requestTaxi(String plate, List<Account> l, Point2D dest) throws TaxiIndisponivelException{
        InfoTravel iT=null;
        Driver d=null;
        Taxi t=null;
        Travel auxT=null;
        LocalDate curT = LocalDate.now();
        double dist, pTime, rTime;

        for(Account a : l)
            if(a.getClass().getSimpleName().equals("Driver") && ((Driver)a).getCar().getPlate().equals(plate)) d = (Driver)a;

        if(d!=null){
            if(d.getStatus()){
                t = d.getCar();
                dist = this.location.getDist(t.getLocation()) + this.location.getDist(dest);
                pTime = dist/t.getAverageSpeed();
                rTime = t.getEffectiveTime(dist);
                auxT = new Travel(t.getPricePerKm()*dist,rTime,pTime,dist,curT, dest, this.location);
                this.addTravel(auxT);
                d.addTravel(auxT);
                d.setNewPosition(dest);
                d.addKmsTraveled(dist);
                d.setPunctuality((pTime-(rTime-pTime)%pTime)/pTime);
            }else if(d.getCar() instanceof TaxiQueue){
                iT=new InfoTravel();
                t=d.getCar();
                if(!((TaxiQueue)t).isAvailable()){
                    auxT=d.getLastEnqueued();
                    dist=this.location.getDist(auxT.getDest()) + this.location.getDist(dest);
                }else dist=this.location.getDist(t.getLocation()) + this.location.getDist(dest);
                pTime=dist/t.getAverageSpeed();
                rTime=t.getEffectiveTime(dist);
                auxT=new Travel(t.getPricePerKm()*dist,rTime,pTime,dist,curT, dest, this.location);
                iT.setClient(this);
                iT.setTravel(auxT); 
                d.enqueTravel(iT);
            }else throw new TaxiIndisponivelException("Taxi unavailable and doesn't support queue.");
        }else throw new TaxiIndisponivelException("Taxi doesn't exist.");
        return d;
	}
}
