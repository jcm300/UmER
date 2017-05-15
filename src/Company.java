import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.io.Serializable;

public class Company implements Serializable{

	//instance variables
	private ArrayList<Taxi> taxis;
	private ArrayList<Driver> drivers;
	private String name;


	//constructors
	public Company(){

		this.taxis = new ArrayList<>();
		this.drivers = new ArrayList<>();
		this.name = new String("");
	}

	public Company(String n, ArrayList<Taxi> tx, ArrayList<Driver> dv){

		this.taxis = tx.stream().map(Taxi::clone).collect(Collectors.toCollection(ArrayList::new));
		this.drivers = dv.stream().map(Driver::clone).collect(Collectors.toCollection(ArrayList::new));		
		this.name = n;

	}

	//methods
 
    //Profit between to dates on one Company
    public double profitBetween(LocalDate init, LocalDate end){
        double ret=0;
 
        for(Taxi t: this.taxis)
        	ret += t.profitBetween(init,end);
 
        return ret;
    }
}
