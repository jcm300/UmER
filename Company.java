import java.util.ArrayList;

public class Company {

	//instance variables
	private ArrayList<Driver> taxis;
	private ArrayList<Driver> drivers;
	private String name;


	//constructors
	public Company(){

		this.taxis = new ArrayList<>();
		this.drives = new ArrayList<>();
		this.name = new String("");
	}

	public Company(String n, ArrayList<Taxi> tx, ArrayList<Driver> dv){

		this.taxis = tx.stream().map(Taxi::clone).collect(Collectors.toCollection(ArrayList::new));
		this.drivers = dv.stream().map(Driver::clone).collect(Collectors.toCollection(ArrayList::new));		
		this.name = n;

	}



}
