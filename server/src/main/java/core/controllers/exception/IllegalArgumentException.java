package core.controllers.exception;

public class IllegalArgumentException extends Exception {
    public IllegalArgumentException() {
        super("Illegal Argument");
    }

    public IllegalArgumentException(String message) {
        super(message);
    }
}
