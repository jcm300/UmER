import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Random;
import java.io.Serializable;

public abstract class Taxi implements Serializable{
	
	//instace variables
	private String plate;
	private double averageSpeed;
	private double pricePerKm;
	private double reliability;
	private Point2D location;
	private Driver driver;
	List<Travel> tReg; 
	
	//Constructors
	public Taxi(){
		this.plate = "";
		this.averageSpeed=0;
		this.pricePerKm=0;
		this.reliability=0;
		this.location = new Point2D();
		this.driver = null;
		this.tReg = new ArrayList<Travel>();
	}

	public Taxi(Taxi t){
		this.plate = t.getPlate();
		this.averageSpeed= t.getAverageSpeed();
        this.pricePerKm=t.getPricePerKm();
        this.reliability=t.getReliability();
        this.location = t.getLocation();
        this.driver = t.getDriver();
		this.tReg = t.getTReg();
	}

	public Taxi(String plate, double avS, double ppkm, double rel, Point2D loc, Driver d, ArrayList<Travel> tr){
		this.plate = plate;
		this.averageSpeed = avS;
		this.pricePerKm = ppkm;
		this.reliability = rel;
		this.location = loc;
		this.driver = d;
		
		for(Travel t: tr)
			this.tReg.add(t.clone());
	}

    	//gets and sets
	public String getPlate(){
		return this.plate;
	}

	public double getAverageSpeed(){
		return this.averageSpeed;
	}

	public double getPricePerKm(){
		return this.pricePerKm;
	}

	public double getReliability(){
		return this.reliability;
	}

	public Point2D getLocation(){
		return this.location.clone();
	}

	public Driver getDriver(){
		return this.driver;
	} 
	public List<Travel> getTReg(){
		return this.tReg.stream().map(Travel::clone).collect(Collectors.toCollection(ArrayList::new));
	}

	public void setPlate(String nPl){
		this.plate = nPl;
	}

	public void setAverageSpeed(double as){
		this.averageSpeed = as;
	}

	public void setPricePerKm(double ppk){
		this.pricePerKm = ppk;
	}

	public void setReliability(double r){
		this.reliability = r;
	}

	public void setLocation(Point2D l){
		this.location = l.clone();
	}

	public void setDriver(Driver d){
		this.driver = d;
	}

	public void setTReg(List<Travel> nTReg){
		this.tReg = nTReg.stream().map(Travel::clone).collect(Collectors.toCollection(ArrayList::new));
	}

	public void addTravel(Travel t){
		this.tReg.add(t.clone());
        	this.location = new Point2D(t.getDest());
		this.driver.addTravel(t);
	}

    // Randomly generates the reliability of a taxi before a ride
    private void genReliability(){
        Random gen = new Random();
        this.reliability = gen.nextDouble();
    }

    public double getEffectiveTime(double dist){
        this.genReliability();
        return dist/this.averageSpeed*this.reliability;
    }

	public boolean equals(Object o){
		if(this==o) return true;
		if((o==null) || (o.getClass()!=this.getClass())) return false;
		Taxi h = (Taxi)o;
		return (this.plate.equals(h.getPlate()) && this.averageSpeed==h.getAverageSpeed() && this.pricePerKm==h.getPricePerKm() && this.reliability==h.getReliability() && this.location.equals(h.getLocation()) && this.driver.equals(h.getDriver()) && this.tReg.equals(h.getTReg()));
	}

    public String toString(){
        StringBuilder r = new StringBuilder();
        r.append("AverageSpeed: ").append(this.averageSpeed).append("\n");
        r.append("PricePerKm: ").append(this.pricePerKm).append("\n");
        r.append("Reliability: ").append(this.reliability).append("\n");
		r.append("Location: ").append(this.location.toString()).append("\n");
		r.append("Driver: ").append(this.driver.toString());
        return r.toString();
    }

    
    public int hashCode(){
        int r=13;
        long aux;
        
        aux = Double.doubleToLongBits(this.averageSpeed);
        r = r*23 + (int)(aux ^ (aux >>> 32));
        aux = Double.doubleToLongBits(this.pricePerKm);
        r = r*23 + (int)(aux ^ (aux >>> 32));
        aux = Double.doubleToLongBits(this.reliability);
        r = r*23 + (int)(aux ^ (aux >>> 32));
        r = r*23 + this.location.hashCode();
        r = r*23 + this.driver.hashCode();
        return r;
    }
	
    public int compareTo(Taxi t){
	    return this.plate.compareTo(t.getPlate());
    }    

    public abstract Taxi clone();

	//Travels between to dates	
	public List<Travel> getTravelsBetween(LocalDate init, LocalDate end){
    	return this.tReg.stream()
                    	.filter(t->t.getDate().isAfter(init) && t.getDate().isBefore(end))
                        .collect(Collectors.toList());
    }

	//Profit between to dates on one Taxi
	public double profitBetween(LocalDate init, LocalDate end){
			List<Travel> aux = getTravelsBetween(init,end);
			double ret=0;
			
			for(Travel t: aux)
					ret += t.getCost();

			return ret;
	}
}
