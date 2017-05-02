/**
 * Class that manages the interactions between the 
 * classes that make up the UmER app, such as 
 * drivers and clients and companies.
 * 
 * @author José Martins, Mariana Costa, Miguel Quaresma
 * 
 */

import java.util.ArrayList;

public class App{

    private ArrayList<Client> users;
    private ArrayList<Driver> drivers;
    private ArrayList<Taxi> vehicles;

    private App(){

        this.users = ArrayList<>();
        this.drivers = ArrayList<>();
        this.vehicles = ArrayList<>();

    }

    private static class SingletonHolder {
		private static final App INSTANCE = new App();
	}

	public static synchronized App getInstance() {
		return SingletonHolder.INSTANCE;
	}

    //gets & sets
    public ArrayList<Client> getUsers(){

        return this.users.stream().map(Client::clone).collect(Collectors.toCollection(ArrayList::new));

    }

    public void setUsers(ArrayList<Client> nC){

        this.users = nC.stream().map(Client::clone).collect(Collectors.toCollection(ArrayList::new));

    }

    public ArrayList<Driver> getDrivers(){

        return this.drivers.stream().map(Driver::clone).collect(Collectors.toCollection(ArrayList::new));

    }

    public void  setDrivers(ArrayList<Driver> nD){

        this.drivers = nD.stream().map(Driver::clone).collect(Collectors.toCollection(ArrayList::new));

    }

    public ArrayList<Taxi> getVehicles(){

        return this.vehicles.stream().map(Taxi::clone).collect(Collectors.toCollection(ArrayList::new));

    }

    public void setVehicles(ArrayList<Taxi> nT){

        this.vehicles = nT.stream().map(Taxi::clone).collect(Collectors.toCollection(ArrayList::new));
        
    }

}