import java.util.InputMismatchException;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

/**
 * Simple text menu
 *
 * @author José Martins
 * @author Mariana Costa
 * @author Miguel Quaresma
 * @version 1.0
*/
public class Menu{

    private List<String> mChoices;
    private List<String> cliChoices;
    private List<String> dChoices;
    private int opt;

    public Menu(String[] mOps, String[] cliOps, String[] dOps){
        this.opt = -1;
        this.mChoices = Arrays.asList(mOps);
        this.cliChoices = Arrays.asList(cliOps);
        this.dChoices = Arrays.asList(dOps);
    }

    public int getOpt(){
        return this.opt;
    }

    private void setOpt(int x){
       this.opt = x; 
    }

    /**
     * Main menu, presented before login
     */
    public void mMenu(){
        do{
            System.out.println("*** Menu ***");
            showMOptions();
            readOption(this.mChoices.size());
        }while(this.opt == -1);
    }

    /**
     * User menu, available only after a sucessful login
     */
    public void cliMenu(){
        do{
            System.out.println("*** User Menu ***");
            showCliOptions();
            readOption(this.cliChoices.size());
        }while(this.opt == -1);
    }

    /**
     * Driver menu, available only after a sucessful login
     */
    public void dMenu(){
        do{
            System.out.println("*** Driver Menu ***");
            showdOptions();
            readOption(this.dChoices.size());
        }while(this.opt == -1);
    }
        
    /**
     * Shows the actions available to the public
     */
    public void showMOptions(){

        for(int i = 0; i < this.mChoices.size(); i ++){
            System.out.printf("%d-", i+1);
            System.out.println(this.mChoices.get(i));
        }
        System.out.println("0-Exit");        
    }

    /**
     * Shows the actions available to the user
     */
    public void showCliOptions(){
        for(int i = 0; i < this.cliChoices.size(); i ++){
            System.out.printf("%d-", i+1);
            System.out.println(this.cliChoices.get(i));
        }
        System.out.println("0-Exit");
    }

    /**
     * Shows the actions available to the driver
     */
    public void showdOptions(){
        for(int i = 0; i < this.dChoices.size(); i ++){
            System.out.printf("%d-", i+1);
            System.out.println(this.dChoices.get(i));
        }
        System.out.println("0-Exit");
    }

 

 
    /**
     * Reads the option from the user
     */
    public void readOption(int size){
        Scanner in = new Scanner(System.in);

        System.out.print("Choice: ");
        try{
           this.opt = in.nextInt(); 
        }catch(InputMismatchException e){
            System.out.println(e.getMessage());
        }
        if(this.opt < 0 || this.opt > size) this.opt = -1;
    }

}
