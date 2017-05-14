public class WrongPasswordException extends Exception {
 
    public WrongPasswordException(){
        super();
    }

   	public WrongPasswordException(String s){
		super(s);
	}
}
