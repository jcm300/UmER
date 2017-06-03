import java.time.LocalDate;
import java.io.Serializable;

public class Travel implements Serializable{
	
	//instance variables
	private double cost;
	private double rTime;
   	private double pTime;
	private double distance;
	private Point2D src;
	private Point2D dest;
	private LocalDate date;

	//Constructors
	public Travel(){
		this.cost = 0;
		this.rTime = 0;
		this.pTime = 0;
		this.distance = 0;
		this.dest = new Point2D();
		this.src = new Point2D();
		this.date = LocalDate.now();
	}

	public Travel(double bCost, double rTime, double pTime, double distance, LocalDate date, Point2D dest, Point2D src){
        if((rTime-pTime)/pTime >= 0.25) this.cost = bCost*(1-(rTime-pTime)/pTime);
        else this.cost=bCost;
		this.rTime = rTime;
		this.pTime = pTime;
		this.distance = distance;
		this.dest = new Point2D(dest);
		this.src = new Point2D(src);
		this.date = date;
	}

	public Travel(Travel t){
		this.cost = t.getCost();
        this.rTime = t.getrTime();
		this.pTime = t.getpTime();
		this.distance = t.getDistance();
		this.dest = t.getDest();
        this.src = t.getSrc();
		this.date = t.getDate();
	}

	//gets and sets
	public double getCost(){
		return this.cost;
	}
	
	public double getrTime(){
		return this.rTime;
	}
    
    public double getpTime(){
		return this.pTime;
	}

	public double getDistance(){
		return this.distance;
	}

	public Point2D getDest(){
		return this.dest.clone();
	}

    public Point2D getSrc(){
		return this.src.clone();
	}

	public LocalDate getDate(){
		return this.date;
	}

	public void setCost(double c){
		this.cost = c;
	}

	public void setrTime(double t){
		this.rTime = t;
	}

    public void setpTime(double t){
		this.pTime = t;
	}

	public void setDistance(double d){
		this.distance = d;
	}

	public void setDest(Point2D nDest){
		this.dest = nDest.clone();
	}

    public void setSrc(Point2D nSrc){
		this.src = nSrc.clone();
	}

	public void setDate(LocalDate ld){
		this.date = ld;
	}

   	public Travel clone(){
		return new Travel(this);
	}

	public boolean equals(Object o){
		if(this == o) return true;
		if((o == null) || (o.getClass()!=this.getClass())) return false;
		Travel t = (Travel)o;
		return (this.cost==t.getCost() && this.rTime==t.getrTime() && this.pTime==t.getpTime() && 
                this.distance==t.getDistance() && this.src.equals(t.getSrc()) && 
                this.dest.equals(t.getDest()) && this.date.equals(t.getDate()));
	}

	public String toString(){
		StringBuilder r = new StringBuilder();
		r.append("Cost: ").append(this.cost).append(" | ");
		r.append("Effective Time: ").append(this.rTime).append(" | ");
		r.append("Predicted Time: ").append(this.pTime).append(" | ");
		r.append("Distance: ").append(this.distance).append(" | ");
		r.append("Source: ").append(this.src.toString()).append(" | ");
		r.append("Destiny: ").append(this.dest.toString()).append(" | ");
		r.append("Date: ").append(this.date.toString());
		return r.toString();
	}

	public int hashCode(){
		int r=13;
		long aux;

		aux = Double.doubleToLongBits(this.cost);
		r = r*23 + (int)(aux ^ (aux >>> 32));
		aux = Double.doubleToLongBits(this.rTime);
        r = r*23 + (int)(aux ^ (aux >>> 32));
		aux = Double.doubleToLongBits(this.distance);
        r = r*23 + (int)(aux ^ (aux >>> 32));
		r = r*23 + this.date.hashCode();
		return r;
	}

	public int compareTo(Travel t){
		return this.date.compareTo(t.getDate());
	}
}

