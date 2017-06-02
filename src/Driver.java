import java.util.List;
import java.time.LocalDate;

public class Driver extends Account {
	 
	//instance variables
	private boolean status;
	private double rating;
	private double kmsTraveled;
	private double punctuality;
    private Taxi car;


	//constructors
	public Driver(){
		super();
		this.status = false;
		this.rating = 0.f;
		this.kmsTraveled = 0.f;
		this.punctuality = 0.f;
        this.car = null;
	}

	public Driver(String email, String nome, String password, String address, String bday,List<Travel> tvl, boolean av, double ratng, double kms, double pc, Taxi vehicle){
		super(email, nome, password, address, bday,tvl);
		this.status = av;
		this.rating = ratng;
		this.kmsTraveled = kms;
		this.punctuality = pc;
        this.car = vehicle;
	}

	public Driver(Driver oldDriver){
		super(oldDriver);
		this.status = oldDriver.getStatus();
		this.rating = oldDriver.getRating();
		this.kmsTraveled = oldDriver.getKmsTraveled();
		this.punctuality = oldDriver.getPunctuality();
        this.car = oldDriver.getCar();
	}

	//gets & sets
	public boolean getStatus(){
		return this.status;
	}
	public void setStatus(boolean status){
		this.status = status;
	}

	public double getRating(){
		return this.rating;
	}
	public void setRating(double nRating){
		this.rating = nRating;
	}

	public double getKmsTraveled(){
		return this.kmsTraveled;
	}
	public void setKmsTraveled(double nKms){
		this.kmsTraveled = nKms;
	}

    public void addKmsTraveled(double nKms){
        this.kmsTraveled += nKms;
    }

	public double getPunctuality(){
		return this.punctuality;
	}
	public void setPunctuality(double p){
		this.punctuality = p;
	}
    
    public Taxi getCar(){
        return this.car.clone(); 
    }
    public void setCar(Taxi nCar){
        this.car = nCar; 
    }
    
    public Point2D getCurPosition(){
        return this.car.getLocation();
    }

    public void setNewPosition(Point2D p){
        this.car.setLocation(p);
    }
    
    public boolean getRStatus(){
        if(this.car instanceof TaxiQueue) return this.car.isAvailable() && this.status;
        else return this.status;
    }

    //Average's the rating of the current driver given the ratings already recieved
    public void setNewRating(double nR){
        this.rating = (nR + this.rating * this.getTravels().size()) / (this.getTravels().size() + 1);
    }

	public Driver clone(){
		return new Driver(this);
	}

	public boolean equals(Object o){
		if(this == o) return true;
		else if(o == null || this.getClass() != o.getClass()) return false;

		Driver aux = (Driver)o;
		return super.equals(aux) && this.status == aux.getStatus() 
					 && this.rating == aux.getRating() 
					 && this.kmsTraveled == aux.getKmsTraveled()
                     && this.car.equals(aux.getCar());
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("\n");
        sb.append("Available: ").append(this.status).append("\n");
		sb.append("Rating: ").append(this.rating).append("\n");
		sb.append("Kms Traveled: ").append(this.kmsTraveled).append("\n");
		sb.append("Punctuality(0-100%): ").append(this.punctuality*100).append("%\n");
        sb.append("Car: ").append(this.car.toString());

		return sb.toString();
	}

    //Profit between to dates on one Taxi/Driver
    public double profitBetween(LocalDate init, LocalDate end){
        List<Travel> aux = getTravelsBetween(init,end);
        double ret=0;

        for(Travel t: aux)
            ret += t.getCost();

        return ret;
    }
}
