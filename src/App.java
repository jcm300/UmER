import java.util.Scanner;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;

public class App implements Serializable{

    private static StateManager curState;
    private static Account curUser;
    private Menu appMenu;
    
    public static void main(String[] args){
        App mApp = new App();
		boolean recover=false, success=false;
		
		try{
			Scanner input = new Scanner(System.in);
			System.out.print("Do you want to recover a previous state?(false or true) ");
	        recover = input.nextBoolean();
		}catch(Exception e){
            System.out.println(e.getMessage());
        }
		if(recover){
			try{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("state"));
				mApp.curState = (StateManager) ois.readObject();
				ois.close();
			}catch(Exception e){
					System.out.println("Not loaded! (" + e.getMessage() + ")");
			}	
		}
        mApp.run(false);
    }

    private App(){
        String[] mOps = {"Login", "Register", "Top 10 Clients", "Top 5 Drivers"};
        String[] cOps = {"Request a Ride", "Check Travel Registry", "Logout"};
        String[] dOps = {"Associate a new vehicle", "Check Travel Registry", "Logout"};
        this.curState = new StateManager();
        this.curUser = null;
        this.appMenu = new Menu(mOps, cOps, dOps);
    }

    public void run(boolean loggedIn){
        do{
            if(!loggedIn) loggedIn = this.menuActions();
            else{ 
                if(this.curUser instanceof Client)
                    loggedIn = this.userActions();
                else loggedIn = this.driverActions();
            }    
        }while(this.appMenu.getOpt() != 0);
    }

    public boolean menuActions(){  
        Account aux;
        boolean login=false;

        this.appMenu.mMenu();
        switch(this.appMenu.getOpt()){
                case 1:
                    try{
                        aux = login();
                        login = true;
                        this.curUser = aux;
                        System.out.println("Log in successful");
                    }catch(WrongPasswordException | UserNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try{
                        aux = register();
                        this.curState.addUser(aux);
                    }catch(DuplicateRegistrationException e){
                        System.out.println("User with email "+e.getMessage()+"already exists");
                    }
                    break;
				case 0:
					try{
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("state"));
                        oos.writeObject(this.curState);
                        oos.flush();
                        oos.close();
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Exiting...");
                    break;
        }
        return login;
    }

    public boolean userActions(){
        boolean login=true;

        this.appMenu.cliMenu();
        switch(this.appMenu.getOpt()){
                case 1:
                    System.out.println("Requesting a ride");
                    break;
                case 2:
                    System.out.println("Checking travel reg");
                    break;
                case 3:
                    login = false;
                    System.out.println("Logging out");
                    this.curState.updateUser(this.curUser);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    login = false;
                    break;
        }
        return login;
    }

    public boolean driverActions(){
        boolean login=true;

        this.appMenu.dMenu();
        switch(this.appMenu.getOpt()){
                case 1:
                    System.out.println("Fetching a taxi");
                    break;
                case 2:
                    System.out.println("Checking travel reg");
                    break;
                case 3:
                    login = false;
                    System.out.println("Logging out");
                    this.curState.updateUser(this.curUser);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    login = false;
                    break;
        }
        return login;
    }


    public Account register () throws DuplicateRegistrationException{
        Account ret = null;
        String name = null, email = null, password = null, homeAdress = null, birthday = null;
        boolean enter = false, type = false;
        
		try{
			Scanner input = new Scanner(System.in);
    		System.out.print("Name: ");
			name = input.nextLine();
			System.out.print("Email: ");
    		email = input.nextLine();
    		System.out.print("Password: ");
    		password = input.nextLine();
			System.out.print("Home Adress: ");
			homeAdress = input.nextLine();
			System.out.print("BirthDay(day-month-year): ");
    		birthday = input.nextLine();
			System.out.print("Are you a client(write false) or a driver(write true)? ");
			type = input.nextBoolean();
		}catch(Exception e){
				System.out.println(e.getMessage());
		}

        if(this.curState.userExists(email)) throw new DuplicateRegistrationException(email);
		
		if(type) ret = new Driver(name,email,password,homeAdress,birthday,new ArrayList<Travel>(),false,0.d,0.d,0.d);
		else ret = new Client(name,email,password,homeAdress,birthday,new ArrayList<Travel>(),new Point2D());
    
		return ret; 
	}

    public Account login () throws WrongPasswordException, UserNotFoundException{
        boolean enter = false;
        String email = null, password = null;
        Account ret = null;
        
		try{
			Scanner input = new Scanner(System.in);
        	System.out.print("Email: ");
        	email = input.nextLine();
        	System.out.print("Password: ");
        	password = input.nextLine();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

        if(this.curState.userExists(email)){
            ret = this.curState.getUser(email);
            enter = ret.getPassword().equals(password);
        }else throw new UserNotFoundException("No user found with "+email);

        if(enter) return ret;
        else throw new WrongPasswordException("Incorrect password for "+email); //paswords didn't match
    }
}
