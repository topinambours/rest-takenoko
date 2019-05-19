package server.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class IllegalArgumentException extends Exception {
    public IllegalArgumentException() {
        super("Illegal Argument");
    }

    public IllegalArgumentException(String message) {
        super(message);
    }
}
