import java.time.LocalDate;

public class Travel {
	
	//instance variables
	private double cost;
	private double time;
	private double distance;
	private Point2D dest;
	private LocalDate date;
	
	//Constructors
	public Travel(){
		this.cost = 0;
		this.time = 0;
		this.distance = 0;
		this.dest = new Point2D();
		this.date = LocalDate.now();
	}

	public Travel(double cost, double time, double distance, LocalDate date, Point2D dest){
		this.cost = cost;
		this.time = time;
		this.distance = distance;
		this.dest = new Point2D(dest);
		this.date = date;
	}

	public Travel(Travel t){
		this.cost = t.getCost();
		this.time = t.getTime();
		this.distance = t.getDistance();
		this.dest = t.getDest();
		this.date = t.getDate();
	}

	//gets and sets
	public double getCost(){
		return this.cost;
	}
	
	public double getTime(){
		return this.time;
	}

	public double getDistance(){
		return this.distance;
	}

	public Point2D getDest(){
		return this.dest.clone();
	}

	public LocalDate getDate(){
		return this.date;
	}

	public void setCost(double c){
		this.cost = c;
	}

	public void setTime(double t){
		this.time = t;
	}

	public void setDistance(double d){
		this.distance = d;
	}

	public void setDest(Point2D nDest){
		this.dest = nDest.clone();
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
		return (this.cost==t.getCost() && this.time==t.getTime() && this.distance==t.getDistance() && this.dest==t.getDest() && this.date.equals(t.getDate()));
	}

	public String toString(){
		StringBuilder r = new StringBuilder();
		r.append("Cost: ").append(this.cost).append("\n");
		r.append("Time: ").append(this.time).append("\n");
		r.append("Distance: ").append(this.distance).append("\n");
		r.append("Destiny: ").append(this.dest.toString()).append("\n");
		r.append("Date: ").append(this.date.toString());
		return r.toString();
	}

	public int hashCode(){
		int r=13;
		long aux;

		aux = Double.doubleToLongBits(this.cost);
		r = r*23 + (int)(aux ^ (aux >>> 32));
		aux = Double.doubleToLongBits(this.time);
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

