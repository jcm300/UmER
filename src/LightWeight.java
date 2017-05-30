public class LightWeight extends Taxi {
		
	//Constructors
	public LightWeight(){
        	super();
        }

        public LightWeight(LightWeight l){
                super(l);
        }

        public LightWeight(String p,double as, double ppk, double rel, Point2D l){
                super(p,as,ppk,rel,l);
        }
  
        //Methods
        public LightWeight clone(){ 
                return new LightWeight(this);
        }  
             
        public boolean equals(Object o){
                if(o==this) return true;
                if((o==null) || o.getClass()!=this.getClass()) return false;
                LightWeight l = (LightWeight)o;
                return super.equals(l);
	}
      
        public String toString(){
                return super.toString();
        }
        
        public int hashCode(){
                return super.hashCode();
        }
  
        public int compareTo(LightWeight l){
                return super.compareTo(l);
        }
}
