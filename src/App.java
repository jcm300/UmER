/**
 * Class that manages the interactions between the 
 * classes that make up the UmER app, such as 
 * drivers and clients and companies.
 * 
 * @author José Martins, Mariana Costa, Miguel Quaresma
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class App{

    private Map<String,Account> users;
    private Map<String,Taxi> vehicles;

    private App(){

        this.users = new HashMap<>();
        this.vehicles = new HashMap<>();

    }

    private static class SingletonHolder {
		private static final App INSTANCE = new App();
	}

	public static synchronized App getInstance() {
		return SingletonHolder.INSTANCE;
	}

    //gets & sets
    public Map<String, Account> getUsers(){
        return this.users.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public void setUsers(Map<String, Account> nC){
        this.users = nC.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public Map<String,Taxi> getVehicles(){
        return this.vehicles.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().clone()));
    }

    public void setVehicles(Map<String,Taxi> nT){
        this.vehicles = nT.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().clone()));
    }

    public void addUser(Account nUser){
        this.users.put(nUser.getName(), nUser.clone());
    }

    public void addVehicle(Taxi nTaxi){
        this.vehicles.put(nTaxi.getPlate(), nTaxi.clone());
    }

    public List<Account> getUserList(){
        return this.users.values().stream().map(Account::clone).collect(Collectors.toList());
    }
}
