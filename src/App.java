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
import java.util.stream.Collectors;

public class App{

    private StateManager curState; 
    private Account curUser; //cur user logged in the system, null if none
    private Menu appMenu; //cur menu based on curUser: wether any user logged in and if it is, the type of user
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
        if(count == 0){ //only allow one instance of App to be running
            String[] mOps = {"Login", "Register", "Top 10 Clients", "Top 5 Drivers"};
            String[] cOps = {"Request a Ride", "Check Travel Registry", "Logout"};
            String[] dOps = {"Associate a new vehicle", "Check Travel Registry", "Toggle Status", "Logout"};
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

    public void save(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("state"));
            oos.writeObject(this.curState);
            oos.flush();
            oos.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Main menu which displays when no
     * user is logged in
     */
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
                System.out.println(top5Drivers());
                break;
            case 0:
                save();
                System.out.println("Exiting...");
                break;
        }
        return login;
    }

    public boolean userActions(){
        boolean login=true;

        System.out.println(this.curUser.toString());
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
                    save();
                    login = false;
                    break;
        }
        return login;
    }

    public boolean driverActions(){
        boolean login=true;
        
        System.out.println(this.curUser.toString());
        this.appMenu.dMenu();
        switch(this.appMenu.getOpt()){
                case 1:
                    Taxi tax=this.getTaxiInfo();
                    ((Driver)this.curUser).setCar(tax);
                    break;
                case 2:
					System.out.println("Checking travel reg");
                    checkReg();
					break;
                case 3:
                    ((Driver)this.curUser).setStatus(!((Driver)this.curUser).getStatus());
                    ((Driver)this.curUser).dispatchQueue();
                    System.out.println("You are now appearing as "+(((Driver)this.curUser).getStatus() ? "" : "un")+"available");
                    break;
                case 4:
                    login = false;
                    System.out.println("Logging out");
                    this.curState.updateUser(this.curUser);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    save();
                    login = false;
                    break;
        }
        return login;
    }

    public Account register () throws DuplicateRegistrationException{
        Scanner input = new Scanner(System.in);
        Account ret=null;
        String name=null, email=null, password=null, homeAdress=null, birthday=null;
        double aux=0.f;
        boolean enter=false, type=false, success=false;
        char c;
        
		while(!success){
            try{
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
                input.nextLine();
            }
        }

        if(this.curState.userExists(email)) throw new DuplicateRegistrationException(email);
		
		if(type){
            success=false;
            while(!success){ 
                try{
                    System.out.print("Appear as available?(Y/N) ");
                    input.nextLine();
                    c=input.nextLine().charAt(0);
                    ret=new Driver(email,name,password,homeAdress,birthday,new ArrayList<Travel>(),c=='Y',0.d,0.d,0.d,this.getTaxiInfo());
                    success=true;
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
		else ret = new Client(email,name,password,homeAdress,birthday,new ArrayList<Travel>(),new Point2D());
    
		return ret; 
	}

    private Taxi getTaxiInfo(){
        Scanner input = new Scanner(System.in);
        Taxi tax=null;
        String plate=null;
        double aux=0.f;
        int taxiType=-1;
        boolean success=false;

        while(!success){
            try{
                System.out.println("What type of taxi do you own");
                System.out.print("1-Motorbike\n2-Lightweight\n3-Van\nChoice: ");
                taxiType=input.nextInt(); 
                switch(taxiType){
                    case 1:
                        tax = new MotorBike();
                        success=true;
                        break;
                    case 2:
                        tax = new LightWeight();
                        success=true;
                        break;
                    case 3:
                        tax = new Van();
                        success=true;
                        break;
                    default:
                        System.out.println("Invalid option");
                        success = false;
                        break;
                }
            }catch(Exception e){
                success=false;
                System.out.println(e.getMessage());
            }
        }
        success=false;
        input.nextLine();
        while(!success){
            try{
                System.out.println("Taxi info"); 
                System.out.print("Number plate: "); 
                plate = input.nextLine();
                tax.setPlate(plate);
                System.out.print("Price charged per km: ");
                aux=input.nextDouble();
                tax.setPricePerKm(aux);
                System.out.print("Average Speed: ");
                aux=input.nextDouble();
                tax.setAverageSpeed(aux);
                success = true;
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        } 
        return tax;
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
		String year = null, month = null, day = null;
		boolean success = false;
		
		while(!success){
			try{
				Scanner input = new Scanner(System.in);
        		System.out.print("Year: ");
        		year = input.nextLine();
        		System.out.print("Month: ");
        		month = input.nextLine();
        		System.out.print("Day: ");
        		day = input.nextLine();
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
				System.out.println(e.getMessage());
			}	
	}

    public void getRide(){
        Client aux=(Client)this.curUser;
        Scanner in=new Scanner(System.in);
        Point2D tmp=new Point2D();
        Driver res=null;
        String plate=null;
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
            in.nextLine();
            tmp.setY(coord);
            System.out.println("Deseja algum taxi em especifico?(S/N)");
            c=in.nextLine().charAt(0);
            if(c=='N' || c=='n') res=aux.requestRide(this.curState.getUserList(), tmp);
            else{
                System.out.print("Insira a matricula:");
                plate = in.nextLine();
                res=aux.requestTaxi(plate, this.curState.getUserList(), tmp);
            }
            if(!res.getStatus()) System.out.println(res.getLastEnqueued().toString());
            else System.out.println(res.getLastTravel().toString());
            System.out.print("Avaliaçao do condutor (0-100): ");
            rat = in.nextDouble();
            res.setNewRating(rat);
            this.curState.updateUser(res);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public String top10Clients(){
        StringBuilder sb = new StringBuilder();
        List<String> aux = this.curState.getUsers().values().stream()
                               .filter(a->a.getClass().getSimpleName().equals("Client"))
                               .sorted(new TopXComparator())
                               .limit(10)
                               .map(a->a.getName())
                               .collect(Collectors.toList());
        int i=1;
        for(String st : aux){
            sb.append(i).append("- ").append(st).append("\n");
            i++;
        }

        return sb.toString();
    }

    public String top5Drivers(){
        StringBuilder sb = new StringBuilder();
        List<String> aux = this.curState.getUsers().values().stream()
                               .filter(a->a.getClass().getSimpleName().equals("Driver"))
                               .sorted(new TopXComparator())
                               .limit(5)
                               .map(a->a.getName())
                               .collect(Collectors.toList());
        int i=1;
        for(String st : aux){
            sb.append(i).append("- ").append(st).append("\n");
            i++;
        }
        return sb.toString();
    }

}
