/**
 * Class that manages the interactions between the 
 * classes that make up the UmER app, such as 
 * drivers and clients and companies.
 * 
 * @author José Martins, Mariana Costa, Miguel Quaresma
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class App{

    private ArrayList<Client> users;
    private ArrayList<Driver> drivers;
    private Map<String,Taxi> vehicles;

    private App(){

        this.users = new ArrayList<Client>();
        this.drivers = new ArrayList<Driver>();
        this.vehicles = new HashMap<>();

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

    public Map<String,Taxi> getVehicles(){
        return this.vehicles.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().clone()));
    }

    public void setVehicles(Map<String,Taxi> nT){
        this.vehicles = nT.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().clone()));
    }

    public void addUser(Client nUser){
        this.users.add(nUser.clone());
    }

    public void addDriver(Driver nDriver){
        this.drivers.add(nDriver.clone());
    }

    public void addVehicle(Taxi nTaxi){
        this.vehicles.put(nTaxi.getPlate(), nTaxi.clone());
    }
}
