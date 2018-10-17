package Takenoko.Util.Exceptions;

public class NoActionSelectedException extends Exception {

    public NoActionSelectedException(){
        super("Please select an action to do");
    }
}
