package takenoko.util.exceptions;

public class NoActionSelectedException extends Exception {

    public NoActionSelectedException(){
        super("Please select an action to do");
    }
}
