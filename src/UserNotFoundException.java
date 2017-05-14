public class UserNotFoundException extends Exception{
    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String e){
        super(e);
    }
}