import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeMap;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class App{

    private StateManager curState;
    private Account curUser;
    private Menu appMenu;
    private static int count=0;
    
    public static void main(String[] args){
        App mApp = null;
		boolean recover=false, success=false;
		
		try{
            mApp = new App();
			Scanner input = new Scanner(System.in);
			System.out.print("Do you want to recover a previous state?(false or true) ");
	        recover = input.nextBoolean();
		}catch(Exception e){
            System.out.println(e.getMessage());
        }
		if(recover){
			try{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("state"));
				mApp.curState = (StateManager)ois.readObject();
				ois.close();
			}catch(Exception e){
					System.out.println("Not loaded! (" + e.getMessage() + ")");
			}	
		}
        mApp.run(false);
    }

    private App() throws TooManyInstancesException{
        if(count == 0){
            String[] mOps = {"Login", "Register", "Top 10 Clients", "Top 5 Drivers"};
            String[] cOps = {"Request a Ride", "Check Travel Registry", "Logout"};
            String[] dOps = {"Associate a new vehicle", "Check Travel Registry", "Logout"};
            this.curState = new StateManager();
            this.curUser = null;
            this.appMenu = new Menu(mOps, cOps, dOps);
        }else throw new TooManyInstancesException();
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
                        System.out.println("User with email "+e.getMessage()+" already exists");
                    }
                    break;
                case 3:
                    System.out.println(top10Clients());
                    break;
                case 4:
                    //System.out.println(top5Drivers());
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
                    getRide();
                    break;
                case 2:
                    System.out.println("Checking travel reg");
                    checkReg();
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
                    checkReg();
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
        boolean enter = false, type = false, success=false;
        
		while(!success){
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
                System.out.println("BirthDay:");
                birthday = dateInput();
                System.out.print("Are you a client(write false) or a driver(write true)? ");
                type = input.nextBoolean();
                success = true;
		    }catch(Exception e){
				System.out.println("Input error ("+e.getMessage() + ") please try again");
            }
        }

        if(this.curState.userExists(email)) throw new DuplicateRegistrationException(email);
		
		if(type) ret = new Driver(name,email,password,homeAdress,birthday,new ArrayList<Travel>(),false,0.d,0.d,0.d);
		else ret = new Client(name,email,password,homeAdress,birthday,new ArrayList<Travel>(),new Point2D());
    
		return ret; 
	}

    public Account login () throws WrongPasswordException, UserNotFoundException{
        boolean enter = false, success = false;
        String email = null, password = null;
        Account ret = null;
        
        while(!success){
            try{
                Scanner input = new Scanner(System.in);
                System.out.print("Email: ");
                email = input.nextLine();
                System.out.print("Password: ");
                password = input.nextLine();
                success = true;
            }catch(Exception e){
                System.out.println("Input error ("+e.getMessage() + ") please try again");
            }
        }
        if(this.curState.userExists(email)){
            ret = this.curState.getUser(email);
            enter = ret.getPassword().equals(password);
        }else throw new UserNotFoundException("No user found with "+email);

        if(enter) return ret;
        else throw new WrongPasswordException("Incorrect password for "+email); //paswords didn't match
    }
	
	private String dateInput() {
		int year = 0, month = 0, day = 0;
		boolean success = false;
		
		while(!success){
			try{
				Scanner input = new Scanner(System.in);
        		System.out.print("Year: ");
        		year = input.nextInt();
        		System.out.print("Month: ");
        		month = input.nextInt();
        		System.out.print("Day: ");
        		day = input.nextInt();
				success = true;
			}catch(Exception e){
				System.out.println("Input error ("+e.getMessage() + ") please try again");
			}
		}
        return (year + "-" + month + "-" + day);
	}

	public void checkReg(){
			String date;
			LocalDate init, end;
			int i=1;

			try{
				System.out.println("Select initial date:");
				date = dateInput();
				init = LocalDate.parse(date);
				System.out.println("Select final date:");
                date = dateInput();
				end = LocalDate.parse(date);
				List<Travel> reg = curUser.getTravelsBetween(init,end);
				System.out.println("Travels done between dates:");
                if(reg.isEmpty()) System.out.println("No travels.");
                else for(Travel t: reg){
						System.out.println(i + ": "+ t.toString());
						i++;
				     }	
			}catch(Exception e){
				System.out.println("Invalid date. Try again.");
			}	
	}

    public void getRide(){
        Client aux=(Client)this.curUser;
        Scanner in=new Scanner(System.in);
        Point2D tmp=new Point2D();
        InfoTravel res=null;
        String plate;
        int coord;
        double rat;
        char c;

        try{
            System.out.println("Onde se encontra: ");
            System.out.print("X: ");
            coord = in.nextInt();
            tmp.setX(coord);
            System.out.print("Y: ");
            coord = in.nextInt();
            tmp.setY(coord);
            aux.setLocation(tmp);
            System.out.println("Indique o destino: ");
            System.out.print("X: ");
            coord = in.nextInt();
            tmp.setX(coord);
            System.out.print("Y: ");
            coord = in.nextInt();
            tmp.setY(coord);
            System.out.println("Deseja algum taxi em especifico?(S/N)");
            c=in.nextLine().charAt(0);
            if(c=='N') res=aux.requestRide(this.curState.getVehicles(), tmp);
            else{
                System.out.print("Insira a matricula:");
                plate = in.nextLine();
                System.out.println("Deseja reservar viagem?(S/N)");
                c=in.nextLine().charAt(0);
                if(c=='N') res=aux.requestTaxi(plate, this.curState.getVehicles(), tmp);
                else res=aux.bookTaxi(plate, this.curState.getVehicles(), tmp);
            }
            System.out.println(res.getTravel().toString());
            System.out.print("Avaliaçao do condutor (0-100): ");
            rat = in.nextDouble();
            res.getDriver().getNewRating(rat);
            this.curState.updateUser(res.getDriver());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public String top10Clients(){
        TreeMap<Double,String> aux = new TreeMap<Double,String>();
        Double d=null;
        StringBuilder ret = new StringBuilder();
        int i;
        
        for(Account a: this.curState.getUsers().values()){
            if(a.getClass().getSimpleName().equals("Client")) aux.put(a.getCosts(),a.getName());
        }
        
        for(i=1; aux.size()>0 && i<=10; i++){
            d=aux.lastKey();
            ret.append(i).append("- ").append(aux.get(d)).append("\n");
            aux.remove(d);
        }
        return ret.toString();
    }

    public String top5Drivers(){
        TreeMap<Double,String> aux = new TreeMap<Double,String>();
        Double d=null;
        StringBuilder ret = new StringBuilder();
        int i;
        
        for(Account a: this.curState.getUsers().values())
            if(a.getClass().getSimpleName().equals("Driver")) aux.put(a.getFluctuationTime(),a.getName());
        
        for(i=1; aux.size()>0 && i<=5; i++){
            d=aux.lastKey();
            ret.append(i).append("- ").append(aux.get(d)).append("\n");
            aux.remove(d);
        }
        return ret.toString();
    }

}
