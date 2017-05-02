//import java.util.ArrayDeque;
import java.util.List;

public class Taxi extends App{
	
	//instace variables
	private String taxiType;
	private String plate;
	private double averageSpeed;
	private double pricePerKm;
	private double reliability;
	private Point2D location;
	private Driver driver;
	List<Travel> tReg; 
	/*
	private boolean haveWaitList; //TODO
	private ArrayDeque<Travel????> waitingList; //TODO
	*/

	//Constructors
	public Taxi(){
		this.taxiType = null;
		this.plate = "";
		this.averageSpeed = 0;
		this.pricePerKm = 0;
		this.reliability = 0;
		this.location = new Point2D();
		this.driver = new Driver();
		this.tReg = new ArrayList<>();
	}

	public Taxi(String taxiType, String plate, double averageSpeed, double pricePerKm, double reliability, Point2D location, Driver driver, List<Travel> tReg){
		this.taxiType = taxiType;
		this.plate = plate;
		this.averageSpeed = averageSpeed;
		this.pricePerKm = pricePerKm;
		this.reliability = reliability;
		this.location = location.clone();
		this.driver = driver.clone();
		this.tReg = tReg.stream().map(Travel::clone).collect(Collectors.toCollection(ArrayList::new));
	}

	public Taxi(Taxi t){
		this.taxiType = t.getTaxiType();
		this.plate = t.getPlate();
		this.averageSpeed = t.getAverageSpeed();
		this.pricePerKm = t.getPricePerKm();
		this.reliability = t.getReliability();
		this.location = t.getLocation();
		this.driver = t.getDriver();
		this.tReg = t.getTReg();
	}

	//gets and sets
	public String getTaxiType(){
		return this.taxiType;
	}

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

	public void setTaxiType(String tt){
		this.taxiType = tt;
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

	public Taxi clone(){
		return new Taxi(this);
	}

	public boolean equals(Object o){
		if(o==this) return true;
		if((o==null) || (o.getClass()!=this.getClass())) return false;
		Taxi t = (Taxi)o;
		return (this.taxiType.equals(t.getTaxiType()) && this.averageSpeed == t.getAverageSpeed() && this.pricePerKm == t.getPricePerKm() && this.reliability == t.getReliability() && this.location.equals(t.getLocation()) &&  this.driver.equals(t.getDriver()));
	}

	public String toString(){
                StringBuilder r = new StringBuilder();
                r.append("TaxiType: ").append(this.taxiType).append("\n");
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
			
			r = r*23 + this.taxiTyp	e.hashCode();
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
                return this.taxiType.compareTo(t.getTaxiType());
        }

	public void addTravel(Client c, Travel t){
		this.tReg.add(t.clone());
		c.addTravel(t);
		this.driver.addTravel(t);
	}
}