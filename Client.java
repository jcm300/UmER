import java.util.ArrayList;

public class Client extends Account {
	
	//instance variavels
	private Point2D location;
	private ArrayList<Travel> travels;  


// contrutores
public Client(){
	this.location = new Point2D();
	this.travels = new ArrayList<Travel>();
}

public Client( Point2D location, ArrayList<Travel> travels){
    this.location = location.clone();
    this.travels = travels.clone();
}

public Client (Client c){
    this.location = c.getLocation();
    this.travels = c.getTravels();
}

