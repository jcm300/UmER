import java.io.Serializable;

public class InfoTravel implements Serializable{
    private Client cli;
    private Travel tl;

    public InfoTravel(){
        this.cli = new Client();
        this.tl = new Travel();
    }

    public InfoTravel(Client nC, Travel nT){
        this.cli = nC.clone();
        this.tl = nT.clone();
    }

    public InfoTravel(InfoTravel iT){
        this.cli = iT.getClient();
        this.tl = iT.getTravel();
    }  

    //gets & sets
    public Client getClient(){
        return this.cli;
    }

    public Travel getTravel(){
        return this.tl;
    }

    public void setClient(Client nC){
        this.cli = nC;
    }

    public void setTravel(Travel nT){
        this.tl = nT;
    }

    public boolean equals(Object o){
        if(o==this) return true;
        if((o==null) || this.getClass()!=this.getClass()) return false;
        InfoTravel it = (InfoTravel)o;
        return this.cli.equals(it.getClient()) && this.tl.equals(it.getTravel());
    }

    public InfoTravel clone(){
        return new InfoTravel(this);
    }
    
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append(this.cli.toString()).append("\n");
        ret.append(this.tl.toString());
        return ret.toString();
    }
}
