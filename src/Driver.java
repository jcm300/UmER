import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class Driver extends Account {
	 
	//instance variables
	private boolean status;
	private double rating;
	private List<Travel> travels;
	private double kmsTraveled;
	private double punctuality;


	//constructors
	public Driver(){
		super();
		this.status = false;
		this.rating = 0.f;
		this.travels = new ArrayList<>();
		this.kmsTraveled = 0.f;
		this.punctuality = 0.f;
	}

	public Driver(String email, String nome, String password, String address, String bday, boolean av, double ratng, List<Travel> tvl, double kms, double pc){
		super(email, nome, password, address, bday);
		this.status = av;
		this.rating = ratng;
		this.travels = tvl.stream()
				  .map(Travel::clone)
				  .collect(Collectors.toCollection(ArrayList::new));
		this.kmsTraveled = kms;
		this.punctuality = pc;
	}

	public Driver(Driver oldDriver){
		super(oldDriver);
		this.status = oldDriver.getStatus();
		this.rating = oldDriver.getRating();
		this.travels = oldDriver.getTravels();
		this.kmsTraveled = oldDriver.getKmsTraveled();
		this.punctuality = oldDriver.getPunctuality();
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

	public List<Travel> getTravels(){

		return this.travels.stream()
				   .map(t->t.clone())
				   .collect(Collectors.toList());

	}

	public void setTravels(List<Travel> nTL){

		this.travels = nTL.stream()
				  .map(Travel::clone)
				  .collect(Collectors.toCollection(ArrayList::new));

	}

	public double getKmsTraveled(){

		return this.kmsTraveled;

	}
	public void setKmsTraveled(double nKms){

		this.kmsTraveled = nKms;

	}

	public double getPunctuality(){

		return this.punctuality;

	}
	
	public void setPunctuality(double p){

		this.punctuality = p;

	}

	//all the travels between two given dates
	public List<Travel> getTravelsBetween(LocalDate init, LocalDate end){

		return this.travels.stream()
				   .filter(t->t.getDate().isAfter(init) && t.getDate().isBefore(end))
				   .collect(Collectors.toList());

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
					 && this.punctuality == aux.getPunctuality()
					 && aux.getTravels().stream()
					   		    .filter(t -> !this.travels.contains(t)).count() == 0L;
	}

	public String toString(){

		StringBuilder sb = new StringBuilder();
		sb.append("Available: ").append(this.status).append("\n");
		sb.append("Rating: ").append(this.rating).append("\n");
		sb.append("Kms Traveled: ").append(this.kmsTraveled).append("\n");
		sb.append("Punctuality(0-100%): ").append(this.punctuality*100).append("%\n");

		return sb.toString();
	}

	//register a travel for a client
	public void addTravel(Travel t){
		this.travels.add(t.clone());
	}
}
