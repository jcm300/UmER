public class Account {

	//instance variavels
	private String email;
	private String name;
	private String password;
	private String homeAddress;
	private String birthday;
}

// contructors
public Account(){
	this.email = null;
	this.name = null;
	this.password = null;
	this.homeAddress = null;
	this.birthday = null;
}

public Account( String email, String name, String password, String homeAddress, String birthday){
    this.email = email;
    this.name = name;
    this.password = password;
    this.homeAddress = homeAddress;
    this.birthday = birthday;
}

public Account (Account a){
    this.email = a.getemail();
    this.name = a.getname();
    this.password = a.password();
    this.

}