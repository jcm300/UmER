public class InfoTravel{

    private Driver driv;
    private Travel tl;

    public InfoTravel(){
        this.driv = new Driver();
        this.tl = new Travel();
    }

    public InfoTravel(Driver nD, Travel nT){
        this.driv = nD.clone();
        this.tl = nT.clone();
    }

    public InfoTravel(InfoTravel iT){
        this.driv = iT.getDriver();
        this.tl = iT.getTravel();
    }  

    //gets & sets
    public Driver getDriver(){
        return this.driv.clone();
    }

    public Travel getTravel(){
        return this.tl.clone();
    }

    public void setDriver(Driver nD){
        this.driv = nD;
    }

    public void setTravel(Travel nT){
        this.tl = nT;
    }
}
