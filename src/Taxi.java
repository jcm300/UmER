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
	
	//Constructors
	public Taxi(){
		this.plate = "";
		this.averageSpeed=0.d;
		this.pricePerKm=0.d;
		this.reliability=0.d;
		this.location = new Point2D();
	}

	public Taxi(Taxi t){
		this.plate = t.getPlate();
		this.averageSpeed= t.getAverageSpeed();
        this.pricePerKm=t.getPricePerKm();
        this.reliability=t.getReliability();
        this.location = t.getLocation();
	}

	public Taxi(String plate, double avS, double ppkm, double rel, Point2D loc, ArrayList<Travel> tr){
		this.plate = plate;
		this.averageSpeed = avS;
		this.pricePerKm = ppkm;
		this.reliability = rel;
		this.location = loc;
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

    public double getEffectiveTime(double dist){
        this.genReliability();
        return (dist/this.averageSpeed)/this.reliability;
    }
    
    // Randomly generates the reliability of a taxi before a ride
    private void genReliability(){
        Random gen = new Random();
        this.reliability = gen.nextDouble()+0.01f;
    }

	public boolean equals(Object o){
		if(this==o) return true;
		if((o==null) || (o.getClass()!=this.getClass())) return false;
		Taxi h = (Taxi)o;
		return (this.plate.equals(h.getPlate()) && this.averageSpeed==h.getAverageSpeed() && this.pricePerKm==h.getPricePerKm() && this.reliability==h.getReliability() && this.location.equals(h.getLocation()));
	}

    public String toString(){
        StringBuilder r = new StringBuilder();
        r.append("Plate: ").append(this.plate).append("\n");
        r.append("AverageSpeed: ").append(this.averageSpeed).append("\n");
        r.append("PricePerKm: ").append(this.pricePerKm).append("\n");
        r.append("Reliability: ").append(this.reliability).append("\n");
		r.append("Location: ").append(this.location.toString()).append("\n");
        return r.toString();
    }

    
    public int hashCode(){
        int r=13;
        long aux;
        
        r = r*23 + this.plate.hashCode();
        aux = Double.doubleToLongBits(this.averageSpeed);
        r = r*23 + (int)(aux ^ (aux >>> 32));
        aux = Double.doubleToLongBits(this.pricePerKm);
        r = r*23 + (int)(aux ^ (aux >>> 32));
        aux = Double.doubleToLongBits(this.reliability);
        r = r*23 + (int)(aux ^ (aux >>> 32));
        r = r*23 + this.location.hashCode();
        return r;
    }
	
    public int compareTo(Taxi t){
	    return this.plate.compareTo(t.getPlate());
    }    

    public abstract Taxi clone();

	//Profit between to dates on one Taxi
	public double profitBetween(LocalDate init, LocalDate end){
			List<Travel> aux = getTravelsBetween(init,end);
			double ret=0;
			
			for(Travel t: aux)
					ret += t.getCost();

			return ret;
	}
}
