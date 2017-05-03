import java.util.List;

public abstract class Taxi extends App{
	
	//instace variables
	private String plate;
	private double averageSpeed;
	private double pricePerKm;
	private double reliability;
	private Point2D location;
	private Driver driver;
	List<Travel> tReg; 

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
		return this.driver.clone();
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
		this.driver = d.clone();
	}

	public void setTReg(List<Travel> nTReg){
		this.tReg = nTReg.stream().map(Travel::clone).collect(Collectors.toCollection(ArrayList::new));
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
        
        r = r*23 + this.taxiType.hashCode();
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

    public abstract Taxi clone();
    public abstract equals(Object o);
    public abstract int compareTo(Taxi t);
    public abstract void addTravel(Client c, Travel t);

}
