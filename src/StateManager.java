/**
 * Class that manages the interactions between the 
 * classes that make up the UmER StateManager, such as 
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
import java.io.Serializable;

public class StateManager implements Serializable{

    private Map<String,Account> users;
    private Map<String,Taxi> vehicles;

    public StateManager(){
        this.users = new HashMap<>();
        this.vehicles = new HashMap<>();
    }
    
    public StateManager(Map<String,Account> usersL, Map<String,Taxi> vehiclesL){
        this.users = usersL.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
        this.vehicles = vehiclesL.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
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

    public void addVehicle(Taxi nTaxi) throws DuplicateTaxiException{
        if(this.vehicles.containsKey(nTaxi.getPlate())) throw new DuplicateTaxiException(nTaxi.getPlate());
        else this.vehicles.put(nTaxi.getPlate(), nTaxi.clone());
    }

    public List<Account> getUserList(){
        return this.users.values().stream().map(Account::clone).collect(Collectors.toList());
    }

    public boolean userExists(String uEmail){
        return this.users.containsKey(uEmail);
    }

    public void updateUser(Account nInfo){
        if(this.users.containsKey(nInfo.getEmail())) this.users.replace(nInfo.getEmail(), nInfo.clone());
    }

    public Account getUser(String mail){
        return this.users.get(mail).clone();
    }
}
