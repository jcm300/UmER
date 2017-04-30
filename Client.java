import java.util.ArrayList;

public class Client extends Account {
	
	//instance variavels
	private Point2D location;
	private ArrayList<Travel> travels;  


// contrutores
public Client(){
	this.location = (0,0);
	this.travels = null;
	
}

public Client( Point2D location, ArrayList<Travel> travels){
    this.location = location;
    this.travels = travels;
}

public Client (Client c){
    this.location = c.getLocation();
    this.travels = c.getTravels();
   
}

