package Takenoko.Util.Exceptions;

public class EmptyDeckException extends Exception {

    public EmptyDeckException(){
        super("Draw on empty deck");
    }

}
