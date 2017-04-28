/* *
 * 2D point
*/

import static java . lang . Math . abs ;
public class Point2D {
	
	//Instance variavels
	private double x , y ;
	
	// Constructors
	public Point2D ( double cx , double cy ) {
		this.x = cx; 
		this.y = cy; 
	}

	public Point2D () { this (0.0 , 0.0); }
	public Point2D ( Point2D p ) {
		this.x = p.getX(); 
		this.y = p.getY(); 
	}
	
	//Instance Methods
	public double getX() { return this.x; }
	public double getY() { return this.y; }

	/* * increment of coordinates */
	public void incCoord ( double dx , double dy ) {
		this.x += dx; 
		this.y += dy;
	}

	/* * decrement of coordinates */
	public void decCoord ( double dx , double dy ) {
		this.x -= dx; 
		this.y -= dy;
	}

	/* * Sum the coordinates of the parameter point to the receiving point */
	public void sumPoint ( Point2D p ) {
		this.x += p.getX(); 
		this.y += p.getY();
	}

	/* * Sums the parameter values and returns a new point */
	public Point2D sumPoint ( double dx , double dy ) {
		return new Point2D (this.x + dx, this.y + dy);
	}

	/* determines if a point is symmetric (distance to XX axis is equals to distance to YY axis) */
	public boolean isSimetric () {
		return abs(this.x) == abs(this.y);
	}

	/* * Verifies that both coordinates are positive */
	public boolean coordPos () {
		return (this.x>0 && this.y>0);
	}

	/* Check if the 2 points are equal */
	public boolean equals (Object o) {
		if(this==o) return true;
		if((o==null) | (this.getClass()!=o.getClass())) return false;
		Point2D p = (Point2D)o;
		return (this.x==p.getX() && this.y==p.getY());
	}

	// Determines if two Point2D have the same instance values
	public boolean equalsValues (Point2D p){
		if(p!=null) return (this.x==p.getX() && this.y==p.getY());
		else return false;
	}

	/* * Converts to a textual representation */
	public String toString () {
		return new String (" Pt2D = " + this.x + " , " + this.y);
	}

	/* * Create a copy*/
	public Point2D clone () {
		return new Point2D (this);
	}

	/* * hashCode of Point2D */
	public int hashCode (){
		int r=13;
		long aux;
		
		aux = Double.doubleToLongBits(this.x);
		r = r*23 + (int)(aux ^ (aux >>> 32));
		aux = Double.doubleToLongBits(this.y);
                r = r*23 + (int)(aux ^ (aux >>> 32));
	       	return r;	
	}
}



