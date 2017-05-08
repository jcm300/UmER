import java.util.ArrayList;

public class Van extends Taxi {

	//Constructors
	public Van(){
		super();
	}
	
	public Van(Van v){
		super(v);
	}

	public Van(String p,double as, double ppk, double rel, Point2D l, Driver d, ArrayList<Travel> t){
		super(p,as,ppk,rel,l,d,t);
	}

	//Methods
	public Van clone(){
		return new Van(this);
	}

	public boolean equals(Object o){
		if(o==this) return true;
		if((o==null) || o.getClass()!=this.getClass()) return false;
		Van v = (Van)o;
		return super.equals(v);
	}

	public String toString(){
		return super.toString();
	}

	public int hashCode(){
		return super.hashCode();
	}

	public int compareTo(Van v){
		return super.compareTo(v);
	}
}
