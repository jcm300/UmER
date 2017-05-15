import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.io.Serializable;

public class Account implements Serializable{

	//instance variables
	private String email;
	private String name;
	private String password;
	private String homeAddress;
	private String birthday;
	private List<Travel> travels;

    // constructors
    public Account(){
	   this.email = null;
	   this.name = null;
	   this.password = null;
	   this.homeAddress = null;
	   this.birthday = null;
	   this.travels = new ArrayList<>();
    }

    public Account( String email, String name, String password, String homeAddress, String birthday, List<Travel> tvl){
        this.email = email;
        this.name = name;
        this.password = password;
        this.homeAddress = homeAddress;
        this.birthday = birthday;
		this.travels = tvl.stream().map(Travel::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public Account (Account a){
        this.email = a.getEmail();
        this.name = a.getName();
        this.password = a.getPassword();
        this.homeAddress = a.getHomeAddress();
        this.birthday =a.getBirthday();
		this.travels = a.getTravels();
    }

    //gets 

    public String getEmail(){
            return this.email;
    }

    public String getName(){
            return this.name;
    }

    public String getPassword(){
            return this.password;
    }

    public String getHomeAddress(){
            return this.homeAddress;
    }

    public String getBirthday(){
            return this.birthday;
    }

	public List<Travel> getTravels(){
   
		return this.travels.stream()
                           .map(t->t.clone())
						   .collect(Collectors.toList());
    }

    //sets 

    public void setEmail (String e){
        this.email = e;
    }

    public void setName (String n){
        this.name = n;
    }

    public void setPassword (String pw){
        this.password = pw;
    }

    public void setHomeAddress (String ha){
        this.homeAddress = ha;
    }

    public void setBirthday (String b){
        this.birthday = b;
    }

	public void setTravels(List<Travel> nTL){

        this.travels = nTL.stream()
                          .map(Travel::clone)
                          .collect(Collectors.toCollection(ArrayList::new)); 
    }	

    //clone, equals, toString, hashcode, compareTo

    public Account clone(){
	       return new Account(this);
    }

    public boolean equals(Object o){
        if (o==this) return true;
        if ((o==null) || (o.getClass()!=this.getClass())) return false;
        Account a = (Account)o;
            return (this.email.equals(a.getEmail()) && this.name.equals(a.getName()) && this.password.equals(a.getPassword()) && this.homeAddress.equals(a.getHomeAddress()) && this.birthday.equals(a.getBirthday())) && a.getTravels().stream().filter(t -> !this.travels.contains(t)).count() == 0L;
    }

    public String toString(){
        StringBuilder r = new StringBuilder();
        r.append("Email: ").append(this.email).append("\n");
        r.append("Name: ").append(this.name).append("\n");
        r.append("Password: ").append(this.password).append("\n");
        r.append("HomeAddress: ").append(this.homeAddress).append("\n");
        r.append("Birthday: ").append(this.birthday).append("\n");
		r.append("Travels: ").append(this.travels.toString());
		return r.toString();
    }

    public int hashCode(){
        int r=7;
               
        r = r*11 + this.email.hashCode();
        r = r*11 + this.name.hashCode();
        r = r*11 + this.password.hashCode();
        r = r*11 + this.homeAddress.hashCode();
        r = r*11 + this.birthday.hashCode();
        r = r*11 + this.travels.hashCode();
		return r;
    }

    public int compareTo (Account a){
         return this.name.compareTo(a.getName());

    }

	//methods
	
	//all the travels between two given dates
    public List<Travel> getTravelsBetween(LocalDate init, LocalDate end){
    	return this.travels.stream()
   				   .filter(t->t.getDate().isAfter(init) && t.getDate().isBefore(end))
                           .collect(Collectors.toList()); 
	}

	//register a travel
    public void addTravel(Travel t){
    	this.travels.add(t.clone());
    }	
}
