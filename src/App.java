import java.util.Scanner;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class App{

    private static StateManager curState;
    private static Account curUser;
    private static Menu appMenu;
    
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
				App.curState = (StateManager) ois.readObject();
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
        App.curState = new StateManager();
        App.curUser = null;
        App.appMenu = new Menu(mOps, cOps, dOps);
    }

    public void run(boolean loggedIn){
        do{
            if(!loggedIn) loggedIn = App.menuActions();
            else{ 
                if(App.curUser instanceof Client)
                    loggedIn = App.userActions();
                else loggedIn = App.driverActions();
            }    
        }while(App.appMenu.getOpt() != 0);
    }

    public boolean menuActions(){  
        Account aux;
        boolean login=false;

        App.appMenu.mMenu();
        switch(App.appMenu.getOpt()){
                case 1:
                    try{
                        aux = login();
                        login = true;
                        App.curUser = aux;
                        System.out.println("Log in successful");
                    }catch(WrongPasswordException | UserNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try{
                        aux = register();
                        App.curState.addUser(aux);
                    }catch(DuplicateRegistrationException e){
                        System.out.println("User with email "+e.getMessage()+"already exists");
                    }
                    break;
				case 0:
					try{
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("state"));
                        oos.writeObject(App.curState);
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

        App.appMenu.cliMenu();
        switch(App.appMenu.getOpt()){
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
                    App.curState.updateUser(App.curUser);
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

        App.appMenu.dMenu();
        switch(App.appMenu.getOpt()){
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
                    App.curState.updateUser(App.curUser);
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

        if(App.curState.userExists(email)) throw new DuplicateRegistrationException(email);
		
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
        if(App.curState.userExists(email)){
            ret = App.curState.getUser(email);
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
				for(Travel t: reg){
						System.out.println(i + ": "+ t.toString());
						i++;
				}	
			}catch(Exception e){
				System.out.println(e.getMessage());
			}	
	}

    public void getRide(){
        Scanner in = new Scanner(System.in);
        Point2D tmp = new Point2D();
        Travel res=null;
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
            App.curUser.setLocation(tmp);
            System.out.println("Indique o destino: ");
            System.out.print("X: ");
            coord = in.nextInt();
            tmp.setX(coord);
            System.out.print("Y: ");
            coord = in.nextInt();
            tmp.setY(coord);
            System.out.println("Deseja algum taxi em especifico?(S/N)");
            c = in.nextChar();
            if(c == 'N') App.curUser.requestRide(App.curState.getVehicles(), dest);
            else{
                System.out.print("Insira a matricula:");
                plate = in.nextLine();
                System.out.println("Deseja reservar viagem?(S/N)")
                c = in.nextChar();
                if(c == 'N') res=App.curUser.requestTaxi(plate, App.curState.getVehicles(), dest);
                else res=App.curUser.bookTaxi(plate, App.curState.getVehicles, dest);
            }
            System.out.println(aux.toString());
            System.out.print("Avaliaçao do condutor (0-100): ");
            rat = in.nextDouble();
            aux.getDriver().getNewRating(rat);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
